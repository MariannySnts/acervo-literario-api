

package br.com.treinarecife.acervo.literario.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.treinarecife.acervo.literario.api.dto.BiografiaRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.BiografiaResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Autor;
import br.com.treinarecife.acervo.literario.api.entity.Biografia;
import br.com.treinarecife.acervo.literario.api.mapper.BiografiaMapper;
import br.com.treinarecife.acervo.literario.api.repository.AutorRepository;
import br.com.treinarecife.acervo.literario.api.repository.BiografiaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BiografiaService {

    private final BiografiaRepository biografiaRepository;
    private final AutorRepository autorRepository;

    public BiografiaService(BiografiaRepository biografiaRepository,
                            AutorRepository autorRepository) {
        this.biografiaRepository = biografiaRepository;
        this.autorRepository = autorRepository;
    }

    public List<BiografiaResponseDTO> listarTodas() {
        return biografiaRepository.findAll().stream()
                .map(BiografiaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public BiografiaResponseDTO buscarPorId(Long id) {
        return biografiaRepository.findById(id)
                .map(BiografiaMapper::toResponse)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Biografia não encontrada."
                    ));
    }

    public BiografiaResponseDTO buscarPorAutor(Long autorId) {
        return biografiaRepository.findByAutorId(autorId)
                .map(BiografiaMapper::toResponse)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Biografia não encontrada para este autor."
                    ));
    }

    public BiografiaResponseDTO salvar(BiografiaRequestDTO dto) {

        if (dto.autorId() == null) {
            throw new IllegalArgumentException(
                "A biografia precisa estar associada a um autor."
            );
        }

        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Autor não encontrado."
                    ));

        if (biografiaRepository.findByAutorId(autor.getId()).isPresent()) {
            throw new IllegalStateException(
                "Este autor já possui uma biografia cadastrada."
            );
        }

        Biografia biografia = BiografiaMapper.toEntity(dto);

        biografia.setAutor(autor);
        autor.setBiografia(biografia);

        return BiografiaMapper.toResponse(
            biografiaRepository.save(biografia)
        );
    }

    public BiografiaResponseDTO atualizar(Long id,
                                          BiografiaRequestDTO dto) {

        Biografia biografia = biografiaRepository.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Biografia não encontrada."
                    ));

        if (dto.autorId() == null) {
            throw new IllegalArgumentException(
                "A biografia precisa estar associada a um autor."
            );
        }

        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Autor não encontrado."
                    ));

        if (!biografia.getAutor().getId().equals(autor.getId())
                && biografiaRepository.findByAutorId(autor.getId()).isPresent()) {
            throw new IllegalStateException(
                "Este autor já possui uma biografia cadastrada."
            );
        }

        biografia.setTexto(dto.texto());
        biografia.setDataNascimento(dto.dataNascimento());

        Autor autorAnterior = biografia.getAutor();

        if (!autorAnterior.getId().equals(autor.getId())) {
            autorAnterior.setBiografia(null);
            autor.setBiografia(biografia);
            biografia.setAutor(autor);
        }

        return BiografiaMapper.toResponse(
            biografiaRepository.save(biografia)
        );
    }

    public void excluir(Long id) {
        Biografia biografia = biografiaRepository.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Biografia não encontrada."
                    ));

        biografiaRepository.delete(biografia);
    }
}

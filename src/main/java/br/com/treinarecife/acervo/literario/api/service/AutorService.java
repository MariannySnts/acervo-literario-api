

package br.com.treinarecife.acervo.literario.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.treinarecife.acervo.literario.api.dto.AutorRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.AutorResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Autor;
import br.com.treinarecife.acervo.literario.api.mapper.AutorMapper;
import br.com.treinarecife.acervo.literario.api.repository.AutorRepository;
import br.com.treinarecife.acervo.literario.api.repository.LivroRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository autorRepository,
                        LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public List<AutorResponseDTO> listarTodos() {
        return autorRepository.findAll().stream()
                .map(AutorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AutorResponseDTO buscarPorId(Long id) {
        return autorRepository.findById(id)
                .map(AutorMapper::toResponse)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Autor não encontrado."
                    ));
    }

    public AutorResponseDTO salvar(AutorRequestDTO dto) {

        Autor autor = AutorMapper.toEntity(dto);

        return AutorMapper.toResponse(
            autorRepository.save(autor)
        );
    }

    public AutorResponseDTO atualizar(Long id,
                                      AutorRequestDTO dto) {

        AutorResponseDTO autorDTO = buscarPorId(id);

        Autor autor = autorRepository.findById(autorDTO.id())
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Autor não encontrado."
                    ));

        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());

        return AutorMapper.toResponse(
            autorRepository.save(autor)
        );
    }

    public void excluir(Long id) {

        AutorResponseDTO autorDTO = buscarPorId(id);

        if (livroRepository.existsByAutorId(autorDTO.id())) {
            throw new IllegalStateException(
                "Não é possível excluir um autor que possui livros cadastrados."
            );
        }

        autorRepository.deleteById(autorDTO.id());
    }
}


package br.com.treinarecife.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.treinarecife.acervo.literario.api.entity.Autor;
import br.com.treinarecife.acervo.literario.api.entity.Biografia;
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

    public List<Biografia> listarTodas() {
        return biografiaRepository.findAll();
    }

    public Biografia buscarPorId(Long id) {
        return biografiaRepository.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException("Biografia não encontrada."));
    }

    public Biografia buscarPorAutor(Long autorId) {
        return biografiaRepository.findByAutorId(autorId)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Biografia não encontrada para este autor."
                    ));
    }

    public Biografia salvar(Biografia biografia) {

        if (biografia.getAutor() == null ||
            biografia.getAutor().getId() == null) {
            throw new IllegalArgumentException(
                "A biografia precisa estar associada a um autor."
            );
        }

        Autor autor = autorRepository.findById(biografia.getAutor().getId())
                .orElseThrow(() ->
                    new EntityNotFoundException("Autor não encontrado."));

        if (biografiaRepository.findByAutorId(autor.getId()).isPresent()) {
            throw new IllegalStateException(
                "Este autor já possui uma biografia cadastrada."
            );
        }

        biografia.setAutor(autor);
        autor.setBiografia(biografia);

        return biografiaRepository.save(biografia);
    }

    public Biografia atualizar(Long id, Biografia dados) {
        Biografia biografia = buscarPorId(id);

        biografia.setTexto(dados.getTexto());
        biografia.setDataNascimento(dados.getDataNascimento());

        return biografiaRepository.save(biografia);
    }

    public void excluir(Long id) {
        Biografia biografia = buscarPorId(id);
        biografiaRepository.delete(biografia);
    }
}


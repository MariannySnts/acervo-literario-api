
package br.com.treinarecife.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.treinarecife.acervo.literario.api.entity.Autor;
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

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException("Autor não encontrado."));
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor atualizar(Long id, Autor dados) {
        Autor autor = buscarPorId(id);

        autor.setNome(dados.getNome());
        autor.setNacionalidade(dados.getNacionalidade());

        return autorRepository.save(autor);
    }

    public void excluir(Long id) {
        Autor autor = buscarPorId(id);

        if (livroRepository.existsByAutorId(id)) {
            throw new IllegalStateException(
                "Não é possível excluir este autor, pois ele possui livros cadastrados."
            );
        }

        autorRepository.delete(autor);
    }
}

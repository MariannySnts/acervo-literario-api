
package br.com.treinarecife.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.treinarecife.acervo.literario.api.entity.Autor;
import br.com.treinarecife.acervo.literario.api.entity.Categoria;
import br.com.treinarecife.acervo.literario.api.entity.Livro;
import br.com.treinarecife.acervo.literario.api.repository.AutorRepository;
import br.com.treinarecife.acervo.literario.api.repository.CategoriaRepository;
import br.com.treinarecife.acervo.literario.api.repository.LivroRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroService(LivroRepository livroRepository,
            AutorRepository autorRepository,
            CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado."));
    }

    public Livro salvar(Livro livro) {
        validarRelacionamentos(livro);
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro dados) {
        Livro livro = buscarPorId(id);

        livro.setTitulo(dados.getTitulo());
        livro.setIsbn(dados.getIsbn());
        livro.setAnoPublicacao(dados.getAnoPublicacao());

        validarRelacionamentos(dados);

        livro.setAutor(dados.getAutor());
        livro.setCategorias(dados.getCategorias());

        return livroRepository.save(livro);
    }

    public void excluir(Long id) {
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }

    public List<Livro> buscarPorCategoria(String nome) {
        return livroRepository.findByCategoriasNome(nome);
    }

    public List<Livro> buscarPorTitulo(String termo) {
        return livroRepository.findByTituloContainingIgnoreCase(termo);
    }

    private void validarRelacionamentos(Livro livro) {

        if (livro.getAutor() == null || livro.getAutor().getId() == null) {
            throw new IllegalArgumentException(
                    "O livro precisa estar associado a um autor.");
        }

        Autor autor = autorRepository.findById(livro.getAutor().getId())
                .orElseThrow(() -> new EntityNotFoundException("Autor informado não existe."));

        if (livro.getCategorias() == null || livro.getCategorias().isEmpty()) {
            throw new IllegalArgumentException(
                    "O livro precisa ter pelo menos uma categoria.");
        }

        List<Long> idsCategorias = livro.getCategorias()
                .stream()
                .map(Categoria::getId)
                .toList();

        if (idsCategorias.contains(null)) {
            throw new IllegalArgumentException(
                    "Todas as categorias precisam possuir um ID válido.");
        }

        List<Categoria> categorias = new ArrayList<>();

        categoriaRepository.findAllById(idsCategorias)
                .forEach(categorias::add);

        if (categorias.size() != idsCategorias.size()) {
            throw new EntityNotFoundException(
                    "Uma ou mais categorias informadas não existem.");
        }

        livro.setAutor(autor);
        livro.setCategorias(categorias);
    }
}

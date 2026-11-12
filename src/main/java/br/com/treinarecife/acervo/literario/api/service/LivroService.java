

package br.com.treinarecife.acervo.literario.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.treinarecife.acervo.literario.api.dto.LivroRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.LivroResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Autor;
import br.com.treinarecife.acervo.literario.api.entity.Categoria;
import br.com.treinarecife.acervo.literario.api.entity.Livro;
import br.com.treinarecife.acervo.literario.api.mapper.LivroMapper;
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

    public List<LivroResponseDTO> listarTodos() {
        return livroRepository.findAll().stream()
                .map(LivroMapper::toResponse)
                .collect(Collectors.toList());
    }

    public LivroResponseDTO buscarPorId(Long id) {
        return livroRepository.findById(id)
                .map(LivroMapper::toResponse)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Livro não encontrado."
                    ));
    }

    public List<LivroResponseDTO> buscarPorCategoria(String nome) {
        return livroRepository.findByCategoriasNome(nome).stream()
                .map(LivroMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<LivroResponseDTO> buscarPorTitulo(String termo) {
        return livroRepository.findByTituloContainingIgnoreCase(termo)
                .stream()
                .map(LivroMapper::toResponse)
                .collect(Collectors.toList());
    }

    public LivroResponseDTO salvar(LivroRequestDTO dto) {

        if (dto.autorId() == null) {
            throw new IllegalArgumentException(
                "O livro precisa estar associado a um autor."
            );
        }

        if (dto.categoriasIds() == null ||
            dto.categoriasIds().isEmpty()) {
            throw new IllegalArgumentException(
                "O livro precisa possuir pelo menos uma categoria."
            );
        }

        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Autor não encontrado."
                    ));

        List<Categoria> categorias =
                categoriaRepository.findAllById(dto.categoriasIds());

        if (categorias.size() != dto.categoriasIds().stream()
                .distinct().count()) {
            throw new EntityNotFoundException(
                "Uma ou mais categorias não foram encontradas."
            );
        }

        Livro livro = LivroMapper.toEntity(dto);

        livro.setAutor(autor);
        livro.setCategorias(categorias);

        return LivroMapper.toResponse(
            livroRepository.save(livro)
        );
    }

    public LivroResponseDTO atualizar(Long id,
                                       LivroRequestDTO dto) {

        LivroResponseDTO livroDTO = buscarPorId(id);

        Livro livro = livroRepository.findById(livroDTO.id())
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Livro não encontrado."
                    ));

        if (dto.autorId() == null) {
            throw new IllegalArgumentException(
                "O livro precisa estar associado a um autor."
            );
        }

        if (dto.categoriasIds() == null ||
            dto.categoriasIds().isEmpty()) {
            throw new IllegalArgumentException(
                "O livro precisa possuir pelo menos uma categoria."
            );
        }

        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Autor não encontrado."
                    ));

        List<Categoria> categorias =
                categoriaRepository.findAllById(dto.categoriasIds());

        if (categorias.size() != dto.categoriasIds().stream()
                .distinct().count()) {
            throw new EntityNotFoundException(
                "Uma ou mais categorias não foram encontradas."
            );
        }

        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setAnoPublicacao(dto.anoPublicacao());
        livro.setAutor(autor);
        livro.setCategorias(categorias);

        return LivroMapper.toResponse(
            livroRepository.save(livro)
        );
    }

    public void excluir(Long id) {

        LivroResponseDTO livroDTO = buscarPorId(id);

        livroRepository.deleteById(livroDTO.id());
    }
}

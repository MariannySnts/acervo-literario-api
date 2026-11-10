
package br.com.treinarecife.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treinarecife.DTO.LivroRequestDTO;
import br.com.treinarecife.DTO.LivroResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Autor;
import br.com.treinarecife.acervo.literario.api.entity.Categoria;
import br.com.treinarecife.acervo.literario.api.entity.Livro;
import br.com.treinarecife.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listarTodos() {
        List<LivroResponseDTO> livros = livroService.listarTodos()
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(
            @PathVariable Long id) {

        Livro livro = livroService.buscarPorId(id);

        return ResponseEntity.ok(converterParaDTO(livro));
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> salvar(
            @RequestBody LivroRequestDTO dados) {

        Livro livro = converterParaEntidade(dados);

        Livro salvo = livroService.salvar(livro);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converterParaDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody LivroRequestDTO dados) {

        Livro livro = converterParaEntidade(dados);

        Livro atualizado = livroService.atualizar(id, livro);

        return ResponseEntity.ok(converterParaDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<LivroResponseDTO>> buscarPorCategoria(
            @RequestParam String nome) {

        List<LivroResponseDTO> livros =
                livroService.buscarPorCategoria(nome)
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(livros);
    }

    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<LivroResponseDTO>> buscarPorTitulo(
            @RequestParam String termo) {

        List<LivroResponseDTO> livros =
                livroService.buscarPorTitulo(termo)
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(livros);
    }

    private Livro converterParaEntidade(LivroRequestDTO dados) {
        Livro livro = new Livro();

        livro.setTitulo(dados.titulo());
        livro.setIsbn(dados.isbn());
        livro.setAnoPublicacao(dados.anoPublicacao());

        Autor autor = new Autor();
        autor.setId(dados.autorId());
        livro.setAutor(autor);

        List<Categoria> categorias = dados.categoriasIds()
                .stream()
                .map(id -> {
                    Categoria categoria = new Categoria();
                    categoria.setId(id);
                    return categoria;
                })
                .toList();

        livro.setCategorias(categorias);

        return livro;
    }

    private LivroResponseDTO converterParaDTO(Livro livro) {
        List<Long> categoriasIds = livro.getCategorias()
                .stream()
                .map(Categoria::getId)
                .toList();

        return new LivroResponseDTO(
            livro.getId(),
            livro.getTitulo(),
            livro.getIsbn(),
            livro.getAnoPublicacao(),
            livro.getAutor().getId(),
            categoriasIds
        );
    }
}
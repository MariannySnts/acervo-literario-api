

package br.com.treinarecife.acervo.literario.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treinarecife.acervo.literario.api.dto.LivroRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.LivroResponseDTO;
import br.com.treinarecife.acervo.literario.api.service.LivroService;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<LivroResponseDTO>> buscarPorCategoria(
            @RequestParam String nome) {

        return ResponseEntity.ok(
            livroService.buscarPorCategoria(nome)
        );
    }

    @GetMapping("/titulo")
    public ResponseEntity<List<LivroResponseDTO>> buscarPorTitulo(
            @RequestParam String termo) {

        return ResponseEntity.ok(
            livroService.buscarPorTitulo(termo)
        );
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> salvar(
            @RequestBody LivroRequestDTO dto) {

        LivroResponseDTO livro = livroService.salvar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(livro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody LivroRequestDTO dto) {

        return ResponseEntity.ok(
            livroService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        livroService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

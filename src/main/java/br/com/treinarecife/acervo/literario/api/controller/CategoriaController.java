
package br.com.treinarecife.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treinarecife.acervo.literario.api.entity.Categoria;
import br.com.treinarecife.dto.CategoriaRequestDTO;
import br.com.treinarecife.dto.CategoriaResponseDTO;
import br.com.treinarecife.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        List<CategoriaResponseDTO> categorias =
                categoriaService.listarTodas()
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        Categoria categoria = categoriaService.buscarPorId(id);

        return ResponseEntity.ok(converterParaDTO(categoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(
            @RequestBody CategoriaRequestDTO dados) {

        Categoria categoria = new Categoria();
        categoria.setNome(dados.nome());

        Categoria salva = categoriaService.salvar(categoria);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converterParaDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody CategoriaRequestDTO dados) {

        Categoria categoria = new Categoria();
        categoria.setNome(dados.nome());

        Categoria atualizada =
                categoriaService.atualizar(id, categoria);

        return ResponseEntity.ok(converterParaDTO(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private CategoriaResponseDTO converterParaDTO(
            Categoria categoria) {

        return new CategoriaResponseDTO(
            categoria.getId(),
            categoria.getNome()
        );
    }
}

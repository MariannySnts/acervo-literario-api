
package br.com.treinarecife.acervo.literario.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treinarecife.acervo.literario.api.dto.CategoriaRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.CategoriaResponseDTO;
import br.com.treinarecife.acervo.literario.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(
            @RequestBody CategoriaRequestDTO dados) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.salvar(dados));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody CategoriaRequestDTO dados) {

        return ResponseEntity.ok(categoriaService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }


}

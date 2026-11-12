

package br.com.treinarecife.acervo.literario.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treinarecife.acervo.literario.api.dto.AutorRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.AutorResponseDTO;
import br.com.treinarecife.acervo.literario.api.service.AutorService;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(autorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AutorResponseDTO> salvar(
            @RequestBody AutorRequestDTO dto) {

        AutorResponseDTO autor = autorService.salvar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(autor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody AutorRequestDTO dto) {

        return ResponseEntity.ok(autorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        autorService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

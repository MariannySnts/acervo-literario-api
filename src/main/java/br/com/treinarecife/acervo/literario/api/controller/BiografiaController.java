

package br.com.treinarecife.acervo.literario.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treinarecife.acervo.literario.api.dto.BiografiaRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.BiografiaResponseDTO;
import br.com.treinarecife.acervo.literario.api.service.BiografiaService;

@RestController
@RequestMapping("/api/biografias")
public class BiografiaController {

    private final BiografiaService biografiaService;

    public BiografiaController(BiografiaService biografiaService) {
        this.biografiaService = biografiaService;
    }

    @GetMapping
    public ResponseEntity<List<BiografiaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(biografiaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BiografiaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(biografiaService.buscarPorId(id));
    }

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<BiografiaResponseDTO> buscarPorAutor(
            @PathVariable Long autorId) {

        return ResponseEntity.ok(
            biografiaService.buscarPorAutor(autorId)
        );
    }

    @PostMapping
    public ResponseEntity<BiografiaResponseDTO> salvar(
            @RequestBody BiografiaRequestDTO dto) {

        BiografiaResponseDTO biografia = biografiaService.salvar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(biografia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BiografiaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody BiografiaRequestDTO dto) {

        return ResponseEntity.ok(
            biografiaService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        biografiaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

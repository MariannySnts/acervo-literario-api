
package br.com.treinarecife.acervo.literario.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treinarecife.acervo.literario.api.dto.BiografiaRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.BiografiaResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Autor;
import br.com.treinarecife.acervo.literario.api.entity.Biografia;
import br.com.treinarecife.acervo.literario.api.service.BiografiaService;

@RestController
@RequestMapping("/biografias")
public class BiografiaController {

    private final BiografiaService biografiaService;

    public BiografiaController(BiografiaService biografiaService) {
        this.biografiaService = biografiaService;
    }

    @GetMapping
    public ResponseEntity<List<BiografiaResponseDTO>> listarTodas() {
        List<BiografiaResponseDTO> biografias =
                biografiaService.listarTodas()
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(biografias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BiografiaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        Biografia biografia = biografiaService.buscarPorId(id);

        return ResponseEntity.ok(converterParaDTO(biografia));
    }

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<BiografiaResponseDTO> buscarPorAutor(
            @PathVariable Long autorId) {

        Biografia biografia =
                biografiaService.buscarPorAutor(autorId);

        return ResponseEntity.ok(converterParaDTO(biografia));
    }

    @PostMapping
    public ResponseEntity<BiografiaResponseDTO> salvar(
            @RequestBody BiografiaRequestDTO dados) {

        Biografia biografia = converterParaEntidade(dados);

        Biografia salva = biografiaService.salvar(biografia);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converterParaDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BiografiaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody BiografiaRequestDTO dados) {

        Biografia biografia = converterParaEntidade(dados);

        Biografia atualizada =
                biografiaService.atualizar(id, biografia);

        return ResponseEntity.ok(converterParaDTO(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        biografiaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Biografia converterParaEntidade(
            BiografiaRequestDTO dados) {

        Biografia biografia = new Biografia();

        biografia.setTexto(dados.texto());
        biografia.setDataNascimento(dados.dataNascimento());

        if (dados.autorId() != null) {
            Autor autor = new Autor();
            autor.setId(dados.autorId());
            biografia.setAutor(autor);
        }

        return biografia;
    }

    private BiografiaResponseDTO converterParaDTO(
            Biografia biografia) {

        return new BiografiaResponseDTO(
            biografia.getId(),
            biografia.getTexto(),
            biografia.getDataNascimento(),
            biografia.getAutor().getId()
        );
    }
}
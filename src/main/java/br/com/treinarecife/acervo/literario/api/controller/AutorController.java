
package br.com.treinarecife.acervo.literario.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treinarecife.acervo.literario.api.dto.AutorRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.AutorResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Autor;
import br.com.treinarecife.acervo.literario.api.service.AutorService;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarTodos() {
        List<AutorResponseDTO> autores = autorService.listarTodos()
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(autores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscarPorId(
            @PathVariable Long id) {

        Autor autor = autorService.buscarPorId(id);

        return ResponseEntity.ok(converterParaDTO(autor));
    }

    @PostMapping
    public ResponseEntity<AutorResponseDTO> salvar(
            @RequestBody AutorRequestDTO dados) {

        Autor autor = new Autor();
        autor.setNome(dados.nome());
        autor.setNacionalidade(dados.nacionalidade());

        Autor salvo = autorService.salvar(autor);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converterParaDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody AutorRequestDTO dados) {

        Autor autor = new Autor();
        autor.setNome(dados.nome());
        autor.setNacionalidade(dados.nacionalidade());

        Autor atualizado = autorService.atualizar(id, autor);

        return ResponseEntity.ok(converterParaDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        autorService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private AutorResponseDTO converterParaDTO(Autor autor) {
        return new AutorResponseDTO(
            autor.getId(),
            autor.getNome(),
            autor.getNacionalidade()
        );
    }
}

package br.com.treinarecife.acervo.literario.api.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.treinarecife.acervo.literario.api.dto.LivroRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.LivroResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Livro;

public class LivroMapper {

    public static Livro toEntity(LivroRequestDTO dto) {

        Livro livro = new Livro();

        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setAnoPublicacao(dto.anoPublicacao());

        return livro;
    }

    public static LivroResponseDTO toResponse(Livro livro) {

        Long autorId = livro.getAutor() != null
                ? livro.getAutor().getId()
                : null;

        List<Long> categoriasIds = livro.getCategorias() != null
                ? livro.getCategorias().stream()
                    .map(categoria -> categoria.getId())
                    .collect(Collectors.toList())
                : Collections.emptyList();

        return new LivroResponseDTO(
            livro.getId(),
            livro.getTitulo(),
            livro.getIsbn(),
            livro.getAnoPublicacao(),
            autorId,
            categoriasIds
        );
    }
}

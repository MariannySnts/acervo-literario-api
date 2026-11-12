
package br.com.treinarecife.acervo.literario.api.dto;

import java.util.List;

public record LivroRequestDTO(
    String titulo,
    String isbn,
    Integer anoPublicacao,
    Long autorId,
    List<Long> categoriasIds
) {}

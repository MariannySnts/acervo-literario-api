
package br.com.treinarecife.dto;

import java.util.List;

public record LivroRequestDTO(
    String titulo,
    String isbn,
    Integer anoPublicacao,
    Long autorId,
    List<Long> categoriasIds
) {}

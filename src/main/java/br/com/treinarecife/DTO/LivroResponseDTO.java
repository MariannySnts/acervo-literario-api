
package br.com.treinarecife.DTO;

import java.util.List;

public record LivroResponseDTO(
    Long id,
    String titulo,
    String isbn,
    Integer anoPublicacao,
    Long autorId,
    List<Long> categoriasIds
) {}
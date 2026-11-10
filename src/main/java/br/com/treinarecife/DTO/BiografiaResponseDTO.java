
package br.com.treinarecife.DTO;

import java.time.LocalDate;

public record BiografiaResponseDTO(
    Long id,
    String texto,
    LocalDate dataNascimento,
    Long autorId
) {}

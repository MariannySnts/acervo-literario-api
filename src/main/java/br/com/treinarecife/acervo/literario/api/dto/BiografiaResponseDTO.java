
package br.com.treinarecife.dto;

import java.time.LocalDate;

public record BiografiaResponseDTO(
    Long id,
    String texto,
    LocalDate dataNascimento,
    Long autorId
) {}


package br.com.treinarecife.DTO;

import java.time.LocalDate;

public record BiografiaRequestDTO(
    String texto,
    LocalDate dataNascimento,
    Long autorId
) {}
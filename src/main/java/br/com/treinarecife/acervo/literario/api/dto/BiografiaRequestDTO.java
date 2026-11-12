
package br.com.treinarecife.dto;

import java.time.LocalDate;

public record BiografiaRequestDTO(
    String texto,
    LocalDate dataNascimento,
    Long autorId
) {}
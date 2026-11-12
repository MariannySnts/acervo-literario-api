
package br.com.treinarecife.acervo.literario.api.dto;

import java.time.LocalDate;

public record BiografiaRequestDTO(
    String texto,
    LocalDate dataNascimento,
    Long autorId
) {}

package br.com.treinarecife.acervo.literario.api.dto;

import java.time.LocalDate;

public record BiografiaResponseDTO(
    Long id,
    String texto,
    LocalDate dataNascimento,
    Long autorId
) {}

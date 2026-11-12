
package br.com.treinarecife.acervo.literario.api.mapper;

import br.com.treinarecife.acervo.literario.api.dto.BiografiaRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.BiografiaResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Biografia;

public class BiografiaMapper {

    public static Biografia toEntity(BiografiaRequestDTO dto) {

        Biografia biografia = new Biografia();

        biografia.setTexto(dto.texto());
        biografia.setDataNascimento(dto.dataNascimento());

        return biografia;
    }

    public static BiografiaResponseDTO toResponse(Biografia biografia) {

        Long autorId = biografia.getAutor() != null
                ? biografia.getAutor().getId()
                : null;

        return new BiografiaResponseDTO(
            biografia.getId(),
            biografia.getTexto(),
            biografia.getDataNascimento(),
            autorId
        );
    }
}

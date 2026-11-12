
package br.com.treinarecife.acervo.literario.api.mapper;

import br.com.treinarecife.acervo.literario.api.dto.AutorRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.AutorResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Autor;

public class AutorMapper {

    public static Autor toEntity(AutorRequestDTO dto) {

        Autor autor = new Autor();

        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());

        return autor;
    }

    public static AutorResponseDTO toResponse(Autor autor) {

        return new AutorResponseDTO(
            autor.getId(),
            autor.getNome(),
            autor.getNacionalidade()
        );
    }
}


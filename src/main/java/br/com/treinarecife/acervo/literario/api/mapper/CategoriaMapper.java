
package br.com.treinarecife.acervo.literario.api.mapper;

import br.com.treinarecife.acervo.literario.api.dto.CategoriaRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.CategoriaResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Categoria;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaRequestDTO dto) {

        Categoria categoria = new Categoria();

        categoria.setNome(dto.nome());

        return categoria;
    }

    public static CategoriaResponseDTO toResponse(Categoria categoria) {

        return new CategoriaResponseDTO(
            categoria.getId(),
            categoria.getNome()
        );
    }

}

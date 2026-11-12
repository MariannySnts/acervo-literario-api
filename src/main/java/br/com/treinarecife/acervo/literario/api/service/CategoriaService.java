
package br.com.treinarecife.acervo.literario.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.treinarecife.acervo.literario.api.dto.CategoriaRequestDTO;
import br.com.treinarecife.acervo.literario.api.dto.CategoriaResponseDTO;
import br.com.treinarecife.acervo.literario.api.entity.Categoria;
import br.com.treinarecife.acervo.literario.api.mapper.CategoriaMapper;
import br.com.treinarecife.acervo.literario.api.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(CategoriaMapper::toResponse)
                .orElseThrow(() ->
                    new EntityNotFoundException("Categoria não encontrada."));
    }

    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Categoria categoria = CategoriaMapper.toEntity(dto);

        return CategoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        CategoriaResponseDTO categoriaDTO =buscarPorId(id);

        Categoria categoria = CategoriaMapper.toEntity(dto);
        

        categoria.setNome(dto.nome());

        return CategoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    public void excluir(Long id) {
        CategoriaResponseDTO categoriaDTO = buscarPorId(id);

        categoriaRepository.deleteById(categoriaDTO.id());
    }
}


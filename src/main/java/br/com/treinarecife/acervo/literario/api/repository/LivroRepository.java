
package br.com.treinarecife.acervo.literario.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.treinarecife.acervo.literario.api.entity.Livro;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Buscar livros pelo nome da categoria
    List<Livro> findByCategoriasNome(String nome);

    // Buscar livros por trecho do título
    List<Livro> findByTituloContainingIgnoreCase(String termo);

    // Verificar se um autor possui livros cadastrados
    boolean existsByAutorId(Long autorId);

}

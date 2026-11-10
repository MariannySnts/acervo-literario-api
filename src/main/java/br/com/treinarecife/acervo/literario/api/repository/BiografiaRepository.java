
package br.com.treinarecife.acervo.literario.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.treinarecife.acervo.literario.api.entity.Biografia;

import java.util.Optional;

public interface BiografiaRepository extends JpaRepository<Biografia, Long> {

    // Buscar biografia pelo ID do autor
    Optional<Biografia> findByAutorId(Long autorId);

}


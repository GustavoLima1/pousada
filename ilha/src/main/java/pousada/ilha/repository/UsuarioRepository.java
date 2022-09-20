
package pousada.ilha.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pousada.ilha.model.UsuarioDTO;

public interface UsuarioRepository extends JpaRepository<UsuarioDTO, Long>{
	public Optional<UsuarioDTO> findByUsuario(String usuario);
}
package connectforospa_registro.repository;

import connectforospa_registro.model.RegistroUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroUsuarioRepository extends JpaRepository<RegistroUsuario, Long> {
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
    RegistroUsuario findByEmail(String email); 
}


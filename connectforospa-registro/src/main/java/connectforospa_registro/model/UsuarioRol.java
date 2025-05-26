package connectforospa_registro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario_rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
    private Long idRol; 
    private String nombreRol;
    private String nombreUsuario;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference 
    private RegistroUsuario usuario;
}
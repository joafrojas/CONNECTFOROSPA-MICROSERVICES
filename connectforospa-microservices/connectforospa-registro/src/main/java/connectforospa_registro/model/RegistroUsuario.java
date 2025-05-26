package connectforospa_registro.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    @Column(unique = true, nullable = false)
    private String email;

    private String nombre;
    private String password;
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private String estado;

    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference 
    private List<UsuarioRol> roles;


}

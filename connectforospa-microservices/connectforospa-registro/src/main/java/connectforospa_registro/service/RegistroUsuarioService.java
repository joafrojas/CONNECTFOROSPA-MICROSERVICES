package connectforospa_registro.service;

import connectforospa_registro.model.RegistroUsuario;
import connectforospa_registro.model.UsuarioRol;
import connectforospa_registro.repository.RegistroUsuarioRepository;
import connectforospa_registro.repository.UsuarioRolRepository;
import connectforospa_registro.webclient.UsuarioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistroUsuarioService {

    private final RegistroUsuarioRepository usuarioRepo;
    private final UsuarioRolRepository rolRepo;
    private final UsuarioClient usuarioClient;

    @Transactional
    public RegistroUsuario guardar(RegistroUsuario usuario) {
        RegistroUsuario usuarioExistente = usuarioRepo.findByEmail(usuario.getEmail());
        if (usuarioExistente != null) {
            if ("ELIMINADO".equals(usuarioExistente.getEstado())) {
                usuarioExistente.setEstado("ACTIVO");
                usuarioRepo.save(usuarioExistente);
                return usuarioExistente;
            }
            log.warn("El usuario con email {} ya existe.", usuario.getEmail());
            return null;
        }

        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setEstado("ACTIVO");
        RegistroUsuario usuarioGuardado = usuarioRepo.save(usuario);

        Long idRol = usuario.getEmail().endsWith("@coadmin.connectforo.cl") ? 0L  
                   : usuario.getEmail().endsWith("@connectforo.cl") ? 1L : 2L;

        String rol = switch (idRol.intValue()) {
            case 0 -> "ROLE_COADMIN";
            case 1 -> "ROLE_ADMIN";
            default -> "ROLE_USER";
        };

        UsuarioRol nuevoRol = new UsuarioRol();
        nuevoRol.setIdRol(idRol);
        nuevoRol.setNombreRol(rol);
        nuevoRol.setNombreUsuario(usuarioGuardado.getNombreUsuario());
        nuevoRol.setUsuario(usuarioGuardado);

        rolRepo.save(nuevoRol);

        usuarioClient.crearUsuarioAuth(usuarioGuardado.getIdUsuario(), usuarioGuardado.getEmail(), usuarioGuardado.getPassword(), usuarioGuardado.getEstado())
                .doOnError(error -> log.error("Error al comunicar con usuarioauth", error))
                .subscribe();

        return usuarioGuardado;
    }

    public RegistroUsuario obtenerPorId(Long id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    public RegistroUsuario actualizar(Long id, RegistroUsuario usuarioActualizado) {
        RegistroUsuario usuarioExistente = usuarioRepo.findById(id).orElse(null);
        if (usuarioExistente != null && !"ELIMINADO".equals(usuarioExistente.getEstado())) {
            usuarioActualizado.setIdUsuario(id);
            usuarioActualizado.setFechaCreacion(usuarioExistente.getFechaCreacion());
            usuarioRepo.save(usuarioActualizado);

            usuarioClient.actualizarEstadoUsuarioAuth(usuarioActualizado.getIdUsuario(), usuarioActualizado.getEstado())
                    .doOnError(error -> log.error("Error al actualizar estado en usuarioauth", error))
                    .subscribe();

            return usuarioActualizado;
        }
        return null;
    }

    public boolean eliminar(Long id) {
        RegistroUsuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setEstado("ELIMINADO");
            usuarioRepo.save(usuario);

            usuarioClient.actualizarEstadoUsuarioAuth(usuario.getIdUsuario(), "ELIMINADO")
                    .doOnError(error -> log.error("Error al actualizar estado en usuarioauth", error))
                    .subscribe();

            return true;
        }
        return false;
    }

    public boolean eliminarUsuarioAdmin(Long idAdmin, Long idUsuarioAEliminar) {
        RegistroUsuario admin = usuarioRepo.findById(idAdmin).orElse(null);
        RegistroUsuario usuarioAEliminar = usuarioRepo.findById(idUsuarioAEliminar).orElse(null);

        if (admin != null && usuarioAEliminar != null) {
            boolean esCoadmin = admin.getEmail().endsWith("@coadmin.connectforo.cl");
            boolean esAdmin = admin.getEmail().endsWith("@connectforo.cl");
            boolean esUsuario = usuarioAEliminar.getEmail().endsWith("@user.connectforo.cl");
            boolean esAdminAEliminar = usuarioAEliminar.getEmail().endsWith("@connectforo.cl");

            if ((esCoadmin && !usuarioAEliminar.getEmail().endsWith("@coadmin.connectforo.cl")) || 
                (esAdmin && esUsuario)) {  
                return eliminar(idUsuarioAEliminar);
            }

            if (esCoadmin && esAdminAEliminar) {
                return eliminar(idUsuarioAEliminar);
            }
        }
        return false;
    }

    public RegistroUsuario actualizarEstado(Long id, String nuevoEstado) {
        RegistroUsuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario != null && !"ELIMINADO".equals(usuario.getEstado())) {
            usuario.setEstado(nuevoEstado);
            usuarioRepo.save(usuario);

            usuarioClient.actualizarEstadoUsuarioAuth(usuario.getIdUsuario(), nuevoEstado)
                    .doOnError(error -> log.error("Error al actualizar estado en usuarioauth", error))
                    .subscribe();

            return usuario;
        }
        return null;
    }
}

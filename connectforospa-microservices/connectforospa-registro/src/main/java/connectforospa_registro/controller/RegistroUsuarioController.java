package connectforospa_registro.controller;

import connectforospa_registro.model.RegistroUsuario;
import connectforospa_registro.service.RegistroUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registro")
@RequiredArgsConstructor
public class RegistroUsuarioController {

    private final RegistroUsuarioService service;

    @PostMapping
    public ResponseEntity<RegistroUsuario> crearUsuario(@RequestBody RegistroUsuario usuario) {
        RegistroUsuario usuarioCreado = service.guardar(usuario);
        return usuarioCreado != null ? ResponseEntity.ok(usuarioCreado) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroUsuario> obtenerUsuario(@PathVariable Long id) {
        RegistroUsuario usuario = service.obtenerPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroUsuario> actualizarUsuario(@PathVariable Long id, @RequestBody RegistroUsuario usuarioActualizado) {
        RegistroUsuario usuario = service.actualizar(id, usuarioActualizado);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<RegistroUsuario> actualizarEstadoUsuario(@PathVariable Long id, @RequestBody ActualizarEstado estado) {
        RegistroUsuario usuarioActualizado = service.actualizarEstado(id, estado.estado());
        return usuarioActualizado != null ? ResponseEntity.ok(usuarioActualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        return service.eliminar(id) ? ResponseEntity.ok("Usuario eliminado correctamente.") : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/admin/{idAdmin}/eliminar/{idUsuarioAEliminar}")
    public ResponseEntity<String> eliminarUsuarioAdmin(@PathVariable Long idAdmin, @PathVariable Long idUsuarioAEliminar) {
        return service.eliminarUsuarioAdmin(idAdmin, idUsuarioAEliminar) 
            ? ResponseEntity.ok("Usuario eliminado por un administrador.") 
            : ResponseEntity.status(403).body("No autorizado para eliminar este usuario.");
    }

    private record ActualizarEstado(String estado) {}
}

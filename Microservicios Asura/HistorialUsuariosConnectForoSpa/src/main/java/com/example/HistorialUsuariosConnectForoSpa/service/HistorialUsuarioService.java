package com.example.HistorialUsuariosConnectForoSpa.service;

import com.example.HistorialUsuariosConnectForoSpa.model.HistorialUsuario;
import com.example.HistorialUsuariosConnectForoSpa.repository.HistorialUsuarioRepository;
import com.example.HistorialUsuariosConnectForoSpa.webclient.UsuarioClient;
import com.example.HistorialUsuariosConnectForoSpa.webclient.dto.HistorialUsuarioResponse;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialUsuarioService {

    private final HistorialUsuarioRepository repository;
    private final UsuarioClient usuarioClient;

    public HistorialUsuarioService(HistorialUsuarioRepository repository, UsuarioClient usuarioClient){
        this.repository = repository;
        this.usuarioClient = usuarioClient;
    }

    public List<HistorialUsuarioResponse> getAll() {
        return repository.findAll().stream()
            .map(historial -> usuarioClient.obtenerUsuarioPorId(historial.getId_usuario())
                .map(usuario -> new HistorialUsuarioResponse(
                        historial.getId_historial(),
                        historial.getAccion(),
                        historial.getFecha_accion(),
                        historial.getId_usuario(),
                        usuario.getNombreUsuario()))
                .defaultIfEmpty(new HistorialUsuarioResponse(
                        historial.getId_historial(),
                        historial.getAccion(),
                        historial.getFecha_accion(),
                        historial.getId_usuario(),
                        "Desconocido"))
                .block())
            .collect(Collectors.toList());
    }

    public HistorialUsuarioResponse save(HistorialUsuario historial) {
        HistorialUsuario historialGuardado = repository.save(historial);

        return usuarioClient.obtenerUsuarioPorId(historialGuardado.getId_usuario())
            .map(usuario -> new HistorialUsuarioResponse(
                    historialGuardado.getId_historial(),
                    historialGuardado.getAccion(),
                    historialGuardado.getFecha_accion(),
                    historialGuardado.getId_usuario(),
                    usuario.getNombreUsuario()))
            .defaultIfEmpty(new HistorialUsuarioResponse(
                    historialGuardado.getId_historial(),
                    historialGuardado.getAccion(),
                    historialGuardado.getFecha_accion(),
                    historialGuardado.getId_usuario(),
                    "Desconocido"))
            .block();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}




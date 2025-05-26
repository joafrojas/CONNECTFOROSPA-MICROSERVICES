package com.example.MetricasSistemaConnectForoSpa.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MetricasSistemaConnectForoSpa.model.MetricaSistema;
import com.example.MetricasSistemaConnectForoSpa.service.MetricaSistemaService;

@RestController
@RequestMapping("/api/metricas")
public class MetricaSistemaController {

    private final MetricaSistemaService service;

    public MetricaSistemaController(MetricaSistemaService service){
        this.service = service;
    }

    @GetMapping
    public List<MetricaSistema> getAll(){
        return service.getAll();
    }

    @PostMapping
    public MetricaSistema create(@RequestBody MetricaSistema metrica){
        return service.save(metrica);
    }

    @DeleteMapping("/{id}")
    public void delte(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public MetricaSistema actualizarValor(@PathVariable Long id, @RequestBody Map<String, String> body){
        String nuevoValor = body.get("valor");
        return service.actualizarValor(id, nuevoValor);
    }

}

package com.example.MetricasSistemaConnectForoSpa.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.MetricasSistemaConnectForoSpa.model.MetricaSistema;
import com.example.MetricasSistemaConnectForoSpa.repository.MetricaSistemaRepository;

@Service
public class MetricaSistemaService {

    private final MetricaSistemaRepository repository;

    public MetricaSistemaService(MetricaSistemaRepository repository){
        this.repository = repository;
    }
    

    public List<MetricaSistema> getAll(){
        return repository.findAll();
    }

    public MetricaSistema save(MetricaSistema metrica){
        metrica.setFecha_registro(new Date());
        return repository.save(metrica);
    }
        public void delete(Long id) { //metodo para borrar
        repository.deleteById(id);
    }

    public MetricaSistema actualizarValor(Long id, String nuevoValor){
        MetricaSistema metrica = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Metrica no encontrada"));

            metrica.setValor(nuevoValor);
            return repository.save(metrica);
    }

}

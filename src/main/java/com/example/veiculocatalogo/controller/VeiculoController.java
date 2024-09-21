package com.example.veiculocatalogo.controller;

import com.example.veiculocatalogo.model.Veiculo;
import com.example.veiculocatalogo.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    // Endpoint para verificar o status do serviço
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("O serviço está ativo - Catálogo de Veículos");
    }

    @GetMapping
    public List<Veiculo> getAllVeiculos() {
        return veiculoService.findAll();
    }

    @PostMapping
    public Veiculo addVeiculo(@RequestBody Veiculo veiculo) {
        return veiculoService.save(veiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> updateVeiculo(@PathVariable Integer id, @RequestBody Veiculo veiculo) {
        Optional<Veiculo> veiculoOptional = veiculoService.findById(id);
        if (veiculoOptional.isPresent()) {
            Veiculo veiculoToUpdate = veiculoOptional.get();
            veiculoToUpdate.setMarca(veiculo.getMarca());
            veiculoToUpdate.setModelo(veiculo.getModelo());
            veiculoToUpdate.setAno(veiculo.getAno());
            Veiculo veiculoUpdated = veiculoService.save(veiculoToUpdate);
            return ResponseEntity.ok(veiculoUpdated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Integer id) {
        if (veiculoService.findById(id).isPresent()) {
            veiculoService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

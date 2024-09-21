package com.example.veiculocatalogo.repository;


import com.example.veiculocatalogo.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
}

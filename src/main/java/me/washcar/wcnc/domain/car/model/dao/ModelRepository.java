package me.washcar.wcnc.domain.car.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.car.model.entity.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {
}

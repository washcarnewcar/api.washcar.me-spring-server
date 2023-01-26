package me.washcar.wcnc.domain.car.model.dao;

import me.washcar.wcnc.domain.car.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}

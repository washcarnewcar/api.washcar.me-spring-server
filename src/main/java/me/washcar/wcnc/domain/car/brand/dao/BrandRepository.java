package me.washcar.wcnc.domain.car.brand.dao;

import me.washcar.wcnc.domain.car.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}

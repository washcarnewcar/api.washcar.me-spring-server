package me.washcar.wcnc.domain.car.brand.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.car.brand.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}

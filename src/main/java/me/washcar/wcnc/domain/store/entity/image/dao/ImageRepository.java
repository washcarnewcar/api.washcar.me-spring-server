package me.washcar.wcnc.domain.store.entity.image.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.store.entity.image.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	Optional<Image> findByUuid(String uuid);

}

package me.washcar.wcnc.domain.store.entity.image.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.store.entity.image.entity.StoreImage;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {

	Optional<StoreImage> findByUuid(String uuid);

}

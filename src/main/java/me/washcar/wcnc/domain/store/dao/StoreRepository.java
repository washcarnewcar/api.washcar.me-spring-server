package me.washcar.wcnc.domain.store.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

	Page<Store> findAll(Pageable pageable);

	Optional<Store> findBySlug(String slug);

	boolean existsBySlug(String slug);

}

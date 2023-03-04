package me.washcar.wcnc.domain.store.entity.menu.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.store.entity.menu.entity.StoreMenu;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {

	Optional<StoreMenu> findByUuid(String uuid);

}

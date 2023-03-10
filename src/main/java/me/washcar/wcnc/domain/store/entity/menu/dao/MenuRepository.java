package me.washcar.wcnc.domain.store.entity.menu.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.washcar.wcnc.domain.store.entity.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	Optional<Menu> findByUuid(String uuid);

}

package me.washcar.wcnc.domain.store.menu;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import me.washcar.wcnc.domain.reservation.Reservation;
import me.washcar.wcnc.domain.store.Store;
import me.washcar.wcnc.global.entity.UuidEntity;

@Entity
@Getter
@Table(indexes = @Index(name = "uuid_store_menu_index", columnList = "uuid"))
public class StoreMenu extends UuidEntity {

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private Long expectedMinute;

	private String description;

	private String image;
	@ManyToOne(fetch = FetchType.LAZY)
	private Store store;

	@OneToMany(mappedBy = "storeMenu")
	private Collection<Reservation> reservations = new ArrayList<>();

}

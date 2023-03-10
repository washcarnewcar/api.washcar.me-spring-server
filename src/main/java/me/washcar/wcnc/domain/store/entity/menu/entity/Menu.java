package me.washcar.wcnc.domain.store.entity.menu.entity;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.washcar.wcnc.domain.reservation.entity.Reservation;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.global.entity.UuidEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "uuid_store_menu_index", columnList = "uuid"))
public class Menu extends UuidEntity {

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private Integer expectedMinute;

	private String description;

	private String image;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	private Store store;

	@OneToMany(mappedBy = "menu")
	private final Collection<Reservation> reservations = new ArrayList<>();

	public void updateMenu(Integer price, Integer expectedMinute, String description, String image) {
		this.price = price;
		this.expectedMinute = expectedMinute;
		this.description = description;
		this.image = image;
	}

	@Builder
	@SuppressWarnings("unused")
	public Menu(Integer price, Integer expectedMinute, String description, String image) {
		this.price = price;
		this.expectedMinute = expectedMinute;
		this.description = description;
		this.image = image;
	}

}

package me.washcar.wcnc.domain.store.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.domain.reservation.entity.Reservation;
import me.washcar.wcnc.domain.store.StoreStatus;
import me.washcar.wcnc.domain.store.entity.image.entity.Image;
import me.washcar.wcnc.domain.store.entity.menu.entity.Menu;
import me.washcar.wcnc.domain.store.entity.operation.entity.Holiday;
import me.washcar.wcnc.domain.store.entity.operation.entity.OperationHour;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "slug_store_index", columnList = "slug"))
public class Store extends BaseEntity {

	@Column(nullable = false)
	private StoreStatus status;

	@Column(nullable = false, unique = true)
	private String slug;

	@Embedded
	private Location location;

	private String name;

	private String tel;

	private String description;

	private String previewImage;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member owner;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@SuppressWarnings("FieldMayBeFinal")
	private OperationHour operationHour = new OperationHour();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
	private final List<Image> images = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
	private final List<Menu> menus = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
	private final List<Holiday> holidays = new ArrayList<>();

	@OneToMany(mappedBy = "store")
	private final List<Reservation> reservations = new ArrayList<>();

	@Builder
	@SuppressWarnings("unused")
	private Store(String slug, Location location, String name, String tel, String description,
		String previewImage) {
		this.status = StoreStatus.PENDING;
		updateStore(slug, location, name, tel, description, previewImage);
	}

	public boolean isOwnedBy(String uuid) {
		return Objects.equals(uuid, this.getOwner().getUuid());
	}

	public void addStoreImage(Image storeImage) {
		this.images.add(storeImage);
		storeImage.setStore(this);
	}

	public void deleteStoreImage(Image storeImage) {
		this.images.remove(storeImage);
	}

	public void addStoreMenu(Menu menu) {
		this.menus.add(menu);
		menu.setStore(this);
	}

	public void deleteStoreMenu(Menu menu) {
		this.menus.remove(menu);
	}

	public void addStoreHoliday(Holiday holiday) {
		this.holidays.add(holiday);
		holiday.setStore(this);
	}

	public void deleteStoreHoliday(Holiday holiday) {
		this.holidays.remove(holiday);
	}

	public void assignOwner(Member owner) {
		this.owner = owner;
		owner.getStores().add(this);
	}

	public void updateStore(String slug, Location location, String name, String tel, String description,
		String previewImage) {
		this.slug = slug;
		this.location = location;
		this.name = name;
		this.tel = tel;
		this.description = description;
		this.previewImage = previewImage;
	}

	public void updateStatus(StoreStatus status) {
		this.status = status;
	}

}

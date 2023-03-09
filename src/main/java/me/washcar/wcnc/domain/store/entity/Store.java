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
import me.washcar.wcnc.domain.store.entity.image.entity.StoreImage;
import me.washcar.wcnc.domain.store.entity.menu.entity.StoreMenu;
import me.washcar.wcnc.domain.store.entity.operation.entity.StoreOperationHoliday;
import me.washcar.wcnc.domain.store.entity.operation.entity.StoreOperationHour;
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
	private StoreOperationHour storeOperationHour = new StoreOperationHour();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
	private final List<StoreImage> storeImages = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
	private final List<StoreMenu> storeMenus = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
	private final List<StoreOperationHoliday> storeOperationHolidays = new ArrayList<>();

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

	public void addStoreImage(StoreImage storeImage) {
		this.storeImages.add(storeImage);
		storeImage.setStore(this);
	}

	public void deleteStoreImage(StoreImage storeImage) {
		this.storeImages.remove(storeImage);
	}

	public void addStoreMenu(StoreMenu storeMenu) {
		this.storeMenus.add(storeMenu);
		storeMenu.setStore(this);
	}

	public void deleteStoreMenu(StoreMenu storeMenu) {
		this.storeMenus.remove(storeMenu);
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

package me.washcar.wcnc.domain.store.entity;

import java.util.ArrayList;
import java.util.List;

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
import lombok.Getter;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.domain.reservation.entity.Reservation;
import me.washcar.wcnc.domain.store.StoreStatus;
import me.washcar.wcnc.domain.store.entity.image.StoreImage;
import me.washcar.wcnc.domain.store.entity.menu.StoreMenu;
import me.washcar.wcnc.domain.store.entity.operation.StoreOperationHoliday;
import me.washcar.wcnc.domain.store.entity.operation.StoreOperationHour;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
@Table(indexes = @Index(name = "slug_store_index", columnList = "slug"))
public class Store extends BaseEntity {

	public static final int MAX_IMAGE_NUMBER = 6;

	@Column(nullable = false)
	private StoreStatus status = StoreStatus.PENDING;

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
	private StoreOperationHour storeOperationHour;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
	private List<StoreImage> storeImages = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
	private List<StoreMenu> storeMenus = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
	private List<StoreOperationHoliday> storeOperationHolidays = new ArrayList<>();

	@OneToMany(mappedBy = "store")
	private List<Reservation> reservations = new ArrayList<>();

	public void addStoreImage(String imageUrl) {
		StoreImage storeImage = StoreImage.builder()
			.imageUrl(imageUrl)
			.store(this)
			.build();
		this.storeImages.add(storeImage);
		storeImage.setStore(this);
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

}

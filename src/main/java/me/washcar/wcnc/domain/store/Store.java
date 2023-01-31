package me.washcar.wcnc.domain.store;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.reservation.Reservation;
import me.washcar.wcnc.domain.store.image.StoreImage;
import me.washcar.wcnc.domain.store.menu.StoreMenu;
import me.washcar.wcnc.domain.store.operationHours.StoreOperationException;
import me.washcar.wcnc.domain.store.operationHours.StoreOperationHours;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
@Table(indexes = @Index(name = "slug_store_index", columnList = "slug"))
public class Store extends BaseEntity {

	@Column(nullable = false)
	private StoreStatus status;

	@Column(nullable = false, unique = true)
	private String slug;

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	private String name;

	private String address;

	private String addressDetail;

	private String tel;

	private String wayTo;

	private String description;

	private String previewImage;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member owner;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "store")
	private StoreOperationHours storeOperationHours;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
	private List<StoreOperationException> storeOperationExceptions = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
	private List<StoreImage> storeImages = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
	private List<StoreMenu> storeMenus = new ArrayList<>();

	@OneToMany(mappedBy = "store")
	private List<Reservation> reservations = new ArrayList<>();

}

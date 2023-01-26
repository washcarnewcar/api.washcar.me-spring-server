package me.washcar.wcnc.domain.store;

import jakarta.persistence.*;
import lombok.Getter;
import me.washcar.wcnc.domain.store.operationHours.StoreOperationException;
import me.washcar.wcnc.domain.store.operationHours.StoreOperationHours;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.global.entity.BaseEntity;
import me.washcar.wcnc.domain.reservation.Reservation;
import me.washcar.wcnc.domain.store.image.StoreImage;
import me.washcar.wcnc.domain.store.menu.StoreMenu;

import java.util.ArrayList;
import java.util.List;

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

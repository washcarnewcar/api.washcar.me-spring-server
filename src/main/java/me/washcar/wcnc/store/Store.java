package me.washcar.wcnc.store;

import jakarta.persistence.*;
import lombok.Getter;
import me.washcar.wcnc.member.Member;
import me.washcar.wcnc.model.BaseEntity;
import me.washcar.wcnc.reservation.Reservation;
import me.washcar.wcnc.store.image.StoreImage;
import me.washcar.wcnc.store.menu.StoreMenu;
import me.washcar.wcnc.store.operationHours.StoreOperationException;
import me.washcar.wcnc.store.operationHours.StoreOperationHours;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

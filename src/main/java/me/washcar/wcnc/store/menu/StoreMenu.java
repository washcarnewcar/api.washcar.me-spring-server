package me.washcar.wcnc.store.menu;

import jakarta.persistence.*;
import lombok.Getter;
import me.washcar.wcnc.model.BaseEntity;
import me.washcar.wcnc.reservation.Reservation;
import me.washcar.wcnc.store.Store;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
public class StoreMenu extends BaseEntity {

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

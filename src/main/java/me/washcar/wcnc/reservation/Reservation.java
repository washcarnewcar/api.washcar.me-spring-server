package me.washcar.wcnc.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import me.washcar.wcnc.car.Model;
import me.washcar.wcnc.member.Member;
import me.washcar.wcnc.model.BaseEntity;
import me.washcar.wcnc.store.Store;
import me.washcar.wcnc.store.menu.StoreMenu;

import java.time.LocalDateTime;

@Entity
@Getter
public class Reservation extends BaseEntity {

    @Column(nullable = false)
    private ReservationStatus reservationStatus;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private String telephone;

    private String request;

    private String carNumber;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Model model;

    @ManyToOne
    private Store store;

    @ManyToOne
    private StoreMenu storeMenu;

}

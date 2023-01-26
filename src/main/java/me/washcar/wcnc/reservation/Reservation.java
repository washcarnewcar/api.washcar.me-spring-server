package me.washcar.wcnc.reservation;

import jakarta.persistence.*;
import lombok.Getter;
import me.washcar.wcnc.car.Model;
import me.washcar.wcnc.member.Member;
import me.washcar.wcnc.model.UuidEntity;
import me.washcar.wcnc.store.Store;
import me.washcar.wcnc.store.menu.StoreMenu;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(indexes = @Index(name = "uuid_reservation_index", columnList = "uuid"))
public class Reservation extends UuidEntity {

    @Column(nullable = false)
    private ReservationStatus reservationStatus;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private String telephone;

    private String request;

    private String carNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreMenu storeMenu;

}

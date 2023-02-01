package me.washcar.wcnc.domain.reservation;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import me.washcar.wcnc.domain.car.model.Model;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.store.Store;
import me.washcar.wcnc.domain.store.menu.StoreMenu;
import me.washcar.wcnc.global.entity.UuidEntity;

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
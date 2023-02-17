package me.washcar.wcnc.domain.car.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import me.washcar.wcnc.domain.car.brand.entity.Brand;
import me.washcar.wcnc.domain.reservation.entity.Reservation;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
@Setter
public class Model extends BaseEntity {

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	private Brand brand;

	@OneToMany(mappedBy = "model")
	private List<Reservation> reservations = new ArrayList<>();

}

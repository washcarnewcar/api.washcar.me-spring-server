package me.washcar.wcnc.car;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import me.washcar.wcnc.model.BaseEntity;
import me.washcar.wcnc.reservation.Reservation;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Model extends BaseEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @OneToMany(mappedBy = "model")
    private List<Reservation> reservations = new ArrayList<>();

}

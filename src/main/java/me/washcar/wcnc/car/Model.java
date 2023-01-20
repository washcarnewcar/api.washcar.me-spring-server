package me.washcar.wcnc.car;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import me.washcar.wcnc.model.NamedEntity;
import me.washcar.wcnc.reservation.Reservation;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Model extends NamedEntity {

    @ManyToOne
    private Brand brand;

    @OneToMany(mappedBy = "model")
    private List<Reservation> reservations = new ArrayList<>();

    public void setBrand(Brand brand) {
        this.brand = brand;
        brand.getModels().add(this);
    }

}

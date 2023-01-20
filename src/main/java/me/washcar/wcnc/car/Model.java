package me.washcar.wcnc.car;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import me.washcar.wcnc.model.NamedEntity;

@Entity
@Getter
public class Model extends NamedEntity {

    @ManyToOne
    private Brand brand;

    public void setBrand(Brand brand) {
        this.brand = brand;
        brand.getModels().add(this);
    }

}

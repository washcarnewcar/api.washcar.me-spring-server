package me.washcar.wcnc.car;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import me.washcar.wcnc.model.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Brand extends BaseEntity {

    @OneToMany(mappedBy = "brand")
    private Set<Model> models = new HashSet<>();

    private String name;

    public void addModel(Model model) {
        this.models.add(model);
        model.setBrand(this);
    }

}

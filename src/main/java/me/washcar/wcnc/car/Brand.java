package me.washcar.wcnc.car;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import me.washcar.wcnc.model.NamedEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Brand extends NamedEntity {

    @OneToMany(mappedBy = "brand")
    private Set<Model> models = new HashSet<>();

    public void addModel(Model model) {
        this.models.add(model);
        model.setBrand(this);
    }

}

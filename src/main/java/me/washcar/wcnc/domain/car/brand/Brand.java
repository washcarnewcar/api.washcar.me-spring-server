package me.washcar.wcnc.domain.car.brand;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import me.washcar.wcnc.domain.car.model.Model;
import me.washcar.wcnc.global.entity.BaseEntity;

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

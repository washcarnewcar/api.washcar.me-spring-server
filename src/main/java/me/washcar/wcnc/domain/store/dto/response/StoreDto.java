package me.washcar.wcnc.domain.store.dto.response;

import lombok.Getter;
import lombok.Setter;
import me.washcar.wcnc.domain.store.entity.Location;
import me.washcar.wcnc.domain.store.StoreStatus;

@Getter
@Setter
public class StoreDto {

	private StoreStatus status;

	private String slug;

	private Location location;

	private String name;

	private String tel;

	private String description;

	private String previewImage;

}

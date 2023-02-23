package me.washcar.wcnc.domain.store.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.washcar.wcnc.domain.store.entity.Location;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@Getter
@AllArgsConstructor
public class StoreRequestDto {

	@Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG)
	private String slug;

	private Location location;

	private String name;

	private String tel;

	private String description;

	private String previewImage;

}

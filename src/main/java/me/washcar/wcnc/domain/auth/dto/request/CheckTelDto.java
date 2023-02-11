package me.washcar.wcnc.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import me.washcar.wcnc.global.def.Regex;

@Getter
public class CheckTelDto {
	@NotNull
	@Pattern(regexp = Regex.TELEPHONE, message = Regex.TELEPHONE_MSG)
	private String telephone;
}

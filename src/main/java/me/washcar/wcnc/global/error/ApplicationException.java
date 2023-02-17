package me.washcar.wcnc.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApplicationException extends RuntimeException {

	private final ApplicationError applicationError;

}

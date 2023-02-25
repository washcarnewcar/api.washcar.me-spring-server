package me.washcar.wcnc.domain.store.dto.request;

import lombok.Getter;
import me.washcar.wcnc.domain.store.StoreStatus;

@Getter
@SuppressWarnings("unused")
public class StorePatchRequestDto {

	private StoreStatus storeStatus;

}

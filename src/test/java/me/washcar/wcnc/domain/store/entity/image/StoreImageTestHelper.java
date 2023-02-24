package me.washcar.wcnc.domain.store.entity.image;

import me.washcar.wcnc.domain.store.entity.image.dto.request.ImageRequestDto;

public class StoreImageTestHelper {

	public ImageRequestDto makeStaticImageRequestDto() {
		ImageRequestDto imageRequestDto = new ImageRequestDto();
		imageRequestDto.setImageUrl("/store/test/static01");
		return imageRequestDto;
	}
	
}

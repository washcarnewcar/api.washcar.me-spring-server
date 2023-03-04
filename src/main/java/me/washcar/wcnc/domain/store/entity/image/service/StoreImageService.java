package me.washcar.wcnc.domain.store.entity.image.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.domain.store.entity.image.dao.StoreImageRepository;
import me.washcar.wcnc.domain.store.entity.image.dto.response.ImageResponseDto;
import me.washcar.wcnc.domain.store.entity.image.dto.response.ImagesDto;
import me.washcar.wcnc.domain.store.entity.image.entity.StoreImage;
import me.washcar.wcnc.domain.store.service.StoreService;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class StoreImageService {

	public static final int MAX_IMAGE_NUMBER = 6;

	private final StoreService storeService;

	private final StoreRepository storeRepository;

	private final StoreImageRepository storeImageRepository;

	private final ModelMapper modelMapper;

	@Transactional
	public void create(String slug, String imageUrl) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		storeService.checkStoreChangePermit(store);
		if (store.getStoreImages().size() >= MAX_IMAGE_NUMBER) {
			throw new BusinessException(BusinessError.EXCEED_STORE_IMAGE_LIMIT);
		}
		StoreImage storeImage = StoreImage.builder()
			.imageUrl(imageUrl)
			.build();
		store.addStoreImage(storeImage);
	}

	@Transactional(readOnly = true)
	public ImagesDto getImagesBySlug(String slug) {
		Store store = storeRepository.findBySlug(slug).orElseThrow(() -> new BusinessException(
			BusinessError.STORE_NOT_FOUND));
		List<StoreImage> storeImages = store.getStoreImages();
		List<ImageResponseDto> imageResponseDtos = storeImages.stream()
			.map(m -> modelMapper.map(m, ImageResponseDto.class))
			.toList();
		return new ImagesDto(imageResponseDtos);
	}

	@Transactional(readOnly = true)
	public ImageResponseDto getImageByUuid(String uuid) {
		StoreImage storeImage = storeImageRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_IMAGE_NOT_FOUND));
		return modelMapper.map(storeImage, ImageResponseDto.class);
	}

	@Transactional
	public void deleteImageByUuid(String uuid) {
		StoreImage storeImage = storeImageRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_IMAGE_NOT_FOUND));
		Store store = storeImage.getStore();
		storeService.checkStoreChangePermit(store);
		store.deleteStoreImage(storeImage);
	}

}

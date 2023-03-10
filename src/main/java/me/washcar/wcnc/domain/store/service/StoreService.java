package me.washcar.wcnc.domain.store.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.domain.store.StoreStatus;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.dto.request.StoreRequestDto;
import me.washcar.wcnc.domain.store.dto.response.StoreDto;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;
import me.washcar.wcnc.global.utility.AuthorizationHelper;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;

	private final MemberRepository memberRepository;

	private final AuthorizationHelper authorizationHelper;

	private final ModelMapper modelMapper;

	@Transactional(readOnly = true)
	public void checkNewSlugSafety(String slug) {
		if (storeRepository.existsBySlug(slug)) {
			throw new BusinessException(BusinessError.STORE_ALREADY_EXIST);
		}
	}

	public void checkChangeSlugSafety(String slug, StoreRequestDto storeRequestDto) {
		if (!storeRequestDto.getSlug().equals(slug)) {
			checkNewSlugSafety(storeRequestDto.getSlug());
		}
	}

	private void refreshStoreStatus(Store store) {
		if (store.getStatus().equals(StoreStatus.REJECTED)) {
			store.updateStatus(StoreStatus.PENDING);
		}
	}

	public void checkStoreChangePermit(Store store) {
		boolean isManager = authorizationHelper.isManager();
		boolean isOwner = store.isOwnedBy(authorizationHelper.getMyUuid());
		if (!isManager && !isOwner) {
			throw new BusinessException(BusinessError.FORBIDDEN_STORE_CHANGE);
		}
	}

	@Transactional
	public void postStore(StoreRequestDto storeRequestDto, String ownerUuid) {
		checkNewSlugSafety(storeRequestDto.getSlug());
		Store store = Store.builder()
			.slug(storeRequestDto.getSlug())
			.location(storeRequestDto.getLocation())
			.name(storeRequestDto.getName())
			.tel(storeRequestDto.getTel())
			.description(storeRequestDto.getDescription())
			.previewImage(storeRequestDto.getPreviewImage())
			.build();
		Member owner = memberRepository.findByUuid(ownerUuid)
			.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
		store.assignOwner(owner);
		storeRepository.save(store);
	}

	@Transactional(readOnly = true)
	public Page<StoreDto> getStoreList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Store> storePage = storeRepository.findAll(pageable);
		return storePage.map(s -> modelMapper.map(s, StoreDto.class));
	}

	@Transactional(readOnly = true)
	public StoreDto getStoreBySlug(String slug) {
		Store store = storeRepository.findBySlug(slug)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_NOT_FOUND));
		boolean isManager = authorizationHelper.isManager();
		boolean isOwner = store.isOwnedBy(authorizationHelper.getMyUuid());
		boolean isOperate = store.getStatus().equals(StoreStatus.RUNNING);
		if (isManager || isOwner || isOperate) {
			return modelMapper.map(store, StoreDto.class);
		} else {
			throw new BusinessException(BusinessError.STORE_NOT_FOUND);
		}
	}

	@Transactional
	public StoreDto putStoreBySlug(String slug, StoreRequestDto requestDto) {
		Store store = storeRepository.findBySlug(slug)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_NOT_FOUND));
		checkStoreChangePermit(store);
		checkChangeSlugSafety(slug, requestDto);
		store.updateStore(requestDto.getSlug(), requestDto.getLocation(), requestDto.getName(),
			requestDto.getTel(), requestDto.getDescription(), requestDto.getPreviewImage());
		refreshStoreStatus(store);
		return modelMapper.map(store, StoreDto.class);
	}

	@Transactional
	public void deleteStoreBySlug(String slug) {
		Store store = storeRepository.findBySlug(slug)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_NOT_FOUND));
		storeRepository.delete(store);
	}

	@Transactional
	public void changeStoreStatusBySlug(String slug, StoreStatus storeStatus) {
		Store store = storeRepository.findBySlug(slug)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_NOT_FOUND));
		store.updateStatus(storeStatus);
		if (storeStatus.equals(StoreStatus.RUNNING)) {
			store.getOwner().promote(MemberRole.ROLE_OWNER);
		}
	}

}

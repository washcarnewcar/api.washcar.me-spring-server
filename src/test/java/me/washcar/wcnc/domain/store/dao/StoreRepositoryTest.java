package me.washcar.wcnc.domain.store.dao;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import me.washcar.wcnc.domain.store.StoreTestHelper;
import me.washcar.wcnc.domain.store.entity.Store;

@SpringBootTest
@Transactional
class StoreRepositoryTest {

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreTestHelper storeTestHelper;

	@Nested
	@DisplayName("세차장 목록 페이징")
	class storePage {

		@Test
		@DisplayName("세차장이 한 곳인 경우 세차장 조회에 성공한다")
		void should_success_when_singleStore() {
			//given
			Store store = storeTestHelper.makeStaticRunningStore();
			storeRepository.save(store);
			int page = 0;
			int size = 10;

			//when
			Pageable pageable = PageRequest.of(page, size);
			Page<Store> resultPage = storeRepository.findAll(pageable);

			//then
			assertThat(resultPage.getPageable()).isEqualTo(pageable);
			assertThat(resultPage.getTotalElements()).isEqualTo(1L);
			assertThat(resultPage.getTotalPages()).isEqualTo(1L);
			assertThat(resultPage.getNumber()).isEqualTo(page);
			assertThat(resultPage.getSize()).isEqualTo(size);
			assertThat(resultPage.hasNext()).isFalse();
			assertThat(resultPage.isFirst()).isTrue();

			assertThat(resultPage.get().findFirst().isPresent()).isTrue();
			assertThat(resultPage.get().findFirst().get()).isEqualTo(store);
			assertThat(resultPage.get().findFirst().get().getSlug()).isEqualTo(store.getSlug());
		}

		@Test
		@DisplayName("세차장이 없는 경우에도 세차장 조회에 성공한다")
		void should_success_when_zeroStore() {
			//given
			int page = 0;
			int size = 5;

			//when
			Pageable pageable = PageRequest.of(page, size);
			Page<Store> resultPage = storeRepository.findAll(pageable);

			//then
			assertThat(resultPage.getPageable()).isEqualTo(pageable);
			assertThat(resultPage.getTotalElements()).isEqualTo(0L);
			assertThat(resultPage.getTotalPages()).isEqualTo(0L);
			assertThat(resultPage.getNumber()).isEqualTo(page);
			assertThat(resultPage.getSize()).isEqualTo(size);
			assertThat(resultPage.hasNext()).isFalse();
			assertThat(resultPage.isFirst()).isTrue();
		}
	}

	@Nested
	@DisplayName("세차장 SLUG 접근")
	class storeSlug {

		@Test
		@DisplayName("세차장이 존재하는 경우 세차장 조회에 성공한다")
		void should_success_when_storeExist() {
			//given
			Store store = storeTestHelper.makeStaticRunningStore();
			storeRepository.save(store);

			//when
			Optional<Store> result = storeRepository.findBySlug(store.getSlug());

			//then
			assertThat(result).isPresent();
			assertThat(result.get().getSlug()).isEqualTo(store.getSlug());
			assertThat(result.get().getName()).isEqualTo(store.getName());
		}

		@Test
		@DisplayName("세차장이 존재하지 않는 경우 세차장 조회에 실패한다")
		void should_fail_when_storeNotExist() {
			//given
			//INTENDED-BLANK

			//when
			Optional<Store> result = storeRepository.findBySlug("badStoreSlug");

			//then
			assertThat(result).isEmpty();
		}

		@Test
		@DisplayName("잘못된 SLUG 양식인 경우 멤버 조회에 실패한다")
		void should_fail_when_wrongSlugFormat() {
			//given
			//INTENDED-BLANK

			//when
			Optional<Store> resultA = storeRepository.findBySlug("");
			Optional<Store> resultB = storeRepository.findBySlug(null);
			Optional<Store> resultC = storeRepository.findBySlug("X");

			//then
			assertThat(resultA).isEmpty();
			assertThat(resultB).isEmpty();
			assertThat(resultC).isEmpty();
		}
	}

}

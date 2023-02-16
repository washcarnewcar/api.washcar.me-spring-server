package me.washcar.wcnc.domain.store.entity.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.global.entity.UuidEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "uuid_store_image_index", columnList = "uuid"))
public class StoreImage extends UuidEntity {

	@Column(nullable = false)
	private String imageUrl;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	private Store store;

	@Builder
	private StoreImage(String imageUrl, Store store) {
		this.imageUrl = imageUrl;
		this.store = store;
	}

}

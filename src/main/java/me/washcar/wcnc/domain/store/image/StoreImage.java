package me.washcar.wcnc.domain.store.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import me.washcar.wcnc.domain.store.Store;
import me.washcar.wcnc.global.entity.UuidEntity;

@Entity
@Getter
@Table(indexes = @Index(name = "uuid_store_image_index", columnList = "uuid"))
public class StoreImage extends UuidEntity {

	@Column(nullable = false)
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	private Store store;

}

package me.washcar.wcnc.domain.store.image;

import jakarta.persistence.*;
import lombok.Getter;
import me.washcar.wcnc.global.entity.UuidEntity;
import me.washcar.wcnc.domain.store.Store;

@Entity
@Getter
@Table(indexes = @Index(name = "uuid_store_image_index", columnList = "uuid"))
public class StoreImage extends UuidEntity {

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

}

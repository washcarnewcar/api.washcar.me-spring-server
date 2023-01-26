package me.washcar.wcnc.store.image;

import jakarta.persistence.*;
import lombok.Getter;
import me.washcar.wcnc.model.UuidEntity;
import me.washcar.wcnc.store.Store;

@Entity
@Getter
@Table(indexes = @Index(name = "uuid_store_image_index", columnList = "uuid"))
public class StoreImage extends UuidEntity {

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

}

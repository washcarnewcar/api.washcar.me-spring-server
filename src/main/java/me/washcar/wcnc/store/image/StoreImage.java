package me.washcar.wcnc.store.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import me.washcar.wcnc.model.BaseEntity;
import me.washcar.wcnc.store.Store;

@Entity
@Getter
public class StoreImage extends BaseEntity {

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

}

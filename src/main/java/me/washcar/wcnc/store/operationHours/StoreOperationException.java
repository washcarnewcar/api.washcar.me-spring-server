package me.washcar.wcnc.store.operationHours;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import me.washcar.wcnc.model.BaseEntity;
import me.washcar.wcnc.store.Store;

import java.time.LocalDateTime;

@Entity
@Getter
public class StoreOperationException extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

}

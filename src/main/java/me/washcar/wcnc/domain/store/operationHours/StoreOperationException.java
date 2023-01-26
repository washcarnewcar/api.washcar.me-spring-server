package me.washcar.wcnc.domain.store.operationHours;

import jakarta.persistence.*;
import lombok.Getter;
import me.washcar.wcnc.global.entity.UuidEntity;
import me.washcar.wcnc.domain.store.Store;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(indexes = @Index(name = "uuid_store_operation_exception_index", columnList = "uuid"))
public class StoreOperationException extends UuidEntity {

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

}

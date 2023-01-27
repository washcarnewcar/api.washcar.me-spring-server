package me.washcar.wcnc.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.washcar.wcnc.global.entity.UuidEntity;
import me.washcar.wcnc.domain.reservation.Reservation;
import me.washcar.wcnc.domain.store.Store;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(indexes = @Index(name = "uuid_member_index", columnList = "uuid"))
public class Member extends UuidEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberAuthenticationType memberAuthenticationType;

    private String name;

    private String password;

    private String telephone;

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "owner")
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    private Member(String name, MemberStatus memberStatus, MemberRole memberRole, MemberAuthenticationType memberAuthenticationType, String password, String telephone, List<Store> stores, List<Reservation> reservations) {
        this.name = name;
        this.memberStatus = memberStatus;
        this.memberRole = memberRole;
        this.memberAuthenticationType = memberAuthenticationType;
        this.password = password;
        this.telephone = telephone;
        this.stores = stores;
        this.reservations = reservations;
    }

    public void changeStatus(MemberStatus status) {
        this.memberStatus = status;
    }
}

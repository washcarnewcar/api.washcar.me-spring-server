package me.washcar.wcnc.member;

import jakarta.persistence.*;
import lombok.Getter;
import me.washcar.wcnc.model.NamedEntity;
import me.washcar.wcnc.reservation.Reservation;
import me.washcar.wcnc.store.Store;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends NamedEntity {

    @Column(nullable = false)
    private MemberStatus memberStatus;

    @Column(nullable = false)
    private MemberRole memberRole;

    @Column(nullable = false)
    private MemberAuthenticationType memberAuthenticationType;

    @Column(nullable = false)
    private String password;

    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

}

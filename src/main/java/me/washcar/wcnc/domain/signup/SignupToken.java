package me.washcar.wcnc.domain.signup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
public class SignupToken extends BaseEntity {

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String token;

}

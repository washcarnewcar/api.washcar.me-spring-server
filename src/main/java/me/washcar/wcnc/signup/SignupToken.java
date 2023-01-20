package me.washcar.wcnc.signup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import me.washcar.wcnc.model.BaseEntity;

@Entity
@Getter
public class SignupToken extends BaseEntity {

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String token;

}

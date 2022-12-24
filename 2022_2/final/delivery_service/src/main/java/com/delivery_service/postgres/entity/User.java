package com.delivery_service.postgres.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.STRING)
@Table(schema = "public", name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String userName;
    private String userPassword;
    @Column(name = "phone_number")
    private String phone;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            }
    )
    @JsonIgnore
    private Set<Role> role;

}

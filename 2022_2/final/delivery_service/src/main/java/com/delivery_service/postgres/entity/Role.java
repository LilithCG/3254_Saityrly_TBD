package com.delivery_service.postgres.entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(schema = "public", name = "role")
public class Role {
    @Id
    private String roleName;
    private String roleDescription;
}
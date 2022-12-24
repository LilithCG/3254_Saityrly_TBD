package com.delivery_service.postgres.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(schema = "public", name = "cafe")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
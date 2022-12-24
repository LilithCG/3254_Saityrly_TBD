package com.delivery_service.postgres.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "food")
@Builder
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name",  nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "product_link")
    private String productLink;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
}

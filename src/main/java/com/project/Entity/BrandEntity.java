package com.project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CAR_BRAND")
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BRAND", nullable = false)
    private String brand;

    @OneToMany(mappedBy = "brand")
    private List<CarEntity> cars;


    public BrandEntity(Long id, String brand) {
        this.id = id;
        this.brand = brand;
    }
}

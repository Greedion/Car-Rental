package com.project.repository;

import com.project.entity.BrandEntity;
import com.project.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    Boolean existsByBrand(BrandEntity brandEntity);
}

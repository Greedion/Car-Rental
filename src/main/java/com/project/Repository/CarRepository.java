package com.project.Repository;
import com.project.Entity.BrandEntity;
import com.project.Entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    Boolean existsByBrand(BrandEntity brandEntity);
}

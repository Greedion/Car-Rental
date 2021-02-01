package com.project.demo.Repository;
import com.project.demo.Entity.CarEntity;
import com.project.demo.Entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    List<LoanEntity> findAllByCar(CarEntity carEntity);
}

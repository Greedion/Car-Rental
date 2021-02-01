package com.project.Repository;
import com.project.Entity.CarEntity;
import com.project.Entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    List<LoanEntity> findAllByCar(CarEntity carEntity);
}

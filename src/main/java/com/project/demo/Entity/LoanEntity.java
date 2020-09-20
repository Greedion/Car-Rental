package com.project.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOAN")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    Long id;

    @ManyToOne( fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "CAR", referencedColumnName = "ID")
    CarEntity car;

    @Column(name = "START_LOAN", nullable = false)
    Date startOfLoan;

    @Column(name = "END_LOAN", nullable = false)
    Date endOfLoan;

    @ManyToOne( fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "USERNAME", referencedColumnName = "ID")
    UserEntity user;
}

package com.project.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CAR")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "BRAND", referencedColumnName = "ID")
    private BrandEntity brand;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private Double pricePerHour;

    @OneToMany(mappedBy = "car")
    private List<LoanEntity> loans;

    public CarEntity(Long id, BrandEntity brand, String description, Double pricePerHour) {
        this.id = id;
        this.brand = brand;
        this.description = description;
        this.pricePerHour = pricePerHour;
    }
}

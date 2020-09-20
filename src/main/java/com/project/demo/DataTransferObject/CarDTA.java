package com.project.demo.DataTransferObject;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class CarDTA implements Serializable {

    String id;

    String brand;

    String description;

    String pricePerHour;
}

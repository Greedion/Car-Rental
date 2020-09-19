package com.project.demo.DataTransferObject;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class BrandDTA implements Serializable {

    String id;

    String brand;


}

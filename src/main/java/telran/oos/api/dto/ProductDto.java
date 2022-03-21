package telran.oos.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    // TODO validation rules
    private Long id;
    private String name;
    private String categoryName;
    private String description;
    private String imageUrl;
    private Float price;
    private MeasurementUnit unitOfMeasurement;
    private boolean isActive;
}

package telran.oos.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import static telran.oos.api.ApiConstants.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotNull
    private Long id;
    @NotNull @Size(min = MIN_PRODUCT_NAME_LENGTH, max = MAX_PRODUCT_NAME_LENGTH)
    private String name;
    @NotNull @Size(min = MIN_CATEGORY_NAME_LENGTH, max = MAX_CATEGORY_NAME_LENGTH)
    private String categoryName;
    @NotNull @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_DESCRIPTION_LENGTH)
    private String description;
    @URL
    private String imageUrl;
    @Min(MIN_PRODUCT_PRICE)
    @Max(MAX_PRODUCT_PRICE)
    private Float price;
    @NotNull
    private MeasurementUnit unitOfMeasurement;
    @JsonProperty("isActive")
    private boolean active;
}

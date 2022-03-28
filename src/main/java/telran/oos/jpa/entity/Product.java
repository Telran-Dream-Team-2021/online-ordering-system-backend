package telran.oos.jpa.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import telran.oos.api.dto.MeasurementUnit;
import static telran.oos.api.ApiConstants.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "products")
public class Product {

    @Id
    private Long id;
    @ManyToOne(targetEntity = Category.class, cascade = CascadeType.PERSIST)
    private Category category;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(nullable = false, precision = 2)
    private Float price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "unit_of_measurement")
    private MeasurementUnit unitOfMeasurement;
    @Column(nullable = false, name = "is_active")
    private boolean active;
}

package telran.oos.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import telran.oos.api.dto.MeasurementUnit;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    private Long id;
    @ManyToOne(targetEntity = Category.class)
    private Category category;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    private String imageUrl;
    @Column(nullable = false, precision = 2)
    private Float price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementUnit unitOfMeasurement;
    @Column(nullable = false)
    private boolean isActive;
}

package telran.oos.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.oos.api.dto.ProductDto;
import telran.oos.jpa.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select new telran.oos.api.dto.ProductDto(id, name, category.name, description, imageUrl, price, unitOfMeasurement, active) from Product where id = :id")
    ProductDto getProduct(@Param("id") Long id);

    @Query("select new telran.oos.api.dto.ProductDto(id, name, category.name, description, imageUrl, price, unitOfMeasurement, active) from Product")
    List<ProductDto> getAllProducts();

}

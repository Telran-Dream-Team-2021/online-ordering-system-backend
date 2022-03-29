package telran.oos.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.oos.api.dto.CategoryDto;
import telran.oos.jpa.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    @Query("select new telran.oos.api.dto.CategoryDto(id, name) from Category where id = :id ")
    CategoryDto getCategory(@Param("id") long id);

    @Query("select new telran.oos.api.dto.CategoryDto(id, name) from Category")
    List<CategoryDto> getAllCategories();
}

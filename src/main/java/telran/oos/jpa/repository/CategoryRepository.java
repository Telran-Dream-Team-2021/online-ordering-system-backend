package telran.oos.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.oos.jpa.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}

package telran.oos.jpa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import telran.oos.api.dto.BasketDto;
import telran.oos.documents.BasketDoc;

import java.util.List;

public interface BasketRepository extends MongoRepository<BasketDoc, Long> {
    @Query("{'userId':?0}")
    BasketDto getBasketById(Long id);
}

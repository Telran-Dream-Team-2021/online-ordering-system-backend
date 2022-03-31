package telran.oos.jpa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.oos.documents.BasketDoc;

public interface BasketRepository extends MongoRepository<BasketDoc, Integer> {
}

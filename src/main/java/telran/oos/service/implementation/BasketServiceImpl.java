package telran.oos.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import telran.oos.api.dto.BasketDto;
import telran.oos.documents.BasketDoc;
import telran.oos.jpa.repository.BasketRepository;
import telran.oos.service.CrudService;

import java.util.List;

@Slf4j
@Service
public class BasketServiceImpl implements CrudService<BasketDto, Integer> {
    private BasketRepository basketRepository;
    private MongoTemplate mongoTemplate;

    public BasketServiceImpl(BasketRepository basketRepository, MongoTemplate mongoTemplate) {
        this.basketRepository = basketRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public BasketDto create(BasketDto entity) {
        basketRepository.save(new BasketDoc(entity.getUserId(), entity.getBasketItems(), entity.getUserId()));
        return entity;
    }

    @Override
    public BasketDto read(Integer id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public BasketDto update(Integer id, BasketDto newEntity) {
        return null;
    }

    @Override
    public BasketDto remove(Integer id) {
        return null;
    }

}

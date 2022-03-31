package telran.oos.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;
import telran.oos.api.dto.BasketDto;
import telran.oos.documents.BasketDoc;
import telran.oos.jpa.repository.BasketRepository;
import telran.oos.service.CrudService;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BasketServiceImpl implements CrudService<BasketDto, Long> {
    private BasketRepository basketRepository;
    private MongoTemplate mongoTemplate;

    public BasketServiceImpl(BasketRepository basketRepository, MongoTemplate mongoTemplate) {
        this.basketRepository = basketRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public BasketDto create(BasketDto entity) {
        BasketDoc basketDoc = basketRepository.insert(new BasketDoc(entity.getUserId(), entity.getBasketItems(), entity.getUserId()));
        return new BasketDto(basketDoc.getUserId(), basketDoc.getUserId(), basketDoc.getBasketItems());
    }

    @Override
    public BasketDto read(Long id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public BasketDto update(Long id, BasketDto newEntity) {
        return null;
    }

    @Override
    public BasketDto remove(Long id) {
        return null;
    }

    public List<String> nativeQuery(String queryJson) {
        BasicQuery q = new BasicQuery(queryJson);
        List<BasketDoc> res = mongoTemplate.find(q, BasketDoc.class);
        return res.stream().map(Objects::toString).toList();
    }

}

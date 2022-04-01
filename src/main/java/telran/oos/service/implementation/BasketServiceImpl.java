package telran.oos.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;
import telran.exceptions.ResourceNotFoundException;
import telran.oos.aop.inter.WebSocketMessagable;
import telran.oos.api.dto.BasketDto;
import telran.oos.documents.BasketDoc;
import telran.oos.jpa.repository.BasketRepository;
import telran.oos.service.CrudService;

import java.util.List;
import java.util.Objects;

import static telran.oos.api.ApiConstants.WEBSOCKET_BASKET_THEME;

@Slf4j
@Service
public class BasketServiceImpl implements CrudService<BasketDto, Long>, WebSocketMessagable {
    private final BasketRepository basketRepository;
    private final MongoTemplate mongoTemplate;
    private final ModelMapper modelMapper;

    public BasketServiceImpl(BasketRepository basketRepository, MongoTemplate mongoTemplate, ModelMapper modelMapper) {
        this.basketRepository = basketRepository;
        this.mongoTemplate = mongoTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public BasketDto create(BasketDto entity) {
        BasketDoc basketDoc = basketRepository.insert(new BasketDoc(entity.getUserId(), entity.getBasketItems(), entity.getUserId()));
        return new BasketDto(basketDoc.getUserId(), basketDoc.getUserId(), basketDoc.getBasketItems());
    }

    @Override
    public BasketDto read(Long id) {
        validateIfExists(id);
        return basketRepository.getBasketById(id);
    }

    @Override
    public List getAll() {
        return basketRepository.findAll();
    }

    @Override
    public BasketDto update(Long id, BasketDto newEntity) {
        BasketDto oldBasketDto = read(id);
        basketRepository.save(convertToEntity(newEntity));
        return oldBasketDto;
    }

    @Override
    public BasketDto remove(Long id) {
        validateIfExists(id);
        BasketDto itemToRemove = basketRepository.getBasketById(id);
        basketRepository.deleteById(id);
        return itemToRemove;
    }

    private void validateIfExists(Long id) {
        if (!basketRepository.existsById(id)) {
            log.error("There is no basket with id {} id DB", id);
            throw new ResourceNotFoundException(String.format(
                    "Basket wth id %d does not exist", id
            ));
        }
    }

    public List<String> nativeQuery(String queryJson) {
        BasicQuery q = new BasicQuery(queryJson);
        List<BasketDoc> res = mongoTemplate.find(q, BasketDoc.class);
        return res.stream().map(Objects::toString).toList();
    }

    @Override
    public String getTheme() {
        return WEBSOCKET_BASKET_THEME;
    }

    private BasketDoc convertToEntity(BasketDto basketDto) {
        return modelMapper.map(basketDto, BasketDoc.class);
    }
}

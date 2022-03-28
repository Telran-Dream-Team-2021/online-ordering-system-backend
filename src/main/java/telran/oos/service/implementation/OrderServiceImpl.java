package telran.oos.service.implementation;

import org.springframework.stereotype.Service;
import telran.oos.api.dto.OrderDto;
import telran.oos.service.CrudService;

import java.util.List;

@Service
public class OrderServiceImpl implements CrudService<OrderDto, Long> {
    @Override
    public OrderDto create(OrderDto entity) {
        return null;
    }

    @Override
    public OrderDto read(Long id) {
        return null;
    }

    @Override
    public List<OrderDto> getAll() {
        return null;
    }

    @Override
    public OrderDto update(Long id, OrderDto newEntity) {
        return null;
    }

    @Override
    public OrderDto remove(Long id) {
        return null;
    }
}

package telran.oos.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telran.oos.api.dto.OrderDto;
import telran.oos.api.dto.OrderItemDto;
import telran.oos.jpa.entity.Order;
import telran.oos.jpa.entity.OrderItem;
import telran.oos.jpa.entity.Product;
import telran.oos.jpa.entity.User;
import telran.oos.jpa.repository.OrderItemRepository;
import telran.oos.jpa.repository.OrderRepository;
import telran.oos.jpa.repository.ProductRepository;
import telran.oos.jpa.repository.UserRepository;
import telran.oos.service.CrudService;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements CrudService<OrderDto, Long> {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                            ProductRepository productRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDto create(OrderDto order) {
        orderRepository.save(convertToEntity(order));
        return convertToDto(orderRepository.findById(order.getId()).orElseThrow());
    }

    @Override
    public OrderDto read(Long id) {
        return convertToDto(orderRepository.findById(id).orElseThrow());
    }

    @Override
    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto update(Long id, OrderDto newEntity) {
        return null;
    }

    @Override
    public OrderDto remove(Long id) {
        return null;
    }

    private OrderDto convertToDto(Order order){
        log.debug(order.toString());
        OrderDto res = modelMapper.map(order, OrderDto.class);
        List<OrderItemDto> items = order.getOrderItems().stream().map(item-> new OrderItemDto(item.getId(),
                item.getOrder().getId(),
                item.getProduct().getId(),
                item.getPricePerUnit(),
                item.getQuantity()
        )).toList();
        res.setOrderItems(items);
        res.setUserId(order.getUser().getId());
        log.debug(res.toString());
        return res;
    }

    private Order convertToEntity(OrderDto orderDto){
        log.debug(orderDto.toString());
        Order order = modelMapper.map(orderDto, Order.class);
        List<OrderItem> items = orderDto.getOrderItems().stream().map(item->{
            Product product = productRepository.findById(item.getProductId()).orElseThrow();
            return new OrderItem(order, product);
        }).toList();

        order.setOrderItems(items);
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow();
        order.setUser(user);
        log.debug(order.toString());
        return order;
    }
}

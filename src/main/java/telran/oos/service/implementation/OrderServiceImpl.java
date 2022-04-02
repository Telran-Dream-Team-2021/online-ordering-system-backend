package telran.oos.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.exceptions.ResourceNotFoundException;
import telran.oos.aop.inter.WebSocketMessagable;
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

import static telran.oos.api.ApiConstants.WEBSOCKET_ORDER_THEME;

@Slf4j
@Service
public class OrderServiceImpl implements CrudService<OrderDto, Long>, WebSocketMessagable {
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
    public OrderDto create(OrderDto orderDto) {
        Order order = orderRepository.save(convertToEntity(orderDto));
        log.debug("created order with id = {}", order.getId());
        OrderDto res = convertToDto(orderRepository.findById(order.getId()).orElse(null));
        if(res==null){
            throw new ResourceNotFoundException(String.format("order didn't created"));
        }
        return res;
    }

    @Override
    public OrderDto read(Long id) {
        OrderDto res = convertToDto(orderRepository.findById(id).orElse(null));
        if(res==null){
            throw new ResourceNotFoundException(String.format("order with id = %s is not defined", id));
        }
        return res;
    }

    @Override
    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream().map(this::convertToDto).toList();
    }

    @Override
    @Transactional
    public OrderDto update(Long id, OrderDto newOrder) {
        Order oldOrder = orderRepository.findById(id).orElse(null);
        OrderDto res = convertToDto(oldOrder);
        if(oldOrder!=null){
            oldOrder.getOrderItems().forEach(item->orderItemRepository.deleteById(item.getId()));
            Order order = convertToEntity(newOrder);
            order.setId(id);
            orderRepository.save(order);
        }
        return res;
    }

    @Override
    public OrderDto remove(Long id) {
        OrderDto res = read(id);
        orderRepository.deleteById(id);
        return res;
    }

    private OrderDto convertToDto(Order order){
        log.debug(order.toString());
        OrderDto res = new OrderDto();
        res.setId(order.getId());
        res.setUserId(order.getUser().getId());
        res.setDeliveryAddress(order.getDeliveryAddress());
        res.setStatus(order.getStatus());
        res.setDeliveryDate(order.getDeliveryDate());
        res.setLastEditionDate(order.getLastEditionDate());
        List<OrderItemDto> items = order.getOrderItems().stream().map(item-> new OrderItemDto(item.getId(),
                item.getOrder().getId(),
                item.getProduct().getId(),
                item.getPricePerUnit(),
                item.getQuantity()
        )).toList();
        res.setOrderItems(items);
        log.debug(res.toString());
        return res;
    }

    private Order convertToEntity(OrderDto orderDto){
        log.debug(orderDto.toString());
//        Order order = modelMapper.map(orderDto, Order.class);


        User user = userRepository.findById(orderDto.getUserId()).orElse(null);
        if(user==null){
            throw new ResourceNotFoundException(String.format("user with id = %s is not defined", orderDto.getUserId()));
        }
        Order order = new Order();
//        user,
//                orderDto.getDeliveryAddress(),
//                orderDto.getStatus(),
//                orderDto.getDeliveryDate(),
//                orderDto.getLastEditionDate()
        order.setUser(user);
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        order.setStatus(orderDto.getStatus());
        order.setDeliveryDate(orderDto.getDeliveryDate());
        order.setLastEditionDate(orderDto.getLastEditionDate());

        List<OrderItem> items = orderDto.getOrderItems().stream().map(item->{
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if(product==null){
                throw new ResourceNotFoundException(String.format("product with id = %s is not defined", item.getProductId()));
            }
            OrderItem res = new OrderItem(order, product);
            res.setPricePerUnit(item.getPricePerUnit());
            res.setQuantity(item.getQuantity());
            return res;
        }).toList();
        order.setOrderItems(items);

        log.debug("order entity = {}", order.toString());
        return order;
    }

    @Override
    public String getTheme() {
        return WEBSOCKET_ORDER_THEME;
    }
}

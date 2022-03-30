package telran.oos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import telran.oos.api.dto.OrderDto;
import telran.oos.service.CrudService;

import java.util.List;

import static telran.oos.api.ApiConstants.ORDER_MAPPING;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(ORDER_MAPPING)
public class OrderController {
    private final CrudService<OrderDto, Long> orderService;

    public OrderController(CrudService<OrderDto, Long> orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDto> getAllOrders(){
        log.info("Getting all orders");
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id){
        log.info("Getting order with id = {}", id);
        return orderService.read(id);
    }

    @DeleteMapping("/{id}")
    public OrderDto deleteOrder(@PathVariable long id){
        log.info("Deleting order with id = {}", id);
        return orderService.remove(id);
    }

    @PostMapping
    public OrderDto addOrder(@RequestBody OrderDto order){
        log.info("Adding new order with id = {}", order.getId());
        return orderService.create(order);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable long id, @RequestBody OrderDto order){
        log.info("Updating order with id = {}", id);
        return orderService.update(id, order);
    }


}

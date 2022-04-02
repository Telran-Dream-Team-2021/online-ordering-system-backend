package telran.oos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import telran.oos.api.dto.OrderDto;
import telran.oos.jpa.entity.User;
import telran.oos.service.CrudService;

import java.util.List;
import java.util.Objects;

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
//        if(!isAdmin()){
//            throw new AccessDeniedException("Access denied (getAllOrders)");
//        }
        log.info("Getting all orders");
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public List<OrderDto> getOrdersByUser(@PathVariable Long id){
//        if(wrongAuth(id)){
//            throw new AccessDeniedException("Access denied (getOrder)");
//        }
        List<OrderDto> res = orderService.getAll();
        log.info("Getting orders by user id = {}", id);
        return res.stream().filter(orderDto -> orderDto.getUserId()==id).toList();
    }

    @DeleteMapping("/{id}")
    public OrderDto deleteOrder(@PathVariable long id){
        if(wrongAuth(id)){
            throw new AccessDeniedException("Access denied (deleteOrder)");
        }
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
        if(wrongAuth(id)){
            throw new AccessDeniedException("Access denied (updateOrder)");
        }
        log.info("Updating order with id = {}", id);
        return orderService.update(id, order);
    }

    private boolean wrongAuth(Long id) {
        OrderDto order = orderService.read(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();

            return !Objects.equals(user.getId(), order.getUserId()) && !user.isAdmin();
        }

        return true;
    }

    private boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            return user.isAdmin();
        }
        return false;
    }

}

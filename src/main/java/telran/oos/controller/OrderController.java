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
        User user = getUser();
        if(user==null){
            throw new AccessDeniedException("Access denied (getAllOrders)");
        }

        if(user.isAdmin()){
            log.info("Getting all orders (admin)");
            return orderService.getAll();
        } else {
            List<OrderDto> res = orderService.getAll();
            log.info("Getting all orders by user id = {}", user.getId());
            return res.stream().filter(orderDto -> orderDto.getUserId().equals(user.getId())).toList();
        }

    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id){
        if(wrongAuth(id)){
            throw new AccessDeniedException("Access denied (getOrder)");
        }
        OrderDto res = orderService.read(id);
        log.info("Getting order by id = {}", id);
        return res;
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

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            return (User) authentication.getPrincipal();
        }

        return null;
    }

    private boolean wrongAuth(Long id) {
        OrderDto order = orderService.read(id);
        User user = getUser();
        if(user!=null){
            return !Objects.equals(user.getId(), order.getUserId()) && !user.isAdmin();
        }

        return false;
    }

}

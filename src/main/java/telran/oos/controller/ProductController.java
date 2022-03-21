package telran.oos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import telran.oos.api.dto.ProductDto;
import telran.oos.service.CrudService;

import java.util.List;

import static telran.oos.api.ApiConstants.PRODUCT_MAPPING;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(PRODUCT_MAPPING)
public class ProductController {
    private final CrudService<ProductDto, Long> service;

    public ProductController(CrudService<ProductDto, Long> service) {
        this.service = service;
    }


    @GetMapping
    public List<ProductDto> getAll() {
        log.debug("Getting all products");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public  ProductDto get(@PathVariable Long id) {
        log.info("Getting product with id = {}", id);
        return service.read(id);
    }
}


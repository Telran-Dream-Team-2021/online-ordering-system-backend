package telran.oos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import telran.exceptions.WrongInputDataException;
import telran.oos.api.dto.ProductDto;
import telran.oos.service.CrudService;

import javax.validation.Valid;
import java.util.List;

import static telran.oos.api.ApiConstants.PRODUCT_MAPPING;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(PRODUCT_MAPPING)
@Validated
public class ProductController {
    private final CrudService<ProductDto, Long> service;

    public ProductController(@Qualifier("productServiceImpl") CrudService<ProductDto, Long> service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductDto add(@RequestBody @Valid ProductDto productDto) {
        ProductDto productDtoAdded = service.create(productDto);
        log.debug("Product with id {} added", productDtoAdded.getId());
        return productDtoAdded;
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

    @DeleteMapping("/{id}")
    ProductDto remove(@PathVariable long id) {
        ProductDto removedProductDto = service.remove(id);
        log.debug("Product with id {} removed", id);
        return removedProductDto;
    }

    @PutMapping("/{id}")
    ProductDto update(@PathVariable long id, @RequestBody @Valid ProductDto newProductDto) {
        if (newProductDto.getId() != id) {
            log.error("Dismatching id: value id parameter is {} and new product id is {}", id, newProductDto.getId());
            throw new WrongInputDataException(String.format(
                    "Dismatching id: value id parameter is %d and new product id is %d",
                    id, newProductDto.getId()
            ));
        }
        ProductDto oldProductDto = service.update(id, newProductDto);
        log.debug("Product with id {} updated", id);
        return oldProductDto;
    }
}


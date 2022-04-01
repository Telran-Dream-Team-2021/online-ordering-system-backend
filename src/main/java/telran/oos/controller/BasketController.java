package telran.oos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import telran.exceptions.WrongInputDataException;
import telran.oos.api.dto.BasketDto;
import telran.oos.service.CrudService;

import javax.validation.Valid;


import static telran.oos.api.ApiConstants.BASKET_MAPPING;

@Slf4j
@RestController
@RequestMapping(BASKET_MAPPING)
@CrossOrigin
@Validated
public class BasketController {

    private final CrudService<BasketDto, Long> service;

    public BasketController(CrudService<BasketDto, Long> service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BasketDto add(@RequestBody @Valid BasketDto basketDto) {
        BasketDto basketDtoAdded = service.create(basketDto);
        log.debug("Basket with id {} has been added", basketDtoAdded.getId());
        return basketDtoAdded;
    }

    @GetMapping("/{id}")
    public BasketDto get(@PathVariable Long id) {
        log.info("Getting basket with id = {}", id);
        return service.read(id);
    }

    @DeleteMapping("/{id}")
    BasketDto remove(@PathVariable long id) {
        BasketDto removedBasketDto = service.remove(id);
        log.debug("Basket with id {} removed", id);
        return removedBasketDto;
    }

    @PutMapping("/{id}")
    BasketDto update(@PathVariable long id, @RequestBody @Valid BasketDto newBasketDto) {
        if (newBasketDto.getId() != id) {
            log.error("Mismatching id: value id parameter is {} and new basket id is {}", id, newBasketDto.getId());
            throw new WrongInputDataException(String.format(
                    "Mismatching id: value id parameter is %d and new basket id is %d",
                    id, newBasketDto.getId()
            ));
        }
        BasketDto oldBasketDto = service.update(id, newBasketDto);
        log.debug("basket with id {} has been updated", id);
        return oldBasketDto;
    }
}

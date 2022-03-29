package telran.oos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import telran.exceptions.WrongInputDataException;
import telran.oos.api.dto.CategoryDto;
import telran.oos.service.CrudService;

import javax.validation.Valid;
import java.util.List;

import static telran.oos.api.ApiConstants.*;

@Slf4j
@CrossOrigin
@Validated
@RestController
@RequestMapping(CATEGORY_MAPPING)
public class CategoryController {
    private final CrudService<CategoryDto, Long> service;

    public CategoryController(CrudService<CategoryDto, Long> service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto add(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto categoryDtoAdded = service.create(categoryDto);
        log.debug("Category with id {} added", categoryDtoAdded.getId());
        return categoryDtoAdded;
    }

    @GetMapping
    public List<CategoryDto> getAll() {
        log.debug("Getting all categories");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public  CategoryDto get(@PathVariable Long id) {
        log.info("Getting category with id = {}", id);
        return service.read(id);
    }

    @DeleteMapping("/{id}")
    CategoryDto remove(@PathVariable long id) {
        CategoryDto removedCategoryDto = service.remove(id);
        log.debug("Category with id {} removed", id);
        return removedCategoryDto;
    }

    @PutMapping("/{id}")
    CategoryDto update(@PathVariable long id, @RequestBody @Valid CategoryDto newCategoryDto) {
        if (newCategoryDto.getId() != id) {
            log.error("Dismatching id: value id parameter is {} and new category id is {}", id, newCategoryDto.getId());
            throw new WrongInputDataException(String.format(
                    "Dismatching id: value id parameter is %d and new category id is %d",
                    id, newCategoryDto.getId()
            ));
        }
        CategoryDto oldCategoryDto = service.update(id, newCategoryDto);
        log.debug("Category with id {} updated", id);
        return oldCategoryDto;
    }
}

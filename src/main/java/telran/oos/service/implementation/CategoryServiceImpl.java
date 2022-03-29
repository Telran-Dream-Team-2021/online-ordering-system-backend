package telran.oos.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.exceptions.ResourceNotFoundException;
import telran.exceptions.WrongInputDataException;
import telran.oos.api.dto.CategoryDto;
import telran.oos.jpa.entity.Category;
import telran.oos.jpa.repository.CategoryRepository;
import telran.oos.service.CrudService;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CrudService<CategoryDto, Long> {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        if (categoryRepository.existsById(categoryDto.getId())) {
            log.error("Can not create category with id {}. " +
                    "Category with this id already exists", categoryDto.getId());
            throw new WrongInputDataException(String.format(
                    "Can not create category with id %d." +
                            "Category with this id already exists", categoryDto.getId()
            ));
        }
        categoryRepository.save(convertToEntity(categoryDto));
        return categoryRepository.getCategory(categoryDto.getId());
    }

    @Override
    @Transactional
    public CategoryDto read(Long id) {
        if (!categoryRepository.existsById(id)) {
            log.error("There is no category with id {} id DB", id);
            throw new ResourceNotFoundException(String.format(
                    "Category wth id %d does not exist", id
            ));
        }
        return categoryRepository.getCategory(id);
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public CategoryDto update(Long id, CategoryDto newCategoryDto) {
        CategoryDto oldCategoryDto = read(id);
        categoryRepository.save(convertToEntity(newCategoryDto));
        return oldCategoryDto;
    }

    @Override
    public CategoryDto remove(Long id) {
        CategoryDto oldCategoryDto = read(id);
        categoryRepository.deleteById(id);
        return oldCategoryDto;
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}

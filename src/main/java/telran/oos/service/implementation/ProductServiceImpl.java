package telran.oos.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.exceptions.ResourceNotFoundException;
import telran.exceptions.WrongInputDataException;
import telran.oos.api.dto.ProductDto;
import telran.oos.jpa.entity.Product;
import telran.oos.jpa.repository.CategoryRepository;
import telran.oos.jpa.repository.ProductRepository;
import telran.oos.service.CrudService;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements CrudService<ProductDto, Long> {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository,
                              ModelMapper modelMapper) {
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ProductDto create(ProductDto productDto) {
        checkProductInDb(productDto);
        productRepository.save(convertToEntity(productDto));
        return productRepository.getProduct(productDto.getId());
    }

    private void checkProductInDb(ProductDto productDto) {
        if (productRepository.existsById(productDto.getId())) {
            log.error("Can not create product with id {}. " +
                    "Product with this id already exists", productDto.getId());
            throw new WrongInputDataException(String.format(
                    "Can not create product with id %d." +
                            "Product with this id already exists", productDto.getId()
            ));
        }
    }

    @Override
    public ProductDto read(Long id) {
        if (!productRepository.existsById(id)) {
            log.error("There is no product with id {} id DB", id);
            throw new ResourceNotFoundException(String.format(
                    "Product wth id %d does not exist", id
            ));
        }
        return productRepository.getProduct(id);
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.getAllProducts();
    }

    @Override
    public ProductDto update(Long id, ProductDto newProductDto) {
        ProductDto oldProductDto = read(id);
        Product newProduct = convertToEntity(newProductDto);
        productRepository.save(newProduct);
        return oldProductDto;
    }

    @Override
    public ProductDto remove(Long id) {
        ProductDto oldProductDto = read(id);
        productRepository.deleteById(id);
        return oldProductDto;
    }

    private Product convertToEntity(ProductDto productDto) {
        log.debug(productDto.toString());
        Product product = modelMapper.map(productDto, Product.class);
        product.setCategory(categoryRepository.findByName(productDto.getCategoryName()));
        log.debug(product.toString());
        return product;
    }
}

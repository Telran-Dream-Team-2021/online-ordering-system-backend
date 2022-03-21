package telran.oos.service.implementation;

import org.springframework.stereotype.Service;
import telran.oos.api.dto.ProductDto;
import telran.oos.jpa.repository.ProductRepository;
import telran.oos.service.CrudService;

import java.util.List;

@Service
public class ProductServiceImpl implements CrudService<ProductDto, Long> {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDto create(ProductDto entity) {
        return null;
    }

    @Override
    public ProductDto read(Long id) {
        return repository.getProduct(id);
    }

    @Override
    public List<ProductDto> getAll() {
        return repository.getAllProducts();
    }

    @Override
    public ProductDto update(Long id, ProductDto newEntity) {
        return null;
    }

    @Override
    public ProductDto remove(Long id) {
        return null;
    }
}

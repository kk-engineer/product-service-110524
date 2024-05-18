package in.itkaran.product_service_110524.repositories;

import in.itkaran.product_service_110524.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);
    List<Product> findAll();
    Product findByIdIs(Long id);
}

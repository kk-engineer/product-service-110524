package in.itkaran.product_service_110524.services;

import in.itkaran.product_service_110524.exceptions.ProductNotFoundException;
import in.itkaran.product_service_110524.models.Category;
import in.itkaran.product_service_110524.models.Product;
import in.itkaran.product_service_110524.repositories.CategoryRepository;
import in.itkaran.product_service_110524.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(productId);
        if (product == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
            );
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProducts( int pageNumber, int pageSize,
                                         String sortParam) {

        // if direction == ascending
        // else  descending
        return productRepository.findAll(PageRequest.of
                (pageNumber, pageSize,
                        Sort.by(sortParam).descending()));
    }

    @Override
    public Product addProduct(String title, String description,
                              String imageUrl, String category, double price) {
        Product newProduct = new Product();
        newProduct.setTitle(title);
        newProduct.setDescription(description);
        newProduct.setImageUrl(imageUrl);
        newProduct.setPrice(price);

        Category categoryfromDb = categoryRepository.findByTitle(category);

        if (categoryfromDb == null) {
            Category newCategory = new Category();
            newCategory.setTitle(category);
            // categoryRepository.save(newCategory);
            categoryfromDb = newCategory;
        }

        newProduct.setCategory(categoryfromDb);
        Product savedProduct = productRepository.save(newProduct);
        return savedProduct;
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(productId);
        if (product == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
            );
        }
        productRepository.delete(product);
        return product;
    }

    @Override
    public Product updateProduct(Long productId, String title, String description,
                                 String imageUrl, String category, double price)
            throws ProductNotFoundException {
        Product productInDb = productRepository.findByIdIs(productId);
        if (productInDb == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
            );
        }

        if (title != null) {
            productInDb.setTitle(title);
        }
        if (description != null) {
            productInDb.setDescription(description);
        }
        if (imageUrl != null) {
            productInDb.setImageUrl(imageUrl);
        }

        if (price != 0) {
            productInDb.setPrice(price);
        }
        if (category != null) {
            Category categoryfromDb = categoryRepository.findByTitle(category);
            if (categoryfromDb == null) {
                Category newCategory = new Category();
                newCategory.setTitle(category);
                categoryfromDb = newCategory;
            }
            productInDb.setCategory(categoryfromDb);
        }
        return productRepository.save(productInDb);
    }

    @Override
    public Product replaceProduct(Long productId, String title, String description, String imageUrl, String category, double price) throws ProductNotFoundException {
        return null;
    }

    public void testMethod() {
        System.out.println("testMethod");
    }
}

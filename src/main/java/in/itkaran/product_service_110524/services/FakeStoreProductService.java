package in.itkaran.product_service_110524.services;

import in.itkaran.product_service_110524.dtos.FakeStoreDto;
import in.itkaran.product_service_110524.dtos.ProductResponseDto;
import in.itkaran.product_service_110524.exceptions.ProductNotFoundException;
import in.itkaran.product_service_110524.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;
    private final RedisTemplate redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {

        // Check in Cache
        Product productInCache = (Product) redisTemplate.opsForHash().
                get("PRODUCTS", "PRODUCT_" + productId);

        if (productInCache != null) {
            // Cache Hit
            System.out.println("Cache hit for product " + productId);
            return productInCache;
        }

        // Cache miss
        System.out.println("Cache miss for product " + productId);

        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "http://fakestoreapi.com/products/" + productId,
                FakeStoreDto.class
        );

        if (fakeStoreDto == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
                    +" try a product with id less than 21");
        }
        Product fakeStoreProduct =  fakeStoreDto.toProduct();
        // Add it to cache for future reference
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + productId, fakeStoreProduct);
        return fakeStoreProduct;
        // one dto to another dto directly
    }

    @Override
    public List<Product> getAllProducts(){
        FakeStoreDto[] fakeStoreDtos = restTemplate.getForObject(
                "http://fakestoreapi.com/products/",
                FakeStoreDto[].class
        );

        // convert all fakestore dto to product object
        List<Product> products = new ArrayList<>();

        for (FakeStoreDto fakeStoreDto : fakeStoreDtos) {
            products.add(fakeStoreDto.toProduct());
        }
        return products;
    }
    @Override
    public Product addProduct(
            String title,
            String description,
            String imageUrl,
            String category,
            double price) {

        FakeStoreDto requestDto = new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setImage(imageUrl);
        requestDto.setCategory(category);
        requestDto.setPrice(price);

        FakeStoreDto response = restTemplate.postForObject(
                "http://fakestoreapi.com/products/",
                requestDto,
                FakeStoreDto.class
        );

        return response.toProduct();
    }

    public Product deleteProduct(Long productId)
        throws ProductNotFoundException {
        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.exchange(
                "http://fakestoreapi.com/products/" + productId,
                HttpMethod.DELETE,
              null,
              FakeStoreDto.class
        );
//        FakeStoreDto fakeStoreDto = restTemplate.exchange(
//                "http://fakestoreapi.com/products/" + productId,
//                HttpMethod.DELETE,
//                null,
//                FakeStoreDto.class
//        ).getBody();

        FakeStoreDto fakeStoreDto = responseEntity.getBody();
        if (fakeStoreDto == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
                            +" try to delete a product with id less than 21");
        }

        return fakeStoreDto.toProduct();
    }

    @Override
    public Product updateProduct (
            Long productId,
            String title,
            String description,
            String imageUrl,
            String category,
            double price)
    throws ProductNotFoundException {

        FakeStoreDto requestDto = new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setImage(imageUrl);
        requestDto.setCategory(category);
        requestDto.setPrice(price);

        // Known Issue: HTTP PATCH is not supported by RestTemplate
        // So below code will NOT work
        // Will throw an exception:
        // Invalid HTTP method: PATCH\n\tat org.springframework.web.client.
        // create request entity to send in patch request body to fakestore
//         HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(requestDto);
//
//        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.exchange(
//                "https://fakestoreapi.com/products/" + productId,
//                HttpMethod.PATCH,
//                requestEntity,
//                FakeStoreDto.class
//        );
//
//        FakeStoreDto response = responseEntity.getBody();
//        if (response == null) {
//            throw new ProductNotFoundException(
//                    "Product with id " + productId + " not found");
//        }

        FakeStoreDto response = requestDto;
        response.setId(productId);
        return response.toProduct();
    }

    @Override
    public Product replaceProduct (
            Long productId,
            String title,
            String description,
            String imageUrl,
            String category,
            double price)
            throws ProductNotFoundException {

        FakeStoreDto requestDto = new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setImage(imageUrl);
        requestDto.setCategory(category);
        requestDto.setPrice(price);

        // create request entity to send in put request body to fakestore
        HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(requestDto);

        FakeStoreDto response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + productId,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreDto.class
        ).getBody();

        if (response == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found" );
        }
        return response.toProduct();
    }

    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortParam) {
        return null;
    }
}


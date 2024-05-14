package in.itkaran.product_service_110524.services;

import in.itkaran.product_service_110524.dtos.FakeStoreDto;
import in.itkaran.product_service_110524.dtos.ProductResponseDto;
import in.itkaran.product_service_110524.exceptions.ProductNotFoundException;
import in.itkaran.product_service_110524.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(int productId) throws ProductNotFoundException {

        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "http://fakestoreapi.com/products/" + productId,
                FakeStoreDto.class
        );

        if (fakeStoreDto == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
                    +" try a product with id less than 21");
        }
        return fakeStoreDto.toProduct();
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

}


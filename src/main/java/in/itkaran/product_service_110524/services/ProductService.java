package in.itkaran.product_service_110524.services;

import in.itkaran.product_service_110524.dtos.FakeStoreDto;
import in.itkaran.product_service_110524.dtos.ProductResponseDto;
import in.itkaran.product_service_110524.models.Product;

public interface ProductService {

    public ProductResponseDto getSingleProduct(int productId);

    public ProductResponseDto addProduct(
            String title,
            String description,
            String imageUrl,
            String category,
            double price);
}

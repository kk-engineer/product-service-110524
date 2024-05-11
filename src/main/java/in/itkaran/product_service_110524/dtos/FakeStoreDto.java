package in.itkaran.product_service_110524.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreDto {
    private int id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;

    public ProductResponseDto toProductResponseDto() {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(id);
        productResponseDto.setTitle(title);
        productResponseDto.setDescription(description);
        productResponseDto.setPrice(price);
        productResponseDto.setImage(image);
        productResponseDto.setCategory(category);

        return productResponseDto;
    }
}

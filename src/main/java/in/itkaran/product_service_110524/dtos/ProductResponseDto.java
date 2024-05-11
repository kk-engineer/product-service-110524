package in.itkaran.product_service_110524.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private int id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
}

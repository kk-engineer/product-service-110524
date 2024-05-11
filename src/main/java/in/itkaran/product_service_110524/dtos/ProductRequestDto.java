package in.itkaran.product_service_110524.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@Setter
public class ProductRequestDto {
    private int id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
}

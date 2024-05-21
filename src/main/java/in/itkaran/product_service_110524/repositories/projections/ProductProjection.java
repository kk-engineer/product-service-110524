package in.itkaran.product_service_110524.repositories.projections;

import in.itkaran.product_service_110524.models.Category;

import java.math.BigDecimal;

public interface ProductProjection {
    Long getId();
    String getTitle();
    String getDescription();
    BigDecimal getPrice();
    Category getCategory();
    String getImageUrl();
}

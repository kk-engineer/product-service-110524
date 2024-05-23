package in.itkaran.product_service_110524.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    private String title;
    private String description;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    //@Fetch(value = FetchMode.J)
    List<Product> products;
}

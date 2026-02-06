package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Getter @Setter
public class Product {
    private String productId;
    @NotBlank(message = "Product name cannot be empty")
    private String productName;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }

    public void setProductQuantity(int productQuantity) {
        if (productQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.productQuantity = productQuantity;
    }
}

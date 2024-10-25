package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity

public class ItemEntity {
    @Id
    private String itemCode;
    private String description;
    private String size;
    private Double unitPrice;
    private Integer qtyOnHand;
    private String supplierId;
}

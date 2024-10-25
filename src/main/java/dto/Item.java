package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data

public class Item {
    private String itemCode;
    private String description;
    private String size;
    private Double unitPrice;
    private Integer qtyOnHand;
    private String supplierId;
}

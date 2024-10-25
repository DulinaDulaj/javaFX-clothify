package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data

public class CartTM {
    private String itemCode;
    private String description;
    private String size;
    private Integer qty;
    private Double unitPrice;
    private Double total;
}

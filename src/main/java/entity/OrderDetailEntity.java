package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "OrderDetails")
public class OrderDetailEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderEntity order;

    @Id
    @Column(nullable = false)
    private String itemCode;

    @Column(nullable = false)
    private Integer qty;


}

package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "Orders")
public class OrderEntity {
    @Id
    private String orderId;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private String orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetails;

    public OrderEntity(String orderId, String orderDate) {
        this.orderId = orderId;
        this.orderDate = orderDate;
    }


}

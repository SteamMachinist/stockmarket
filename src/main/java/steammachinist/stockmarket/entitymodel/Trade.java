package steammachinist.stockmarket.entitymodel;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private Double unitPrice;
    private Integer quantity;
    private LocalDateTime closed;

    public Trade(User seller, User buyer, Stock stock, Double unitPrice, Integer quantity, LocalDateTime closed) {
        this.seller = seller;
        this.buyer = buyer;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.closed = closed;
    }
}

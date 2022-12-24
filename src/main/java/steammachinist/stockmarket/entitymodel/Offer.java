package steammachinist.stockmarket.entitymodel;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Enumerated(EnumType.STRING)
    private OfferType type;

    private Double unitPrice;
    private Integer quantity;
    private LocalDateTime open;

    public Offer(User creator, Stock stock, OfferType type, Double unitPrice, Integer quantity, LocalDateTime open) {
        this.creator = creator;
        this.stock = stock;
        this.type = type;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.open = open;
    }
}

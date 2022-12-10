package steammachinist.stockmarket.entitymodel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(PositionId.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Position {
    @Id
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer quantity;

    public Position(Stock stock, User user, Integer quantity) {
        this.stock = stock;
        this.user = user;
        this.quantity = quantity;
    }
}

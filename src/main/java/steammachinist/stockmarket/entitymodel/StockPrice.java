package steammachinist.stockmarket.entitymodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class StockPrice {
    @Id
    @OneToOne
    private Stock stock;

    private Double buy;
    private Double sell;
    private LocalDateTime lastChange;

    public StockPrice(Stock stock, Double buy, Double sell, LocalDateTime lastChange) {
        this.stock = stock;
        this.buy = buy;
        this.sell = sell;
        this.lastChange = lastChange;
    }
}

package steammachinist.stockmarket.entitymodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public void setBuyLastChange(Double buy, LocalDateTime lastChange) {
        setBuy(buy);
        setLastChange(lastChange);
    }

    public void setSellLastChange(Double sell, LocalDateTime lastChange) {
        setBuy(sell);
        setLastChange(lastChange);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StockPrice that = (StockPrice) o;
        return stock != null && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package steammachinist.stockmarket.entitymodel;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PositionId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PositionId that = (PositionId) o;
        return stock != null && Objects.equals(stock, that.stock) && user != null && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stock, user);
    }
}

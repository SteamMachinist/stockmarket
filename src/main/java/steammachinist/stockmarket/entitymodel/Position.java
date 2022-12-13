package steammachinist.stockmarket.entitymodel;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    @EmbeddedId
    private PositionId positionId;

    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Position position = (Position) o;
        return positionId != null && Objects.equals(positionId, position.positionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionId);
    }
}

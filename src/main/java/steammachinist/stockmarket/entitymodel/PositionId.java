package steammachinist.stockmarket.entitymodel;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PositionId implements Serializable {
    private Stock stock;
    private User user;
}

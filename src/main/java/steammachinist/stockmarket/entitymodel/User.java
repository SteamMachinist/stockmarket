package steammachinist.stockmarket.entitymodel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private Character[] password;
    private String name;
    private String lastName;
    private String email;
    private Double balance;

    public User(String username, Character[] password, String name, String lastName, String email, Double balance) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.balance = balance;
    }
}

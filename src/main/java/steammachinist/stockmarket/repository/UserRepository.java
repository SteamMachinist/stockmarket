package steammachinist.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import steammachinist.stockmarket.entitymodel.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

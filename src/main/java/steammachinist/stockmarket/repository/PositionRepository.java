package steammachinist.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import steammachinist.stockmarket.entitymodel.Position;
import steammachinist.stockmarket.entitymodel.PositionId;
import steammachinist.stockmarket.entitymodel.User;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, PositionId> {
    List<Position> getPositionsByPositionId_User(User user);
}

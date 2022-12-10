package steammachinist.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import steammachinist.stockmarket.entitymodel.Position;
import steammachinist.stockmarket.entitymodel.PositionId;

public interface PositionRepository extends JpaRepository<Position, PositionId> {
}

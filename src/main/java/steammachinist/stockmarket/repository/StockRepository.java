package steammachinist.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import steammachinist.stockmarket.entitymodel.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}

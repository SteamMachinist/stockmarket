package steammachinist.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import steammachinist.stockmarket.entitymodel.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}

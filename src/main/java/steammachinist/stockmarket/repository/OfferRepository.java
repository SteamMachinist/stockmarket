package steammachinist.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import steammachinist.stockmarket.entitymodel.Offer;
import steammachinist.stockmarket.entitymodel.OfferType;
import steammachinist.stockmarket.entitymodel.Stock;
import steammachinist.stockmarket.entitymodel.User;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Offer findByTypeAndStockAndUnitPriceIsGreaterThanEqual(OfferType type, Stock stock, Double unitPrice);
    Offer findByTypeAndStockAndUnitPriceIsLessThanEqual(OfferType type, Stock stock, Double unitPrice);
    List<Offer> findAllByCreator(User creator);
}

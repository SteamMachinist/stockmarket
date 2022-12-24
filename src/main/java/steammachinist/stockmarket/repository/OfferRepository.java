package steammachinist.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import steammachinist.stockmarket.entitymodel.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}

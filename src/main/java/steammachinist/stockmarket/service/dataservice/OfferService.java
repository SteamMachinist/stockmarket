package steammachinist.stockmarket.service.dataservice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.Offer;
import steammachinist.stockmarket.entitymodel.OfferType;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.repository.OfferRepository;
import steammachinist.stockmarket.service.NewOfferEvent;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Offer findById(Long id) throws Exception {
        return offerRepository.findById(id)
                .orElseThrow(() -> new Exception("Stock not found: id = " + id));
    }

    public Offer findByOppositeTypeAndBiggerThanEqualPrice(Offer offer) {
        OfferType type;
        if (offer.getType() == OfferType.BUY) {
            type = OfferType.SELL;
        } else {
            type = OfferType.BUY;
        }
        return offerRepository
                .findFirstByTypeAndStockAndUnitPriceIsGreaterThanEqualOrderByUnitPriceDesc(type, offer.getStock(), offer.getUnitPrice());
    }

    public Offer findByOppositeTypeAndSmallerThanEqualPrice(Offer offer) {
        OfferType type;
        if (offer.getType() == OfferType.BUY) {
            type = OfferType.SELL;
        } else {
            type = OfferType.BUY;
        }
        return offerRepository
                .findFirstByTypeAndStockAndUnitPriceIsLessThanEqualOrderByUnitPriceAsc(type, offer.getStock(), offer.getUnitPrice());
    }

    public List<Offer> findAllUserOffers(User user) {
        return offerRepository.findAllByCreator(user);
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }


    public void addOffer(Offer offer) {
        offerRepository.save(offer);
        applicationEventPublisher.publishEvent(new NewOfferEvent(this, offer));
    }

    public void addOffers(List<Offer> offers) {
        offerRepository.saveAll(offers);
    }

    public void deleteOffer(Offer offer) {
        offerRepository.delete(offer);
    }

    public long count() {
        return offerRepository.count();
    }

}

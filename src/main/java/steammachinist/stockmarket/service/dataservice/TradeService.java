package steammachinist.stockmarket.service.dataservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.Offer;
import steammachinist.stockmarket.repository.OfferRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final OfferRepository offerRepository;

    public Offer findById(Long id) throws Exception {
        return offerRepository.findById(id)
                .orElseThrow(() -> new Exception("Stock not found: id = " + id));
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public void addOffer(Offer offer) {
        offerRepository.save(offer);
    }

    public void addOffers(List<Offer> offers) {
        offerRepository.saveAll(offers);
    }

    public long count() {
        return offerRepository.count();
    }

}

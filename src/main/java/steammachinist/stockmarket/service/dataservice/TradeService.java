package steammachinist.stockmarket.service.dataservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.Trade;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.repository.TradeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeRepository tradeRepository;

    public Trade findById(Long id) throws Exception {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new Exception("Trade not found: id = " + id));
    }

    public List<Trade> findByBuyerOrSeller(User buyer, User seller) {
        return tradeRepository.findByBuyerOrSeller(buyer, seller);
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public void addTrade(Trade trade) {
        tradeRepository.save(trade);
    }

    public void addTrades(List<Trade> trades) {
        tradeRepository.saveAll(trades);
    }

    public long count() {
        return tradeRepository.count();
    }

}

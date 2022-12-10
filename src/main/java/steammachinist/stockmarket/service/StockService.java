package steammachinist.stockmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.Stock;
import steammachinist.stockmarket.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public Stock getStockById(Long id) throws Exception {
        return stockRepository.findById(id)
                .orElseThrow(() -> new Exception("Stock not found: id = " + id));
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public void addStock(Stock stock) {
        stockRepository.save(stock);
    }
}

package steammachinist.stockmarket;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import steammachinist.stockmarket.service.PositionService;
import steammachinist.stockmarket.service.StockService;
import steammachinist.stockmarket.service.UserService;
import steammachinist.stockmarket.entitymodel.Stock;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataFiller {
    private final UserService userService;
    private final StockService stockService;
    private final PositionService positionService;

    public void fill() throws IOException, CsvException {
        List<Stock> stocks = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/stocks.csv"))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> stocks.add(new Stock(x[0], x[1])));
        }
        stocks.forEach(stockService::addStock);
    }
}

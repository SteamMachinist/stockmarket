package steammachinist.stockmarket;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import steammachinist.stockmarket.entitymodel.Position;
import steammachinist.stockmarket.entitymodel.PositionId;
import steammachinist.stockmarket.entitymodel.Stock;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.service.PositionService;
import steammachinist.stockmarket.service.StockService;
import steammachinist.stockmarket.service.UserService;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TestDataFiller {
    private final UserService userService;
    private final StockService stockService;
    private final PositionService positionService;
    private final Random random = new Random();

    public void fill() {
        if (positionService.getAllPositions().isEmpty()) {

            List<Stock> stocks = readStocksFromFile();
            if(stockService.getAllStocks().isEmpty()) {
                stockService.addStocks(stocks);
            }

            List<User> users = readUsersFromFile();
            if(userService.getAllUsers().isEmpty()) {
                userService.addUsers(users);
            }

            List<Position> positions = generateRandomPositions(users, stocks);
            positionService.addPositions(positions);
        }
    }

    private List<Stock> readStocksFromFile() {
        List<Stock> stocks = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/stocks.csv"))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> stocks.add(new Stock(x[0], x[1])));
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return stocks;
    }

    private List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/users.csv"))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> users.add(createRandomizedUser(x[0], x[1])));
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private User createRandomizedUser(String firstName, String lastName) {
        String username = firstName + lastName + random.nextInt(1000, 9999);
        Character[] password = ArrayUtils.toObject(UUID.randomUUID().toString().replace("-", "").toCharArray());
        String email = lastName + "_" + firstName.charAt(0) + "_" + random.nextInt(1950, 2005) + "@mail.com";
        double balance = random.nextInt(3000, 30001) + random.nextInt(100) / 100.0;
        return new User(username, password, firstName, lastName, email, balance);
    }

    private List<Position> generateRandomPositions(List<User> users, List<Stock> stocks) {
        List<Position> positions = new ArrayList<>();
        Collections.shuffle(users);
        for (User user: users) {
            List<Stock> userStocks = ListUtils.pickNRandomElements(stocks, random.nextInt(3, 20), random);
            assert userStocks != null;
            List<Position> userPositions = userStocks.stream()
                    .map(stock -> new Position(new PositionId(stock, user), random.nextInt(1, 101)))
                    .toList();
            positions.addAll(userPositions);
        }
        return positions;
    }
}

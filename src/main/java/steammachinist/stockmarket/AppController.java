package steammachinist.stockmarket;

import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AppController {
    @Autowired
    private TestDataFiller testDataFiller;

    @GetMapping("/")
    public String hello() throws IOException, CsvException {
        testDataFiller.fill();
        return "hello";
    }
}

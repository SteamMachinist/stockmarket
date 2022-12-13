package steammachinist.stockmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @Autowired
    private TestDataFiller testDataFiller;

    @GetMapping("/")
    public String hello() {
        testDataFiller.fill();
        return "hello";
    }
}

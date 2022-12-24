package steammachinist.stockmarket.testdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupDatabaseFiller implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TestDataFiller testDataFiller;

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
        testDataFiller.fill();
    }
}

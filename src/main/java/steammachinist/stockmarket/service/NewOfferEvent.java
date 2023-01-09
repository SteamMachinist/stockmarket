package steammachinist.stockmarket.service;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import steammachinist.stockmarket.entitymodel.Offer;

@Getter
public class NewOfferEvent extends ApplicationEvent {
    private final Offer offer;

    public NewOfferEvent(Object source, Offer offer) {
        super(source);
        this.offer = offer;
    }
}

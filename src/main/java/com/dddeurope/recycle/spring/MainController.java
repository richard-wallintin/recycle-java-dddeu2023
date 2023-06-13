package com.dddeurope.recycle.spring;

import com.dddeurope.recycle.commands.CalculatePrice;
import com.dddeurope.recycle.commands.CommandMessage;
import com.dddeurope.recycle.domain.Price;
import com.dddeurope.recycle.domain.PriceCalculator;
import com.dddeurope.recycle.events.Event;
import com.dddeurope.recycle.events.EventMessage;
import com.dddeurope.recycle.events.PriceWasCalculated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String home() {
        return "please enter a public URL to this site on https://domainmodelling.dev, as specified in the readme";
    }

    @GetMapping("/validate")
    public String validate() {
        return "Hi!";
    }

    @PostMapping("/handle-command")
    public ResponseEntity<EventMessage> handle(@RequestBody RecycleRequest request) {
        LOGGER.info("Incoming Request: {}", request.asString());

        PriceCalculator priceCalculator = new PriceCalculator(eventsOf(request));

        String cardId = commandOf(request).cardId();
        Price calculatedPrice = priceCalculator.calculatePrice(cardId);

        return ResponseEntity.ok(new EventMessage(
            UUID.randomUUID().toString(),
            new PriceWasCalculated(cardId, calculatedPrice.asEuroDouble() , "EUR")
        ));
    }

    private List<Event> eventsOf(RecycleRequest request) {
        return request.history().stream()
            .map(EventMessage::getPayload)
            .collect(Collectors.toList());
    }

    private CalculatePrice commandOf(RecycleRequest request) {
        return (CalculatePrice) request.command().getPayload();
    }

    public record RecycleRequest(List<EventMessage> history, CommandMessage command) {

        public String asString() {
            var historyAsString = history.stream()
                .map(EventMessage::toString)
                .collect(Collectors.joining("\n\t"));

            return String.format("%n%s %nWith History\n\t%s", command, historyAsString);
        }
    }
}

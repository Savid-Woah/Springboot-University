package springboot_university.strategy;

import org.springframework.stereotype.Component;

@Component
public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public String pay() {
        return "Pago con tarjeta";
    }
}
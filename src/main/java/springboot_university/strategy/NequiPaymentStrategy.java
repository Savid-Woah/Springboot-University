package springboot_university.strategy;

public class NequiPaymentStrategy implements PaymentStrategy {
    @Override
    public String pay() {
        return "Pago con nequi";
    }
}
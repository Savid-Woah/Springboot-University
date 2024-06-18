package springboot_university.strategy;

public class CashPaymentStrategy implements PaymentStrategy{
    @Override
    public String pay() {
        return "Pago en efectivo";
    }
}
package springboot_university.strategy;

public class BancolombiaPaymentStrategy implements PaymentStrategy{
    @Override
    public String pay() {
        return "Pago con bancolombia";
    }
}

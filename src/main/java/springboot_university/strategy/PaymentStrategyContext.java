package springboot_university.strategy;

import org.springframework.stereotype.Component;
import springboot_university.exception.BackendException;

import static springboot_university.exception.MsgCode.OOPS_ERROR;
import static springboot_university.strategy.PaymentStrategyContext.PaymentStrategyType.*;

@Component
public class PaymentStrategyContext {

    static class PaymentStrategyType {
        static final String CASH = "CASH";
        static final String CARD = "CARD";
        static final String NEQUI = "NEQUI";
        static final String BANCOLOMBIA = "BANCOLOMBIA";
    }

    public PaymentStrategy resolvePaymentStrategy(String strategy) {

        return switch (strategy) {
            case CASH -> new CashPaymentStrategy();
            case CARD -> new CardPaymentStrategy();
            case NEQUI -> new NequiPaymentStrategy();
            case BANCOLOMBIA -> new BancolombiaPaymentStrategy();
            default -> throw new BackendException(OOPS_ERROR);
        };
    }
}
package springboot_university.strategy;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentStrategyService {

    private final PaymentStrategyContext paymentStrategyContext;

    public String pay(String strategy) {

        PaymentStrategy paymentStrategy = paymentStrategyContext.resolvePaymentStrategy(strategy);
        return paymentStrategy.pay();
    }
}
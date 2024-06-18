package springboot_university.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("payment-strategy/")
public class PaymentStrategyController {

    private final PaymentStrategyService paymentStrategyService;

    @GetMapping(path = "{strategy}")
    public String pay(@PathVariable("strategy") String strategy) {
        return paymentStrategyService.pay(strategy);
    }
}
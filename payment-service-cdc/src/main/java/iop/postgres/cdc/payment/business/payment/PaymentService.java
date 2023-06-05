package iop.postgres.cdc.payment.business.payment;

import iop.postgres.cdc.payment.business.event.order.OrderCreationEvent;
import iop.postgres.cdc.payment.infrastructure.payment.PaymentEntity;
import iop.postgres.cdc.payment.infrastructure.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void createPayment(OrderCreationEvent orderCreationEvent) {
        log.info("Creating payment for order {}", orderCreationEvent.getId());
        paymentRepository.save(PaymentEntity.of(orderCreationEvent));
    }

}

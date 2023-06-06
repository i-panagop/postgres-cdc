package iop.postgres.cdc.payment.business.payment;

import iop.postgres.cdc.payment.business.command.PaymentUpdateCommand;
import iop.postgres.cdc.payment.business.commerceItem.CommerceItem;
import iop.postgres.cdc.payment.business.event.order.OrderCreationEvent;
import iop.postgres.cdc.payment.infrastructure.payment.CiPaymentItemEntity;
import iop.postgres.cdc.payment.infrastructure.payment.CiPaymentItemRepository;
import iop.postgres.cdc.payment.infrastructure.payment.PaymentEntity;
import iop.postgres.cdc.payment.infrastructure.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CiPaymentItemRepository ciPaymentItemRepository;

    public void createPayment(OrderCreationEvent orderCreationEvent) {
        log.info("Creating payment for order {}", orderCreationEvent.getId());
        paymentRepository.save(PaymentEntity.from(orderCreationEvent));
    }

    @Transactional
    public void updateOrCreatePayment(PaymentUpdateCommand paymentUpdateCommand) {
        CommerceItem commerceItem = paymentUpdateCommand.getCommerceItem();
        if(Objects.isNull(commerceItem)){
            return;
        }
        log.info("Create/Update payment for order {}", commerceItem.orderId());
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(commerceItem.orderId());
        CiPaymentItemEntity ciPaymentItemEntity = CiPaymentItemEntity.from(commerceItem, paymentEntity.getId());
        ciPaymentItemRepository.save(ciPaymentItemEntity);
        if(Objects.nonNull(paymentEntity)){
            paymentEntity.getCiPaymentItems().add(ciPaymentItemEntity);
            paymentRepository.save(paymentEntity);
        } else {
            paymentEntity = PaymentEntity.from(paymentUpdateCommand);
            paymentEntity.getCiPaymentItems().add(ciPaymentItemEntity);
            paymentRepository.save(paymentEntity);
        }
    }
}

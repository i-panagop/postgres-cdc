package iop.postgres.cdc.payment.business.payment;

import iop.postgres.cdc.payment.business.command.CommerceItem;
import iop.postgres.cdc.payment.business.command.CreatePaymentCommand;
import iop.postgres.cdc.payment.infrastructure.payment.CiPaymentItemEntity;
import iop.postgres.cdc.payment.infrastructure.payment.CiPaymentItemRepository;
import iop.postgres.cdc.payment.infrastructure.payment.PaymentEntity;
import iop.postgres.cdc.payment.infrastructure.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CiPaymentItemRepository ciPaymentItemRepository;

    @Transactional
    public void handleCreatePaymentCommand(CreatePaymentCommand createPaymentCommand) {
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(createPaymentCommand.getOrderId());
        if (Objects.nonNull(paymentEntity)) {
            updatePayment(createPaymentCommand, paymentEntity);
        } else {
            createPayment(createPaymentCommand);
        }
    }

    private void createPayment(CreatePaymentCommand createPaymentCommand) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(UUID.randomUUID());
        paymentEntity.setOrderId(createPaymentCommand.getOrderId());
        paymentEntity.setAmount(createPaymentCommand.getAmount());
        paymentEntity = paymentRepository.save(paymentEntity);
        if (CollectionUtils.isNotEmpty(createPaymentCommand.getCommerceItems())) {
            for (CommerceItem commerceItem : createPaymentCommand.getCommerceItems()) {
                createCiPaymentItem(commerceItem, paymentEntity);
            }
        }
        paymentRepository.save(paymentEntity);
    }

    private void createCiPaymentItem(CommerceItem commerceItem, PaymentEntity paymentEntity) {
        CiPaymentItemEntity ciPaymentItemEntity = searchForExistingItemByProductId(commerceItem, paymentEntity);
        if(Objects.isNull(ciPaymentItemEntity)){
            ciPaymentItemEntity = new CiPaymentItemEntity();
            ciPaymentItemEntity.setId(UUID.randomUUID());
            ciPaymentItemEntity.setPaymentId(paymentEntity.getId());
            ciPaymentItemEntity.setProductId(commerceItem.productId());
            ciPaymentItemEntity.setQuantity(commerceItem.quantity());
            ciPaymentItemEntity.setPrice(commerceItem.price());
            ciPaymentItemEntity.setTotalPrice(commerceItem.totalPrice());
            ciPaymentItemRepository.save(ciPaymentItemEntity);
        } else {
            ciPaymentItemEntity.setPrice(commerceItem.price());
            ciPaymentItemEntity.setQuantity(commerceItem.quantity());
            ciPaymentItemEntity.setTotalPrice(commerceItem.totalPrice());
            ciPaymentItemRepository.save(ciPaymentItemEntity);
        }
        paymentEntity.getCiPaymentItems().add(ciPaymentItemEntity);
    }

    private void updatePayment(CreatePaymentCommand createPaymentCommand, PaymentEntity paymentEntity) {
        if (CollectionUtils.isNotEmpty(createPaymentCommand.getCommerceItems())) {
            for (CommerceItem commerceItem : createPaymentCommand.getCommerceItems()) {
                createCiPaymentItem(commerceItem, paymentEntity);
            }
        } else {
            paymentEntity.setAmount(createPaymentCommand.getAmount());
        }
        paymentRepository.save(paymentEntity);
    }

    private CiPaymentItemEntity searchForExistingItemByProductId(CommerceItem commerceItem,
        PaymentEntity paymentEntity) {
        return paymentEntity.getCiPaymentItems().stream()
            .filter(ciPaymentItemEntity -> ciPaymentItemEntity.getProductId().equals(commerceItem.productId()))
            .findFirst()
            .orElse(null);
    }
}

package iop.postgres.cdc.commerceitem.business.commerceItem;

import iop.postgres.cdc.commerceitem.business.event.CommerceItemCreationEvent;
import iop.postgres.cdc.commerceitem.infrastructure.commerceitem.CommerceItemEntity;
import iop.postgres.cdc.commerceitem.infrastructure.commerceitem.CommerceItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommerceItemService {

    private final CommerceItemRepository commerceItemRepository;

    public void createCommerceItems(CommerceItemCreationEvent commerceItemCreationEvent) {
        log.info("Creating commerce items");
        commerceItemRepository.saveAll(
            commerceItemCreationEvent.getItems().stream()
                .map(CommerceItemEntity::from)
                .toList()
        );
    }

}

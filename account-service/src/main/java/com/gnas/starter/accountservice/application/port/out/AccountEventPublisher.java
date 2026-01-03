package com.gnas.starter.accountservice.application.port.out;

import com.gnas.starter.accountservice.domain.event.AccountOpenedEvent;

public interface AccountEventPublisher {
    void publishAccountOpened(AccountOpenedEvent event);
}

package org.example.messaging;

import org.example.domain.types.AuditMessage;
import org.example.messaging.impl.SendResult;

public interface AuditMessageProducer {
    SendResult send(AuditMessage message);
}

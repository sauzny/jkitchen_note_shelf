package org.example.demo0201.ddd;

public interface AuditMessageProducer {
    SendResult send(AuditMessage message);
}

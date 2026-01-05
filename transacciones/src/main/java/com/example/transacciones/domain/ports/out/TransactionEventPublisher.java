package com.example.transacciones.domain.ports.out;

import com.example.transacciones.domain.model.Transaction;
import reactor.core.publisher.Flux;

public interface TransactionEventPublisher {
    void publishTransaction(Transaction transaction);
    Flux<Transaction> subscribe();
}

package com.example.transacciones.domain.ports.out;

import com.example.transacciones.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionPort {
    Mono<Transaction> findById(Long id);
    Flux<Transaction> findAll();
    Mono<Transaction> save(Transaction trx);
    void deleteById(Long id);
}

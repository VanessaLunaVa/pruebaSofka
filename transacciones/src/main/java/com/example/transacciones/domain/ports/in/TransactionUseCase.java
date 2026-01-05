package com.example.transacciones.domain.ports.in;

import com.example.transacciones.application.command.transacciones.TransaccionesCommand;
import com.example.transacciones.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionUseCase {
    Mono<Transaction> findById(Long id);

    Mono<Transaction> save(TransaccionesCommand trx);

    Mono<Transaction> update(Long id, TransaccionesCommand request);

    void delete(Long id);

    Flux<Transaction> findAll();

    Flux<Transaction> streamTransactions();
    Flux<Transaction> streamClientTransactions(String clientId);

}

package com.example.transacciones.infrastructure.adapter.out.event;

import com.example.transacciones.domain.model.Transaction;
import com.example.transacciones.domain.ports.out.TransactionEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class TransactionEventPublisherAdapter implements TransactionEventPublisher {

    // Sink para publicar eventos
    private final Sinks.Many<Transaction> sink = Sinks.many()
            .multicast()
            .onBackpressureBuffer();

    @Override
    public void publishTransaction(Transaction transaction) {
        sink.tryEmitNext(transaction);
    }

    @Override
    public Flux<Transaction> subscribe() {
        return sink.asFlux();
    }
}

package com.example.transacciones.application.service;


import com.example.transacciones.application.command.transacciones.TransaccionesCommand;
import com.example.transacciones.domain.model.Comision;
import com.example.transacciones.domain.model.Transaction;
import com.example.transacciones.domain.ports.in.TransactionUseCase;
import com.example.transacciones.domain.ports.out.TransactionPort;
import com.example.transacciones.domain.ports.out.TransactionEventPublisher;
import com.example.transacciones.infrastructure.adapter.out.persistence.transacciones.mapper.TransactionEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TransactionService implements TransactionUseCase {

    private final TransactionPort transaccionesPort;
    private final TransactionEntityMapper mapper;
    private final TransactionEventPublisher eventPublisher;

    @Override
    public Mono<Transaction> findById(Long id) {
        return transaccionesPort.findById(id);
    }

    @Override
    public Mono<Transaction> save(TransaccionesCommand trx) {
        // Publicar evento para SSE
        return transaccionesPort.save(
                Transaction.create(trx.valor(), trx.clientId(), trx.description(), new Comision()))
                        .doOnSuccess(eventPublisher::publishTransaction);
    }

    @Override
    public Mono<Transaction> update(Long id, TransaccionesCommand request) {
        Transaction trx = Transaction.create(request.valor(), request.clientId(), request.description(), new Comision());
        return transaccionesPort.findById(id)
                .map(mapper::toEntity)
                .flatMap(transactionExistence -> {
                    transactionExistence.setMonto(trx.getMonto());
                    transactionExistence.setComision(trx.getCommission());
                    transactionExistence.setTotal(trx.getTotal());
                    return transaccionesPort.save(mapper.toDomain(transactionExistence));
                });
    }

    @Override
    public void delete(Long id) {
        transaccionesPort.deleteById(id);
    }

    @Override
    public Flux<Transaction> findAll() {
        return transaccionesPort.findAll();
    }

    @Override
    public Flux<Transaction> streamTransactions() {
        // Retornar el stream de eventos en tiempo real
        return eventPublisher.subscribe();
    }

    @Override
    public Flux<Transaction> streamClientTransactions(String clientId) {
        // Filtrar transacciones por usuario
        return eventPublisher.subscribe()
                .filter(transaction -> transaction.getClienteId().equals(clientId));
    }


}

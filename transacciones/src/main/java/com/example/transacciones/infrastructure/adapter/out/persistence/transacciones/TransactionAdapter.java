package com.example.transacciones.infrastructure.adapter.out.persistence.transacciones;

import com.example.transacciones.domain.model.Transaction;
import com.example.transacciones.domain.ports.out.TransactionPort;
import com.example.transacciones.infrastructure.adapter.out.persistence.transacciones.mapper.TransactionEntityMapper;
import com.example.transacciones.infrastructure.adapter.out.persistence.transacciones.repository.TransactionRepository;
import com.example.transacciones.util.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Repository
@Slf4j
public class TransactionAdapter implements TransactionPort {

    private final TransactionRepository trxRepository;
    private final TransactionEntityMapper mapper;

    @Override
    public Mono<Transaction> findById(Long id) {
        return trxRepository.findById(id)
                .map(mapper::toDomain).switchIfEmpty(Mono.error(
                        new NotFoundException("Transaccion con id " + id + " no existe")));
    }

    @Override
    public Flux<Transaction> findAll() {
        return trxRepository.findAll().map(mapper::toDomain)
                .switchIfEmpty(Mono.error(new NotFoundException("NO_RECORDS_FOUND")));
    }

    @Override
    public Mono<Transaction> save(Transaction trx) {
        return trxRepository.save(mapper.toEntity(trx))
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        trxRepository.deleteById(id);
    }
}

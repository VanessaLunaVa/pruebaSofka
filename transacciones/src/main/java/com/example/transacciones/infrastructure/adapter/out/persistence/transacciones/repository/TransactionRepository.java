package com.example.transacciones.infrastructure.adapter.out.persistence.transacciones.repository;

import com.example.transacciones.infrastructure.adapter.out.persistence.transacciones.entity.TransaccionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TransactionRepository extends ReactiveCrudRepository<TransaccionEntity, Long> {
}

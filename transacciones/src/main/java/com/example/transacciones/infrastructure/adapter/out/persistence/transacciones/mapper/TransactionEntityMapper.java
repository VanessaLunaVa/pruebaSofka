package com.example.transacciones.infrastructure.adapter.out.persistence.transacciones.mapper;

import com.example.transacciones.domain.model.Transaction;
import com.example.transacciones.infrastructure.adapter.out.persistence.transacciones.entity.TransaccionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityMapper {

    public Transaction toDomain(TransaccionEntity entity) {
        Transaction trx = new Transaction(
                entity.getId(),
                entity.getClienteId(), entity.getMonto(), entity.getComision(),
                entity.getTotal(), entity.getFecha(), entity.getDescription()
        );
        trx.setId(entity.getId());
        return trx;
    }

    public TransaccionEntity toEntity(Transaction trx) {
        TransaccionEntity entity = new TransaccionEntity();
        entity.setId(trx.getId());
        entity.setFecha(trx.getFecha());
        entity.setMonto(trx.getMonto());
        entity.setComision(trx.getCommission());
        entity.setClienteId(trx.getClienteId());
        entity.setTotal(trx.getTotal());
        entity.setDescription(trx.getDescription());
        return entity;
    }
}

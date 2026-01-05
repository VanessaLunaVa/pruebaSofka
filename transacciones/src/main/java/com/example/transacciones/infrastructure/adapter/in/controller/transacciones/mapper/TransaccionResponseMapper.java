package com.example.transacciones.infrastructure.adapter.in.controller.transacciones.mapper;

import com.example.transacciones.domain.model.Transaction;
import com.example.transacciones.infrastructure.adapter.in.controller.transacciones.dto.TransactionResponse;

public class TransaccionResponseMapper {

    public static TransactionResponse toResponse(Transaction trx) {
        return new TransactionResponse(trx.getId(), trx.getFecha(), trx.getMonto(),
                trx.getCommission(), trx.getTotal(), trx.getClienteId());
    }

}

package com.example.transacciones.infrastructure.adapter.in.controller.transacciones.mapper;

import com.example.transacciones.application.command.transacciones.TransaccionesCommand;
import com.example.transacciones.infrastructure.adapter.in.controller.transacciones.dto.TransactionRequest;

public class TransactionRequestMapper {

    public static TransaccionesCommand toCommand(
            TransactionRequest request
    ) {
        return new TransaccionesCommand(
                request.clientId(),
                request.valor(),
                request.description()
        );
    }
}

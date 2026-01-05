package com.example.transacciones.application.command.transacciones;

import java.math.BigDecimal;

public record TransaccionesCommand(
        Long clientId,
        BigDecimal valor,
        String description
) {}

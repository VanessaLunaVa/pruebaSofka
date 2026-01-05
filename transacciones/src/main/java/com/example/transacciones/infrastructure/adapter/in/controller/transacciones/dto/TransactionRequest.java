package com.example.transacciones.infrastructure.adapter.in.controller.transacciones.dto;

import java.math.BigDecimal;

public record TransactionRequest(BigDecimal valor, Long clientId, String description) {

}

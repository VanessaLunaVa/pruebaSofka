package com.example.transacciones.infrastructure.adapter.in.controller.transacciones.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(Long id, LocalDateTime fecha, BigDecimal monto,
                                  BigDecimal commission, BigDecimal total, Long clientId) {}



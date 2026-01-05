package com.example.transacciones.domain.model;

import com.example.transacciones.util.exception.MontoInvalidoException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private Long id;
    private Long clienteId;
    private BigDecimal monto;
    private BigDecimal commission;
    private BigDecimal total;
    private LocalDateTime fecha;
    private String description;

    public static Transaction create(BigDecimal monto, Long cliente, String description, Comision comision) {
        validarMonto(monto);

        return new Transaction(null, cliente, monto, comision.calcularCommission(monto),
                comision.calcularMontoTotal(monto), LocalDateTime.now(), description);
    }

    /**
     * Validar monto
     */
    private static void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new MontoInvalidoException();
        }
    }
}

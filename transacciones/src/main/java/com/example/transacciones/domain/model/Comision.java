package com.example.transacciones.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Comision {

    private static final BigDecimal LIMITE_SUPERIOR = new BigDecimal("10000");
    private static final BigDecimal COMMISSION_ALTA = new BigDecimal("0.05");    // 5%
    private static final BigDecimal COMMISSION_BABA = new BigDecimal("0.02");    // 2%

    public BigDecimal calcularCommission(BigDecimal montoTransaccion) {

        BigDecimal percentCommission = getPercentCommission(montoTransaccion);
        BigDecimal commission = montoTransaccion.multiply(percentCommission);

        return redondearComision(commission);
    }

    /**
     * Determina el porcentaje de comision basado en el monto
     */
    public BigDecimal getPercentCommission(BigDecimal monto) {
        if (monto.compareTo(LIMITE_SUPERIOR) > 0) {
            return COMMISSION_ALTA;  // 5%
        }
        return COMMISSION_BABA;      // 2%
    }

    /**
     * Redondea la comision a 2 decimales
     */
    private BigDecimal redondearComision(BigDecimal comision) {
        return comision.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el monto total (transaccion + comision)
     */
    public BigDecimal calcularMontoTotal(BigDecimal montoTransaccion) {
        BigDecimal commission = calcularCommission(montoTransaccion);
        return montoTransaccion.add(commission);
    }
}

package com.example.transacciones.infrastructure.adapter.out.persistence.transacciones.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("TRANSACCION")
public class TransaccionEntity {

    @Id
    private Long id;
    @Column("CLIENTE_ID")
    private Long clienteId;
    private LocalDateTime fecha;
    private BigDecimal monto;
    private BigDecimal comision;
    private BigDecimal total;
    private String description;
}

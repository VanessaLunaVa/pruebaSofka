package com.example.transacciones;

import com.example.transacciones.application.command.transacciones.TransaccionesCommand;
import com.example.transacciones.application.service.TransactionService;
import com.example.transacciones.domain.model.Comision;
import com.example.transacciones.domain.model.Transaction;
import com.example.transacciones.domain.ports.out.TransactionEventPublisher;
import com.example.transacciones.domain.ports.out.TransactionPort;
import com.example.transacciones.util.exception.MontoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionUseCaseTest {

    @Mock
    private TransactionPort transactionPort;

    @Mock
    private TransactionEventPublisher eventPublisher;

    @InjectMocks
    private TransactionService transactionService;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    private TransaccionesCommand command;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        command = new TransaccionesCommand(1L,
                new BigDecimal("100000"),
                "Compra en línea"
        );

        transaction = new Transaction(
                1L,
                1L,
                new BigDecimal("100000"),
                new BigDecimal("3000"),
                new BigDecimal("103000"),
                LocalDateTime.now(),
                "Compra en línea"
        );
    }

    @Test
    @DisplayName("Debe guardar transacción y publicar evento exitosamente")
    void createAndPublish() {
        when(transactionPort.save(any(Transaction.class)))
                .thenReturn(Mono.just(transaction));
        doNothing().when(eventPublisher).publishTransaction(any(Transaction.class));

        Mono<Transaction> result = transactionService.save(command);

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();

        verify(transactionPort).save(transactionCaptor.capture());
        verify(eventPublisher).publishTransaction(transaction);

        Transaction capturedTransaction = transactionCaptor.getValue();
        assertThat(capturedTransaction.getClienteId()).isEqualTo(command.clientId());
        assertThat(capturedTransaction.getMonto()).isEqualByComparingTo(command.valor());
        assertThat(capturedTransaction.getDescription()).isEqualTo(command.description());
    }

    @Test
    @DisplayName("Debe encontrar transaccion por ID exitosamente")
    void findByIdId() {
        Long transactionId = 1L;
        when(transactionPort.findById(transactionId))
                .thenReturn(Mono.just(transaction));

        Mono<Transaction> result = transactionService.findById(transactionId);

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();

        verify(transactionPort, times(1)).findById(transactionId);
    }

    @Test
    @DisplayName("Debe crear una transaccion valida con todos los campos calculados")
    void validCreateTransaction() {
        Transaction transaction = Transaction.create(new BigDecimal("1000.00"), 1L,
                "Compra en tienda", new Comision());

        assertThat(transaction).isNotNull();
        assertThat(transaction.getId()).isNull();
        assertThat(transaction.getClienteId()).isEqualTo(1L);
        assertThat(transaction.getMonto()).isEqualByComparingTo(new BigDecimal("1000.00"));
        assertThat(transaction.getCommission()).isNotNull();
        assertThat(transaction.getTotal()).isNotNull();
        assertThat(transaction.getFecha()).isNotNull()
                .isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(transaction.getDescription()).isEqualTo("Compra en tienda");
    }

    @Test
    @DisplayName("Debe lanzar excepcion cuando el monto es null")
    void exceptionMontoIsNull() {
        Long clienteId = 1L;
        String description = "Test monto null";
        Comision comision = new Comision();

        // Act & Assert
        assertThatThrownBy(() -> Transaction.create(null, clienteId, description, comision))
                .isInstanceOf(MontoInvalidoException.class);
    }

}

package com.example.transacciones.infrastructure.adapter.in.controller.transacciones;

import com.example.transacciones.application.command.transacciones.TransaccionesCommand;
import com.example.transacciones.domain.ports.in.TransactionUseCase;
import com.example.transacciones.infrastructure.adapter.in.controller.transacciones.dto.TransactionRequest;
import com.example.transacciones.infrastructure.adapter.in.controller.transacciones.dto.TransactionResponse;
import com.example.transacciones.infrastructure.adapter.in.controller.transacciones.mapper.TransactionRequestMapper;
import com.example.transacciones.infrastructure.adapter.in.controller.transacciones.mapper.TransaccionResponseMapper;
import com.example.transacciones.util.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransaccionesController {

    private final TransactionUseCase transactionUseCase;

    @GetMapping()
    public Mono<GeneralResponse<List<TransactionResponse>>> findAll() {
        return transactionUseCase.findAll()
                .map(TransaccionResponseMapper::toResponse)
                .collectList()
                .map(list -> GeneralResponse.ok(HttpStatus.OK.getReasonPhrase(), list));
    }

    @GetMapping("/{id}")
    public Mono<GeneralResponse<TransactionResponse>> findId(@PathVariable Long id) {
        return transactionUseCase.findById(id).map(TransaccionResponseMapper::toResponse)
                .map(list -> GeneralResponse.ok(HttpStatus.OK.getReasonPhrase(), list));
    }

    @PostMapping()
    public Mono<GeneralResponse<TransactionResponse>> save(@RequestBody TransactionRequest trx) {
        TransaccionesCommand command = TransactionRequestMapper.toCommand(trx);
        return transactionUseCase.save(command).map(TransaccionResponseMapper::toResponse)
                .map(s -> GeneralResponse.ok(HttpStatus.OK.getReasonPhrase(), s));
    }

    @PutMapping("/{id}")
    public Mono<GeneralResponse<TransactionResponse>> update(@PathVariable Long id, @RequestBody TransactionRequest mov) {
        TransaccionesCommand command = TransactionRequestMapper.toCommand(mov);
        return transactionUseCase.update(id, command).map(TransaccionResponseMapper::toResponse)
                .map(u -> GeneralResponse.ok(HttpStatus.OK.getReasonPhrase(), u));
    }

    @DeleteMapping("{id}")
    public GeneralResponse<String> delete(@PathVariable Long id) {
        transactionUseCase.delete(id);
        return GeneralResponse.ok(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TransactionResponse> streamTransactions() {
        log.info("Cliente conectado al stream");

        return transactionUseCase.streamTransactions()
                .map(TransaccionResponseMapper::toResponse)
                .doOnNext(tx -> log.info("Enviando transacción: {}", tx.id()))
                .doOnSubscribe(s -> log.info("Subscripción iniciada"))
                .doOnComplete(() -> log.info("Stream completado"))
                .doOnCancel(() -> log.info("Cliente desconectado"))
                .doOnError(e -> log.error("Error en stream", e));
    }

}

package com.phorest.client;

import com.phorest.client.model.ClientDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping(path = "/top-clients", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ClientDto>> topClientsForPurchases(
            @RequestParam(value = "start_date") @DateTimeFormat(iso = DATE) @NonNull LocalDate startTime,
            @RequestParam(value = "limit") int limit
    ) {
        var clients = this.clientService.findTopClients(limit, startTime.toString());

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping(path = "/client/{identifier}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDto> findClient(@PathVariable @NonNull String identifier) {
        var client = clientService.findClient(identifier);
        return new ResponseEntity<>(client.toDto(), HttpStatus.OK);
    }
}

package com.phorest.client;

import com.phorest.client.model.ClientDto;
import com.phorest.data.Client;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/top-clients")
    public ResponseEntity<Collection<ClientDto>> topClientsForPurchases(
            @RequestParam(value = "start_date") @DateTimeFormat(iso = DATE) @NonNull LocalDate startTime,
            @RequestParam(value = "limit") int limit
    ){
        Collection<ClientDto> clients =
                this.clientService.findTopClients(limit, startTime.toString());

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

}

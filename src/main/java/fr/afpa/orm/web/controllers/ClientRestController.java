package fr.afpa.orm.web.controllers;

import org.springframework.web.bind.annotation.RestController;

import fr.afpa.orm.dto.AccountDTO;
import fr.afpa.orm.dto.ClientDTO;
import fr.afpa.orm.dto.InsuranceDTO;
import fr.afpa.orm.services.AccountService;
import fr.afpa.orm.services.ClientService;
import fr.afpa.orm.services.InsuranceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/clients")

public class ClientRestController {

    private final ClientService clientService;
    
    private final AccountService accountService;

    private final InsuranceService insuranceService;

    ClientRestController(ClientService clientService, AccountService accountService, InsuranceService insuranceService) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.insuranceService = insuranceService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll() {
        return new ResponseEntity<>(clientService.getAllClient(), HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getOne(@PathVariable UUID id) {
        return new ResponseEntity<ClientDTO>(clientService.getOneClient(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<AccountDTO>> getAccountsByClient(@PathVariable UUID id) {
        try {
            List<AccountDTO> accounts = accountService.getAccountsByIdClient(id);
            return new ResponseEntity<List<AccountDTO>>(accounts, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/insurances")
    public ResponseEntity<List<InsuranceDTO>> getInsurancesByClient(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(insuranceService.getInsurancesByClientId(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO client) {
        return new ResponseEntity<ClientDTO>(clientService.createClient(client), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ClientDTO client) {
        try {
            return new ResponseEntity<ClientDTO>(clientService.updateClient(id, client), HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void remove (@PathVariable UUID id, HttpServletResponse response) {
        clientService.removeClient(id, response);
    }
}

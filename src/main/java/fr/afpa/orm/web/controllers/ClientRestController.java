package fr.afpa.orm.web.controllers;

import org.springframework.web.bind.annotation.RestController;

import fr.afpa.orm.dto.ClientDTO;
import fr.afpa.orm.services.ClientService;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientDTO> getAll() {
        return clientService.getAllClient();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getOne(@PathVariable UUID id) {
        return clientService.getOneClient(id);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO client) {
        return clientService.createClient(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ClientDTO client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    public void remove (@PathVariable UUID id, HttpServletResponse response) {
        clientService.removeClient(id, response);
    }
}

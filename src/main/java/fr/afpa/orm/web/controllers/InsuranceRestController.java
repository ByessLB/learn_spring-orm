package fr.afpa.orm.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.afpa.orm.dto.ClientDTO;
import fr.afpa.orm.dto.InsuranceDTO;
import fr.afpa.orm.services.ClientService;
import fr.afpa.orm.services.InsuranceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/insurances")
public class InsuranceRestController {

    private final InsuranceService service;
    private final ClientService clientService;

    public InsuranceRestController(InsuranceService service, ClientService clientService) {
        this.service = service;
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<InsuranceDTO>> getAllInsurances() {
        return new ResponseEntity<>(service.getAllInsurances(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceDTO> getOneInsurance(Long id) {
        return new ResponseEntity<>(service.getOneInsurance(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/clients")
    public ResponseEntity<List<ClientDTO>> getClientsByOneInsurance(Long id) {
        try {
            return new ResponseEntity<>(clientService.getAllClientsByInsuranceId(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<InsuranceDTO> createInsurance(@RequestBody InsuranceDTO insurance) {
        return new ResponseEntity<>(service.createInsurance(insurance), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInsurance(@PathVariable Long id, @RequestBody InsuranceDTO insurance) {
        try {
            return new ResponseEntity<>(service.updateInsurance(id, insurance), HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void removeInsurance(@PathVariable Long id, HttpServletResponse response) {
        service.removeInsurance(id, response);
    }
}

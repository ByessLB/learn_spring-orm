package fr.afpa.orm.dto;

import java.time.LocalDate;
import java.util.UUID;


public record ClientDTO(UUID id, String firstName, String lastName, String email, LocalDate birthdate) {
}

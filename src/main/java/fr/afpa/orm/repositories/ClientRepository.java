package fr.afpa.orm.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.afpa.orm.entities.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}

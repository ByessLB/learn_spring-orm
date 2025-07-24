package fr.afpa.orm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.afpa.orm.entities.Account;
import java.util.Optional;
import java.time.LocalDateTime;


/**
 *  implémenter un "repository" (similaire à un DAO) permettant d'interagir avec les données de la BDD
 * Tutoriel -> https://www.geeksforgeeks.org/spring-boot-crudrepository-with-example/
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    Optional<Account> findByCreationTime(LocalDateTime creationTime);
}

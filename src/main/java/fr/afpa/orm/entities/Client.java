package fr.afpa.orm.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="owner")
public class Client {

    /**
     * Identifiant unique de l'utilisateur
     * Article présentant l'utilisation d'UUID -> https://www.baeldung.com/java-hibernate-uuid-primary-key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

      /**
     * Prénom du propriétaire
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * Nom du propriétaire
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * Adresse email (unique) du propriétaire
     */
    @Column(name = "email", unique = true)
    private String email;
    /**
     * Date d'anniversaire du prop
     */
    @Column(name = "birthdate")
    private LocalDate birthdate;

    /**
     * Association de type "OneToMany" : une personne peut avoir plusieurs comptes
     */
    @JsonIgnore
    @OneToMany(targetEntity = Account.class, mappedBy = "client")
    private List<Account> accounts;

    public Client() {
        // Constructeur vide.
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}

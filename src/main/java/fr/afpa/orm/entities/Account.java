package fr.afpa.orm.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Classe représentant le compte bancaire d'un utilisateur
 * 
 * faire de cette classe une entité
 * Plus d'informations sur les entités ->
 * https://gayerie.dev/epsi-b3-orm/javaee_orm/jpa_entites.html
 * Attention de bien choisir les types en fonction de ceux du script SQL.
 */
@Entity
@Table(name = "Account")
public class Account {
    /**
     * Identifiant unique du compte
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creationtime")
    private LocalDateTime creationTime;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "active")
    private Boolean active;

    /**
     * ajout d'une association de type @ManyToOne : plusieurs comptes différents
     * peuvent être associés à la même personne
     * 
     * Tutoriel présentant l'utilisation d'une telle association :
     * https://koor.fr/Java/TutorialJEE/jee_jpa_many_to_one.wp
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    /*
     * implémenter un constructeur vide --> obligatoire pour l'utilisation d'un ORM
     */
    public Account() {
    }

    public Account(Long id, LocalDateTime creationTime, BigDecimal balance, Boolean active, Client client) {
        this.id = id;
        this.creationTime = creationTime;
        this.balance = balance;
        this.active = active;
        this.client = client;
    }

    public Account(LocalDateTime creationTime, BigDecimal balance, Boolean active, Client client) {
        this.creationTime = creationTime;
        this.balance = balance;
        this.active = active;
        this.client = client;
    }

    /*
     * implémenter les getters et les setters
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean valide) {
        this.active = valide;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

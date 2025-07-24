package fr.afpa.orm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import fr.afpa.orm.entities.Account;
import fr.afpa.orm.entities.Client;

/**
 * : implémenter un DTO (uniquement à partir de l'implémentation de la relation
 * "OneToMany")
 * 
 * Attention : il faudra peut être 1 DTO par classe métier ?
 * 
 * Plus d'informations sur la pattern DTO :
 * https://medium.com/@zubeyrdamar/java-spring-boot-handling-infinite-recursion-a95fe5a53c92
 */

public class AccountDTO {

    private Long id;
    private LocalDateTime createionTime;
    private BigDecimal balance;
    private Boolean valide;
    private ClientDTO client;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.createionTime = account.getCreationTime();
        this.balance = account.getBalance();
        this.valide = account.getValide();

        Client clientEnity = account.getClient();
        client = new ClientDTO(clientEnity.getId(),
                clientEnity.getFirstName(),
                clientEnity.getLastName(),
                clientEnity.getEmail(),
                clientEnity.getBirthdate());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateionTime() {
        return createionTime;
    }

    public void setCreateionTime(LocalDateTime createionTime) {
        this.createionTime = createionTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }
}

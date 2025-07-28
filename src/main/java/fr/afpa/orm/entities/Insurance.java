package fr.afpa.orm.entities;

import java.util.ArrayList;
import java.util.List;

import fr.afpa.orm.dto.InsuranceDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "insurance")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "Subscribes",
                joinColumns = @JoinColumn(name ="insurance_id"),
                inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> clients = new ArrayList<>();

    public Insurance() {
    }

    public Insurance(InsuranceDTO insuranceDTO) {
        this.id = insuranceDTO.getId();
        this.name = insuranceDTO.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}

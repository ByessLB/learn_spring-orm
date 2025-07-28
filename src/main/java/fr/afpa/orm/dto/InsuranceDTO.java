package fr.afpa.orm.dto;

import fr.afpa.orm.entities.Insurance;

public class InsuranceDTO {
    private Long id;
    private String name;

    public InsuranceDTO() {
    }

    public InsuranceDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public InsuranceDTO(Insurance insurances) {
        this.id = insurances.getId();
        this.name = insurances.getName();
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
}

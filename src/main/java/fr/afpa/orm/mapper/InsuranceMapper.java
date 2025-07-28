package fr.afpa.orm.mapper;

import fr.afpa.orm.dto.InsuranceDTO;
import fr.afpa.orm.entities.Insurance;

public class InsuranceMapper {
    public InsuranceDTO convertToDTO(Insurance insurance) {
        return new InsuranceDTO(insurance);
    }

    public Insurance convertToEntity(InsuranceDTO DTO) {
        return new Insurance(DTO);
    }
}

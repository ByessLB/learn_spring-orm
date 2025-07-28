package fr.afpa.orm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.afpa.orm.dto.InsuranceDTO;
import fr.afpa.orm.entities.Insurance;
import fr.afpa.orm.mapper.InsuranceMapper;
import fr.afpa.orm.repositories.InsuranceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    private final InsuranceMapper mapper;

    public InsuranceService(InsuranceRepository insuranceRepository, InsuranceMapper mapper) {
        this.insuranceRepository = insuranceRepository;
        this.mapper = mapper;
    }

    /**
     * GetAllInsurances
     *
     * @return
     * Renvoie toute les assurances
     */
    public List<InsuranceDTO> getAllInsurances() {
        return insuranceRepository.findAll()
                                    .stream()
                                    .map(insurance -> new InsuranceDTO(insurance))
                                    .collect(Collectors.toList());
    }

    /**
     * GetOneInsurance
     * @param id
     * @return
     * Renvoie une assurance selon l'ID
     */
    public InsuranceDTO getOneInsurance(Long id) {
        return mapper.convertToDTO(insuranceRepository.findById(id).orElse(null));
    }

    /**
     * getInsurancesByClientId
     * @param id
     * @return
     * Vérifie si assurance(s) sont lié à client
     * renvoie la liste des assurances en DTO
     */
    public List<InsuranceDTO> getInsurancesByClientId(UUID id) {
        List<Insurance> insurances = insuranceRepository.findAllByClientId(id);

        if (insurances.isEmpty()) {
            throw new EntityNotFoundException("Insurances not found");
        }

        List<InsuranceDTO> insurancesDTO = new ArrayList<>();

        for (Insurance insurance : insurances) {
            insurancesDTO.add(mapper.convertToDTO(insurance));
        }

        return insurancesDTO;
    }

    /**
     * createInsurance
     * @param insuranceDTO
     * @return
     * 
     * Détail : 
     * - Convertie les données reçu (qui est en DTO) en entité pour
     * pouvoir l'enreigstrer (save) dans le repository
     * - Renvoie les données reçu lors de l'nereigstrement converti
     * en DTO
     */
    public InsuranceDTO createInsurance(InsuranceDTO insuranceDTO) {
        return mapper.convertToDTO(insuranceRepository.save(mapper.convertToEntity(insuranceDTO)));
    }

    /**
     * updateInsurance
     * @param id
     * @param insuranceDTO
     * @return
     * 
     * On vérifie si l'ID reçu renvoie bien à une entité
     * On recherche une correspondance pour pouvoir 
     * enregistrer les modifications souhaité
     */
    public InsuranceDTO updateInsurance(Long id, InsuranceDTO insuranceDTO) {
        Optional<Insurance> originalInsurance = insuranceRepository.findById(id);

        if (originalInsurance.isEmpty()) {
            throw new EntityNotFoundException("Insurance not found");
        }

        if (!id.equals(insuranceDTO.getId())) {
            throw new IllegalArgumentException("Id mismatch between path and body");
        }

        Insurance insurance = originalInsurance.get();
        insurance.setName(insuranceDTO.getName());

        return mapper.convertToDTO(insuranceRepository.save(insurance));
    }

    /**
     * removeInsurance
     * @param id
     * @param response
     * 
     * On verifie si une assurance existe bien avec l'ID reçu
     * avant de le supprimer.
     * Gestion des retours de réponses HTTP
     */
    public void removeInsurance(Long id, HttpServletResponse response) {
        if (insuranceRepository.existsById(id)) {
            insuranceRepository.deleteById(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

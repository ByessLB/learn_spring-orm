package fr.afpa.orm.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.afpa.orm.entities.Insurance;

public interface InsuranceRepository extends JpaRepository <Insurance, Long> {

    List<Insurance> findAllByClientId(UUID id);
}

package tn.myadvans.authentification.authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.myadvans.authentification.authentification.entities.Individual;

public interface IndividualRepository extends JpaRepository<Individual,Long> {

    Individual findIndividualByUserCode(String userCode);
    Boolean existsByUserCode(String userCode);
}

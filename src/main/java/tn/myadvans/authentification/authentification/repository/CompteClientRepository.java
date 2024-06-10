package tn.myadvans.authentification.authentification.repository;

import tn.myadvans.authentification.authentification.entities.CompteClient;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.myadvans.authentification.authentification.entities.Opportunity;

public interface CompteClientRepository extends JpaRepository<CompteClient, Long> {

    Boolean existsByCustomer(String userCode);
    Boolean existsByUserCode(String userCode);
    CompteClient findByCustomer(String customer);
}

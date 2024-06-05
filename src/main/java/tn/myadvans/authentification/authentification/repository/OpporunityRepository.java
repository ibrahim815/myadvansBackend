package tn.myadvans.authentification.authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.myadvans.authentification.authentification.entities.Opportunity;

public interface OpporunityRepository extends JpaRepository<Opportunity,Long> {

    Boolean existsByCustomer(String userCode);
    Boolean existsByUserCode(String userCode);
    Opportunity findByCustomer(String customer);

}

package tn.myadvans.authentification.authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.myadvans.authentification.authentification.entities.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

    Client findClientByUsername(String userName);
}

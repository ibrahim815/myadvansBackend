package tn.myadvans.authentification.authentification.service;

import org.springframework.web.multipart.MultipartFile;
import tn.myadvans.authentification.authentification.entities.Client;
import tn.myadvans.authentification.authentification.entities.Customer;
import tn.myadvans.authentification.authentification.entities.Individual;
import tn.myadvans.authentification.authentification.entities.Opportunity;

import java.io.IOException;

public interface ClientService {

    Client getClient(Long id);

    String addPicture(Long idUser, MultipartFile file) throws IOException;

    Client modifyClient(Client client);

    String deleteClient(Long id);

    Client addProspect(Individual individual);
    Client addOpportunite(Opportunity opportunity);
    Customer searshcustomer(String idCustomer);
}

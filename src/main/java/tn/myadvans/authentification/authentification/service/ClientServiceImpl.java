package tn.myadvans.authentification.authentification.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.myadvans.authentification.authentification.entities.*;
import tn.myadvans.authentification.authentification.repository.ClientRepository;
import tn.myadvans.authentification.authentification.repository.IndividualRepository;
import tn.myadvans.authentification.authentification.repository.OpporunityRepository;
import tn.myadvans.authentification.authentification.repository.RoleRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static tn.myadvans.authentification.authentification.entities.ERole.ROLE_CLIENT;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final CcService ccService;
    private final IndividualRepository individualRepository;
    private final OpporunityRepository opporunityRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    public ClientServiceImpl(ClientRepository clientRepository, CcService ccService, IndividualRepository individualRepository, OpporunityRepository opporunityRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.clientRepository = clientRepository;
        this.ccService = ccService;
        this.individualRepository = individualRepository;
        this.opporunityRepository = opporunityRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Client getClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public String addPicture(Long idUser, MultipartFile file) throws IOException {
        Client client = clientRepository.findById(idUser).orElse(null);
        if (client == null) {
            return "Client not found";
        }
        String fileName= client.getUsername()+"."+file.getContentType().replaceAll("image/","");
        Path uploadPath = Paths.get("pictures", fileName);
        File uploadDir = new File(uploadPath.toString()).getParentFile();
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!file.isEmpty()) {
            try {
                File outputFile = uploadPath.toFile();
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();
                client.setPicture(fileName);
                clientRepository.save(client);
                return fileName;
            } catch (IOException e) {
                return "Error while saving the picture";
            }
        } else {
            return "No picture found in the request";
        }
    }

    @Transactional
    @Override
    public Client modifyClient(Client client) {

        Client originalClient = clientRepository.findById(client.getId()).orElse(null);
        if (originalClient != null) {
            originalClient.setAddress(client.getAddress());
            originalClient.setEmail(client.getEmail());
            originalClient.setName(client.getName());
            originalClient.setLastname(client.getLastname());
            originalClient.setPhone(client.getPhone());
            return clientRepository.save(originalClient);
        }
        return null;
    }

    @Override
    public Client addProspect(Individual individual){
        Client client = new Client();
        client.setUsername(individual.getUserCode());
        Role clientRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        client.getRoles().add(clientRole);
        client.setIndividual(individualRepository.save(individual));
        client.setPassword(encoder.encode(individual.getPassword()));
        client.setPhone(individual.getPhoneNumber());
        client.setName(individual.getFirstname());
        client.setLastname(individual.getLastname());
        client.setAddress(individual.getAddress());
        client.setEmail(individual.getEmail());

        return clientRepository.save(client);
    };

    @Override
    public Client addOpportunite(Opportunity opportunity) {
        Client client = clientRepository.findClientByUsername(opportunity.getUserCode());
        opportunity.setCustomer(client.getIndividual().getCustomer());
        client.setOpportunity(opporunityRepository.save(opportunity));
        return clientRepository.save(client);

    }

    @Override
    public Customer searshcustomer(String idCustomer) {
        Customer customer = new Customer();
        customer.setCustomer(idCustomer);
        customer.setDatecustomer(LocalDate.now());
        Opportunity opportunity = opporunityRepository.findByCustomer(idCustomer);
        opportunity.setDate(customer.getDatecustomer());
        opporunityRepository.save(opportunity);
        return customer;
    }

    @Override
    public String deleteClient(Long id) {
        clientRepository.deleteById(id);
        return "Compte Client effacé avec succès";
    }



}

package tn.myadvans.authentification.authentification.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.myadvans.authentification.authentification.entities.Client;
import tn.myadvans.authentification.authentification.entities.CompteClient;
import tn.myadvans.authentification.authentification.repository.ClientRepository;
import tn.myadvans.authentification.authentification.repository.CompteClientRepository;

import java.util.Optional;

@Service
public class CompteClientServiceImpl implements CompteClientService {

    @Autowired
    private CompteClientRepository compteClientRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public CompteClient createCompte(Long id, CompteClient compteClient) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            compteClient.setClient(client.get());
            return compteClientRepository.save(compteClient);
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    @Override
    public Optional<CompteClient> getCompteById(Long idNumCompte) {
        return compteClientRepository.findById(idNumCompte);
    }

    @Override
    public void transfererSolde(Long idSource, Long idDestination, int montant) throws Exception {
        Optional<CompteClient> sourceCompteOpt = compteClientRepository.findById(idSource);
        Optional<CompteClient> destinationCompteOpt = compteClientRepository.findById(idDestination);

        if (sourceCompteOpt.isPresent() && destinationCompteOpt.isPresent()) {
            CompteClient sourceCompte = sourceCompteOpt.get();
            CompteClient destinationCompte = destinationCompteOpt.get();

            if (sourceCompte.getSoldeCompte() >= montant) {
                sourceCompte.setSoldeCompte(sourceCompte.getSoldeCompte() - montant);
                destinationCompte.setSoldeCompte(destinationCompte.getSoldeCompte() + montant);

                compteClientRepository.save(sourceCompte);
                compteClientRepository.save(destinationCompte);
            } else {
                throw new Exception("Solde insuffisant dans le compte source.");
            }
        } else {
            throw new Exception("Compte source ou destination introuvable.");
        }
    }
}
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import tn.myadvans.authentification.authentification.entities.Client;
//import tn.myadvans.authentification.authentification.entities.CompteClient;
//import tn.myadvans.authentification.authentification.repository.ClientRepository;
//import tn.myadvans.authentification.authentification.repository.CompteClientRepository;
//import tn.myadvans.authentification.authentification.repository.OpporunityRepository;
//
//import java.util.List;
//
//@Service
//public class CompteClientServiceImpl implements CompteClientService {
//
//    private final ClientRepository clientRepository;
//    private final OpporunityRepository opporunityRepository;
//    @Autowired
//    public CompteClientServiceImpl(ClientRepository clientRepository,OpporunityRepository opporunityRepository, CompteClientRepository compteClientRepository) {
//        this.clientRepository = clientRepository;
//        this.opporunityRepository = opporunityRepository;
//        this.compteClientRepository = compteClientRepository;
//    }
//
//    @Autowired
//    private CompteClientRepository compteClientRepository;
//
//    @Override
//    public List<CompteClient> getAllComptes() {
//        return compteClientRepository.findAll();
//    }
//
//    @Override
//    public CompteClient getCompteById(Long id) {
//        return compteClientRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    @Transactional
//    public CompteClient createOrUpdateCompte(CompteClient compteClient) {
//        // Récupérer le client associé au code utilisateur
//        String userCode = compteClient.getUserCode();
//        Client client = clientRepository.findClientByUsername(userCode);
//
//        // Vérifier si le client existe
//        if (client == null) {
//            // Gérer le cas où le client n'existe pas
//            throw new RuntimeException("Client non trouvé pour le code utilisateur: " + userCode);
//        }
//
//        // Récupérer le solde de l'opportunité du client
//        int soldeOpportunite = client.getOpportunity().getSolde(); // Assurez-vous que Opportunity a un attribut `solde`
//
//        // Mettre à jour le solde du compte client
//        compteClient.setSoldeCompte(soldeOpportunite);
//
//        // Sauvegarder le compte client dans la base de données
//        return compteClientRepository.save(compteClient);
//    }
//
//    @Override
//    public void deleteCompte(Long id) {
//        compteClientRepository.deleteById(id);
//    }
//
//    @Override
//    @Transactional
//    public void transfererFonds(Long idNumCompteSource, Long idNumCompteDestination, int montant) {
//        CompteClient source = compteClientRepository.findById(idNumCompteSource)
//                .orElseThrow(() -> new RuntimeException("Compte source non trouvé"));
//        CompteClient destination = compteClientRepository.findById(idNumCompteDestination)
//                .orElseThrow(() -> new RuntimeException("Compte destination non trouvé"));
//
//        if (source.getSoldeCompte() < montant) {
//            throw new RuntimeException("Fonds insuffisants");
//        }
//
//        source.setSoldeCompte(source.getSoldeCompte() - montant);
//        destination.setSoldeCompte(destination.getSoldeCompte() + montant);
//
//        compteClientRepository.save(source);
//        compteClientRepository.save(destination);
//    }
//}

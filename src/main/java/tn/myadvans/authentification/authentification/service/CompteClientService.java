package tn.myadvans.authentification.authentification.service;

import tn.myadvans.authentification.authentification.entities.CompteClient;

import java.util.Optional;

public interface CompteClientService {
    CompteClient createCompte(Long id, CompteClient compteClient);
    Optional<CompteClient> getCompteById(Long idNumCompte);
    void transfererSolde(Long idSource, Long idDestination, int montant) throws Exception;
}







//
//import tn.myadvans.authentification.authentification.entities.CompteClient;
//
//import java.util.List;
//
//public interface CompteClientService {
//
//    List<CompteClient> getAllComptes();
//    CompteClient getCompteById(Long id);
//
//    void deleteCompte(Long id);
//    void transfererFonds(Long idNumCompteSource, Long idNumCompteDestination, int montant);
//    CompteClient createOrUpdateCompte(CompteClient compteClient);
//}

package tn.myadvans.authentification.authentification.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.myadvans.authentification.authentification.entities.CompteClient;
import tn.myadvans.authentification.authentification.service.CompteClientServiceImpl;

import java.util.Optional;
@RestController
@RequestMapping("/api/comptes")
public class CompteClientController {

    @Autowired
    private CompteClientServiceImpl compteClientService;

    @PostMapping("/{clientId}")
    public ResponseEntity<CompteClient> createCompte(@PathVariable Long clientId, @RequestBody CompteClient compteClient) {
        return ResponseEntity.ok(compteClientService.createCompte(clientId, compteClient));
    }

    @GetMapping("/{idNumCompte}")
    public ResponseEntity<CompteClient> getCompteById(@PathVariable Long idNumCompte) {
        Optional<CompteClient> compteClient = compteClientService.getCompteById(idNumCompte);
        return compteClient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfererSolde(@RequestParam Long idSource, @RequestParam Long idDestination, @RequestParam int montant) {
        try {
            compteClientService.transfererSolde(idSource, idDestination, montant);
            return ResponseEntity.ok("Transfert effectué avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import tn.myadvans.authentification.authentification.entities.CompteClient;
//import tn.myadvans.authentification.authentification.service.CompteClientService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/comptes")
//public class CompteClientController {
//
//    @Autowired
//    private CompteClientService compteClientService;
//
//    @PostMapping
//    public CompteClient createOrUpdateCompte(@RequestBody CompteClient compteClient) {
//        return compteClientService.createOrUpdateCompte(compteClient);
//    }
//
//    @GetMapping
//    public List<CompteClient> getAllComptes() {
//        return compteClientService.getAllComptes();
//    }
//
//    @GetMapping("/{id}")
//    public CompteClient getCompteById(@PathVariable Long id) {
//        return compteClientService.getCompteById(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteCompte(@PathVariable Long id) {
//        compteClientService.deleteCompte(id);
//    }
//
//    @PostMapping("/transfer")
//    public void transfererFonds(@RequestParam Long sourceId, @RequestParam Long destinationId, @RequestParam int montant) {
//        compteClientService.transfererFonds(sourceId, destinationId, montant);
//    }
//}

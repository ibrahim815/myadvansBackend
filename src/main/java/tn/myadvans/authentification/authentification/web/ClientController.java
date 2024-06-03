package tn.myadvans.authentification.authentification.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.myadvans.authentification.authentification.entities.Client;
import tn.myadvans.authentification.authentification.entities.Customer;
import tn.myadvans.authentification.authentification.entities.Individual;
import tn.myadvans.authentification.authentification.entities.Opportunity;
import tn.myadvans.authentification.authentification.repository.IndividualRepository;
import tn.myadvans.authentification.authentification.repository.OpporunityRepository;
import tn.myadvans.authentification.authentification.service.ClientService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@CrossOrigin("*")
@RestController
public class ClientController {

    private final ClientService clientService;
    private final IndividualRepository individualRepository;
    private final OpporunityRepository opporunityRepository;

    public ClientController(ClientService clientService, IndividualRepository individualRepository, OpporunityRepository opporunityRepository) {
        this.clientService = clientService;
        this.individualRepository = individualRepository;
        this.opporunityRepository = opporunityRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CLIENT')")
    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.getClient(id),HttpStatus.OK);
    }

    @GetMapping(value = "/user/picture/{pictureName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImageWithMediaType(@PathVariable("pictureName") String pictureName) throws IOException {
        String imagePath = "pictures/"+pictureName;
        File imageFile = new File(imagePath);

        if (!imageFile.exists() || imageFile.isDirectory()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CLIENT')")
    @PutMapping("/client/modify")
    public ResponseEntity<Client> getClient(@RequestBody Client client) {
        return new ResponseEntity<>(clientService.modifyClient(client), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @DeleteMapping("/client/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id ) {
        return new ResponseEntity<>(clientService.deleteClient(id),HttpStatus.OK);
    }

    @PostMapping("prosp/add")
    public ResponseEntity<Client> addProsp(@RequestBody Individual individual) {
        if (individualRepository.existsByUserCode(individual.getUserCode())) {
            throw new IllegalArgumentException("UserCode already exists");
        }
        return new ResponseEntity<>(clientService.addProspect(individual), HttpStatus.OK);
    }

    @PostMapping("opp/add")
    public ResponseEntity<Client> addOpp(@RequestBody Opportunity opportunity) {
        if (opporunityRepository.existsByCustomer(opportunity.getCustomer())) {

            throw new IllegalArgumentException("Customer already exists");
        }
        return new ResponseEntity<>(clientService.addOpportunite(opportunity), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("opp/rech/{customer}")
    public ResponseEntity<Customer> addOpp(@PathVariable("customer") String customer) {
        if (!opporunityRepository.existsByCustomer(customer)) {
            throw new IllegalArgumentException("Customer does not exist");
        }
        return new ResponseEntity<>(clientService.searshcustomer(customer), HttpStatus.OK);
    }


}

package tn.myadvans.authentification.authentification.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompteClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNumCompte")
    private Long idNumCompte;

    @Column(name = "soldeCompte", nullable = false)
    private int soldeCompte;

    private  String userCode;

    private String customer;

    private String typeCompte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonBackReference
    //erreur de base pour données 
    private Client client;
}

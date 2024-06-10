package tn.myadvans.authentification.authentification.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompteClient {


    @Id
    @GeneratedValue(generator = "elevenDigitIdGenerator")
    @GenericGenerator(name = "elevenDigitIdGenerator", strategy = "tn.myadvans.authentification.authentification.entities.ElevenDigitIdGenerator")
    @Column(name = "idNumCompte", nullable = false, unique = true)
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

package tn.myadvans.authentification.authentification.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Individual {

    @Id
    @GeneratedValue(generator = "custom-generator")
    @GenericGenerator(name = "custom-generator", strategy = "tn.myadvans.authentification.authentification.entities.CustomIdentifierGenerator")
    private String customer;

    private String userCode;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private String bornon;

    private String title;

    private String branch;

    private String gender;

    private String fieldOfActivity;

    private String jobOfTheHolder;

    private String subJobOfTheHolder;

    private String nbrSalariesFamiliaux;

    private String typePosition;

    private String phoneNumber;

    private String address;
}

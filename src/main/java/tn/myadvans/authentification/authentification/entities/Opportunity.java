package tn.myadvans.authentification.authentification.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(max = 8)
//    @Size(min=7)
    private String customer;

    private String idTreatmentChannel;

    private String idType;

    private String idOrigin;

    private String projectDate;

    private String idRealization;

    private String idStatus;

    private String subject;

    private  String designation ;

    private String  codeAccountOfficer;

    private  String userCode;

    private LocalDate date;

    private int solde;
}

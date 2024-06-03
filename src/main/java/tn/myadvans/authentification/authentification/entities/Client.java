package tn.myadvans.authentification.authentification.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User implements Serializable {

    private String cin;

    @OneToOne
    private Individual individual;

    @OneToOne
    private Opportunity opportunity;

    @ManyToOne
    private Cc cc;

}

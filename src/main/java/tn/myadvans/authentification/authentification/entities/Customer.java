package tn.myadvans.authentification.authentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @NotBlank
    @Size(max = 8)
    String customer;
    LocalDate datecustomer;
}

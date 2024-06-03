package tn.myadvans.authentification.authentification.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.myadvans.authentification.authentification.entities.Cc;
import tn.myadvans.authentification.authentification.entities.ERole;
import tn.myadvans.authentification.authentification.entities.Role;
import tn.myadvans.authentification.authentification.repository.CcRepository;
import tn.myadvans.authentification.authentification.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleInitializerRunner implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CcRepository ccRepository;
    private final PasswordEncoder encoder;

    public RoleInitializerRunner(RoleRepository roleRepository, CcRepository ccRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.ccRepository = ccRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {

        for (ERole enumRole : ERole.values()) {
            if (roleRepository.findByName(enumRole).isEmpty()) {
                Role role = new Role();
                role.setName(enumRole);
                roleRepository.save(role);
            }
        }
        if(ccRepository.findAll().isEmpty()) {
            Cc utilisateur = new Cc();
            utilisateur.setUsername("saidiaCc");
            utilisateur.setEmail("siadi1@gmail.com");
            utilisateur.setPassword(encoder.encode("12345678"));
            utilisateur.setPhone("51031771");
            utilisateur.setLastname("Ibrahim");
            utilisateur.setName("saidi");
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(ERole.ROLE_CC)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            utilisateur.setRoles(roles);
            ccRepository.save(utilisateur);
        }
    }

}

package tn.myadvans.authentification.authentification.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.myadvans.authentification.authentification.entities.*;
import tn.myadvans.authentification.authentification.repository.RoleRepository;
import tn.myadvans.authentification.authentification.repository.UserRepository;
import tn.myadvans.authentification.authentification.security.jwt.JwtUtils;
import tn.myadvans.authentification.authentification.service.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/authentication")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;
  private final ClientService clientService;
  private final CcService ccService;

  public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils, ClientService clientService, CcService ccService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.encoder = encoder;
    this.jwtUtils = jwtUtils;
    this.clientService = clientService;
    this.ccService = ccService;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    if (!userRepository.existsByUsername(loginRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Nom d'utilisateur inexistant!");
    }

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateTokenFromUsername(userDetails.getUsername(),userDetails.getAuthorities());

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

      return ResponseEntity.ok()
              .body(new LoginResponse(userDetails.getId(),
                      userDetails.getUsername(),
                      userDetails.getEmail(),
                      userDetails.getName(),
                      userDetails.getLastname(),
                      userDetails.getPhone(),
                      userDetails.getPostedAt(),
                      userDetails.getLastUpdatedAt(),
                      jwt,
                      roles));

  }

  @PostMapping("/cc/signup")
  public ResponseEntity<?> registerCc(@Valid @RequestBody CcSignUpRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }

    // Create new admin's account
    Cc utilisateur = new Cc();
    utilisateur.setUsername( signUpRequest.getUsername());
    utilisateur.setEmail( signUpRequest.getEmail());
    utilisateur.setPassword(encoder.encode(signUpRequest.getPassword()));
    utilisateur.setPhone( signUpRequest.getPhone());
    utilisateur.setLastname( signUpRequest.getLastname());
    utilisateur.setName( signUpRequest.getName());
    utilisateur.setAddress( signUpRequest.getAddress());

    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role utilisateurRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(utilisateurRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "ROLE_CC" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_CC)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
          case "ROLE_CLIENT" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
        }
      });
    }
    utilisateur.setRoles(roles);

    return ResponseEntity.ok().body(userRepository.save(utilisateur));
  }

  @PostMapping("/client/signup")
  public ResponseEntity<?> registerClient(@Valid @RequestBody ClientSignUpRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Le nom d'utilisateur est déjà pris !");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body("L'adresse e-mail est déjà utilisée !");
    }

    Client utilisateur = new Client();
    utilisateur.setUsername( signUpRequest.getUsername());
    utilisateur.setEmail( signUpRequest.getEmail());
    utilisateur.setPassword( encoder.encode(signUpRequest.getPassword()));
    utilisateur.setPhone( signUpRequest.getPhone());
    utilisateur.setLastname( signUpRequest.getLastname());
    utilisateur.setName( signUpRequest.getName());
    utilisateur.setCin( signUpRequest.getCin());
    //utilisateur.setSpeciality( signUpRequest.getSpeciality());
    utilisateur.setAddress( signUpRequest.getAddress());

    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role utilisateurRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(utilisateurRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "ROLE_CC" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_CC)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
          case "ROLE_CLIENT" -> {

            Role adminRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
        }
      });
    }
    utilisateur.setRoles(roles);

    return ResponseEntity.ok().body(userRepository.save(utilisateur));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUtilisateur() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("You've been signed out!");
  }

//  @PostMapping("/client/picture/{idUser}")
//  public ResponseEntity<?> registerUser(@PathVariable("idUser") Long idUser,@RequestParam("file") MultipartFile file) throws IOException {
//
//    return ResponseEntity.ok(clientService.addPicture(idUser,file));
//  }
//  @PostMapping("/admin/picture/{idUser}")
//  public ResponseEntity<?> registerAdmin(@PathVariable("idUser") Long idUser,@RequestParam("file") MultipartFile file) throws IOException {
//
//    return ResponseEntity.ok(ccService.addPicture(idUser,file));
 // }

}

package tn.myadvans.authentification.authentification.service;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tn.myadvans.authentification.authentification.entities.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ToString
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private String lastname;
  private String username;
  private String password;
  private String email;
  private String phone;
  private Date postedAt;
  private Date lastUpdatedAt;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String email, String name, String password, String lastname, String phone,Date postedAt,Date lastUpdatedAt,Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.name = name;
    this.password = password;
    this.lastname = lastname;
    this.phone = phone;
    this.postedAt=postedAt;
    this.lastUpdatedAt=lastUpdatedAt;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getName(),
        user.getPassword(),
        user.getLastname(),
        user.getPhone(),
        user.getPostedAt(),
        user.getLastUpdatedAt(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {return authorities;}

  public Long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public String getName() {
    return this.name;
  }

  public String getLastname() {
    return this.lastname;
  }

  public String getPhone() {
    return this.phone;
  }

  public Date getPostedAt() {
    return this.postedAt;
  }

  public Date getLastUpdatedAt() {
    return this.lastUpdatedAt;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }

}

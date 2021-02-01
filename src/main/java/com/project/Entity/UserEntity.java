package com.project.Entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "USER")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ROLE", referencedColumnName = "ID")
    private UserRoleEntity role;


    @Column(name = "MONEY", nullable = false)
    private BigDecimal moneyOnTheAccount;

    @OneToMany(mappedBy = "user")
    private List<LoanEntity> loanEntities;


    public UserEntity(String username, String password, UserRoleEntity role, BigDecimal money) {
        id = null;
        this.username = username;
        this.password = password;
        this.role = role;
        this.moneyOnTheAccount = money;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getRole()));
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
}

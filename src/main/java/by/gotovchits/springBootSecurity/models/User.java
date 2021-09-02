package by.gotovchits.springBootSecurity.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private Date dataRegistration;
    private Date lastLoginDate;
    private boolean block = true;

    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return block;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    public String getStatus(){
        if(block){
            return "unlocked";
        }
        else {
            return "locked";
        }
    }

    public void saveLastLoginDate(){
        this.lastLoginDate = new Date();
    }

    public String getFormatDateRegistration(){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return formater.format(getDataRegistration());
    }

    public String getFormatDateLastLogin(){

        if(getLastLoginDate() != null){
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            return formater.format(getLastLoginDate());
        }
        return "";
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }



    @Override
    public String toString() {
        return "id=" + id + '\n' +
                "username='" + username + '\n' +
                "email='" + email + '\n' +
                "password='" + password + ' ' +
                "dataRegistration='" + dataRegistration + '\n' +
                "lastLoginDate='" + lastLoginDate + '\n' +
                "activationCode='" + activationCode + '\n' +
                "block='" + block + '\n' +
                "roles=" + roles;
    }
}
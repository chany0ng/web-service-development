package db.project.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
public class User implements UserDetails {
    private String id;
    private String password;
    private String email;
    private String phone_number;
    private int pw_question;
    private String pw_answer;
    private String role;
    private int cash;
    private int overfee;
    private int ticket_id;


    @Builder
    public User(String id, String password, String email, String phone_number,
                int pw_question, String pw_answer, String role, int cash, int overfee, int ticket_id) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
        this.pw_question = pw_question;
        this.pw_answer = pw_answer;
        this.role = role;
        this.cash = cash;
        this.overfee = overfee;
        this.ticket_id = ticket_id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role.toUpperCase() == "ADMIN") {
            List<SimpleGrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
            list.add(new SimpleGrantedAuthority("ROLE_USER"));
            return list;
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
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

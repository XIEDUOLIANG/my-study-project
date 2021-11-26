package org.xdl.springboot.oauth2.config.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author XieDuoLiang
 * @date 2021/10/20 下午1:45
 */
public class UserDetailBO2 implements UserDetails {

    private String username;

    private String password;

    private String auth;

    private String menu;

    private final Set<GrantedAuthority> grantedAuthoritySet;

    public UserDetailBO2(String username, String password, String auth, String menu, Set<GrantedAuthority> grantedAuthoritySet) {
        this.username = username;
        this.password = password;
        this.auth = auth;
        this.menu = menu;
        this.grantedAuthoritySet = grantedAuthoritySet;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

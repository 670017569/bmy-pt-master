package com.bmy.auth.common.vo;

import com.bmy.dao.domain.Role;
import com.bmy.dao.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName SecurityUser
 * @Description TODO
 * 用于Security认证的用户信息实体类
 * 继承自Mapper模块中的User
 * 实现用户rbac信息的处理
 * @Author potato
 * @Date 2020/12/15 下午5:47
 **/
public class SecurityUser extends User implements UserDetails{

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(super.getRoles()==null){
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<>(super.getRoles().size());
        for (Role role : super.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}

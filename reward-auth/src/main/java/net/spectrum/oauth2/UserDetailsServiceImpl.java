package net.spectrum.oauth2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginRepository userRepository;

    @Autowired
    LoginTrailRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginEntity user = userRepository.findByUserId(username);

        if (user != null) {
            List<GrantedAuthority> authorities = getAuthorities(user);
            return buildUserForAuthentication(user , authorities);

            // For permission based authentication
            /* List<GrantedAuthority> authorities = user.getRoles().stream() // user.getRoles => operator-01 == role-operator
                                .flatMap(role -> role.getPermissions().stream())// role.getPermissions => role-operator = getlist == permissionoid-create-password, permissionoid-update-password etc
                                .map(permission -> permission.getPermissionName())
                                .map(SimpleGrantedAuthority::new)
                               .collect(Collectors.toList());
             */
        }
        throw new UsernameNotFoundException(username);
    }

    // For role based authentication
    private List<GrantedAuthority> getAuthorities(LoginEntity user) {
        Collection<RoleEntity> roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleOid()));
        }
        return authorities;
    }

    private UserDetails buildUserForAuthentication(LoginEntity user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), authorities);
    }
}

package iit.ase.cw.service;

import iit.ase.cw.platform.common.security.model.AuthenticationRequest;
import iit.ase.cw.platform.common.security.model.ThaproRole;
import iit.ase.cw.platform.common.security.model.ThaproUser;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class UserDetailPopulateService implements ThaproUserDetailsPopulateService {
    @Override
    public ThaproUser findByUsername(AuthenticationRequest authenticationRequest) {
        //Call the database and fetch the user object.
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//            .username("user")
//            .password("user")
//            .roles("USER")
//            .build();
//
//        return userDetails;
        List<ThaproRole> roleList = Arrays.asList(new String[] { "ADMIN_USER", "FACILITY_USER" })
            .stream()
            .map(ThaproRole::new)
            .collect(Collectors.toList());

        return ThaproUser.builder()
            .userId(authenticationRequest.getUsername())
            .password(authenticationRequest.getPassword())
            .organizationId(1000)
            .roles(roleList)
            .build();
    }
}

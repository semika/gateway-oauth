package iit.ase.cw.controller;

import iit.ase.cw.platform.common.security.model.AuthenticationRequest;
import iit.ase.cw.platform.common.security.model.AuthenticationResponse;
import iit.ase.cw.platform.common.security.model.ThaproUser;
import iit.ase.cw.service.UserDetailPopulateService;

import iit.ase.cw.util.ThaproJwtTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class LoginController {

    @Autowired
    private UserDetailPopulateService userDetailPopulateService;

    @Autowired
    private ThaproJwtTokenHandler thaproJwtTokenHandler;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest)
        throws JsonProcessingException {

        ThaproUser thaproUser = userDetailPopulateService.findByUsername(authenticationRequest.getUsername());

        //validate password
        if (!thaproUser.getPassword().equals(authenticationRequest.getPassword())) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED);
            throw new RuntimeException("Invalid Login Credentials");
        }

        String jwt = thaproJwtTokenHandler.generateClientToken(authenticationRequest);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}

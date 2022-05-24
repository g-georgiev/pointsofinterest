package world.pointsofinterest.controllers.v1;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import world.pointsofinterest.api.v1.model.AuthRequestDTO;
import world.pointsofinterest.api.v1.model.AuthResponseDTO;
import world.pointsofinterest.services.interfaces.JwtTokenService;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@CrossOrigin
public class AuthenticationController {

    AuthenticationManager authenticationManager;
    JwtTokenService jwtTokenService;
    UserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenService jwtTokenService,
                                    UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/v1/authenticate")
    public AuthResponseDTO authenticate(@RequestBody @Valid final AuthRequestDTO authenticationRequest)
            throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        return new AuthResponseDTO(jwtTokenService.generateToken(
                userDetailsService.loadUserByUsername(authenticationRequest.getUsername())
        ));
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

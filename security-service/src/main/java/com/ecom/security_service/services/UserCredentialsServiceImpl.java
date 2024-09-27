package com.ecom.security_service.services;

import com.ecom.security_service.dto.LoginDTO;
import com.ecom.security_service.dto.LoginResponseDTO;
import com.ecom.security_service.dto.SignUpDTO;
import com.ecom.security_service.entities.Role;
import com.ecom.security_service.entities.UserCredentials;
import com.ecom.security_service.enums.ERole;
import com.ecom.security_service.exceptions.PasswordsNotMatchException;
import com.ecom.security_service.exceptions.RoleNotFoundException;
import com.ecom.security_service.exceptions.UserCredentialsNotFoundException;
import com.ecom.security_service.repository.RoleRepository;
import com.ecom.security_service.repository.UserCredentialsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {
    private UserCredentialsRepository userCredentialsRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtDecoder jwtDecoder;
    private final UserDetailsService userDetailsService;

    @Override
    public UserCredentials createUserCredentials(SignUpDTO signUpDTO, Role role) {
        return userCredentialsRepository.save(UserCredentials.builder()
                .userName(signUpDTO.username())
                .password(passwordEncoder.encode(signUpDTO.password()))
                .email(signUpDTO.email())
                .userId(signUpDTO.userId())
                .role(role)
                .build());
    }

    @Override
    public UserCredentials createUserCredentialsWithUserRole(SignUpDTO signUp) throws RoleNotFoundException {
        Role role = roleRepository.findByRoleName(ERole.USER)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        return createUserCredentials(signUp,role);
    }

    @Override
    public UserCredentials createUserCredentialsWithVendorRole(SignUpDTO signUp) throws RoleNotFoundException {
        Role role = roleRepository.findByRoleName(ERole.VENDOR)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        return createUserCredentials(signUp,role);
    }

    @Override
    public UserCredentials createUserCredentialsWithAdminRole(SignUpDTO signUp) throws RoleNotFoundException {
        Role role = roleRepository.findByRoleName(ERole.ADMIN)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        return createUserCredentials(signUp,role);
    }

    @Override
    public UserCredentials getUserCredentials(long userId) throws UserCredentialsNotFoundException {
        return userCredentialsRepository.findById(userId)
                .orElseThrow(() -> new UserCredentialsNotFoundException("User credentials not found"));
    }

    @Override
    public UserCredentials loadUserByUsername(String username) throws UserCredentialsNotFoundException {
        UserCredentials credentials = userCredentialsRepository.findByUserNameOrEmail(username, username);
        if (credentials == null) {
            throw new UserCredentialsNotFoundException("User credentials not found");
        }
        return credentials;
    }

    @Override
    public UserCredentials changePassword(long userId, String oldPassword, String newPassword) throws UserCredentialsNotFoundException, PasswordsNotMatchException {
        UserCredentials userCredentials = getUserCredentials(userId);
        if(!passwordEncoder.matches(oldPassword, userCredentials.getPassword())) {
            throw new PasswordsNotMatchException("Old password not match");
        }else{
            userCredentials.setPassword(passwordEncoder.encode(newPassword));
            return userCredentialsRepository.save(userCredentials);
        }
    }

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = null;
        String subject = null;
        String scope = null;

        if(loginDTO.grantType().equals("password")) {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.username(), loginDTO.password()));
            subject = authentication.getName();
            scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));

        } else if(loginDTO.grantType().equals("refreshToken")) {
            Jwt decoded = jwtDecoder.decode(loginDTO.refreshToken());
            subject = decoded.getSubject();
            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(subject);
            Collection<? extends GrantedAuthority> authorities =
                    userDetails.getAuthorities();
            scope = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
        }

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(1, ChronoUnit.MINUTES))
                .issuer("security-service")
                .claim("scope", scope)
                .build();
        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters
                .from(jwtClaimsSet)).getTokenValue();
        loginResponseDTO.setAccessToken(jwtAccessToken);

        if(loginDTO.grantType().equals("password")) {
            JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(5, ChronoUnit.MINUTES))
                    .issuer("security-service")
                    .build();
            String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters
                    .from(jwtClaimsSetRefresh)).getTokenValue();
            loginResponseDTO.setRefreshToken(jwtRefreshToken);
        }
        return loginResponseDTO;
    }

    @Override
    public void deleteUserCredentials(long userId) throws UserCredentialsNotFoundException {
        userCredentialsRepository.delete(getUserCredentials(userId));
    }
}

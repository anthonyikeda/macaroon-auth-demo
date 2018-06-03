package com.example.secureapione.service;

import com.github.nitram509.jmacaroons.Macaroon;
import com.github.nitram509.jmacaroons.MacaroonsBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by Anthony Ikeda <anthony.ikeda@gmail.com> on 3/06/2018.
 */
@Component
public class MacaroonUserDetailsService implements UserDetailsService {


    /**
     * The username in this case is the value of a Macaroon
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Macaroon macaroon = MacaroonsBuilder.deserialize(username);
        User.UserBuilder builder;

        builder = User.withUsername(macaroon.identifier);

        return builder.build();
    }
}

package com.example.secureapione.service;

import com.github.nitram509.jmacaroons.CaveatPacket;
import com.github.nitram509.jmacaroons.Macaroon;
import com.github.nitram509.jmacaroons.MacaroonsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(MacaroonUserDetailsService.class);

    /**
     * The username in this case is the value of a Macaroon
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user for macaroon!");
        Macaroon macaroon = MacaroonsBuilder.deserialize(username);
        User.UserBuilder builder;

        builder = User.withUsername(macaroon.identifier);
        for (CaveatPacket packet :
                macaroon.caveatPackets) {
            log.debug("{}, {}", packet.getType(), packet.getValueAsText());
        }

        return builder.build();
    }
}

package com.example.secureapione.filter;

import com.example.secureapione.config.MacaroonConfig;
import com.github.nitram509.jmacaroons.CaveatPacket;
import com.github.nitram509.jmacaroons.Macaroon;
import com.github.nitram509.jmacaroons.MacaroonsBuilder;
import com.github.nitram509.jmacaroons.MacaroonsVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by Anthony Ikeda <anthony.ikeda@gmail.com> on 3/06/2018.
 */
public class MacaroonAuthFilter extends GenericFilterBean {

    private Logger log = LoggerFactory.getLogger(MacaroonAuthFilter.class);

    private MacaroonConfig macConfig;

    public MacaroonAuthFilter(MacaroonConfig _config) {
        this.macConfig = _config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequestWrapper req = new HttpServletRequestWrapper( (HttpServletRequest) request);

        Macaroon macaroon = resolveMacaroon(req);
        log.debug("Macaroon location: {}", macaroon.location);
        log.debug("Macaroon identifier: {}", macaroon.identifier);
        log.debug("Macaroon signature: {}", macaroon.signature);

        MacaroonsVerifier verifier = new MacaroonsVerifier(macaroon);

        log.debug("Validating macaroon with secret '{}'", macConfig.getSecret());

        boolean valid2 = verifier.isValid(macConfig.getSecret());
        boolean validity = verifier.isValid("apples and oranges");

        if (! validity ) {
            log.debug("Macaroon is invalid ({})", validity);
        } else {
            log.debug("We have a valid macaroon");
        }

        log.debug("validity {}, valid2 {}", validity, valid2);

        CaveatPacket[] packets = macaroon.caveatPackets;

        for(CaveatPacket packet : packets) {
            log.debug("Caveat: {}", packet.getValueAsText());
        }

        if (validity) {
            chain.doFilter(request, response);
        } else {

        }
    }

    /**
     * So not much docs on how to pass a macaroon to a service or what header name to use so I'm going to go with the
     * assumption that like all auth headers, it should be a form of Authorization (Authorization: macaroon {macaroon})
     *
     * @param request HttpServlet request to use to get the header
     * @return A macaroon that can be used to auth with
     */
    private Macaroon resolveMacaroon(HttpServletRequest request) {
        log.debug("Resolving macaroon...");

        String authHeader = request.getHeader("Authorization");

        //Check to see if it's a macaroon
        if (authHeader.indexOf("macaroon") >= 0) {
            log.debug("Macaroon found!");
            String serialized = authHeader.substring(authHeader.indexOf(" "), authHeader.length());

            log.debug("Macaroon found is '{}'", serialized.trim());

            // decode with the secret
            return MacaroonsBuilder.deserialize(serialized.trim());
        } else {
            log.debug("Header 'Authorization: macaroon (macaroon)' not found");
            throw new RuntimeException("No macaroon header found!");
        }
    }
}

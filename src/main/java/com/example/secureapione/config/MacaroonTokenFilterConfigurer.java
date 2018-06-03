package com.example.secureapione.config;

import com.example.secureapione.filter.MacaroonAuthFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Anthony Ikeda <anthony.ikeda@gmail.com> on 3/06/2018.
 */
public class MacaroonTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private MacaroonConfig config;

    public MacaroonTokenFilterConfigurer(MacaroonConfig _config) {
        this.config = _config;
    }

    @Override
    public void configure(HttpSecurity http) {
        MacaroonAuthFilter filter = new MacaroonAuthFilter(config);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}

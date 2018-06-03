package com.example.secureapione.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by Anthony Ikeda <anthony.ikeda@gmail.com> on 3/06/2018.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    private MacaroonConfig macConfig;

    public WebSecurityConfig(MacaroonConfig _config) {
        this.macConfig = _config;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().anyRequest().authenticated();

        MacaroonTokenFilterConfigurer configurer = new MacaroonTokenFilterConfigurer(macConfig);
        http.apply(configurer);
    }
}

package org.xdl.springboot.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.xdl.springboot.oauth2.config.bo.UserDetailBO1;
import org.xdl.springboot.oauth2.config.bo.UserDetailBO2;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XieDuoLiang
 * @date 2021/10/20 上午10:47
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final static String signKey = "oauth2_study";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private HashPasswordEncoder hashPasswordEncoder;

    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer.withClientDetails(jdbcClientDetailsService);
    }

    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setClientDetailsService(jdbcClientDetailsService);
        defaultTokenServices.setTokenStore(new JwtTokenStore(jwtAccessTokenConverter));

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(jwtAccessTokenConverter);
        tokenEnhancers.add(((oAuth2AccessToken, oAuth2Authentication) -> {
            Object authObj = oAuth2Authentication.getUserAuthentication().getPrincipal();
            if (authObj instanceof UserDetailBO1) {
                
            }
            if (authObj instanceof UserDetailBO2) {

            }
            return null;
        }));
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);

        return defaultTokenServices;
    }



    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signKey);
        return jwtAccessTokenConverter;
    }

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(hashPasswordEncoder);
        return jdbcClientDetailsService;
    }
}

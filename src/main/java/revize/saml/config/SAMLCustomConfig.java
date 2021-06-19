package revize.saml.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.context.SAMLContextProvider;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.storage.EmptyStorageFactory;
import com.github.ulisesbocchio.spring.boot.security.saml.bean.SAMLConfigurerBean;


import revize.saml.config.security.SAMLUserDetailsServiceImpl;

@Configuration
public class SAMLCustomConfig extends WebSecurityConfigurerAdapter {

	@Value("${revize.entityId}")
	private String entityID;
	
	@Bean
	SAMLConfigurerBean saml() {
		return new SAMLConfigurerBean();
	}
	
	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(null);
    }
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.httpBasic()
            .disable()
            .csrf()
            .disable()
            .anonymous()
            .and()
            .authorizeRequests()
            .requestMatchers(saml().endpointsMatcher())
            .permitAll()
        .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
        .and()
            .apply(saml())
            .serviceProvider()
            	.samlContextProvider(emptySAMLProcessor())
                .metadataGenerator() //(1)
                .entityId(entityID)
                .and()
                .authenticationProvider().userDetailsService(new SAMLUserDetailsServiceImpl())
            .and()
                .sso() //(2)
                .defaultSuccessURL("/home")
               
                //.idpSelectionPageURL("/idpselection")
            .and()
                .logout() //(3)
                .defaultTargetURL("/")
            .and()
                .metadataManager() //(4)
                .metadataLocations("classpath:/idp-ssocircle.xml")
                .refreshCheckInterval(0)
            .and()
                .extendedMetadata() //(5)
                .idpDiscoveryEnabled(false)
            .and()
                .keyManager() //(6)
                .privateKeyDERLocation("classpath:/localhost.key.der")
                .publicKeyPEMLocation("classpath:/localhost.cert");
                
        // @formatter:on
    }

	private SAMLContextProvider emptySAMLProcessor() {
		
		SAMLContextProviderImpl impl = new SAMLContextProviderImpl();
		impl.setStorageFactory(new EmptyStorageFactory());
		return impl;
	}

}

package revize.saml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.ulisesbocchio.spring.boot.security.saml.annotation.EnableSAMLSSO;

@SpringBootApplication
@EnableSAMLSSO
public class RevizeSamlApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevizeSamlApplication.class, args);
	}

}

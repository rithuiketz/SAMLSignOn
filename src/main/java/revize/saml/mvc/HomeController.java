package revize.saml.mvc;

/**
 * @author Ulises Bocchio
 */


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import revize.saml.config.security.SAMLUser;
import revize.saml.config.security.SAMLUserDetails;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public ModelAndView home(@SAMLUser SAMLUserDetails user) {
        ModelAndView homeView = new ModelAndView("home");
        homeView.addObject("userId", user.getUsername());
        homeView.addObject("samlAttributes", user.getAttributes());
        return homeView;
    }

}

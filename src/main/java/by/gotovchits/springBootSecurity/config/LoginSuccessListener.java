package by.gotovchits.springBootSecurity.config;

import by.gotovchits.springBootSecurity.models.User;
import by.gotovchits.springBootSecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent>
{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent evt) {

        String loginName = evt.getAuthentication().getName();
        User user = userRepository.findByUsername(loginName);
        user.saveLastLoginDate();
        userRepository.save(user);
    }
}
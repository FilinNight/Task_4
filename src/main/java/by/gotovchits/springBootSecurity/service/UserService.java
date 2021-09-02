package by.gotovchits.springBootSecurity.service;

import by.gotovchits.springBootSecurity.models.Role;
import by.gotovchits.springBootSecurity.models.User;
import by.gotovchits.springBootSecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser(User user) {

        try {
            User userFromDb = userRepository.findByUsername(user.getUsername());

            if (userFromDb != null) {
                return false;
            }
            user.setRoles(Collections.singleton(Role.USER));
            user.setDataRegistration(new Date());
            user.setActivationCode(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean change(User user) {
        User userDB = userRepository.findByUsername(user.getUsername());
        try {
            if (userDB != null){
                userRepository.save(user);
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public boolean deleteById(long id) {
        User userFromDb = findById(id);
        try {
            if (userFromDb != null) {
                userRepository.deleteById(id);
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }

    }

    public boolean changeStatus(boolean block, User user){

        try {
            user.setBlock(block);
            boolean result = change(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean checkStatus(){


        User user = findById(getAuthenticationUser().getId());


        if(user.isBlock()){
            return true;
        }
        else
        {
            SecurityContextHolder.clearContext();
            return false;
        }

    }

    public User getAuthenticationUser() {
        User user = null;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
        return user;
    }

}
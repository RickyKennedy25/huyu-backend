package com.practice_day2.huyu.service.serviceImpl;

import com.practice_day2.huyu.model.NotFoundException;
import com.practice_day2.huyu.model.User;
import com.practice_day2.huyu.repository.UserRepository;
import com.practice_day2.huyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (userRepository.findOne(user.getId()) == null) {
            throw new NotFoundException("user doesn't exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User readUser(String id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new NotFoundException("user not found");
        }
        return user;
    }

    @Override
    public ResponseEntity deleteUser(String id) {
        User user = userRepository.findOne(id);

        if (user == null) {
            throw new NotFoundException("the user you wants to delete is not found");
        }
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public User readUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private List<GrantedAuthority> buildAuthority(User user) {
        List<GrantedAuthority> grantedAuthoritys = new ArrayList<>();
        grantedAuthoritys.add(new SimpleGrantedAuthority(user.getRole()));

        return grantedAuthoritys;
    }

    /**
     *
     * Method standar untuk login
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= readUserByUsername(username);
        List<GrantedAuthority> authorities = buildAuthority(user);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true, true, authorities);
    }
}

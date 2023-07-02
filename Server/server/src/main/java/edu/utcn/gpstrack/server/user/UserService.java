package edu.utcn.gpstrack.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByEmailAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username,password);

    }
    public User save(User user){return userRepository.save(user);}
}
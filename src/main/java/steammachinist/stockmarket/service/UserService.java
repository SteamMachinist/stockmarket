package steammachinist.stockmarket.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.repository.UserRepository;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found: id = " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addUsers(List<User> users) {
        users.forEach(user -> user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())));
        userRepository.saveAll(users);
    }

    public long count() {
        return userRepository.count();
    }

    public boolean isUsernameUsed(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}

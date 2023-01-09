package steammachinist.stockmarket.service.dataservice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.Role;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final Set<Role> DEFAULT_ROLES = Collections.singleton(Role.USER);
    private final double DEFAULT_BALANCE = 2000.0;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found: id = " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addDefaultUser(User user) {
        user.setRoles(DEFAULT_ROLES);
        user.setBalance(DEFAULT_BALANCE);
        this.addUser(user);
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user) {
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

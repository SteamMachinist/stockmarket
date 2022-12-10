package steammachinist.stockmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found: id = " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addStock(User user) {
        userRepository.save(user);
    }
}

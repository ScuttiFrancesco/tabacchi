package it.tabacchi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.tabacchi.exception.DuplicateDataException;
import it.tabacchi.auth.service.EmailService;
import it.tabacchi.auth.service.PasswordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper,
                       PasswordEncoder passwordEncoder, PasswordService passwordService, EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserCreationRequest request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utente con ID " + id + " non trovato."));

        boolean exist = userRepository.existsByEmailAndIdNot(request.getEmail(), id);

        if (exist) {
            throw new DuplicateDataException("Utente con email inserita già esistente.");
        }
        User user = userMapper.toEntity(request);
        user.setId(id);
        user.setIsTemporaryPassword(existingUser.getIsTemporaryPassword());
        user.setPassword(existingUser.getPassword());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Utente con ID " + id + " non trovato."));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Utente con email " + email + " non trovato."));
    }
}

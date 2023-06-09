package iop.postgres.cdc.user.business.user;

import iop.postgres.cdc.user.api.user.UserDto;
import iop.postgres.cdc.user.infrastructure.user.UserEntity;
import iop.postgres.cdc.user.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UUID createUser(UserDto userDto) {
        return userRepository.save(UserEntity.of(userDto)).getId();
    }

    public UserDto updateUser(UserDto userDto) {
        return UserDto.of(userRepository.save(UserEntity.of(userDto)));
    }

    public UserDto getUser(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        return UserDto.of(userEntity);
    }

    public UserDto getUserByEmail(String email) {
        return UserDto.of(userRepository.findByEmail(email));
    }
}

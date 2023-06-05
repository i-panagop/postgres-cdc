package iop.postgres.cdc.user.api.user;

import iop.postgres.cdc.user.api.address.AddressDto;
import iop.postgres.cdc.user.infrastructure.user.UserEntity;

import java.util.UUID;

public record UserDto(UUID userId, String name, String email, AddressDto address) {

    public static UserDto of(UserEntity userEntity) {
        return new UserDto(
            userEntity.getId(),
            userEntity.getName(),
            userEntity.getEmail(),
            AddressDto.of(userEntity.getAddress())
        );
    }
}


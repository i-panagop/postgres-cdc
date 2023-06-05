package iop.postgres.cdc.user.infrastructure.user;

import iop.postgres.cdc.user.api.user.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user_cdc")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4272182824069493980L;
    @Id
    private UUID id;
    private String name;
    @Column(unique = true)
    private String email;

    public static UserEntity of(UserDto userDto) {
        UUID orderId = Objects.isNull(userDto.userId()) ? UUID.randomUUID() : userDto.userId();
        return new UserEntity(orderId, userDto.name(), userDto.email());
    }
}

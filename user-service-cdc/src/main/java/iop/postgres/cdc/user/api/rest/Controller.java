package iop.postgres.cdc.user.api.rest;

import iop.postgres.cdc.user.api.user.UserDto;
import iop.postgres.cdc.user.business.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;

    @PostMapping("/write")
    public ResponseEntity<UUID> write(@RequestParam String name) {
        return new ResponseEntity<>(userService.createUser(name), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<UserDto> write(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> get(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

}

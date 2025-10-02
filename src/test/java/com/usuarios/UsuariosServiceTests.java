package com.usuarios;

import com.usuarios.dto.UserRequest;
import com.usuarios.dto.UserResponse;
import com.usuarios.entity.User;
import com.usuarios.repository.UserRepository;
import com.usuarios.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.findById(1L));
    }

    @Test
    void shouldSaveUser() {
        User user = new User();
        user.setNome("Ismael");
        user.setEmail("ismaellawrenz@gmail.com");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);


        UserResponse response = userService.save(new UserRequest("Ismael", "ismaellawrenz@gmail.com"));
        assertEquals("Ismael", response.nome());
    }

    @Test
    void shouldUpdateUser() {
        User user = new User();
        user.setId(2L);
        user.setNome("Taís");
        user.setEmail("tais@gmail.com");

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        when(userRepository.save(Mockito.<User>any())).thenAnswer(invocation -> invocation.getArgument(0));


        var request = new UserRequest("Tais Andrin", "tais@gmail.com");
        var response = userService.update(request, 2L);
        assertEquals("Tais Andrin", response.nome());
        assertEquals("tais@gmail.com", response.email());
    }

    @Test
    void shouldDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setNome("Ismael");
        user.setEmail("ismaellawrenz@gmail.com");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.empty());
        ;
        userService.deleteById(1L);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.findById(1L);
        });
        assertEquals("Usuário não encontrado.", exception.getReason());
    }


}

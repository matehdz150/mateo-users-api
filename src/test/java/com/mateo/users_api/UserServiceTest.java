package com.mateo.users_api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mateo.users_api.dto.CreateUserRequest;
import com.mateo.users_api.dto.LoginRequest;
import com.mateo.users_api.exception.BadRequestException;
import com.mateo.users_api.exception.UnauthorizedException;
import com.mateo.users_api.exception.UserNotFoundException;
import com.mateo.users_api.model.User;
import com.mateo.users_api.repository.UserRepository;
import com.mateo.users_api.service.EncryptionService;
import com.mateo.users_api.service.UserService;
import com.mateo.users_api.service.ValidationService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Ana");
        user.setEmail("ana@mail.com");
        user.setPhone("5551111111");
        user.setTaxId("AARR990101XXX");
        user.setPassword("encrypted-password");
    }

    @Test
    void shouldReturnUsersSortedByName() {
        User bruno = new User();
        bruno.setName("Bruno");

        User ana = new User();
        ana.setName("Ana");

        when(userRepository.findAll()).thenReturn(List.of(bruno, ana));

        List<User> result = userService.getUsers("name", null);

        assertEquals(2, result.size());
        assertEquals("Ana", result.get(0).getName());
        assertEquals("Bruno", result.get(1).getName());
    }

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("new@mail.com");
        request.setName("New User");
        request.setPhone("5559999999");
        request.setPassword("123456");
        request.setTaxId("NEWW990101XXX");

        userService.createUser(request);

        verify(validationService).validateTaxId("NEWW990101XXX");
        verify(validationService).validatePhone("5559999999");
        verify(validationService).validateUniqueTaxId("NEWW990101XXX");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowWhenTaxIdAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest();
        request.setTaxId("AARR990101XXX");
        request.setPhone("5559999999");

        doThrow(new BadRequestException("tax_id already exists"))
                .when(validationService).validateUniqueTaxId("AARR990101XXX");

        assertThrows(BadRequestException.class, () -> userService.createUser(request));
    }

    @Test
    void shouldLoginSuccessfully() {
        LoginRequest request = new LoginRequest();
        request.setTaxId("AARR990101XXX");
        request.setPassword("123456");

        when(userRepository.findByTaxId("AARR990101XXX")).thenReturn(Optional.of(user));
        when(encryptionService.matches("123456", "encrypted-password")).thenReturn(true);

        User result = userService.login(request);

        assertNotNull(result);
        assertEquals("AARR990101XXX", result.getTaxId());
    }

    @Test
    void shouldThrowWhenCredentialsAreInvalid() {
        LoginRequest request = new LoginRequest();
        request.setTaxId("AARR990101XXX");
        request.setPassword("wrong");

        when(userRepository.findByTaxId("AARR990101XXX")).thenReturn(Optional.of(user));
        when(encryptionService.matches("wrong", "encrypted-password")).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> userService.login(request));
    }

    @Test
    void shouldThrowWhenUserDoesNotExistOnLogin() {
        LoginRequest request = new LoginRequest();
        request.setTaxId("XXXX990101XXX");
        request.setPassword("123456");

        when(userRepository.findByTaxId("XXXX990101XXX")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> userService.login(request));
    }

    @Test
    void shouldThrowWhenDeletingNonExistingUser() {
        UUID id = UUID.randomUUID();

        when(userRepository.deleteById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
    }
}
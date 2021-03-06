package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUserWithExistedEmail(){

        EmailExistedException thrown = assertThrows(EmailExistedException.class,()-> {
            String email = "tester@example.com";
            String name = "Tester";
            String password = "test";

            User user = User.builder().build();
            given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

            userService.registerUser(email, name, password);

            verify(userRepository, never()).save(any());
        });
    }

    @Test
    public void authenticateWithValidAttributes(){
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder().email(email).build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertThat(user.getEmail(), is(email));
    }

    @Test
    public void authenticateWithNotExistedEmail(){

        EmailNotExistedException thrown = assertThrows(EmailNotExistedException.class,()->{
            String email = "tester@example.com";
            String password = "test";

            given(userRepository.findByEmail(email)).willReturn(Optional.empty());

            userService.authenticate(email, password);
        });

    }

    @Test
    public void authenticateWithWrongPassword(){

        PasswordWrongException thrown = assertThrows(PasswordWrongException.class,()->{
            String email = "tester@example.com";
            String password = "x";

            User mockUser = User.builder().email(email).build();
            given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
            given(passwordEncoder.matches(any(), any())).willReturn(false);

            userService.authenticate(email, password);
        });

    }

}
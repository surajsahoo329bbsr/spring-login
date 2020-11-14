package com.demo.springlogin.tests;

import com.demo.springlogin.models.User;
import com.demo.springlogin.repositories.RoleRepository;
import com.demo.springlogin.repositories.UserRepository;
import com.demo.springlogin.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceTest;
    private User user;

    @Before
    public void setUp(){
        initMocks(this);
        userServiceTest = new UserService(mockUserRepository, mockRoleRepository, mockBCryptPasswordEncoder);
        user = User.builder()
                .id(1)
                .email("test@gmail.com")
                .firstName("Suraj")
                .lastName("Sahoo")
                .build();

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);

        Mockito.when(mockUserRepository.findByEmail(anyString()))
                .thenReturn(user);
    }

    @Test
    public void testFindUserByEmail(){
        String email = "test@gmail.com";
        User user = userServiceTest.findUserByEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testSaveUser(){
        String email = "test@gmail.com";
        User user = userServiceTest.saveUser(User.builder().build());
        assertEquals(email, user.getEmail());
    }
}

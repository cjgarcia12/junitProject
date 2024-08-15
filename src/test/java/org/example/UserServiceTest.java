package org.example;

import org.example.UserService;
import org.example.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private Map<String, User> mockUserDatabase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown() {
        mockUserDatabase = null;
    }

    @Test
    public void testRegisterUser_Successful() {
        User newUser = new User("john_doe", "password123", "john@example.com");
        when(mockUserDatabase.containsKey("john_doe")).thenReturn(false);

        assertTrue(userService.registerUser(newUser));
        verify(mockUserDatabase, times(1)).put("john_doe", newUser);
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        User existingUser = new User("jane_doe", "password456", "jane@example.com");
        when(mockUserDatabase.containsKey("jane_doe")).thenReturn(true);
        assertFalse(userService.registerUser(existingUser));

        verify(mockUserDatabase, never()).put(anyString(), any(User.class));
    }

    @Test
    public void testRegisterUser_NullUser() {
        assertThrows(NullPointerException.class, () -> userService.registerUser(null));
        verify(mockUserDatabase, never()).put(anyString(), any(User.class));
    }

    @Test
    public void testLoginUser_Successful() {
        User user = new User("john_doe", "password123", "john@example.com");
        when(mockUserDatabase.get("john_doe")).thenReturn(user);

        User loginUser = userService.loginUser("john_doe", "password123");
        assertEquals(user, loginUser);
        verify(mockUserDatabase, times(1)).get("john_doe");
    }

    @Test
    public void testLoginUser_Failure() {
        User user = new User("john_doe", "password123", "john@example.com");
        when(mockUserDatabase.get("john_doe")).thenReturn(user);

        User loginUser = userService.loginUser("john_doe", "password14");
        assertNull(loginUser);
        verify(mockUserDatabase, times(1)).get("john_doe");
    }

    @Test
    public void testLoginUser_NullUser() {
        assertNull(userService.loginUser("blah", "blahblahblahblahblahblahblahblahblahblah"));
        verify(mockUserDatabase, never()).put(anyString(), any(User.class));
    }
}

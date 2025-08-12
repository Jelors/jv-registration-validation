package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.ValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private final int minPasswordLength = 6;

    @BeforeAll
    static void setUpAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOK() {
        User user = new User(123213L, null,
                "1234567", 22);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginLargeOrEqualsThanSixSymbols_Ok() {
        User user = new User(123L, "Carlos",
                "password", 18);
        assertTrue(user.getLogin().length() >= minPasswordLength);
    }

    @Test
    void register_LoginShorterThanSixSymbols_notOk() {
        User user = new User(1005L, "Jane",
                "paswordd", 20);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordLargeOrEqualsThanSixSymbols_Ok() {
        User user = new User(123L, "Sanchez",
                "pass12", 21);
        assertTrue(user.getPassword().length() >= minPasswordLength);
    }

    @Test
    void register_PasswordShorterThanSixSymbols_notOk() {
        User user = new User(1004L, "JessyP",
                "pas", 24);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserAlreadyRegistered_notOk() {
        User user1 = new User(3L, "DuplicateLogin", "pass123", 20);
        registrationService.register(user1);
        User user2 = new User(4L, "DuplicateLogin", "pass456", 22);

        assertThrows(ValidationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_AgeUnderEighteen_notOk() {
        User user = new User(111L, "Maria",
                "passd123", 14);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeExactlyEighteen_Ok() {
        User user = new User(104L, "Andrii23",
                "passd12", 18);
        User registered = registrationService.register(user);

        assertNotNull(registered);
        assertEquals(user.getLogin(), registered.getLogin());
        assertEquals(user.getPassword(), registered.getPassword());
        assertEquals(user.getAge(), registered.getAge());
    }

    @Test
    void register_AgeAboveEighteen_Ok() {
        User user = new User(24L, "Gustavo",
                "password123", 44);
        User registered = registrationService.register(user);

        assertNotNull(registered);
        assertTrue(registered.getAge() > 18);
    }

    @Test
    void register_AgeIsNegative_notOk() {
        User user = new User(6L, "Heisenberg",
                "password123", -52);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginIsEmpty_notOk() {
        User user = new User(10L, "",
                "passwo123", 49);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordIsEmpty_notOk() {
        User user = new User(7L, "Michael",
                "", 34);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }
}

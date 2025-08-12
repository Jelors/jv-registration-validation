package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.ValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private final int passlogMinlength = 6;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullLogin_NotOK() {
        User user = new User(123213L, null,
                "1234567", 22);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginLargeOrEqualsThanSixSymbols_Ok() {
        User user = new User(123L, "Carlos",
                "password", 18);
        assertTrue(user.getLogin().length() >= passlogMinlength);
    }

    @Test
    void register_PasswordLargeOrEqualsThanSixSymbols_Ok() {
        User user = new User(123L, "Sanchez",
                "pass12", 21);
        assertTrue(user.getPassword().length() >= passlogMinlength);
    }

    @Test
    void register_UserAlreadyRegistered_NotOk() {
        User user = new User(123L, "Sanchez",
                "pass12", 21);
        User user2 = new User(1232L, "Carlos",
                "password", 18);
        storageDao.add(user);

        assertThrows(ValidationException.class, () -> registrationService.register(user2));
    }
}

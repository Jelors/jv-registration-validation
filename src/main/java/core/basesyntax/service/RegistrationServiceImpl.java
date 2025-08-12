package core.basesyntax.service;

import core.basesyntax.ValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException(
                    "User login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new ValidationException(
                    "User password cannot be null");
        }
        if (user.getAge() == null) {
            throw new ValidationException(
                    "User age cannot be null");
        }
        if (user.getLogin().length() <= 5) {
            throw new ValidationException(
                    "User login length cannot be less than 6 symbols");
        }
        if (user.getPassword().length() <= 5) {
            throw new ValidationException(
                    "User password length cannot be less than 6 symbols");
        }
        if (user.getAge() <= 0) {
            throw new ValidationException(
                    "User age cannot be negative");
        }
        if (user.getAge() <= 17) {
            throw new ValidationException(
                    "User age cannot be less than 18 y.o.");
        }
        if (isLoginTaken(user.getLogin())) {
            throw new ValidationException("User already registered!");
        }

        return storageDao.add(user);
    }

    public StorageDao getStorageDao() {
        return storageDao;
    }

    public boolean isLoginTaken(String login) {
        return getStorageDao().get(login) != null;
    }
}

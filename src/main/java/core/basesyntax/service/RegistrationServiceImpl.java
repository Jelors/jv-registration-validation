package core.basesyntax.service;

import core.basesyntax.ValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() <= 5) {
            throw new ValidationException(
                    "User login cannot be null or less than 6 symbols");
        }
        if (user.getPassword() == null || user.getPassword().length() <= 5) {
            throw new ValidationException(
                    "User password cannot be null or less than 6 symbols");
        }
        if (user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            throw new ValidationException(
                    "User login or password cannot be empty");
        }
        if (user.getAge() == null || user.getAge() <= 17) {
            throw new ValidationException(
                    "User age cannot be null or user younger than 18 y.o.");
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

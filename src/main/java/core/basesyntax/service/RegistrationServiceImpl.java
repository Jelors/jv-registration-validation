package core.basesyntax.service;

import core.basesyntax.ValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if ((user.getLogin() == null || user.getLogin().length() <= 5)
                || (user.getPassword() == null || user.getPassword().length() <= 5)) {
            throw new ValidationException(
                    "User login or password cannot be null or less than 6 symbols");
        }
        if (isLoginTaken(user.getLogin())) {
            throw new ValidationException("User already registered!");
        }
        storageDao.add(user);
        return null;
    }

    public StorageDao getStorageDao() {
        return storageDao;
    }

    public boolean isLoginTaken(String login) {
        return getStorageDao().get(login) == null;
    }
}

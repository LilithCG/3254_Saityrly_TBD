package com.delivery_service.service.user;

import com.delivery_service.dto.request.UserRequest;
import com.delivery_service.postgres.entity.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    void saveUser(UserRequest user);
    List<User> fetchUserList();
    User fetchUser(long id) throws ChangeSetPersister.NotFoundException;
    void updateUser(UserRequest user, long userId) throws ChangeSetPersister.NotFoundException;
    boolean exist(long id);
}

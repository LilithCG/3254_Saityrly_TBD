package com.delivery_service.service.user;

import com.delivery_service.dto.request.UserRequest;
import com.delivery_service.postgres.entity.User;
import com.delivery_service.postgres.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private User toUser(UserRequest userRequest) {
        return User.builder()
                .userName(userRequest.getId())
                .name(userRequest.getName())
                .ref(userRequest.getRef())
                .phone(userRequest.getPhone())
                .street(userRequest.getStreet())
                .houseNumber(userRequest.getHouseNumber())
                .build();
    }

    @Override
    public void saveUser(UserRequest userRequest) {
        var user = toUser(userRequest);
        userRepository.save(user);
    }

    @Override
    public List<User> fetchUserList() {
        return userRepository.findAll();
    }

    @Override
    public User fetchUser(long id) throws ChangeSetPersister.NotFoundException {
        return Optional.ofNullable(userRepository.findById(id)).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public void updateUser(UserRequest userRequest, long userId) throws ChangeSetPersister.NotFoundException {
        var user = toUser(userRequest);
        var userDB = fetchUser(userId);

        if (Objects.nonNull(user.getName()) && !"".equalsIgnoreCase(user.getName())) {
            userDB.setName(user.getName());
        }
        if (Objects.nonNull(user.getRef()) && !"".equalsIgnoreCase(user.getRef())) {
            userDB.setRef(user.getRef());
        }
        if (Objects.nonNull(user.getPhone()) && !"".equalsIgnoreCase(user.getPhone())) {
            userDB.setPhone(user.getPhone());
        }
        if (Objects.nonNull(user.getStreet()) && !"".equalsIgnoreCase(user.getStreet())) {
            userDB.setStreet(user.getStreet());
        }
        if (Objects.nonNull(user.getHouseNumber()) && !"".equalsIgnoreCase(user.getHouseNumber())) {
            userDB.setHouseNumber(user.getHouseNumber());
        }

        userRepository.save(userDB);
    }

    @Override
    public boolean exist(long id) {
        return userRepository.existsById(id);
    }
}

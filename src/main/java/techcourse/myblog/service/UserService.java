package techcourse.myblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.repository.UserRepository;
import techcourse.myblog.dto.UserEditRequestDto;
import techcourse.myblog.exception.UserDuplicateException;
import techcourse.myblog.exception.UserNotFoundException;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private static final String ERROR_DUPLICATE_EMAIL_MESSAGE = "이미 가입된 이메일 주소입니다!";
    private final UserRepository userRepository;

    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserDuplicateException(ERROR_DUPLICATE_EMAIL_MESSAGE);
        }

        log.debug("save user={}", user);
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("error find user by id={}", id);
                    return new UserNotFoundException();
                });
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User update(Long id, UserEditRequestDto userEditRequestDto) {
        log.debug("update user params={} by id={}", userEditRequestDto, id);

        User user = findById(id);
        user.update(userEditRequestDto);
        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.debug("delete user id={}", id);
    }
}

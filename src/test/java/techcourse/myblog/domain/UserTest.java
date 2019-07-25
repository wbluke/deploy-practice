package techcourse.myblog.domain;

import org.junit.jupiter.api.Test;
import techcourse.myblog.dto.UserEditParams;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void matchPassword() {
        User user = User.builder()
                .name("name")
                .email("test@test.com")
                .password("password1!")
                .build();

        assertThat(user.matchPassword("password1!")).isTrue();
        assertThat(user.matchPassword("password2@")).isFalse();
    }

    @Test
    void update() {
        User user = User.builder()
                .name("name")
                .email("test@test.com")
                .password("password1!")
                .build();

        UserEditParams userEditParams = new UserEditParams();
        userEditParams.setName("newName");

        user.update(userEditParams);

        assertThat(user.getName()).isEqualTo("newName");
    }
}
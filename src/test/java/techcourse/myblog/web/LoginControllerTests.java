package techcourse.myblog.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import techcourse.myblog.dto.UserSaveRequestDto;
import techcourse.myblog.testutil.LoginTestUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LoginControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private String jSessionId;
    private UserSaveRequestDto userSaveRequestDto;

    @BeforeEach
    void setUp() {
        userSaveRequestDto = new UserSaveRequestDto("테스트", "login@test.com", "password1!");

        LoginTestUtil.signUp(webTestClient, userSaveRequestDto);
        jSessionId = LoginTestUtil.getJSessionId(webTestClient, userSaveRequestDto);
    }

    @Test
    void login_페이지_이동() {
        webTestClient.get().uri("/login")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void signUp_페이지_이동() {
        webTestClient.get().uri("/signup")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void login() {
        webTestClient.post().uri("/login")
                .body(BodyInserters
                        .fromFormData("email", userSaveRequestDto.getEmail())
                        .with("password", userSaveRequestDto.getPassword()))
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    void logout() {
        webTestClient.get().uri("/logout")
                .cookie("JSESSIONID", jSessionId)
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @AfterEach
    void tearDown() {
        LoginTestUtil.deleteUser(webTestClient, userSaveRequestDto);
    }
}
package mdspring.me.mohammednajib.runners.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest(UserRestClient.class)
public class UserRestClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    UserRestClient client;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldFindAllUsers() throws JsonProcessingException {
        // given
        User user1 = new User(
                1,
                "Mohammed Najib",
                "Mohammed",
                "mohammed@gmail.com",
                new Address(
                        "Kulas Light",
                        "Apt. 556",
                        "Gwenborough",
                        "92998-3874",
                        new Geo(
                                "-37.3159",
                                "81.1496")),
                "1-770-736-8031 x56442",
                "mohammed.org",
                new Company(
                        "Mohammed Comp",
                        "Multi-layered client-server neural-net",
                        "harness real-time e-markets"));

        List<User> users = List.of(user1);

        // when
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users"))
                .andRespond(withSuccess(
                        objectMapper.writeValueAsString(users),
                        MediaType.APPLICATION_JSON));

        // then
        List<User> allUsers = client.findAll();
        assertEquals(users, allUsers);
    }

    @Test
    void shouldFindUserById() throws JsonProcessingException {
        // given
        User user = new User(
                1,
                "Mohammed Najib",
                "Mohammed",
                "mohammed@gmail.com",
                new Address(
                        "Kulas Light",
                        "Apt. 556",
                        "Gwenborough",
                        "92998-3874",
                        new Geo(
                                "-37.3159",
                                "81.1496")),
                "1-770-736-8031 x56442",
                "mohammed.org",
                new Company(
                        "Mohammed Comp",
                        "Multi-layered client-server neural-net",
                        "harness real-time e-markets"));

        // when
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users/1"))
                .andRespond(withSuccess(
                        objectMapper.writeValueAsString(user),
                        MediaType.APPLICATION_JSON));

        // then
        User mohammed = client.findById(1);
        assertEquals(mohammed.name(), "Mohammed Najib", "user name should be 'Mohammed Najib'");
        assertEquals(mohammed.username(), "Mohammed", "user username should be 'Mohammed'");
        assertEquals(mohammed.email(), "mohammed@gmail.com", "user email should be 'mohammed@gmail.com'");

        assertAll("address",
                () -> assertEquals(mohammed.address().street(), "Kulas Light",
                        "address street should be 'Kulas Light'"),
                () -> assertEquals(mohammed.address().suite(), "Apt. 556", "address suite should be 'Apt. 556'"),
                () -> assertEquals(mohammed.address().city(), "Gwenborough", "address city should be 'Gwenborough'"),
                () -> assertEquals(mohammed.address().zipcode(), "92998-3874",
                        "address zipcode should be '92998-3874'"),
                () -> assertEquals(mohammed.address().geo().lat(), "-37.3159", "address geo lat should be '-37.3159'"),
                () -> assertEquals(mohammed.address().geo().lng(), "81.1496", "address geo lng should be '81.1496'"));

        assertEquals(mohammed.phone(), "1-770-736-8031 x56442", "user phone should be '1-770-736-8031 x56442'");
        assertEquals(mohammed.website(), "mohammed.org", "user website should be 'mohammed.org'");

        assertAll("Company",
                () -> assertEquals(mohammed.company().name(), "Mohammed Comp",
                        "company name should be 'Mohammed Comp'"),
                () -> assertEquals(mohammed.company().catchPhrase(), "Multi-layered client-server neural-net",
                        "company catchPhrase should be 'Multi-layered client-server neural-net'"),
                () -> assertEquals(mohammed.company().bs(), "harness real-time e-markets",
                        "company bs should be 'harness real-time e-markets'"));
    }
}

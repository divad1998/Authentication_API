package com.aneeque.codingchallenge.unitTest;

import com.aneeque.codingchallenge.entity.User;
import com.aneeque.codingchallenge.service.UserService;
import com.aneeque.codingchallenge.utilities.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // ensures actual server is not started and provides a mock server
public class AddUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    private Utils utils = new Utils();

    @Test
    public void testAddUser() throws Exception {

        User user = Utils.createUser();

        when(userService.addUser((User)any())).thenReturn(user);

        // convert user to json
        String requestBody = new ObjectMapper().writeValueAsString(user);

        // make request
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .string("{\"message\":\"User created. Welcome!\"}"));
    }

}

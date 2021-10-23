//package com.aneeque.codingchallenge.unitTest;
//
//import com.aneeque.codingchallenge.LoginRequest;
//import com.aneeque.codingchallenge.entity.User;
//import com.aneeque.codingchallenge.service.UserService;
//import com.aneeque.codingchallenge.utilities.Utils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.event.annotation.BeforeTestMethod;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultHandler;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc // ensures server is not started and provides a mock server
//public class UserControllerTest {
//
//    @Autowired
//    ApplicationContext applicationContext;
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    UserService userService;
//
//    private String jwtToken;
//
////    @Value("jwt.secret.key")
////    private String secretKey;
//
//    private Utils utils = new Utils();
//
//    @Test
//    public void contextLoads() {
//        assertThat(applicationContext).isNotNull();
//    }
//
//    @Test
//    public void testAddUser() throws Exception {
//
//        User user = Utils.createUser();
//
//        when(userService.addUser((User)any())).thenReturn(user);
//
//        // convert user to json string
//        String body = new ObjectMapper().writeValueAsString(user);
//
//        // make request
//        mockMvc.perform(post("/api/v1/users")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(body))
//                .andExpect(status().isCreated())
//                .andExpect(content()
//                            .string("{\"message\":\"User created. Welcome!\"}"));
//    }
//
//    @Test
//    public void testLogin() throws Exception { // use @BeforeTestClass to save token
//
//        // create user
//        User user = Utils.createUser();
//
//        // stub loadByUsername
//        when(userService.loadUserByUsername(anyString())).thenReturn(user);
//
//        // create login request
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("davinci");
//        loginRequest.setPassword("password");
//
//        // convert to json string
//        String body = new ObjectMapper().writeValueAsString(loginRequest);
//
//        // make post request
//        MockHttpServletResponse response = mockMvc.perform(post("/login")
//                                                    .contentType(MediaType.APPLICATION_JSON)
//                                                    .content(body))
//                                                    .andExpect(status().isOk())
//                                                    .andExpect(header().exists("Authorization"))
//                                                    .andReturn()
//                                                    .getResponse();
//        jwtToken = response.getHeader("Authorization");
//
//    }
//
//    @Test
//    public void testGetUsers() throws Exception {
//
//        // create user
//        User user = Utils.createUser();
//
//        // stub getByUsername
//        when(userService.getByUsername(user.getUsername())).thenReturn(user);
//
//        // send request
//        mockMvc.perform(get("/api/v1/users")
//                            .header("Authorization", jwtToken)
//                            .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//}

package com.example.printease;
import com.example.printease.apis.AuthenticationApi;
import com.example.printease.modals.Address;
import com.example.printease.modals.DTO.RegistrationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationApi.class)
public class AuthenticationApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
    public void testRegisterServiceProvider() throws Exception {
        RegistrationRequestDTO request = RegistrationRequestDTO.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password")
                .userRole("SERVICE_PROVIDER")
                .phoneNumber("1234567890")
                .businessName("John's Business")
                .gstIn("1234567890")
                .address(Address.builder()
                        .addressLine1("123, ABC Street")
                        .addressLine2("XYZ Building")
                        .city("Mumbai")
//                        .state("Maharashtra")
//                        .country("India")
                        .pincode(400001)
                        .build())
                .build();

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

//    @Test
    public void testRegisterCustomer() throws Exception {
        RegistrationRequestDTO request = RegistrationRequestDTO.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("password")
                .userRole("CUSTOMER")
                .phoneNumber("0987654321")
                .build();

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
package com.crio.rent_read.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;
import com.crio.rent_read.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = true) // Make sure security filters are enabled
public class BookControllerTest {
// class BookControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    // @MockBean
    @MockitoBean
    private BookService bookService;

    @Test
    @WithMockUser(username = "admin@test.com", roles = {"ADMIN"})
    void registerBook_WhenAdminRole_ShouldReturnCreated() throws Exception {

        BookResponse response = new BookResponse();
        Mockito.when(bookService.registerBook(any(BookRequest.class)))
               .thenReturn(response);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Spring Boot",
                          "author": "Author",
                          "available": true
                        }
                        """))
                .andExpect(status().isCreated());
    }

    // @Test
    // @WithMockUser(username = "user@test.com", roles = {"USER"})
    // void registerBook_WhenUserRole_ShouldReturnForbidden() throws Exception {

    //     mockMvc.perform(post("/books")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content("""
    //                     {
    //                       "title": "Spring Boot",
    //                       "author": "Author",
    //                       "available": true
    //                     }
    //                     """))
    //             .andExpect(status().isForbidden());
    // }

    // @Test
    // void registerBook_WhenUnauthenticated_ShouldReturnUnauthorized() throws Exception {

    //     mockMvc.perform(post("/books")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content("""
    //                     {
    //                       "title": "Spring Boot",
    //                       "author": "Author",
    //                       "available": true
    //                     }
    //                     """))
    //             .andExpect(status().isUnauthorized());
    // }
}

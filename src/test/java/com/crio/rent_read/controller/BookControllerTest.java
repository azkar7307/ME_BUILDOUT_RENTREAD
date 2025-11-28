package com.crio.rent_read.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import com.crio.rent_read.config.SecurityConfig;
import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;
import com.crio.rent_read.service.BookService;
import com.crio.rent_read.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfig.class)
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = true) // Make sure security filters are enabled
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // @MockBean
    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private UserDetailsServiceImpl userDetailsService;  // mock dependency


// ####################################################################################################
    // works
    // @Test
    // @WithMockUser(username = "admin@rentread.com", password = "admin123456",  roles = {"ADMIN"})

    // works
    // @Test
    // @WithMockUser(username = "admin@rentread.com",  roles = {"ADMIN"})
    
    // works: even though email is different from database 
    // @Test
    // @WithMockUser(username = "admin@rent.com",  roles = {"ADMIN"})

    // works
    // @Test
    // @WithMockUser(roles = {"ADMIN"})


// ############################ POST /book ##############################################

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void registerBook_With_ADMIN_Role_Return_Created() throws Exception {

        BookResponse response = new BookResponse();
        when(bookService.registerBook(any(BookRequest.class)))
               .thenReturn(response);

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Spring Boot",
                          "author": "Author"
                        }
                        """)
        )
            .andExpect(status().isCreated());
    
        verify(bookService, times(1)).registerBook(any(BookRequest.class));
    }


    @Test
    @WithMockUser(roles = {"USER"})
    void registerBook_With_USER_Role_Throw_Forbidden() throws Exception {

        BookResponse response = new BookResponse();
        when(bookService.registerBook(any(BookRequest.class)))
               .thenReturn(response);

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Spring Boot",
                          "author": "Author"
                        }
                        """)
        )
            .andExpect(status().isForbidden());

        verify(bookService, never()).registerBook(any(BookRequest.class));

    }

    @Test
    @WithMockUser(roles = {"undefined"})
    void registerBook_With_undefined_Role_Throw_Forbidden() throws Exception {

        BookResponse response = new BookResponse();
        when(bookService.registerBook(any(BookRequest.class)))
               .thenReturn(response);

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Spring Boot",
                          "author": "Author"
                        }
                        """)
        )
            .andExpect(status().isForbidden());
        
        verify(bookService, never()).registerBook(any(BookRequest.class));
        
    }

    @Test
    void registerBook_When_Unauthenticated_Throw_Unauthorized() throws Exception {
        BookResponse response = new BookResponse();
        when(bookService.registerBook(any(BookRequest.class)))
            .thenReturn(response);

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "title": "Spring Boot",
                        "author": "Author",
                        "available": true
                    }
                    """)
        )
            .andExpect(status().isUnauthorized());
        
        verify(bookService, never()).registerBook(any(BookRequest.class));   
    }


// ############################ DELETE /book/{book_id} ##############################################

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteBookById_With_ADMIN_Role_Return_Ok() throws Exception {

        doNothing().when(bookService).deleteBookById(anyLong());

        mockMvc.perform(
            delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Spring Boot",
                          "author": "Author"
                        }
                        """)
        )
            .andExpect(status().isNoContent());
    
        verify(bookService, times(1)).deleteBookById(anyLong());
    }

    @Test
    @WithMockUser(roles = {"admin"})
    void deleteBookById_With_admin_Role_InLowerCase_Throw_Forbidden() throws Exception {

        doNothing().when(bookService).deleteBookById(anyLong());

        mockMvc.perform(
            delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Spring Boot",
                          "author": "Author"
                        }
                        """)
        )
            .andExpect(status().isForbidden());
    
        verify(bookService, never()).deleteBookById(anyLong());
    }


    @Test
    @WithMockUser(roles = {"Admin"})
    void deleteBookById_With_admin_Role_InTitleCase_Throw_Forbidden() throws Exception {

        doNothing().when(bookService).deleteBookById(anyLong());

        mockMvc.perform(
            delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Spring Boot",
                          "author": "Author"
                        }
                        """)
        )
            .andExpect(status().isForbidden());
    
        verify(bookService, never()).deleteBookById(anyLong());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void deleteBookById_With_USER_Role_Throw_Forbidden() throws Exception {

        doNothing().when(bookService).deleteBookById(anyLong());

        mockMvc.perform(
            delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Spring Boot",
                          "author": "Author"
                        }
                        """)
        )
            .andExpect(status().isForbidden());
    
        verify(bookService, never()).deleteBookById(anyLong());
    }

    @Test
    void deleteBookById_When_Unauthenticated_Throw_Unauthorized() throws Exception {

        doNothing().when(bookService).deleteBookById(anyLong());

        mockMvc.perform(
            delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Spring Boot",
                          "author": "Author"
                        }
                        """)
        )
            .andExpect(status().isUnauthorized());
    
        verify(bookService, never()).deleteBookById(anyLong());
    }

    // ######################## GET /books/available ##########################################

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAvalilableBook_With_ADMIN_Role_Return_Ok() throws Exception {
        // Arrange
        List<BookResponse> books = List.of(new BookResponse());

        // setup
        when(bookService.getAllAvalilableBooks()).thenReturn(books);

        // Execute
        mockMvc.perform(get("/books/available"))
                .andExpect(status().isOk());

        // verify
        verify(bookService, times(1)).getAllAvalilableBooks();
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getAvalilableBook_With_USER_Role_Return_Ok() throws Exception {
        // Arrange
        List<BookResponse> books = List.of(new BookResponse());

        // setup
        when(bookService.getAllAvalilableBooks()).thenReturn(books);

        // Execute
        mockMvc.perform(get("/books/available"))
                .andExpect(status().isOk());

        // verify
        verify(bookService, times(1)).getAllAvalilableBooks();
    }

    @Test
    void getAvalilableBook_When_UNAUTHENTICATED_Throw_Unauthorized() throws Exception {

        // Execute
        mockMvc.perform(get("/books/available"))
                .andExpect(status().isUnauthorized());

        // verify
        verify(bookService, never()).getAllAvalilableBooks();
    }
}

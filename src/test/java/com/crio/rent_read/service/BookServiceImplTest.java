package com.crio.rent_read.service;

import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.enums.Status;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.service.impl.BookServiceImpl;
import com.crio.rent_read.service.impl.ValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;


    @Mock
    private ValidationServiceImpl validationServiceImpl;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookServiceimpl;

    BookRequest request;
    Book sampleBook;

    @BeforeEach
    void setup() {
        request = new BookRequest(
                "Test Book 1",
                "Test Author 1",
                "FICTION",
                Status.NOT_AVAILABLE
        );

        sampleBook = modelMapper.map(request, Book.class);
        sampleBook.setId(1L);
    }

    @Test
    void registerBook_return_BookResponse() {

        // setup
        when(bookRepository.save(any(Book.class))).thenAnswer(
            invocation -> invocation.getArgument(0)
        );

        // execute
        BookResponse response = bookServiceimpl.registerBook(request);
        assertNotNull(response);
        assertEquals(sampleBook.getAuther(), response.getAuthor());
        assertEquals(Status.NOT_AVAILABLE.name(), response.getAvailabilityStatus());

        //verify
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(modelMapper, times(1)).map(any(Book.class), eq(BookResponse.class));
    }

    @Test
    void updateBookById_return_BookResponse() {

        BookRequest updateBookRequest = new BookRequest(
                "Test Book 1",
                "Test Author 1",
                "COMEDY",
                Status.AVAILABALE
        );
        // setup
        when(validationServiceImpl.validateAndGetBook(anyLong())).thenReturn(sampleBook);

        // execute
        BookResponse bookResponse = bookServiceimpl.updateBookById(
                sampleBook.getId(),
                updateBookRequest
        );

        assertNotNull(bookResponse);
        assertEquals(updateBookRequest.getGenre(), bookResponse.getGenre());
        assertEquals(Status.AVAILABALE.name(), bookResponse.getAvailabilityStatus());

        //verify
        verify(validationServiceImpl, times(1)).validateAndGetBook(anyLong());
        verify(modelMapper, times(1)).map(any(Book.class), eq(BookResponse.class));
    }

    // @Test
    // void updateBookById_Non_Existing_Throw_NotFoundException() {

    //     // setup
    //     when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

    //     // execute
    //     org.springframework.data.crossstore.ChangeSetPersister.NotFoundException ex =
    //             assertThrows(
    //                     ChangeSetPersister.NotFoundException.class,
    //                     () ->
    //                     bookServiceimpl.updateBookById(
    //                         sampleBook.getId(),
    //                         request
    //                     )
    //             );

    //     assertEquals("Book '" + sampleBook.getId() + "' not found for update!", ex.getMessage());

    //     //verify
    //     verify(bookRepository, times(1)).findById(anyLong());
    //     verify(bookRepository, never()).save(any(Book.class));
    //     verify(modelMapper, never()).map(any(Book.class), eq(BookResponse.class));
    // }

    @Test
    void deleteBookById_return_void() {
        // setup
        when(validationServiceImpl.validateAndGetBook(anyLong())).thenReturn(sampleBook);
        doNothing().when(bookRepository).delete(any(Book.class));

        // execute
        bookServiceimpl.deleteBookById(sampleBook.getId());

        //verify
        verify(validationServiceImpl, times(1)).validateAndGetBook(anyLong());
        verify(bookRepository, times(1)).delete(any(Book.class));
    }
}


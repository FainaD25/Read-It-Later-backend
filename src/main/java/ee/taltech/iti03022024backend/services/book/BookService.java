package ee.taltech.iti03022024backend.services.book;

import ee.taltech.iti03022024backend.dto.book.BookDtoIn;
import ee.taltech.iti03022024backend.dto.book.BookDtoOut;
import ee.taltech.iti03022024backend.entities.book.BookEntity;
import ee.taltech.iti03022024backend.exceptions.NotFoundException;
import ee.taltech.iti03022024backend.mappers.book.BookMapper;
import ee.taltech.iti03022024backend.repositories.books.BookRepository;
import ee.taltech.iti03022024backend.response.book.BookPageResponse;
import ee.taltech.iti03022024backend.services.genre.GenreService;
import ee.taltech.iti03022024backend.specifications.book.BookSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final GenreService genreService;

    public BookDtoOut createBook(BookDtoIn bookDtoIn) {
        BookEntity bookEntity = bookMapper.toEntity(bookDtoIn);
        bookRepository.save(bookEntity);
        String bookGenre = genreService.getGenreById(bookEntity.getGenreId());
        BookDtoOut bookDtoOut = bookMapper.toDto(bookEntity);
        bookDtoOut.setGenre(bookGenre);

        return bookDtoOut;
    }

    public BookDtoOut getBookById(Long id) throws NotFoundException{
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        String bookGenre = genreService.getGenreById(bookEntity.getGenreId());

        BookDtoOut bookDtoOut = bookMapper.toDto(bookEntity);
        bookDtoOut.setGenre(bookGenre);

        return bookDtoOut;
    }

    public BookPageResponse getBooks(String author, String title, Long genreId, int page, int size) {
        Specification<BookEntity> specification = Specification
                .where(BookSpecifications.getByAuthor(author))
                .and(BookSpecifications.getByTitle(title))
                .and(BookSpecifications.getByGenreId(genreId));

        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        if (author != null) {
            pageable = PageRequest.of(page, size, Sort.by("author").ascending());
        }

        Slice<BookEntity> slice = bookRepository.findAll(specification, pageable);

        boolean isLastPage = slice.isLast();

        List<BookDtoOut> booksDtoOutList = slice.stream()
                .map(bookEntity -> {
                    BookDtoOut bookDtoOut = bookMapper.toDto(bookEntity);
                    String bookGenre = genreService.getGenreById(bookEntity.getGenreId());
                    bookDtoOut.setGenre(bookGenre);
                    return bookDtoOut;
                })
                .toList();

        return new BookPageResponse(booksDtoOutList, !isLastPage);
    }

}

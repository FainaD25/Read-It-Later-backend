package ee.taltech.iti03022024backend.services.book;

import ee.taltech.iti03022024backend.controllers.genre.GenreController;
import ee.taltech.iti03022024backend.dto.book.BookDtoIn;
import ee.taltech.iti03022024backend.dto.book.BookDtoOut;
import ee.taltech.iti03022024backend.entities.book.BookEntity;
import ee.taltech.iti03022024backend.exceptions.NotFoundException;
import ee.taltech.iti03022024backend.mappers.book.BookMapper;
import ee.taltech.iti03022024backend.repositories.books.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final GenreController genreController;

    public BookDtoOut createBook(BookDtoIn bookDtoIn) {
        BookEntity bookEntity = bookMapper.toEntity(bookDtoIn);
        bookRepository.save(bookEntity);
        String bookGenre = genreController.getGenreById(bookEntity.getGenreId()).getBody();
        BookDtoOut bookDtoOut = bookMapper.toDto(bookEntity);
        bookDtoOut.setGenre(bookGenre);

        return bookDtoOut;
    }

    public BookDtoOut getBookById(Long id) throws NotFoundException{
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        String bookGenre = genreController.getGenreById(bookEntity.getGenreId()).getBody();

        BookDtoOut bookDtoOut = bookMapper.toDto(bookEntity);
        bookDtoOut.setGenre(bookGenre);

        return bookDtoOut;
    }

    public List<BookDtoOut> getBooks(String author, String title, Long genreId) {
        List<BookEntity> bookEntities = bookRepository.findAll();

        return bookEntities.stream()
                .filter(bookEntity -> (author == null || bookEntity.getAuthor().equalsIgnoreCase(author)))
                .filter(bookEntity -> (title == null || bookEntity.getTitle().equalsIgnoreCase(title)))
                .filter(bookEntity -> (genreId == null || bookEntity.getGenreId() == genreId))
                .map(bookEntity -> {
                    BookDtoOut bookDtoOut = bookMapper.toDto(bookEntity);
                    // Kui vaja, tõmba žanri nimi ja lisa DTO-sse
                    String bookGenre = genreController.getGenreById(bookEntity.getGenreId()).getBody();
                    bookDtoOut.setGenre(bookGenre);
                    return bookDtoOut;
                })
                .toList();
    }


    public List<BookDtoOut> getBooksByGenre(Long genreId) {
        List<BookEntity> bookEntities = bookRepository.findAll().stream()
                .filter(book -> genreId.equals(book.getGenreId()))
                .sorted(Comparator.comparing(BookEntity::getTitle))
                .toList();

        return bookEntities.stream()
                .map(bookEntity -> {
                    BookDtoOut bookDtoOut = bookMapper.toDto(bookEntity);
                    String bookGenre = genreController.getGenreById(bookEntity.getGenreId()).getBody();
                    bookDtoOut.setGenre(bookGenre);
                    return bookDtoOut;
                })
                .toList();
    }
}

package beyond.myfavoirtebook.book.service;

import beyond.myfavoirtebook.book.domain.Book;
import beyond.myfavoirtebook.book.dto.BookReqDto;
import beyond.myfavoirtebook.book.dto.BookResDto;
import beyond.myfavoirtebook.book.dto.BookUpdateDto;
import beyond.myfavoirtebook.book.dto.SearchBookResDto;
import beyond.myfavoirtebook.book.repository.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private static final String KAKAO_BOOK_SEARCH_URL = "https://dapi.kakao.com/v3/search/book";

    @Value("${kakao.api.key}")
    private String KAKAO_API_KEY;

    // 책 검색
    public List<SearchBookResDto> searchBook(String query) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);

        String requestUrl = KAKAO_BOOK_SEARCH_URL + "?query=" + query + "&size=5";

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        List<SearchBookResDto> searchedBooks = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode documents = root.path("documents");

            for (JsonNode doc : documents) {
                String title = doc.path("title").asText();
                List<String> authors = new ArrayList<>();
                doc.path("authors").forEach(author -> authors.add(author.asText()));
                String publisher = doc.path("publisher").asText();
                String thumbnail = doc.path("thumbnail").asText();

                searchedBooks.add(new SearchBookResDto(title, authors, publisher, thumbnail));
            }
        } catch (Exception e) {
            throw new RuntimeException("책 검색 실패", e);
        }

        return searchedBooks;
    }

    // 책 추가
    public BookResDto createBook(BookReqDto dto) {
        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .finishDate(dto.getFinishDate())
                .rating(dto.getRating())
                .review(dto.getReview())
                .build();

        bookRepository.save(book);
        return new BookResDto(book.getId(), book.getTitle(), book.getAuthor(),
                book.getFinishDate(), book.getRating(), book.getReview());
    }

    // 책 정보 수정
    @Transactional
    public void updateBook(Long id, BookUpdateDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.updateBook(dto);
    }

    // 책 삭제
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.updateDelYn();
    }
}

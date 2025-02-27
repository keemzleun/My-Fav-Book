package beyond.myfavoirtebook.book.controller;

import beyond.myfavoirtebook.book.dto.BookReqDto;
import beyond.myfavoirtebook.book.dto.BookUpdateDto;
import beyond.myfavoirtebook.book.service.BookService;
import beyond.myfavoirtebook.common.dto.CommonResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBook(@RequestParam String query) {
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "books are found", bookService.searchBook(query)), HttpStatus.OK);
    }

    // 책 추가
    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody BookReqDto dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    // 책 정보 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long id,
            @RequestBody BookUpdateDto dto) {
        bookService.updateBook(id, dto);
        return ResponseEntity.ok("Book updated successfully");
    }

    // 책 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

}

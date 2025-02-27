package beyond.myfavoirtebook.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookReqDto {
    private String title;
    private String author;
    private LocalDate finishDate;
    private int rating;
    private String review;
}

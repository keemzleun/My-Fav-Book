package beyond.myfavoirtebook.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDto {
    private LocalDate finishDate;
    private int rating;
    private String review;
}

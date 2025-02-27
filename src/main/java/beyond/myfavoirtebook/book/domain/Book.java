package beyond.myfavoirtebook.book.domain;

import beyond.myfavoirtebook.book.dto.BookUpdateDto;
import beyond.myfavoirtebook.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String thumbnail;
    private LocalDate finishDate;
    private int rating;
    private String review;

    public void updateBook(BookUpdateDto dto){
        this.finishDate = dto.getFinishDate();
        this.rating = dto.getRating();
        this.review = dto.getReview();
    }

}

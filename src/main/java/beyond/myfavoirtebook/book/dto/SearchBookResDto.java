package beyond.myfavoirtebook.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookResDto {
    private String title;
    private List<String> authors;
    private String thumbnail;
    private String publisher;

}

package beyond.myfavoirtebook.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResDto {
	private Long id;
	private String title;
	private String member_email;

}

package beyond.myfavoirtebook.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostDetResDto {
	private Long id;
	private String title;
	private String contents;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private String member_email;
}

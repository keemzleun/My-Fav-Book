package beyond.myfavoirtebook.post.dto;

import beyond.myfavoirtebook.member.domain.Member;
import beyond.myfavoirtebook.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveReqDto {
	private String title;
	private String contents;

//	private String email;	// 추후 로그인 기능 이후에는 없어질 dto


	public Post toEntity(Member member){
		return Post.builder()
			.title(this.title)
			.contents(this.contents)
			.member(member)
			.build();
	}




}

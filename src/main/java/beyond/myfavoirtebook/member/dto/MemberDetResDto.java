package beyond.myfavoirtebook.member.dto;

import beyond.myfavoirtebook.member.domain.Member;
import beyond.myfavoirtebook.member.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetResDto {
	private Long id;
	private String name;
	private String email;
	private String password;
	private int postCounts;

	private LocalDateTime createdTime;


	public MemberDetResDto fromEntity(Member member) {
		return MemberDetResDto.builder()
			.id(member.getId())
			.name(member.getName())
			.email(member.getEmail())
			.password(member.getPassword())
//			.role(member.getRole())
//			.postCounts(member.getPosts().size())
			.createdTime(member.getCreatedTime())
			.build();
	}

}

package beyond.myfavoirtebook.member.dto;

import beyond.myfavoirtebook.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberListResDto {
	private Long id;
	private String name;
	private String email;

	public MemberListResDto fromEntity(Member member) {
		return MemberListResDto.builder()
			.id(member.getId())
			.name(member.getName())
			.email(member.getEmail())
			.build();
	}
}

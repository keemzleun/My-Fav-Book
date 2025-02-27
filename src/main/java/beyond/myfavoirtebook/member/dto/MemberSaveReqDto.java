package beyond.myfavoirtebook.member.dto;

import beyond.myfavoirtebook.member.domain.Member;
import beyond.myfavoirtebook.member.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSaveReqDto {

	private String name;
	@NotEmpty(message = "email is essential")
	private String email;

	@NotEmpty(message = "password is essential")
	@Size(min = 8, message = "password id minimum length is 8")
	private String password;
//	private Role role = Role.USER;

	public Member toEntity(String password){
		return Member.builder()
				.name(this.name)
				.email(this.email)
				.password(password)
//				.posts(new ArrayList<>())
//				.role(this.role)
				.build();
	}

}

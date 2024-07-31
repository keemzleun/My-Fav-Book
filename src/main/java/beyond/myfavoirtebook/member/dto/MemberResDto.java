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
public class MemberResDto {
    private Long id;
    private String name;
    private String email;

    public MemberResDto fromEntity(Member member) {
        return MemberResDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}

package beyond.myfavoirtebook.member.domain;

import beyond.myfavoirtebook.common.domain.BaseTimeEntity;
import beyond.myfavoirtebook.member.dto.MemberResDto;
import beyond.myfavoirtebook.member.dto.MemberUpdateDto;
import beyond.myfavoirtebook.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Column(columnDefinition = "ENUM('ADMIN', 'USER') DEFAULT 'USER'")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts;

    public void updateMember(MemberUpdateDto dto) {
        this.name = dto.getName();
        this.password = dto.getPassword();
    }

    public MemberResDto fromEntity(){
        return MemberResDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .build();
    }

}

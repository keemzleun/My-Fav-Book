package beyond.myfavoirtebook.member.domain;


import beyond.myfavoirtebook.common.domain.BaseEntity;
import beyond.myfavoirtebook.member.dto.MemberDetResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


//    // 일반적으로 부모엔티티(자식 객체에 영향을 끼칠 수 있는 엔티티)에 cascade 옵션을 설정
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<MyBook> myBooks;

    public MemberDetResDto fromEntity(){
        return MemberDetResDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
//                .bookCount(this.myBooks.size())
                .build();
    }
}

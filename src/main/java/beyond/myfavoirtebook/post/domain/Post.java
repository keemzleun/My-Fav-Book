package beyond.myfavoirtebook.post.domain;

import beyond.myfavoirtebook.common.domain.BaseTimeEntity;
import beyond.myfavoirtebook.member.domain.Member;
import beyond.myfavoirtebook.post.dto.PostDetResDto;
import beyond.myfavoirtebook.post.dto.PostListResDto;
import beyond.myfavoirtebook.post.dto.PostUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 30000)
    private String contents;

    // 연관관계의 주인은 fk가 있는 post - 보통 Many 쪽이 연관관계의 주인이라고 들음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public PostListResDto listFromEntity(){
        return PostListResDto.builder()
                .id(this.id)
                .title(this.getTitle())
//			.author(this.author)
                .member_email(this.member.getEmail())
                .build();
    }

    public PostDetResDto detFromEntity(){
        return PostDetResDto.builder()
                .id(this.id)
                .title(this.title)
                .contents(this.contents)
                .member_email(this.member.getEmail())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdatedTime())
                .build();
    }



    public void updatePost(PostUpdateDto dto){
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }
}

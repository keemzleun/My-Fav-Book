package beyond.myfavoirtebook.cmt.domain;

import beyond.myfavoirtebook.common.domain.BaseTimeEntity;
import beyond.myfavoirtebook.member.domain.Member;
import beyond.myfavoirtebook.post.domain.Post;

import javax.persistence.*;

@Entity
public class Cmt extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


}

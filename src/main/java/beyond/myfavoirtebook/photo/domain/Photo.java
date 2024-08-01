package beyond.myfavoirtebook.photo.domain;

import beyond.myfavoirtebook.common.domain.BaseTimeEntity;
import beyond.myfavoirtebook.photo.dto.PhotoListRsDto;
import beyond.myfavoirtebook.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PhotoListRsDto.PhotoInfoDto FromEntity() {
        return PhotoListRsDto.PhotoInfoDto.builder()
                .id(this.photoId)
                .url(this.photoUrl)
                .build();
    }
}

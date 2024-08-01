package beyond.myfavoirtebook.photo.dto;

import lombok.*;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoListRsDto {
    private Long postId;
    private List<PhotoInfoDto> photoList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhotoInfoDto{
        private Long id;
        private String url;

        public PhotoRsDto fromDto(){
            return PhotoRsDto.builder()
                    .url(this.url)
                    .build();
        }

    }
}

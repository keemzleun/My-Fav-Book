package beyond.myfavoirtebook.photo.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import beyond.myfavoirtebook.photo.domain.Photo;
import beyond.myfavoirtebook.photo.dto.PhotoListRsDto;
import beyond.myfavoirtebook.photo.repository.PhotoRepository;
import beyond.myfavoirtebook.post.domain.Post;
import beyond.myfavoirtebook.post.repository.PostRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class PhotoService {

    private final AmazonS3 amazonS3;
    private final PhotoRepository photoRepository;
    private final PostRepository postRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    // S3 Uploader
    @Transactional
    public PhotoListRsDto.PhotoInfoDto uploadFile(Long postId, MultipartFile multipartFile) throws IOException {
        // todo ) 각 기능별로 메서드 분해하기 -> s3 업로드, 레포지토리 save
        String inputFileName = multipartFile.getOriginalFilename();

        //파일 형식 구하기
        String ext = inputFileName.substring(inputFileName.lastIndexOf(".") + 1).toLowerCase();
        String contentType;

        //content type을 지정해서 올려주지 않으면 자동으로 "application/octet-stream"으로 고정이 되서 링크 클릭시 웹에서 열리는게 아니라 자동 다운이 시작됨.
        switch (ext) {
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "jpg":
                contentType = "image/jpg";
                break;
            default:
                throw new IllegalArgumentException("Only image files (jpeg, png, jpg) are allowed.");   // 안뜸
        }


        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);   // ObjectMetadata에 contentType 입력

        String uuidFileName = UUID.randomUUID().toString() + "." + ext; // 파일명 UUID로 변환 후 파일 타입 붙여주기


        try {
            amazonS3.putObject(new PutObjectRequest(bucket, uuidFileName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Amazon ServiceException");
        } catch (SdkClientException e) {
            throw new SdkClientException("SdkClientException");
        }


        String url = amazonS3.getUrl(bucket, uuidFileName).toString();

        Post post = postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException("post id is not found"));

        // Photo 저장
        Photo photo = Photo.builder()
                .photoUrl(url)
                .post(post)
                .build();
        photoRepository.save(photo);

        // dto에 담기
        PhotoListRsDto.PhotoInfoDto photoInfoDto = PhotoListRsDto.PhotoInfoDto.builder()
                .id(photo.getPhotoId())
                .url(url)
                .build();

        return photoInfoDto;
    }

    // 사진 리스트
    public PhotoListRsDto photoList(Long postId) {
        List<Photo> photos = photoRepository.findByPostId(postId);
        List<PhotoListRsDto.PhotoInfoDto> infoDtos = new ArrayList<>();
        for (Photo p : photos){
            infoDtos.add(p.FromEntity());
        }
        PhotoListRsDto photoListRsDto = PhotoListRsDto.builder()
                .postId(postId)
                .photoList(infoDtos)
                .build();
        return photoListRsDto;
    }

}

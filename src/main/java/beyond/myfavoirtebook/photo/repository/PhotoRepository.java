package beyond.myfavoirtebook.photo.repository;


import beyond.myfavoirtebook.photo.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByPostId(Long postId);
}

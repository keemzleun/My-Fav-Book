package beyond.myfavoirtebook.post.repository;

import beyond.myfavoirtebook.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

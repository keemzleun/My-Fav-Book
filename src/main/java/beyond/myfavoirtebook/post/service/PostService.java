package beyond.myfavoirtebook.post.service;

import beyond.myfavoirtebook.member.domain.Member;
import beyond.myfavoirtebook.member.service.MemberService;
import beyond.myfavoirtebook.post.domain.Post;
import beyond.myfavoirtebook.post.dto.PostDetResDto;
import beyond.myfavoirtebook.post.dto.PostListResDto;
import beyond.myfavoirtebook.post.dto.PostSaveReqDto;
import beyond.myfavoirtebook.post.dto.PostUpdateDto;
import beyond.myfavoirtebook.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    // service 또는 repository를 autowired할지는 상황에 따라 다르나,
    // serviceㄷ레벨에서 코드가 고도화되어있고, 코드의 중복이 심할경우, service레이어를 autowired
    // 그러나, 순환참조가 발생하는 경우에는 repository를 autowired
    // 때문에 상황에 따라 repository, service 중 어떤 것을 autowired할지 선택해야한다.


    @Autowired
    public PostService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
        this.memberService = memberService;
    }


    // 	생성
    // authorservice에서 author객체를 찾아 post의 toEntity에 넘기고
    // jpa가 author객체에서 author_id를 찾아 db에는 author_id가 들어감.
    public Post postCreate(PostSaveReqDto dto) {
        // Author author = authorRepository.findByEmail(dto.getEmail())
        // 	.orElseThrow(() -> new EntityNotFoundException("author not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberService.memberFindByEmail(email);

        String appointment = null;

        System.out.println(dto);
        Post post = postRepository.save(dto.toEntity(member));
        Post savedPost = postRepository.save(post);

        return savedPost;
    }

    // 	조회
    public List<PostListResDto> postList(Pageable pageable) {
		List<Post> postList = postRepository.findAllFetch();
		List<PostListResDto> postListResDtos = new ArrayList<PostListResDto>();
		for (Post post : postList) {
			postListResDtos.add(post.listFromEntity());
		}
        return postListResDtos;
    }

    public Page<PostListResDto> postListPage(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());
        return postListResDtos;
    }

    // 상세 조회
    public PostDetResDto postDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("post is not found"));
        return post.detFromEntity();
    }

    // 삭제
    @Transactional
    public void delete(Long id){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("post id not found"));
        if(post.getMember().getEmail().equals(email)){
            throw new IllegalArgumentException("본인의 게시글이 아닙니다");
        }
        postRepository.delete(post);
    }

    // 수정
    @Transactional
    public Post update(Long id, PostUpdateDto dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("없는 id입니다"));
        if(!post.getMember().getEmail().equals(email)){
            throw new IllegalArgumentException("본인의 게시글이 아닙니다");
        }
        post.updatePost(dto);
        return postRepository.save(post);
    }


}

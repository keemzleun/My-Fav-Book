package beyond.myfavoirtebook.post.service;


import beyond.myfavoirtebook.member.domain.Member;
import beyond.myfavoirtebook.member.repository.MemberRepository;
import beyond.myfavoirtebook.member.service.MemberService;
import beyond.myfavoirtebook.post.domain.Post;
import beyond.myfavoirtebook.post.dto.PostDetResDto;
import beyond.myfavoirtebook.post.dto.PostListResDto;
import beyond.myfavoirtebook.post.dto.PostSaveReqDto;
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

@Service
public class PostService {
	private final PostRepository postRepository;
	private final MemberService memberService;
	private final MemberRepository memberRepository;


	@Autowired
	public PostService(PostRepository postRepository, MemberService memberService, MemberRepository memberRepository) {
		this.postRepository = postRepository;
		this.memberService = memberService;
		this.memberRepository = memberRepository;
	}



	public Post postCreate(PostSaveReqDto dto) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Member member = memberRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("member is not found"));

		System.out.println(dto);
		Post post = postRepository.save(dto.toEntity(member));
		Post savedPost = postRepository.save(post);

		return savedPost;
	}

	// 	조회
	public Page<PostListResDto> postList(Pageable pageable) {
		Page<Post> posts = postRepository.findAll(pageable);
		Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());
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


}

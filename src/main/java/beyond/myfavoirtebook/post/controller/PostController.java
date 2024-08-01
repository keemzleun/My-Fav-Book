package beyond.myfavoirtebook.post.controller;

import beyond.myfavoirtebook.common.dto.CommonResDto;
import beyond.myfavoirtebook.post.domain.Post;
import beyond.myfavoirtebook.post.dto.PostDetResDto;
import beyond.myfavoirtebook.post.dto.PostListResDto;
import beyond.myfavoirtebook.post.dto.PostSaveReqDto;
import beyond.myfavoirtebook.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PostController {

	private final PostService postService;

	@Autowired
    PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/post/register")
	public ResponseEntity<?> postCreate(@RequestBody PostSaveReqDto dto) {
		Post post = postService.postCreate(dto);
		return new ResponseEntity<>(new CommonResDto(HttpStatus.CREATED, "post is successfully created", post.getId()), HttpStatus.CREATED);
	}

	// 	조회
	@GetMapping("/post/list")
	public ResponseEntity<?> postList(@PageableDefault(size=10, sort="createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<PostListResDto> posts = postService.postList(pageable);
		return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "postlist is sucessfully found", posts), HttpStatus.OK);
	}

	@GetMapping("post/list/page")
	// Pageable 요청 방법: Localhost:/8080/post/list/page?size=10&page=0  -> 데이터 바인딩
	public Page<PostListResDto> postListPage(@PageableDefault(size=10, sort="createdTime", direction = Sort.Direction.DESC) Pageable pageable){
		return postService.postListPage(pageable);

	}

	@GetMapping("/post/detail/{id}")
	public ResponseEntity<?> postDetail(@PathVariable Long id){
//		log.info("get 요청 & parameter = " + id);
//		log.info("method명 : postList");
		PostDetResDto dto = postService.postDetail(id);
		return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "login is successful", dto), HttpStatus.OK);
	}


}

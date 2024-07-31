package beyond.myfavoirtebook.post.controller;

import beyond.myfavoirtebook.post.dto.PostListResDto;
import beyond.myfavoirtebook.post.dto.PostSaveReqDto;
import beyond.myfavoirtebook.post.dto.PostUpdateDto;
import beyond.myfavoirtebook.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class PostController {

    private final PostService postService;

    @Autowired
    PostController(PostService postService) {
        this.postService = postService;
    }

    // 	생성
    @GetMapping("/post/register")
    public String postCreateScreen(){
        return "post/post_register";
    }
    @PostMapping("/post/register")
    public String postCreate(@ModelAttribute PostSaveReqDto dto) {
        postService.postCreate(dto);
        return "redirect:/post/list";
    }

    // 	조회
    @GetMapping("/post/list")
    public String postList(Model model, @PageableDefault(size=10, sort="createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("postList", postService.postList(pageable));
        return "post/post_list";
    }

    @GetMapping("post/list/page")
    @ResponseBody
    // Pageable 요청 방법: Localhost:/8080/post/list/page?size=10&page=0  -> 데이터 바인딩
    public Page<PostListResDto> postListPage(@PageableDefault(size=10, sort="createdTime", direction = Sort.Direction.DESC) Pageable pageable){
        return postService.postListPage(pageable);

    }

    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model){
//		log.info("get 요청 & parameter = " + id);
//		log.info("method명 : postList");
        model.addAttribute("post", postService.postDetail(id));
        return "post/post_detail";
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable Long id, Model model){
        postService.delete(id);
        return "redirect:/post/list";
    }

    @PostMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, @ModelAttribute PostUpdateDto dto, Model model){
        postService.update(id, dto);
        return "redirect:/post/detail/" + id;
    }

}


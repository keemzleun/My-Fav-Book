package beyond.myfavoirtebook.mail.controller;


import beyond.myfavoirtebook.common.dto.CommonErrorDto;
import beyond.myfavoirtebook.common.dto.CommonResDto;
import beyond.myfavoirtebook.mail.service.MailVerifyService;
import beyond.myfavoirtebook.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
public class MailVerifyController {

	private final MailVerifyService mailVerifyService;
	private final MemberService memberService;

	@Autowired
	public MailVerifyController(MailVerifyService mailVerifyService, MemberService memberService) {
		this.mailVerifyService = mailVerifyService;
		this.memberService = memberService;
	}

	@PostMapping("/requestCode")
	public ResponseEntity<CommonResDto> requestEmail(@RequestParam("email") @Valid String email) {
		mailVerifyService.sendCodeToEmail(email);
		// 이메일 전송하고 레디스에 저장
		CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "이메일 인증 요청을 성공했습니다.", email);
		return new ResponseEntity<>(commonResDto, HttpStatus.OK);
	}

	@GetMapping("/requestCode")
	public ResponseEntity<?> verificationEmail(@RequestParam("email") @Valid String email,
		@RequestParam("code") String authCode) {
		boolean response = mailVerifyService.verifiedCode(email, authCode);	// 레디스의 정보와 입력한 정보를 비교

		if(response){
			CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "이메일 인증을 성공하였습니다", response);
			return new ResponseEntity<>(commonResDto, HttpStatus.OK);
		}else{
			CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), "인증번호가 일치하지 않습니다.");
			return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
		}

	}
}

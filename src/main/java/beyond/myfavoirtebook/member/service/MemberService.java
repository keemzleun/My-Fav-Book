package beyond.myfavoirtebook.member.service;

import beyond.myfavoirtebook.common.service.RedisService;
import beyond.myfavoirtebook.member.domain.Member;
import beyond.myfavoirtebook.member.dto.MemberDetResDto;
import beyond.myfavoirtebook.member.dto.MemberListResDto;
import beyond.myfavoirtebook.member.dto.MemberLoginDto;
import beyond.myfavoirtebook.member.dto.MemberSaveReqDto;
import beyond.myfavoirtebook.member.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final RedisService redisService;
	private static final String AUTH_EMAIL_PREFIX = "EMAIL_CERTIFICATE : ";

//    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

	@Autowired
	private PasswordEncoder passwordEncoder;

	public MemberService(MemberRepository memberRepository, RedisService redisService) {
		this.memberRepository = memberRepository;
        this.redisService = redisService;
    }

	public Member memberCreate(MemberSaveReqDto dto){
		String checkVerified = redisService.getValues(AUTH_EMAIL_PREFIX + dto.getEmail());

		if (checkVerified == null || !checkVerified.equals("true")) {
			throw new IllegalStateException("이메일 인증이 필요합니다.");
		}
		memberRepository.findByEmail(dto.getEmail()).ifPresent(existingMember -> {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		});

		Member savedMember = memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
		return savedMember;
	}

//	public Page<MemberDetResDto> memberList(Pageable pageable){
//		Page<Member> members = memberRepository.findAll(pageable);
////        return members.map((a->a.fromEntity()));
//		return members.map(a->a.fromEntity());
//	}

	public MemberDetResDto myInfo(){
		Member member = memberRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 이메일입니다."));
		return member.fromEntity();
	}

	public Member login(MemberLoginDto dto){
		// email 존재 여부 검증
		Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 이메일입니다."));

		// password 일치 여부 검증
		if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
		}
		return member;
	}

	public Member memberFindByEmail(String email) {
		return memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("해당 이메일의 사용자는 없습니다"));
	}



}

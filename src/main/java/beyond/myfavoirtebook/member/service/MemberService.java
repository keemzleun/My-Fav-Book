package beyond.myfavoirtebook.member.service;


import beyond.myfavoirtebook.member.domain.Member;
import beyond.myfavoirtebook.member.dto.*;
import beyond.myfavoirtebook.member.repository.MemberRepository;
import beyond.myfavoirtebook.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
// 조회 작업시에 readOnly 설정하면 성능 향상.
// 다만, 저장 작업시에는 Transactional 필요
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 생성
    @Transactional
    public Member memberCreate(MemberReqDto dto){
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 email입니다.");
        }
        if(dto.getPassword().length() < 8){
            throw new IllegalArgumentException("비밀번호가 너무 짧아욘");
        }

        Member member = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        // cascade persist 테스트. remove 테스트는 회원 삭제로 대체
        Member savedAuthor = memberRepository.save(member
        );

        // 오류메시지가 같다면 여기에서 authorService.authorFindByEmail을 사용할 수 있음
        return savedAuthor;
    }

    // 	리스트 조회
    public Page<MemberResDto> memberList(Pageable pageable){
        Page<Member> members = memberRepository.findAll(pageable);
//        return members.map((a->a.fromEntity()));
        return members.map((Member::fromEntity));
    }

    // 	상세 조회
    public MemberDetResDto memberDetail(Long id){
        Member member
                = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("member is not found"));

        return new MemberDetResDto().fromEntity(member);
    }

    public Member memberFindByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("해당 이메일의 사용자는 없습니다"));
    }

    @Transactional
    public void delete(Long id){
//		Member member
//		= authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("그런 회원 없습니다;;;"));
//		authorRepository.delete(member
//		);
        memberRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, MemberUpdateDto dto){
        Member member = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("그런 회원 없습니다;;;"));
        member.updateMember(dto);
        // jpa가 특정 엔티티의 변경을 자동으로 인지하고 변경사항을 DB에 반영하는 것이 dirtyChecking(변경 감지)
        // authorRepository.save(member
        // );  // 그래서 save 안해줘도 됨. 대신 @Transactional은 반드시 붙어있어야 함
    }

    public MemberResDto myInfo(){
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
}

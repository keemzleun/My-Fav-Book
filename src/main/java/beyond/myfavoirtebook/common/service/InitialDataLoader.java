//package beyond.myfavoirtebook.common.service;
//
//import beyond.myfavoirtebook.member.domain.Role;
//import beyond.myfavoirtebook.member.dto.MemberSaveReqDto;
//import beyond.myfavoirtebook.member.repository.MemberRepository;
//import beyond.myfavoirtebook.member.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//// CommandLineRunner를 상속함으로서 해당 컴포넌트가 스트링 빈으로 등록되는 시점(서버가 켜질 때)에 run메서드 실행
//@Component
//public class InitialDataLoader implements CommandLineRunner {
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if(memberRepository.findByEmail("admin@naver.com").isEmpty()){
//            memberService.memberCreate(MemberSaveReqDto.builder()
//                    .name("admin")
//                    .email("admin@naver.com")
//                    .password("12341234")
////                    .role(Role.ADMIN)
//                    .build());
//        }
//    }
//}

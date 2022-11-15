package springjpa.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springjpa.order.domain.Member;
import springjpa.order.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    /**
     * @return null이면 로그인 실패
     */
    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId).get();  // 해당 loginId로 member 가져오기 > member의 password랑 받은 password가 같으면 뭘 리턴해
        if(member.getPassword().equals(password)) {
            return member;
        } else {
            return null;
        }
    }
}
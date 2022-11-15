package springjpa.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springjpa.order.controller.session.MemberSession;
import springjpa.order.controller.session.SessionConst;
import springjpa.order.domain.Member;
import springjpa.order.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    // 아래 코드 대신 @Slf4j 사용
    //Logger log = LoggerFactory.getLogger(getClass());
    private final MemberRepository memberRepository;

    //@RequestMapping("/")
    public String home(){
        log.info("home controller");
        return "home";
    }

    // 로그인이 된 사용자와 로그인이 안된 사용자의 화면이 다르게 보여야 함.

    // 쿠키를 사용한 로그인 화면처리
//    @GetMapping("/")
    public String homeLogin( // 로그인 안한 사용자도 들어오므로 required = false임
            @CookieValue(name="memberId", required = false) Long memberId, // Cookie 가 Long 으로 자동 변환??? : Long memberId = Cookie cookie
            Model model) {
        //로그인 안된 사용자
        System.out.println("memberId : " + memberId); // 이 값 못가져오는 이유 확인할것

        if (memberId == null) {
            System.out.println("로그인안한사용자");
            return "home";
        }
        //로그인된 사용자

        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            System.out.println("로그인안한사용자");
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    // 세션을 사용한 로그인 화면처리
    @GetMapping("/")
    public String sessionLogin(HttpServletRequest request, Model model) {
        //로그인 안된 사용자
        HttpSession session = request.getSession(false);// 세션이 있으면 있는 세션 반환, 없으면 null 반환
        if (session == null) {
            System.out.println("로그인안한사용자");
            return "home";
        }

        //로그인된 사용자                  casting
        MemberSession memberSession = (MemberSession)session.getAttribute(SessionConst.MEMBER_SESSION);

        if (memberSession == null) {
            System.out.println("로그인안한사용자");
            return "home";
        }
        model.addAttribute("memberSession", memberSession);    // username, loginId
        return "loginHome";
    }
}

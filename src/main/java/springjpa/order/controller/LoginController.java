package springjpa.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springjpa.order.controller.session.MemberSession;
import springjpa.order.controller.session.SessionConst;
import springjpa.order.domain.Member;
import springjpa.order.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "login/loginMemberForm";
    }

    // 쿠키 방식의 로그인 처리
//    @PostMapping("/login")
//    public String login(@Validated @ModelAttribute LoginForm form, BindingResult result, HttpServletResponse response){ // 사이즈나 값 등에 대해 validation 된 내용에 대해 작업
//        if (result.hasErrors()){
//            return "login/loginMemberForm";
//        }
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        if(loginMember == null){ // global 오류 처리
//            result.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginMemberForm";
//        }
//
//        // 로그인 성공처리 Todo - 쿠키를 만들어서 클라이언트에 전달해줘야 함.
//
//        //쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료), 쿠키의 이름은 memberId 이고 값은 member_id 임
//        //로그인 시 browser의 response header 에 set-cookie:memberId=1 가 있는지 확인
//        //그 이후의 request header에 cookie 값이 있는지 확인 / application 의 storage 아래 cookies 확인
//        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
//        idCookie.setPath("/");  // /member /items /
//        response.addCookie(idCookie);
//
//        return "redirect:/";
//    }

    // 세션을 활용한 로그인 처리 : Httpsession, HttpServletRequest 이용
//    @PostMapping("/login")
//    public String sessionLogin(@Validated @ModelAttribute LoginForm form, BindingResult result, HttpServletRequest request) { // 사이즈나 값 등에 대해 validation 된 내용에 대해 작업
//        if (result.hasErrors()) {
//            return "login/loginMemberForm";
//        }
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        if (loginMember == null) { // global 오류 처리
//            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginMemberForm";
//        }
//
//        // 세션에 저장할 정보 : membersession
//        MemberSession memberSession = new MemberSession();
//        memberSession.setLoginId(loginMember.getLoginId());
//        memberSession.setUsername(loginMember.getUsername());
//
//        // 세션이 있으면 있는 세션을 반환, 없으면 생성해서 반환
//        HttpSession session = request.getSession(true);
//        session.setAttribute(SessionConst.MEMBER_SESSION, memberSession);   // session에 값을 할당
//
//        return "redirect:/";
//    }
    @PostMapping("/login")
    public String sessionLoginRedirect(@Validated @ModelAttribute LoginForm form,
                                       @RequestParam(defaultValue = "/") String redirectUrl, BindingResult result, HttpServletRequest request) { // 사이즈나 값 등에 대해 validation 된 내용에 대해 작업
                                            // redirectUrl = "/orders" => "/login?=redirectUrl" or 안 들어오면 "/"

        if (result.hasErrors()) {
            return "login/loginMemberForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if (loginMember == null) { // global 오류 처리
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginMemberForm";
        }

        // 세션에 저장할 정보 : membersession
        MemberSession memberSession = new MemberSession();
        memberSession.setLoginId(loginMember.getLoginId());
        memberSession.setUsername(loginMember.getUsername());

        // 세션이 있으면 있는 세션을 반환, 없으면 생성해서 반환
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.MEMBER_SESSION, memberSession);   // session에 값을 할당

        System.out.println(redirectUrl); // sendRedirect("/login?redirectUrl="+requestURI) => 대소문자 맞춰주기
        return "redirect:" + redirectUrl; // "/" or "/orders", "/items/new" 등
    }

    // 쿠키를 사용한 로그아웃 처리
//    @GetMapping("/logout")
//    public String logout(HttpServletResponse response) {
//        expireCookie(response, "memberId");
//        return "redirect:/";
//    }

    // 세션을 사용한 로그아웃 처리
    @GetMapping("/logout")
    public String sessionLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

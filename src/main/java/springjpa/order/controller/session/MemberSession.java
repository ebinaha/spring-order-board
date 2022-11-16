package springjpa.order.controller.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberSession {
    // 최소한의 정보만 세션에 담음
    private String loginId;
    private String username;
    private Long id;
}

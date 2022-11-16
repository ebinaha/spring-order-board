package springjpa.order.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
@Embeddable // jpa의 내장 타입 즉, 어딘가에 내장 될 수 있다는 것을 의미함
@Getter
public enum OrderStatus {
    // 주문의 상태 : [ORDER,CANCEL]    ===> 방법 알아보기
    ORDER,

    // 발생할 수 있는 모든 키를 넣음 : enum
    CANCEL,

}

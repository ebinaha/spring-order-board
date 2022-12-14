package springjpa.order.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")    //원테이블 전략?
@Getter
@Setter

public class Book extends Item{ // 상품 공통사항 추상인터페이스로 상속 받음
    @Column(length = 60)
    private String author;
    @Column(length = 13)
    private String isbn;
}

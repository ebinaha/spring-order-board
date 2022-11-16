package springjpa.order.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable // jpa의 내장 타입 즉, 어딘가에 내장 될 수 있다는 것을 의미함
@Getter
public class Address {
    @Column(length = 20)
    private String city;
    @Column(length = 255)
    private String street;
    @Column(length = 6)
    private String zipcode;

    protected Address(){
    }

    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

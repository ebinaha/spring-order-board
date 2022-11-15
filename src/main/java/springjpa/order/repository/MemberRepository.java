package springjpa.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springjpa.order.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository  // 등록해놓으면 component scan에 의해 스프링빈으로 자동 등록됨
@RequiredArgsConstructor
public class MemberRepository {
//    jpa를 사용하는 경우에는 Entity manager가 필요함\
//    작성 후 Control + shift + T 를 누르면 자동으로 테스트 코드 생성됨.
//    entity manager를 통한 명령은 항상 transaction 안에서 이루어져야 한다.
    //@PersistenceContext // spring이 entity manger를 만들어서 injection 해준다.
    //@requiredArgsConstructor를 사용하면 PersistenceContext 또는 autowired를 사용한 것과 동일한 작업을 자동으로 해준다.

    private final EntityManager em;

    // Member에 입력한 값이 들어있는 상태
    public Long save(Member member){
        em.persist(member);     // record insert(생성) : member 내용 저장
        return member.getId();
    }

    public Member findById(Long id){
        Optional<Member> member = Optional.ofNullable(em.find(Member.class, id));  // id를 가져와서 넘겨줌, 값이 없을 수도 있기 때문에 Optional, null이면 리스트[]로 반환, null이 아니면 [{}] 객체를 리스트에 담음
        return member.get();    // list 형태이기 때문에 하나의 값을 가져옴 : get();

    }
    public List<Member> findAll(){
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();// member 형태로 반환되는 쿼리의 결과를 list로 가져옴
        return members;
    }

    // 기존에 존재하는 loginId가 있는지 확인
    public Optional<Member> findByLoginId(String loginId){
        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)  //매개변수로 받아온 loginId랑 같음
                .setParameter("loginId", loginId)   // 뒤의 loginId 가 :loginId에 들어감
                .getResultList()
                .stream()
                .filter(m->m.getLoginId().equals(loginId))
                .findFirst();


    }



}


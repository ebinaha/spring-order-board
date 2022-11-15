package springjpa.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpa.order.domain.item.Item;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    /**
     * 아이템 저장
     */
    public void save(Item item){
//        em.persist(item);
        if (item.getId() == null){
            em.persist(item);
        } else {
            em.merge(item);     // 다른 값에 대해 수정이 없는 부분이 null로 저장될 수 있음
        }

    }

    /**
     * 아이템 1개 찾기(id 기준)
     * @return
     */
    public Item findOne(Long id){
        return Optional.ofNullable(em.find(Item.class, id)).get(); //[{}] : {}만 가져오기 위해 get()
                            //NullpointException 일어나지 않게 nullable 처리
    }

    /**
     * 아이템 전체 찾기
     * @return
     */
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}

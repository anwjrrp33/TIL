# 리포지터리와 모델 구현

## JPA를 이용한 리포지터리 구현
* 도메인 모델과 리포지터리를 구현할 떄 선호하는 기술을 꼽으면 JPA를 들 수 있다.
* 데이터 보관소로 RDBMS를 사용할 때 객체 기반의 도메인 모델과 관계형 데이터 모델 간의 매핑을 처리하는 기술로 ORM 만한 것이 없다.

### 모듈 위치
* 리포지터리 인터페이스는 애그리거트와 같이 도메인 영역에 속하고, 리포지터리를 구현한 클래스는 인프라스트럭처 영역에 속한다.
<br/><img src="./그림 4.1.png">

### 리포지터리 기본 기능 구현
* 리포지터리가 제공하는 기본 기능
    * ID로 애그리거트 조회하기
    * 애그리거트 저장하기
    ```
    public interface OrderRespository {
        Order findById(OrderNo no);
        void save(Order order);
    }
    ```
* 인터페이스는 애그리거트 루트를 기준으로 작성한다.
* 애그리거트를 조회하는 기능의 이름을 작성할 때 널리 사용되는 규칙은 `findBy프로퍼티이름(프로퍼티 값)` 형식이다.
* 애그리거트를 조회할 때 존재하지 않으면 null을 리턴하는데 null을 사용하고 싶지 않으면 Optional을 사용한다.
```
Optional<Order> findById(OrderNo no);
```
* 인터페이스를 구현한 클래스는 JPA의 EntityManger를 이용해서 기능을 구현한다.
```
// 스프링 데이터 JPA를 사용하지 않은 코드로 실질적으로 리포지터리 인터페이스를 구현한 클래스를 직접 작성할 일은 거의 없다.
@Repository
public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findById(OrderNo id) {
        return entityManager.find(Order.class, id); 
    }
    
    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }
}
```
* 애그리거트를 수정한 결과를 저장소에 반영하는 메서드를 추가할 필요는 없는데 JPA에서는 따로 변경 사항을 저장하지 않아도 [더티 체킹](https://jojoldu.tistory.com/415)을 통해서 `변화가 있는 모든 엔티티 객체` 데이터베이스에 자동 반영해준다.
* ID가 아닌 다른 조건으로 애그리거트를 조회할 때는 findBy 뒤에 조건 대상이 되는 프로퍼티 이름을 붙인다.
* ID 외에 다른 조건으로는 애그리거트를 죄회할 때에는 JPA의 Criteria나 JPQL을 사용할 수 있다
* 애그리거트의 삭제 메서드는 애그리거트 객체를 파라미터로 전달 받는다.

## 스프링 데이터 JPA를 이용한 리포지터리 구현
* 스프링과 JPA를 함꼐 사용할 때는 스프링 데이터 JPA를 사용한다.
* 지정한 규칙에 맞게 인터페이스를 정의하면 구현체를 만들어 스프링 Bean으로 등록해준다.
```
// 인터페이스 등록
public interface OrderRepository extends Repository<Order, OrderNo> {
	Optional<Order> findById(OrderNo id);
    
    void save(Order order);
}
```
```
// 코드 주입
@Service
public class CancelOrderService {
	private OrderRepository orderRepository;
    
    public cancelOrderService(OrderRepository orderRepository, ...) {
    	this.orderRepository = orderRepository;
        ..
    }
    
    @Transactional
    public void cancel(OrderNo orderNo, Canceller canceller) {
    	Order order = orderRepository.findById(orderNo)
        	.orElseThrow(() -> new NoOrderException());
            
       if(!cancelPolicy.hasCancellationPermission(order, canceller)) {
       		throw new NoCancellablePermission();
       }
       order.cancel();
    }
}
```
* 저장
```
Order save(Order entity)
void save(Order entity)
```
* 식별자 조회
```
Order findById(OrderNo id)
Optional<Order> findById(OrderNo id)
``` 
* 목록 조회
```
List<Order> findByOrderer(Orderer orderer)
// 중첩 프로퍼티
List<Order> findByOrderMemberId(MemberId memberId)
``` 
* 삭제
```
void delete(Order order)
void deleteById(OrderNo id)
```

## 매핑 구현
### 엔티티와 밸류 기본 매핑 구현
* 애그리거트와 JPA 매핑을 위한 기본 규칙은 다음과 같다.
    * 애그리거트 루트는 엔티티이므로 @Entity 로 매핑 설정한다.
* 한 테이블에 엔티티와 밸류 데이터가 같이 있다면
    * 밸류는 @Embeddable 로 매핑 설정한다.
    * 밸류 타입 프로퍼티는 @Embedded 로 매핑 설정한다.

<img src="./그림 4.2.png">

```
// 루트 엔티티는 JPA의 @Entity로 매핑한다.
@Entity
@Table(name="purchase_order")
public class Order {
	...
}
```
```
// 밸류는 @Embeddable로 매핑한다.
@Embeddable
public class Orderer {
	
    // MemberId에 정의된 컬럼 이름을 변경하기 위해
    // @AttributeOverride 애너테이션 
    @Embedded
    @AttributeOverrides(
    	@AttributeOverride(name = "id", Column = @Column(name="orderer_id"))
    )
	private MemberId memberId;
    
    @Column(name = "orderer_name")
    private String name;
}
```
```
// Shippinginfo 밸류는 또 다른 밸류를 포함하고 매핑 설정과 다른 칼럼 이름을 사용하기 위해서 @AttributeOverrids 애너테이션을 사용한다.
@Embeddable
public class ShippingInfo {
	@Embedded
    @AttributeOverrids({
    	@AttributeOverride(name = "zipCode", column= @Column(name="shipping_zipcode")),
        @AttributeOverride(name = "address1", column= @Column(name="shipping_addr1")),
        @AttributeOverride(name = "address2", column= @Column(name="shipping_addr2"))
    })
    private Address address;
    
    @Column(name = "shipping_message")
    private String message;
    
    @Embedded
    private Receiver receiver;
}
```

### 기본 생성자
* 엔티티와 밸류 생성자는 객체를 생성할 때 필요한 것을 전달받는다.
```
// 불변 타입이면 생성 시점에 필요한 값을 모두 전달받으므로 값을 변경하는 set 메서드를 제공하지 않는다.
public class Receiver {
	private String name;
    private String phone;
    
    public Receiver(String name, String phone) {
    	this.name = name;
        this.phone = phone;
    }
}
```
* JPA에선 @Entity와 @Embeddable 클래스를 매핑할려면 기본 생성자를 제공해야하는데 DB에서 데이터를 읽어와 매핑된 객체를 생성할 때 기본 생성자를 사용해서 객체를 생성해야 하기 때문이다.
```
// 기본 생성자는 JPA 프로바이더가 객체 생성 시에만 사용한다.
// 다른 코드에서 기본 생성자를 사용하지 못하도록 접근 제한자를 protected로 선언한다.
@Embeddable
public class Receiver {
	@Column(name = "receiver_name")
    private String name;
    @Column(name = "receiver_phone")
    private String phone;
    
    protected Receiver() {}	// JPA 적용을 위한 기본 생성자
    
    public Receiver(String name, String phone) {
    	this.name = name;
        this.phone = phone;
    }
}
```

### 필드 접근 방식 사용

# 스프링 데이터 JPA를 이용한 조회 기능

## 시작에 앞서
* `CQRS`는 명령 모델과 조회 모델을 분리하는 패턴이다.
    * `명령 모델`은 상태를 변경하는 기능을 구현할 때 사용한다.
    * `조회 모델`은 데이터를 조죄하는 기능을 구현할 때 사용한다.
* 도메인 모델은 주문 취소, 배송지 변경과 같이 상태를 변경할 때 주로 사용되는데 도메인 모델은 명령 모델로 주로 사용한다.
* 정렬, 페이징, 검색 조건 지정과 같은 주문 목록, 상품 상세와 같은 조회 기능은 조회 모델을 구현할 때 주로 사용한다.

## 검색을 위한 스펙
* 검색 조건이 고정되어 있다면 기능을 특정 조건 조회 기능을 추가하면 된다.
```
public interface OrderDataDao {
    Optional<OrderData> findById(OrderNo id);
    List<OrderData> findByOrderer(String orderId, Data fromData, Data toData);
    ...
}
```
* 검색 조건을 다양하게 조합해야 할 때 사용할 수 있는 것이 스펙인데 스펙은 애그리거트가 특정 조건을 충족하는지 검사할 때 사용하는 인터페이스다.
```
public interface Speficiation<T> {
    public boolean isSatisfiedBy(T egg);
}
```
* agg 파라미터는 검사 대상이 되는 객체이며 리포지터리에서 사용하면 애그리거트 루트가 되고 스펙을 DAO 에 사용하면 검색 결과로 리턴할 데이터 객체가 된다.
```
public class OrdererSpec implements Specification<Order> {

  private String ordererId;

  public boolean isSatisfiedBy(Order agg) {
    return agg.getOrdererId().getMemberId().getId().equals(ordererId);
  }

}
```
* 리포지터리나 DAO는 검색 대상을 걸러내는 용도로 스펙을 사용한다.
```
public class MemoryOrderRepository implements OrderRepository {
    public List<Order> findAll(Specification<Order> spec) {
        List<Order> aUOrders = findAll();
        return aUOrders.stream()
            .filter(order -> spec.isSatisfiedBy(order))
            .toList();
    }
}
```
* 모든 애그리거트 객체를 메모리에 보관하기도 어렵고 설사 메모리에 다 보관할 수 있다 하더라도 조회 성능에 심각한 문제가 발생하기 때문에 실제 스펙은 사용하는 기술에 맞춰 구현하게 된다.

## 스프링 데이터 JPA를 이용한 스펙 구현
* 스프링 데이터 JPA는 검색 조건을 표현하기 위한 인터페이스인 Specification을 제공한다.
* 제네릭 타입 파라미터 T는 JPA 엔티티 타입을 의미한다.
```
public interface Specification<T> extends Serializable {
  // not, where, and, or 메서드 생략

  @Nullable
  Predicate toPredicate(Root<T> root, CriteriaQuery query, CriteriaBuilder cb);
}
```
* toPredicat() 메서드는 JPA 크리테리아(Criteria) API에서 조건을 표현하는 Predicate를 생성한다.
* OrdererIdSpec 클래스는 Specification<OrderSummary> 타입을 구현하므로 OrderSummary에 대한 검색 조건을 표현한다.
* toPredicate() 메서드는 ordererId 프로퍼티 값이 생성자로 전달받은 ordererId와 동일한지 비교하는 Predicate를 생성한다.
```
// 스펙 인터페이스를 구현한 클래스 예시
public class OrdererldSpec implements Specification<OrderSummary> {
    
    private String ordererld;
    
    public OrdererIdSpec(String ordererld) {
        this.ordererld = ordererld;
    }

    ©Override
    public Predicate toPredicate(Root<OrderSummary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get(OrderSummary_.ordererId), ordererld);
    }
}
```
* 스펙 구현 클래스를 개별적으로 만들지 않고 별도 클래스에 스펙 생성 기능을 모아도 된다.
```
// 스펙 생성 기능을 별도 클래스에 모은 예시
public class OrderSummarySpecs {
    public static Specification<OrderSummary> ordererId(String ordererld) {
        return (Root<OrderSummary> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.equal(root.<String>get("ordererld"), ordererld);
    }

    public static Specification<OrderSummary> orderDateBetween( LocalDateTime from, LocalDateTime to) {
        return (Root<OrderSummary> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.between(root.get(OrderSummary_.orderDate), from, to);
    } 
}
```
```
// 스펙 생성 기능 클래스를 이용한 코드
Specification<OrderSummary> betweenSpec = OrderSummarySpecs.orderDateBetween(from, to);
```

## 리포지터리/DAO에서 스펙 사용하기
* 스펙을 충족하는 엔티티를 검색하고 싶다면 findAll() 메서드를 사용하면 된다.
```
// 메서드를 사용하는 예시
public interface OrderSummaryDao extends Repository<OrderSummary, String> {
    List<OrderSummary> findAll.(Specification<OrderSummary> spec);
}
```
* 스펙 구현체를 사용하면 특정 조건을 충족하는 엔티티를 검색할 수 있다.
```
// 코드 단위로 사용하는 예시
// 스펙 객체를 생성하고
Specification<OrderSummary> spec = new OrdererIdSpec("user1"); 
// findAllO 메서드를 이용해서 검색
List<OrderSummary> results = OrderSummaryDao.findAll(spec);
```

## 스펙 조합
* 스프링 데이터 JPA가 제공하는 스펙 인터페이스는 스펙을 조합할 수 있는 `and`와 `or`를 제공하고 있다.
```
// and와 or 메서드를 제공하는 스펙 인터페이스
public interface Specification<T> extends Serializable {

  default Specification<T> and(@Nullable Specification<T> other) { ... }
  default Specification<T> or(@Nullable Specification<T> other) { ... }
  
  @Nullable
  Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}
```
* and()와 or() 메서드는 기본 구현을 가진 디폴트 메서드이다.
* `and()` 메서드는 두 스펙을 모두 충족하는 조건을 표현하는 스펙을 생성하고 `or()` 메서드는 두 스펙 중 하나 이상 충족하는 조건을 표현하는 스펙을 생성한다.
```
// and()와 or()의 사용 예시
Specification<OrderSummary> specl = OrderSummarySpecs.ordererId("userl"); 
Specification<OrderSummary> spec2 = OrderSummarySpecs.orderDateBetween(
    LocaWateTime.of(2022, 1, 1, 0, 0, 0),
    LocalDateTime.of(2022, 1, 2, 0, 0, 0)); 
Specification<OrderSummary> spec3 = spec1.and(spec2);
Specification<OrderSummary> spec3 = spec1.or(spec2);
```
```
// 체이닝을 통한 불필요한 변수 사용 제거
Specification<OrderSummary> spec = OrderSummarySpecs.ordererId("user1")
    .and(OrderSummarySpecs.orderDateBetween(from, to));
```
* `not()` 메서드 또한 제공하는데 조건을 반대로 적용할 때 사용한다.
```
Specification<OrderSummary> spec = Specification.not(OrderSummarySpecs.ordererld(user1"));
```
* null 가능성이 있는 스펙과 다른 스펙을 조합해야하면 NullPointerException이 발생할 수 있는데 코드를 통해서 null을 검사하면 매우 힘들기 때문에 `where()` 메서드를 사용하면 해당 문제를 방지할 수 있다.
```
Specification<OrderSummary> spec = Specification.where(createNull.ableSpec()).and(createOtherSpec());
```

## 정렬 지정하기
* 스프링 데이터 JPA는 두 가지 방법을 사용해서 정렬을 지정할 수 있다.
    * 메서드 이름에 OrderBy를 사용해서 정렬 기준 지정
    * Sort를 인자로 전달 
```
// 특정 프로퍼티를 조회하는 find 메서드는 이름 뒤에 OrderBy를 사용해서 정렬 순서를 지정하는 코드 예시
public interface OrderSummaryDao extends Repository<OrderSummary, String> { 
    List<OrderSummary> findByOrdererIdOrderByNumberDesc(String ordererld);
}
```

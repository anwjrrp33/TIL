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
// 스펙 생성 기능을 별도 클래스에 모은 예

```
# 10 이벤트

## 10.1 시스템 간 강결합 문제
구매를 취소하면 환불을 처리해야 하는데 환불 기능을 실행하는 주체는 주문 도메인 엔티티가 된다.<br>
환불 기능은 도메인 서비스를 파라미터로 전달받아서 도메인 기능에서 도메인 서비스를 실행해서 처리할 수도 있고, 응용 서비스에서 처리할 수도 있다.<br>
```
// 도메인 서비스에서 처리
public class Order {
    ...
    // 외부 서비스를 실행하기 위해 도메인 서비스를 파라미터로 전달받음
    public void cancel(RefundService refundservice) { 
        verifyNotYetShipped();
        this.state = Orderstate.CANCELED;
        this.refundstatus = State.REFUND_STARTED; 
        try {
            refundservice.refund(getPaymentld());
            this.refundstatus = State.REFUND_COMPLETED; 
        } catch(Exception ex) {
            ??? 
        }
    }
    ...
```
```
// 응용 서비스에서 처리
public class CancelOrderService { 
    private Refundservice refundservice;
    
    @Transactional
    public void cancel(OrderNo orderNo) {
        Order order = findOrder(orderNo); 
        order.cancel();

        order.refundStarted();
        try {
            refundservice.refund(order.getPaymentId());
            order.refundCompleted(); 
        } catch(Exception ex) {
            ??? 
        }
    }
    ...
```

보통 결제 시스템은 외부에 존재하므로 외부에 있는 결제 시스템이 제공하는 환불 서비스를 호출하는데 이때 두 가지 문제가 발생한다.<br>

첫 번째로 외부 서비스가 정상이 아닐 경우 환불 기능을 실행 중 익셉션이 발생하면 롤백할지 커밋할지 애매하다.<br>
외부의 환불 서비스를 실행하는 과정에서 익셉션이 발생하면 환불에 실패했으므로 주문 취소 트랜잭션을 롤백 하는 것이 맞아 보이지만 반드시 트랜잭션을 롤백 해야 하는 것은 아니며, 주문은 취소 상태로 변경하고 환불만 나중에 다시 시도하는 방식으로 처리할 수도 있다.<br>

두 번째 문제는 외부 시스템의 응답 시간이 길어질 때 대기 시간이 길어지는 성능의 문제이다.<br>
환불 처리 기능이 대기되는 시간만큼 주문 취소 기능은 그만큼 대기 시간이 증가하는데, 외부 서비스 성능에 직접적인 영향을 받게된다.<br>

<img src="./그림 10.1.png">

그 외에 문제로 도메인 객체에 서비스를 전달하면 설계상의 문제가 나타나는데 주문 로직과 결제 로직이 섞이는 문제가 발생한다.<br>

<img src="./그림 10.2.png">

도메인 객체에 서비스를전달할시 또 다른 문제는 기능을 추가할 때 발생하는데 주문을 취소한 뒤 환불뿐만 아니라 취소했다는 내용을 통지한다면 환불 도메인 서비스와 동일하게 파라미터로 통지 서비스를 받도록 구현하면 앞서 언급한 로직이 섞이는 문제가 커지고 트랜잭션 처리가 더 복잡해진다.
```
public class Order {
    // 기능을 추가할 때마다 파라미터가 함께 추가되면
    // 다른 로직이 더 많이 섞이고, 트랜적션 처리가 더 복잡해진다.
    public void cancel(RefundService refundservice, NotiService notiSvc) {
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
        ...
        // 주문+결제+통지 로직이 섞임
        // refundService는 성공하고, notiSvc는 실패하면?
        // refundService와 notiSvc 중 무엇을 먼저 처리하나?
    }
```

지금까지 언급한 문제가 발생하는 이유는 주문 바운디드 컨텍스트와 결제 바운디드 컨텍스트간의 강결합 때문이다.<br>
이런 강결합은 이벤트를 통해서 없앨 수 있는데 특히 비동기 이벤트를 사용하면 사용하면 두 시스템 간의 결합을 크게 낮출 수 있다.<br>
한번 익숙해지면 모든 연동을 이벤트와 비동기로 처리하고 싶을 정도로 강력하고 매려적인 것이 이벤트다.<br>

## 10.2 이벤트 개요
이 절에서 사용하는 `이벤트(event)`라는 용어는 `과거에 벌어진 어떤 것`을 뜻한다.<br>
* 사용자가 암호를 변경한 것을 암호를 변경했다는 이벤트가 벌어졌다고 할 수 있다.
* 주문을 취소하면 주문을 취소했다는 이벤트 발생했다고 할 수 있다.

이벤트가 발생했다는 것은 상태가 변경됐다는 것을 의미한다.<br>
이벤트는 발생하는 것에서 끝나지 않고, 이벤트가 발생하면 그 이벤트에 반응하여 원하는 동작을 수행하는 기능을 구현한다.<br>
보통 '~할 때', '~가 발생하면', '만역 ~하면'과 같은 요구사항은 도메인 상태 변경과 관련된 경우가 많고 이런 요구사항을 이벤트를 이용해서 구현할 수 있다.<br>
* 주문을 취소할 때 이메일을 보낸다라는 요구사항에 주문을 취소할 때는 주문이 취소 상태로 바뀌는 것의 의미하기 때문에 주문 취소됨 이벤트를 활용할 수 있다.

### 10.2.1 이벤트 관련 구성요소
도메인 모델에 이벤트를 도입하려면 네 개의 구성요소를 구현해야 한다.
* 이벤트
* 이벤트 생성 주체
* 이벤트 디스패처(퍼블리셔)
* 이벤트 핸들러(구독자)

<img src="./그림 10.3.png">

도메인 모델에서 이벤트 생성 주체는 엔티티, 밸류, 도메인 서비스와 같은 도메인 객체 인데 도메인 로직을 싱행해서 상태가 바뀌면 관련 이벤트를 발생시킨다.<br>

`이벤트 핸들러`는 이벤트 생성 주체가 발생한 이벤트에 반응하는데 생성 주체가 발생한 이벤트를 전달받아 이벤트에 담긴 데이터를 이용해서 원하는 기능을 실행한다.<br>

이벤트 생성 주체와 이벤트 핸들러를 연결해 주는 것이 `이벤트 디스패처(Dispatcher)`라고 하는데 이벤트 생성 주체는 이벤트를 생성해서 디스패처에 이벤트를 전달한다.<br>
이벤트 디스패처는 전달받은 이벤트를 처리할 수 있는 핸들러에 이벤트를 전파한다.<br>
이벤트 디스패처의 구현 방식에 따라 이벤트 생성과 처리를 동기나 비동기로 실행하게 된다.<br>

### 10.2.2 이벤트의 구성
이벤트는 발생한 이벤트에 대한 정보를 담는다.
* 이벤트 종류: 클래스 이름을 이벤트 종류를 표현
* 이벤트 발생 시간
* 추가 데이터: 주문번호, 신규 배송지 정보 등 이벤트와 관련된 정보

```
// 이벤트를 위한 클래스
public class ShippinglnfoChangedEvent {
    private String orderNumber;

    private long timestamp;

    private Shippinginfo newShippinglnfo;

    // 생성자, getter
}
```
Changed라는 과거 시제를 사용했는데 이벤트는 현재 기준으로 과거에 벌어진 것을 표현하기 때문에 이벤트 이름에는 과거 시제를 사용한다.<br>

```
// 이벤트를 발생하는 주체 클래스
// 배송지 정보를 변경한 뒤 이벤트를 발생시킨다.
public class Order {
    public void changeShippingInfo(ShippingInfo newShippinglnfo) {
        verifyNotYetShipped();
        setShippinglnfo(newShippinglnfo);
        // Events.raise()는 디스패처를 통해 이벤트를 전파하는 기능을 제공
        Events.raise(new ShippinglnfoChangedEvent (number, newShippinglnfo));
    }
    ...
```

```
// 이벤트를 처리하는 핸들러 클래스
// 디스패처로부터 이벤트를 전달받아 이벤트를 처리한다.
public class ShippinglnfoChangedHandler {
    
    @EventListener(ShippingInfoChangedEvent.class)
    public void handle(ShippingInfoChangedEvent evt) {
        shippingInfoSynchronizer.sync(
            evt.getOrderNumber(),
            evt.getNewShippinglnfo());
    }
```

이벤트는 이벤트 핸들러가 작업을 수행하는데 필요한 데이터를 담아야 하는데 그렇다고 이벤트와 관련 없는 데이터를 포함할 필요는 없다.<br>

### 10.2.3 이벤트 용도
이벤트는 크게 두가지 용도로 쓰인다.
* 트리거
    * 도메인의 상태가 바뀔때 후처리가 필요할 경우 후처리를 실행하기 위한 트리거로 이벤트를 사용할 수 있다.
    * 예시로 주문을 취소할 때 환불 처리를 위한 주문 취소 이벤트로 사용하거나 예매 결과를 문자로 통지할 때 예매 도메인이 완료 에빈트를 발생 시키면 문자를 발송시키는 등의 방식으로 구현 수 있다.
    <br><img src="./그림 10.4.png">

* 타 시스템 간의 데이터 동기화
    * 배송지를 변경하면 외부 배송 서비스에 바뀐 배송지 정보를 전송해야 하는데 주문 도메인이 배송지 변경 이벤트를 발생시키고 이벤트 핸들러가 외부 배송서비와 배송지 정보를 동기화하면 된다.

### 10.2.4 이벤트 장점
이벤트를 사용하면 서로 다른 도메인 로직이 섞이는 것을 방지할 수 있다.<br>

<img src="./그림 10.5.png">

이벤트 핸들러를 사용하면 기능 확장도 용이하다.<br>
기능을 확장해도 핸들러 내에서만 구현하면 되기 때문에 도메인 로직은 수정할 필요가 없다.<br>

<img src="./그림 10.6.png">

## 10.3 이벤트, 핸들러, 디스패처 구현
이벤트와 관련된 코드는 다음과 같다.
* 이벤트 클래스: 이벤트를 표현한다.
* 디스패처: 스프링이 제공하는 ApplicationEventPublisher 를 이용한다.
* Events: 이벤트를 발생한다.
* 이벤트 핸들러: 이벤트를 수신해서 처리한다.

### 10.3.1 이벤트 클래스
이벤트 자체를 위한 상위 타입은 존재하지 않는데 원하는 클래스를 이벤트로 사용하면 된다.<br>
이벤트 클래스 이름을 결정할 때는 과거 시제를 사용해야하고 보통 이름 뒤에 Event 붙여서 명시적으로 표현할 수도 생략할 수도 있다.<br>

이벤트 클래스는 이벤트를 처리하는데 필요한 최소한의 데이터만 포함해야 한다.<br>
```
public class OrderCanceledEvent {
  // 이벤트는 핸들러에서 이벤트를 처리하는 데 필요한 데이터를 포함한다.
  private String orderNumber;

  public OrderCanceledEvent(String number) {
    this.orderNumber = number;
  }

  public String getOrderNumber() {
    return orderNumber;
  }
}
```

모든 이벤트가 공통으로 갖는 프로퍼티가 존재할 경우 관련 상위 클래스를 만들어서 각 이벤트 클래스가 상속받도록 할 수 있다.<br>
```
public abstract class Event {

  private long timestamp;

  public Event() {
    this.timestamp = System.currentTimeMillis();
  }

  public long getTimestamp() {
    return timestamp;
  }
}
```
```
// 발생 시간이 필요한 각 이벤트 클래스는 Event 를 상속받아 구현한다.
public class OrderCanceledEvent extends Event {

  private String orderNumber;

  public OrderCanceledEvent(String number) {
    super();
    this.orderNumber = number;
  }

}
```

### 10.3.2 Events 클래스와 ApplicationEventPublisher
이벤트 발생과 출판을 위해 스프링이 제공하는 ApplicationEventPublisher를 사용한다.<br>
```
// 이벤트를 발생시키기 위한 이벤트 클래스
public class Events {
    private static ApplicationEventPublisher publisher;

    static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Object event) {
        if (publisher != null) {
            publisher.publishEvent(event);
        }
    }
}
```
```
// 의존성 주입을 받기위한 스프링 설정 클래스
@Configuration
public class EventsConfig {

    @Autowired
    private Applicationcontext applicationcontext;
    
    @Bean
    public InitializingBean eventsInitializer() {
        return () -> Events.setPublisher(eventPublisher);
    }
}
```

### 10.3.3 이벤트 발생과 이벤트 핸들러
```
public class Order {

    public void cancel() {
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
        Events.raise(new OrderCanceledEvent(number.getNumber()));
    }
    ...
}
```

이벤트를 처리할 핸들러는 스프링이 제공하는 @EventListenr 애너테이션을 사용해서 구현한다.<br>
ApplicationEventPublisher#publishEvent () 메서드를 실행할 때 Event 타입 객체를 전달하면 클래스 값을 갖는 애너테이션을 찾아서 실행한다.<br>
```
public class OrderCanceledEventHandler {
    private RefundService refundService;

    public OrderCancelOrderService(RefundService refundService) {
        this.refundService = refundService;
    }

    @EventListener(OrderCanceledEvent.class)
    public void handle(OrderCanceledEvent orderCanceledEvent) {
        refundService.refund(event.getOrderNumber());
    }
}
```

### 10.3.4 흐름 정리

<img src="./그림 10.7.png">

1. 도메인기능을실행한다.
2. 도메인 기능은 Events.raise()를 이용해서 이벤트를 발생시킨다.
3. Events.raiseO는 스프링이 제공하는 ApplicationEventPublisher를 이용해서 이벤트를 출판한다.
4. ApplicationEventPublisher @EventListener(이벤트타입.class) 애너테이션이 붙은 메서드를 찾아 실행한다.

코드 흐름을 보면 응용 서비스와 동일한 트랜잭션 범위에서 이벤트 핸들러를 실행하고 있다.<br>
즉, 도메인 상태 변경과 이벤트 핸들러는 같은 트랜잭션 범위에서 실행된다.<br>

## 10.4 동기 이벤트 처리 문제
이벤트를 사용해서 강결합 문제는 해소했지만 외부 서비스에 영향을 받는 문제는 아직 남아 있다.<br>
```
//1. 응용 서비스 코드
@Transactional // 외부 연동 과정에서 익셉션이 발생하면 트랜잭션 처리는? public void public void cancel(OrderNo orderNo) {
    Order order = findOrder(orderNo);
    order.cancel(); // order.cancel()에서 OrderCanceledEvent 발생 
}

// 2. 이벤트를 처리하는 코드
@Service
public class OrderCanceledEventHandler {
    ...생략
    
    @EventListener(OrderCanceledEvent.class) 
    public void handle(OrderCanceledEvent event) {
        // refundservice.refund()가 느려지거나 익셉션이 발생하면?
        refundservice.refund(event.getOrderNumber()); 
    }
}
```

외부 환불 기능이 느려지면 cancel() 메서드도 함께 느려지기 때문에 외부 서비스의 성능 저하가 시스템의 성능 저하로 연결된다.<br>
외부 환불 기능에서 익셉션이 발생하면 실패했다고 반드시 트랜잭션을 롤백 해야 하는지에 대한 문제인데 구매 취소 자체는 처리 하고 환불만 재처리하거나 수동으로도 처리할 수 있다.<br>
외부 시스템 연동으로 발생하는 성능과 트랜잭션 범위 문제의 해소 방법은 이벤트를 비동기로 처리하거나 이벤트와 트랜잭션을 연계하는 것이다.<br>
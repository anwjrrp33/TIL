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

## 10.5 비동기 이벤트 처리
회원 가입 신청 검증을 위한 검증 이메일 발송이나 주문 취소 시 결제 취소가 곧바로 될 필요는 없다 수 초 뒤 또는 수십 초 뒤에 도착해도 문제가 되지 않는데, `A 하면 이어서 B 하라`는 내용을 담고 있는 요구사항은 실제로 `A 하면 최대 언제까지 B 하라`인 경우가 많기 때문에 일정 시간 안에만 후속 조치를 처리하면 되는 경우가 종종 있다.<br>

이벤트를 비동기로 구현할 수 있는 방법은 다양하다.
* 로컬 핸들러를 비동기로 실행하기
* 메시지 큐를 사용하기
* 이벤트 저장소와 이벤트 포워더 사용하기
* 이벤트 저장소와 이벤트 제공 API 사용하기

### 10.5.1 로컬 핸들러 비동기 실행
이벤트 핸들러를 비동기로 실행하는 방법은 이벤트 핸들러를 별도 스레드 실행하는 것으로 스프링이 제공하는 `@Async`어노테이션을 사용하면 된다.
* @EnableAsync 어노테이션을 사용하여 비동기 기능 활성화
* 이벤트 핸들러 메서드에 @Async 어노테이션을 추가

```
@SpringBootApplication 
@EnableAsync
public class ShopApplication {
    public static void main(String[] args) { 
        SpringApplication.run(ShopApplication.class, args);
    } 
}
```
```
@Service
public class OrderCanceledEventHandler {
    @Async
    @EventListener(OrderCanceledEvent.class)
    public void handler(OrderCanceledEvent orderCanceledEvent) {
        refundService.refund(event.getOrderNumber());
    }
}
```

책의 예제에서는 베이스 패키지에서 걸어줬지만 보통은 config 파일로 따로 설정한다.
```
@Configuration
@EnableAsync
public class AsyncConfig {
    ...
}
```

### 10.5.2 메시징 시스템을 이용한 비동기 구현
카프카(Kafka), 래빗MQ(RabbitMQ)와 같은 메세징 시스템 사용하는 것이다.<br>
1. 이벤트가 발생하면 이벤트 디스패처는 이벤트를 메세지 큐에 보낸다.
2. 메세지 큐는 이벤트를 메세지 리스너에 전달한다.
3. 메세지 리스너는 알맞는 이벤트 핸들러를 이용해서 이벤트를 처리한다.

메세지 큐에 저장하는 과정, 메세지 큐에서 이벤트를 읽어와서 처리하는 과정은 별도의 스레드나 프로세스로 처리한다.<br>

<img src="./그림 10.8.png">

필요하다면 이벤트 발생 도메인 기능과 메세지 큐에 이벤트를 저장하는 절차를 한 트랜잭션으로 묶어야 한다.<br>
도메인 기능을 실행한 결과를 DB에 반영하고 발생한 이벤트를 메세지 큐에 저장하는 것을 같은 트랜잭션 범위로 실행하려면 `글로벌 트랜잭션`이 필요하다.<br>

글로벌 트랜잭션은 안전하게 이벤트를 메세지 큐에 전달할수 있는 장점이 있지만 반대로 글로벌 트랜잭션으로 인해 전체 성능이 떨어지는 단점이 있고, 글로벌 시스템을 지원하지 않는 메시징 시스템도 있다.

래빗MQ는 글로벌 트랜잭션 지원을 지원하며 클러스터 고가용성을 지원해서 안정적을 메세지를 전달하고, 카프카는 글로벌 트랜잭션을 지원하진 않지만 다른 메세징 시스템에 비해 높은 성능을 보여준다.

### 10.5.3 이벤트 저장소를 이용한 비동기 처리
이벤트를 일단 DB에 저장한 뒤에 별도 프로그램을 이용해서 이벤트 핸들러에 전달하는 방법도 있다.

<img src="./그림 10.9.png">

<img src="./그림 10.10.png">

API방식과 포워더 방식의 차이점은 이벤트를 전달하는 방식에 있다.
* 포워더
  * 포워더를 이용해서 이벤트를 외부에 전달한다.
  * 이벤트를 어디까지 처리했는지 추적하는 역할이 포워더에 있다.
* API
  * 외부 핸들러가 API 서버를 통해 이벤트 목록을 가져간다.
  * 이벤트를 어디까지 처리했는지 추적하는 역할이 외부 핸들러에 있다.

### 이벤트 저장소 구현
포워더 방식과 API 방식 모두 이벤트 저장소를 사용해서 이벤트를 저장할 저장소가 필요하다.

<img src="./그림 10.11.png">

## 10.6 이벤트 적용 시 추가 고려 사항
1. 이벤트 소스를 EventEntry에 추가할지 여부
    * EvntEntry는 이벤트 발생 주체에 대한 정보가 없기 때문에 특정 주체가 발생한 이벤만 조회하는 기능을 구현할 수 없다.
    * 이벤트만 조회하는 기능을 구현하려면 이벤트에 발생 주체 정보를 추가해야 한다.
2. 포워더에서 전송 실패를 얼마나 허용할 것이냐에 대한 여부
    * 특정 이벤트에서 계속 전송에 실패하면 실패한 이벤트 때문에 나머지 이벤트를 전송할 수 없게된다.
    * 포워더를 구현할 때는 실패한 이벤트의 재전송 횟수 제한을 두어야 한다.
    * 실패 이벤트를 생략하지 않고 실패용 DB나 메세지 큐에 저장하기도 한다.
3. 이벤트 손실에 대한 여부
    * 이벤트 저장소를 사용하는 방식은 이벤트 발생과 저장을 한 트랜잭션으로 처리하기 때문에 성공하면 이벤트가 저장소에 보관된다는 것을 보장할 수 있다.
    * 로컬 핸들러를 이용해서 이벤트를 비동기로 처리할 경우 이벤트 처리에 실패하면 이벤트를 유실하게 된다.
4. 이벤트 순서에 대한 여부
    * 이벤트를 발생 순서대로 외부 시스템에 전달해야 할 경우, 이벤트 저장소를 사용하는것이 좋다.
    * 이벤트 저장소는 일단 저장소에 이벤트를 발생순서대로 저장하고 그 순서대로 이벤트 목록을 제공하기 때문이다.
    * 메시징 시스템은 사용 기술에 따라 이벤트 발생 순서와 메시지 전달 순서가 다를 수도 있다.
5. 이벤트 재처리에 대한 여부
    * 동일한 이벤트를 다시 처리해야 할 때 이벤트를 어떻게 할지 결정해야 한다.
    * 가장 쉬운 방법은 마지막으로 처리한 이벤트의 순번을 기억해 두었다가 이미 처리한 순번의 이벤트가 도착하면 해당 이벤트를 처리하지 않고 무시하는 것이다.
    * 이 외에 이벤트 처리를 멱등으로 처리하는 방봅도 있다.

### 멱등성
`연산을 여러 번 적용해도 결과가 달라지지 않는 성질`을 멱등성(idempotent)이라고 한다.<br>
대표적인 함수로는 abs()가 있는데 abs(x), abs(abs(x)) abs(abs(abs(x)))의 결과가 모두 같다.<br>
이벤트 핸들러가 멱등성을 가지면 시스템 장애로 인한 이벤트 중복이 발생하더라도 결과적으로 동일 상태가 되는데 이벤트 중복 발생이나 중복 처리에 대한 부담을 줄여준다.<br>

### 10.6.1 이벤트 처리와 DB 트랜잭션 고려
이벤트를 처리할 때는 DB 트랜잭션을 함께 고려해야 한다.<br>
이벤트를 동기로 하든 비동기로 하든 이벤트 처리 실패와 트랜잭션 실패를 함께 고려해야 한다.<br>
트랜잭션 실패와 이벤트 처리 실패 모두 고려하면 복잡해지므로 경우의 수를 줄이면 도움이 된다.<br>
경우의 수를 줄이는 방법은 트랜잭션이 성공할 때만 이벤트 핸들러를 실행하는 것이다.<br>
스프링은 @TransactionalEventListener 에너테이션을 지원해서 트랜잭션 상태에 따라 이벤트 핸들러를 실행할 수 있게 한다.
```
@TransactionalEventListener(
        classes = OrderCanceledEvent.class,
        phase = TransactionPhase.AFTER_COMMIT
)
public void handle(OrderCanceledEvent event) {
    refundService.refund(event.getOrderNumber());
}
```
TransactionPhase.AFTER_COMMIT의 경우 트랜잭션 커밋에 성공한 뒤에 핸들러 메서드를 실행한다.<br>
이벤트 저장소로 DB를 사용하는 경우 이벤트 발생 코드와 이벤트 저장 처리를 한 트랜잭션으로 처리하면 동일한 효과를 볼 수 있다.<br>
트랜잭션이 성공할 때만 이벤트 핸들러를 실행하게 되면 트랜잭션 실패에 대한 경우의 수가 줄어 이벤트 처리 실패만 고민하면 된다.<br>
이벤트 특성에 따라 재처리 방식을 결정하면 된다.<br>
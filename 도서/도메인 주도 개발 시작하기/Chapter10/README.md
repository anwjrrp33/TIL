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

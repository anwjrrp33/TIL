# Chapter2 아키텍처 개요

## 2.1 네 개의 영역
* 표현, 응용, 도메인, 인프라스트럭처는 아키텍처를 설계할 떄 출현하는 전혁적인 네 가지 영역이다. 
* [레이어드 아키텍처](https://stylishc.tistory.com/144)라고 표현한다.

### 표현(Presentation) 영역
* 사용자의 요청을 받아 응용 영역에 전달하고 응용 역역 처리 결과를 다시 사용자에게 보여주는 역할을 한다.
* 스프링 MVC 프레임워크가 표현 영역을 위한 기술에 해당한다.
* 웹 애플리케이션에서 표현 영역에서 사용자는 웹 브라우저를 사용하는 사람일 수도 있고 REST API를 호출하는 외부 시스템일 수도 있다.
* HTTP 요청을 응용 영역이 필요로 하는 형식으로 변환해서 응용 영역에 전달하고 응용 영역의 응답을 HTTP 응답으로 변환하여 전송한다.


<img src="./그림 2.1.png">

### 응용(Application) 영역
* 표현 영역을 통해 사용자의 요청을 전달받은 응용 영역은 시스템이 사용자에게 제공해야 할 기능을 구현한다.
* 기능을 구현하기 위해 도메인 영역의 도메인 모델을 사용한다.

주문 취소 기능을 제공하는 응용 역역의 서비스를 예를 들면 아래와 같이 주문 도메인 모델을 사용해 기능을 구현한다. 응용 영역의 서비스는 로직을 직접 수행하기보다 도메인 모델에 로직 수행을 위함하며 아래의 코드 또한 직접 구현하지 않고 Order 객체에 취소 처리를 위임하고 있다.
```
public class CancelOrderService {
    @Transactional
    public void cancelOrder(String orderld) {
        Order order = findOrderByld(orderld);
        if (order = = null) throw new OrderNotFoundException(orderId); 
        order.cancel();
    }
}
```

<img src="./그림 2.2.png">

### 도메인 영역
* 도메인 모델과 도메인의 핵심 로직을 구현한다.

### 인프라스트럭처(Infrastructure) 영역
# 01. 객체, 설계
이론과 실무는 어떤 분야든 초기 단계에선 아무것도 없는 상태인데 이론을 정립하기 보다는 실무를 발전시킨 후 실무를 토대로 이론을 정립하는 것이 최선이다.

추상적인 개념과 이론은 훌륭한 코드를 작성하는 데 필요한 도구이며 개발자는 구체적인 코드를 만지며 손을 더럽힐 떄 가장 많은 것을 얻어가는 존재다.

## 01. 티켓 판매 애플리케이션 구현하기
연극이나 음악회를 공연할 수 있는 작은 소극장을 경영한다고 가정했을 때 티켓을 판매한다고 가정해보자

### 요구사항
* 이벤트를 통해 추첨을 통해 당첨된 관람객에게 공연을 무료로 관람할 수 있는 초대장을 발송한다.
* 소극장 앞에는 이벤트 당첨자와 표를 구매하려는 관람객이 기다리고 있다.
* 당첨자와 구매자는 다른 방식으로 입장시켜야 하고 당첨자부터 티켓을 판매한 후 입장시켜야 한다.

### [초대장](./step01/Invitation.java)
* 이벤트 당첨자에게 발송되는 초대장을 구현하고, 초대일자를 포함한다.
```
public class Invitation {
    // 초대일자(when)를 인스턴수 변수로 포함
    private LocalDateTime when;
} 
```

### [티켓](./step01/Ticket.java)
* 공연을 관람하기 위한 모든 사람들은 티켓을 소지해야 한다.
```
public class Ticket {
   private Long fee; // 요금
   
   public Long getFee() {
     return fee;
   }
}
```

### [가방](./step01/Bag.java)
* 관람객이 소지품을 보관할 용도로 가방을 들고올 수 있다.
* 관람객은 초대장, 티켓, 현금을 보유할 수 있다.
```
public class Bag {
    private Long amount; // 현금
    private Invitation invitation; // 초대장
    private Ticket ticket; // 티켓

    public boolean hasInvitation() { // 초대장 소지 여부
        return invitation != null;
    }

    public boolean hasTicket() { // 티켓 소지 여부
        return ticket != null;
    }

    public void setTicket(Ticket ticket) { // 티켓 설정
        this.ticket = ticket;
    }

    public void minusAmount(Long amount) { // 현금 감소
        this.amount -= amount;
    }

    public void plusAmount(Long amount) { // 현금 증가
        this.amount += amount;
    }
}
```
* 티켓을 수령하기 전 당첨자는 현금과 초대장, 관람객은 현금만 보관하고 있는 상태로 존재하기 때문에 생성자를 추가한다.
```
public class Bag {
    public Bag(long amount) { // 현금만 존재(관람객)
        this(null, amount);
    }
  
    public Bag(Invitation invitation, long amount) { // 초대장, 현금 존재(당첨자)
        this.invitation = invitation;
        this.amount = amount;
    }
} 
```

### [관람객](./step01/Audience.java)
* 관람객은 소지품 보관을 위한 가방을 소지한다.
```
public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }
}
```

### [매표소](./step01/TicketOffice.java)
* 매표소에서 초대장을 티켓으로 교환하거나 구매해야 한다.
* 매표소는 판매할 티켓과 판매 금액이 보관돼야 한다.
```
public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice(Long amount, Ticket ... tickets) {
        this.amount = amount;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    public Ticket getTicket() { // 티켓을 주면서, 하나 제거
        return tickets.remove(0);
    }

    public void minusAmount(Long amount) { // 현금 감소
        this.amount -= amount;
    }

    public void plusAmount(Long amount) { // 현금 증가
        this.amount += amount;
    }
}
```

### [판매원](./step01/TicketSeller.java)
* 매표소에서 초대장을 티켓으로 교환하거나 티켓을 판매한다.
* 자신이 일하는 매표소에 속해있다.
```
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }
}
```

### [소극장](./step01/Theater.java)
* 소극장은 판매원이 존재하고 있어 관람객이 입장하는 역할을 처리한다.
```
public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    public void enter(Audience audience) {
        if (audience.getBag().hasInvitation()) { // 초대장 O, 교환
            Ticket ticket = ticketSeller.getTicketOffice().getTIcket();
            audience.getBag().setTicket(ticket);
        } else { // 초대장 X, 현금 받고 티켓 주기
            Ticket ticket = ticketSeller.getTicektOffice().getTicket();
            audience.getBag().minusAmount(ticekt.getFee());
            ticketSeller.getTicketOffice.plusAmount(ticket.getFee());    			
            audience.getBag().setTicket(ticket);
        }
    }
}
```
<img src="./image/그림 1.2.png">

##### 절차
1. 소극장이 관람객의 가방 안에 초대장이 있는지 확인한다.
2. 초대장이 있다면 ➔ 판매원에게 받은 티켓을 가방에 넣어준다.<br>초대장이 없다면 ➔ 가방에서 티켓 금액만큼 차감한 후 매표소 금액을 증가 시키고 티켓을 가방에 넣어준다.
3. 입장 철자가 종료된다.

##### 문제점
* 위와 같이 작성된 간단한 프로그램은 동작하지만 몇 가지 문제가 발생한다.

## 02. 무엇이 문제인가
로버트 마틴이 작성한 저서에는 소프트웨어 모듈이 가져야 하는 세 가지 기능이 존재한다.
* 제대로 실행돼야 한다.
* 변경에 용이해야 한다.
* 이해하기 쉬워야 한다.

### 예상을 빗나가는 코드
관람객과 판매원이 소극장의 통제를 받는 `수동적인 존재`라는 점인데 소극장이라는 객체가 관람객의 가방과 매표소의 티켓, 현금도 마음대로 접근할 수 있다.

결과는 같지만 현실에서 우리가 생각한 상식적인 행동과 코드가 동작하는 행동이 다르다는 점이다.<br>개발자가 생각한 코드와 해당 코드는 예상이 빗나가기 때문에 이해하기 쉬운 코드가 아니다.

우리가 레이어드 아키텍처를 쓰는 이유만 봐도 이해하기 쉬운 코드는 정말 중요한데 레이어드 아키텍처는 계층적인 구조를 상위에서 하위로 의존적인 흐름가져서 개발자가 이해하기 쉽도록 유도하기 때문이다.

또한 하나의 클래스에 여러 책임이 들어있어서 코드를 이해하기 위해서 세부적인 내용들을 한꺼번에 기억해야 한다. ➔ 단일 책임의 원칙(SRP)에 어긋난다.

### 변경에 취약한 코드
관람객(Audience) 또는 판매원(TicketSeller)를 변경할 경우 소극장(Theater)도 변경해야 한다.<br>관람객이 가방을 들고 있지 않다면?, 관람객이 현금이 아니라 신용카드를 사용한다면?, 판매원이 매표소 밖에서 티켓을 판매해야 한다면? 이와 같은 이유로 코드를 변경해야 하는 상황이 나올 수 있고, 실무에선 더더욱 변경하는 상황이 많이 일어날 것이다.

현재 짜여진 코드는 객체가 다른 객체에 지나치게 의존적으로 나타나는 문제인데 객체가 변경이 됬을 때 의존을 가지는 객체도 함께 변경될 수 있다.<br>그렇다고 객체 사이의 의존성을 없애는 것은 객체지향 설계에 어긋난다.

객체 사이의 결합도를 낮춰서 변경에 용이한 설계를 만들어야 한다. ➔ 개방 폐쇄의 원칙(OCP)를 지켜야 한다.

## 03. 설계 개선하기
변경에 용이해야 한다와 이해하기 쉬워야 한다의 조건을 만족시키지 못했기 때문에 설계를 개선해야 한다.
* 관람객이 스스로 가방 안의 현금과 초대장을 처리
* 판매원이 스스로 매표소의 티켓과 판매 요금 다루기
* 소극장이 관람객과 판매원에 관해 세세히 알지 못하게 정보를 차단

즉 관람객과 판매원을 `자율적인 존재`로 만들면 된다. 결국 자율적인 존재란 외부에서 대신 메서드를 호출하는 것이 아닌 캡슐화를 통해서 스스로 메서드를 호출하는 것을 의미한다. 또한 개념적 또는 물리적으로 `객체 내부의 세부적인 사항을 감추는 캡슐화`를 통해서 `변경하기 쉬운 객체로 변경`된다.

### 자율성을 높이자
#### 1단계
* 판매원이 매표소에서 티켓을 직접 판매하도록 자율성을 부여해야 한다.
* 소극장의 enter 메서드의 매표소를 접근하는 모든 코드를 판매원의 sellTo 메서드로 캡슐화한다.

##### [소극장](./step03/Theater.java)
```
public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    public void enter(Audience audience) {
        ticketSeller.sellTo(audience);
    }
}
```

##### [판매원](./step03/TicketSeller.java)
```
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        if (audience.getBag().hasInvitation()) { // 초대장 O, 교환
            Ticket ticket = ticketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        } else { // 초대장 X, 현금 받고 티켓 주기
            Ticket ticket = ticketOffice().getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketOffice().plusAmount(ticket.getFee());    			
            audience.getBag().setTicket(ticket);
        }
    }
}
```

#### 2단계
* 판매원이 직접 가방에서 돈을 차감하는 것이 아닌 관람객이 직접 돈을 지불하도록 자율성을 부여해야 한다.
* 판매원의 sellTo 메서드에서 getBag 메서드 접근을 관람객의 buy 메서도로 캡슐화한다.

##### [판매원](./step03/TicketSeller.java)
```
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()));
    }
}
```

##### [관람객](./step03/Audience.java)
```
public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Long buy(Ticket ticket) {
        if (bag.hasInvitation()) {
            bag.setTicket(ticket);
            return 0L;
        } else {
            bag.setTicket(ticket);
            bag.minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }
}
```

### 무엇이 개선됐는가
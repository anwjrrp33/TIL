# 02. 객체지향 프로그래밍

## 객체지향의 프로그래밍의 강력함
객체지향 프로그래밍의 강력함은 객체들 사이의 협력 관계를 기반으로 객체들이 주고받는 메세지로부터 나온다.

## 기술서적의 난이도
프로그래밍 기술 서적에서 소프트웨어를 설명할 때 난이도와 복잡도를 유지하면서 이해하기 쉬운 예제를 선택하는 것은 매우 어렵다.
* 책이 어렵우면 ➔ 전문성이 높은 책일 확률이 높다.
* 책이 쉬울수록 ➔ 대중성이 높은 책일 확률이 높다.

## 01. 영화 예매 시스템
사용자가 영화 예매 시스템을 이용해 보고 싶은 영화를 예매할 수 있다고 가정해본다.

### 유비쿼터스 언어
* 시작하기 전 용어를 지정해야하는데 영화 예매 시스템에서 사용자가 실제로 예매하는 것은 영화가 아닌 상영이기 때문이다.

<table>
    <thead>
        <tr>
            <th>용어</th>
            <th>의미</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>영화</td>
            <td>
                영화에 대한 기본 정보를 의미하며, 제목 상영시간 가격 정보와 같은 정보
            </td>
        </tr>
        <tr>
            <td>상영</td>
            <td>
                관객들이 영화를 관람하는 사건을 표현하며, 상영 일자, 시간, 순번과 같은 정보
            </td>
        </tr>
        <tr>
            <td>할인 조건</td>
            <td>
                순서 조건 ➔ 상영 순번을 이용해 할인 여부를 결정하는 규칙
                <br>
                기간 조건 ➔ 상영 시작 시간을 이용해 할인 여부를 결정하는 규칙
            </td>
        </tr>
        <tr>
            <td>할인 정책</td>
            <td>
                금액 할인 정책 ➔ 예매 요금에서 일정 금액을 할인해주는 방식
                <br>
                비율 할인 정책 ➔ 예매 요금에서 일정 비율을 할인해주는 방식
            </td>
        </tr>
    </tbody>
</table>

### 요구사항
* 사용자는 온라인 예매 시스템을 통해서 금액을 지불하고 영화를 예매할 수 있다.
* 할인 정책 또는 할인 조건을 만족하는 예매자는 요금 할인을 받을 수 있다.
    * 할인 조건은 다수를 지정할 수 있지만 할인 정책은 최대 하나만 지정할 수 있다.
    * 할인 조건에 하나라도 부합하면 할인 정책을 이용해 할인 요금을 계산한다.

<img src="./image/그림 2.1.png">

## 02. 객체지향 프로그래밍을 향해
### 협력, 객체, 클래스
객체지향 프로그래밍을 작성할 때 가장 먼저 고려하는 것은 클래스이며 그 후 클래스에 어떤 속성과 메서드가 필요한지 고민할 것이다.

하지만 이는 객체지향과 본질과는 거리가 멀다. 말 그래도 객체를 지향하는 것이기 때문에 클래스가 아닌 객체 초점을 맞춰야 한다. 따라서 객체지향 프로그래밍을 하는 동안 두 가지에 집중해야 한다.
* 어떤 클래스가 필요한지를 고민하기 전에 어떤 객체들이 필요한지 고민하라 ➔ 클래스는 공통적인 상태와 행동을 공유하는 객체들을 추상화한 것이다. 즉 클래스를 설계 하기 전 객체를 중점으로 설계를 진행해서 설계를 단순하고 깔끔하게 만든다.
* 객체를 독립적인 존재가 아니라 기능을 구현하기 위해 협력하는 공동체의 일원으로 봐야 한다. ➔ 다른 객체에게 도움을 주거나 의존하면서 살아가는 협력적인 존재이다. 즉 객체를 협력하는 공동체 일원으로 바라보는 것은 설계를 유연하고 확장 가능하게 만든다.

#### 객체 & 클래스
* 객체
  * 물리적으로 존재하거나 추상적으로 생각할 수 있는 것 중에서 속성을 가지고 있고, 식별 가능한 것을 의미한다.
* 클래스
  * Java에서 객체를 구현하기 위한 일종의 설계도를 의미하고, 데이터와 프로세스로 이루어져 있다.

### 도메인의 구조를 따르는 프로그램 구조

#### 도메인
* 영화 예매 시스템의 목적처럼 영화를 쉽고 빠르게 예매하려는 사용자의 문제를 해결하려는 것으로 이처럼 사용자가 프로그램을 사용하는 분야를 도메인이라고 부른다.
* 개발자 입장에서 영화 예매 시스템은 도메인이자 구현해야 할 소프트웨어의 대상이 된다.
  
#### 객체지향 패러다임의 장점
* 요구사항을 분석하는 초기 단계부터 프로그램을 구현하는 마지막 단계까지 객체라는 동일한 추상화 기법을 사용할 수 있기 때문이다. ➔ 요구사항과 프로그램을 객체라는 동일한 관점으로 바라봐서 설계를 진행한 후 이를 통해서 프로그램의 객체와 클래스로 매끄럽게 연결될 수 있다.
* 앞서 말했던 것처럼 만약 클래스부터 설계를 진행하면 클래스가 동작하기 위해서 클래스들끼리의 의존성, 프로세스, 주고 받는 메세지, 연관관계처럼 복잡하게 코드를 설계하고 구현되버린 상태에서 추가 요구사항을 계속 붙이는 과정 중에 유연한 확장을 하기 힘들게 만들기 때문이라고 생각한다.

#### 클래스 구조의 원칙
* 일반적으로 클래스의 이름은 대응되는 도메인 개념의 이름과 동일하거나 적어도 유사하게 지어야 한다.
* 클래스 사이의 관계는 최대한 도메인 개념 사이에 맺어진 관계와 유사하게 만들어서 프로그램 구조를 이해하고 예상하기 쉽게 만들어야 한다.

#### 영화 예매 도메인을 구성하는 개념과 관계
* 영화는 여러 번 상영될 수 있다.
* 상영은 여러 번 예매될 수 있다.
* 영화는 할인 정책을 할당하거나 하나만 할당할 수 있다.
* 할인 정책은 하나 이상의 할인 조건이 존재한다.
* 할인 정책은 금액 할인 정책과 비율 할인 정책이 있다.
* 할인 조건의 종류는 순번 조건과 기간 조건이 있다.
  
<img src="./image/그림 2.3.png">

<img src="./image/그림 2.4.png">

### 클래스 구현하기
#### [상영](./movie/step01/Screening.java)
```
public class Screening {
    private Movie movie; // 상영할 영화
    private int sequence; // 순번
    private LocalDateTime whenScreened; // 상영 시작 시간

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    public LocalDateTime getStartTime() { // 상영 시작 시간을 반환하는 메서드
        return whenScreened;
    }

    public boolean isSequence(int sequence) { // 순번의 일치 여부를 검사하는 메서드
        return this.sequence == sequence;
    }

    public Money getMovieFee() { // 기본 요금을 반환하는 메서드
        return movie.getFee();
    }
}
```

#### 가시성
상영 클래스의 인스턴스 변수의 가시성은 `private`이고 메서드의 가시성은 `public`이라는 점인데, 클래스를 구현하거나 이미 개발된 클래스를 사용할 때 중요한 것은 클래스의 경계를 구분 짓는 것이다.

훌륭한 클래스를 설계하기 위한 핵심은 내부와 외부로 구분해서 어떤 부분을 공개하고, 감출지를 결정하는 것이다.
외부에서는 객체의 속성에 직접 접근을 막아야 하고, 메서드를 통해서만 내부 상태를 변경해야 한다.

즉 내부와 외부를 구분하는 이유는 경계의 명확성이 생겨서 객체의 자율성을 보장하기 때문이다.
이를 통해서 앞서 언급했던 외부 클래스가 내부 데이터를 가져가서 하나의 프로세스에서 모든 로직들이 동작할 수 없도록 코드에 강제성을 부여하기도 하며, 결국 다미터 법칙을 위반하지 않게 된다.

#### 자율적인 객체

##### 객체의 특징
객체는 두 가지 특징을 가지고 있다.
* 상태와 행동을 함께 가지는 복합적인 존재
* 스스로 판단하고 행동하는 자율적인 존재

##### 캡슐화와 접근제어
객체지향은 객체라는 단위 안에 데이터와 기능을 한 덩어리로 묶어 문제 영역의 아이디어를 적절하게 표현할 수 있다.
이처럼 데이터와 기능을 객체 내부로 함께 묶는 것을 캡슐화라고 부른다. ➔ 객체 내부의 데이터는 내부의 기능(메서드)를 통해서 처리해야 한다.

상태와 행동을 캡슐화에서 한걸음 더 나아가 외부에서의 접근을 통제할 수 있게 하는 것이 접근 제어이다.
객체 내부에 대한 접근을 통제하는 이유는 객체를 자율적인 존재로 만들기 위해서이며, 객체에게 원하는 것을 요청하고 객체가 스스로 최선을 방법을 결정할 수 있을 것이라는 점을 믿어야 한다.

캡슐화와 접근제어는 객체를 두 부분으로 나눈다.
* 외부에서 접근 가능한 퍼블릭 인터페이스(public interface)
* 내부에서만 접근 가능한 부분인 구현(implementation)

퍼블릭 인터페이스와 구현은 인터페이스와 구현의 분리(separation of interface and implementation) 원칙에 해당하며 휼륭한 객체지향 프로그램을 만들기 위한 핵심 원칙이다.

이처럼 캡슐화와 접근 제어를 사용하면 이점을 얻을 수 있다.
* 외부 객체가 객체의 내부 구현을 몰라도 되며, 단지 메세지를 전달하고 원하는 메세지를 받으면 된다.
* 외부 객체에서 객체의 데이터를 접근해서 변경하는 일을 방지할 수 있다.
* 내부 구현을 변경해도 인타페이스를 통해서 외부 객체에 영향을 주지 않는다.

##### 프로그래머의 자유
프로그래머의 역할은 클래스 작성자(class creator)와 클라이언트 프로그래머(client programmer)로 구분하는 것이 유용하다.

<table>
    <thead>
        <tr>
            <th>역할</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>클래스 작성자</td>
            <td>
                새로운 데이터 타입을 프로그램에 추가한다.
            </td>
        </tr>
        <tr>
            <td>클라이언트 프로그래머</td>
            <td>
                클래스 작성자가 추가한 데이터 타입을 사용한다.
            </td>
        </tr>
        <tr>
            <td>접근 제어 메커니즘</td>
            <td>
                클래스의 내부와 외부를 명확하게 경계 지어서 클래스 작성자가 내부 구현을 은닉할 수 있게 해준다.<br>
                클라이언트 프로그래머가 실수로 숨겨진 부분에 접근하는 것을 막아준다.
            </td>
        </tr>
        <tr>
            <td>구현 은닉</td>
            <td>
                내부의 구현은 무시한 채 인터페이스만 알고 있어도 클래스를 사용할 수 있기 때문에 머리 속에 담아둬야 하는 지식의 양을 줄일 수 있다.<br>
                클래스 작성자가 인터페이스를 바꾸지 않는 한 내부 구현을 마음대로 변경할 수 있다.
            </td>
        </tr>
    </tbody>
</table>

결국 객체의 외부와 내부를 구분하면 코드를 이해하기 위해서 알아 할 정보가 줄어들고, 변경에는 닫혀있고 확장에는 열려있는 개방 폐쇄의 원칙을 지킬 수 있게 된다.

설계란 변경이 일어날 때 사이드 이펙트를 가장 줄이기 위한 것이라고 생각하는데 객체지향 언어는 객체 사이의 의존성을 적절히 관리함으로써 변경에 대한 파급효과를 제어할 수 있는 다양한 방법을 제공한다.

대표적으로 public, private와 같은 접근 제한자를 통한 접근 제어로 변경될 가능성이 있는 세부적인 구현 내용은 private 영역 안에 감춰서 변경이 발생할 때 혼란을 최소화 할 수 있다. ➔ 인터페이스를 활용한 추상화

### 협력하는 객체들의 공동체
영화를 예매하는 기능을 구현하는 메서드를 살펴본다.

#### [상영](./movie/step01/Screening.java)
* 예매자와 인원수를 전달받으면 영화를 예매한다.
```
public class Screening {
    public Reservation reserve(Customer customer, int audienceCount) { // 영화를 예매한 후 예매 정보를 반환하는 메서드
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }
}
```
* 인원수에 맞는 예매 요금을 구한다.
```
public class Screening {
    private Money calculateFee(int audienceCount) { // 예매 요금을 구하는 메서드
        return movie.calculateMovieFee(this).times(audienceCount);
    }
}
```

#### [금액](./movie/step01/Movie.java)
* 금액과 관련된 다양한 계산을 한다.
* 금액을 구현하기 위해서 Long 타입이 아닌 객체를 이용해서 로직의 중복과 도메인의 의미를 풍부하게 표현할 수 있다.
  * 의미를 좀 더 명시적이고 분명하게 표현할 수 있다면 객체를 사용해서 개념을 구현하는 것이 좋다.
  * 인스턴스 변수만 포함해도 개념을 명시적으로 표현하는 것은 전체적인 설계의 명확성과 유연성을 높인다.
```
public class Money {
    public static final Money ZERO = Money.wons(0);

    private final BigDecimal amount;

    public static Money wons(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money wons(double amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money plus(Money amount) {
        return new Money(this.amount.add(amount.amount));
    }

    public Money minus(Money amount) {
        return new Money(this.amount.subtract(amount.amount));
    }

    public Money times(double percent) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(percent)));
    }

    public boolean isLessThan(Money other) {
        return amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Money)) {
            return false;
        }

        Money other = (Money)object;
        return Objects.equals(amount.doubleValue(), other.amount.doubleValue());
    }

    public int hashCode() {
        return Objects.hashCode(amount);
    }

    public String toString() {
        return amount.toString() + "원";
    }
}
```

#### [예매 정보](./movie/step01/Reservation.java)
* 고객 정보, 상영 정보, 예매 요금, 인원 수와 같이 고객이 예매한 정보를 가진다.
```
public class Reservation {
    private Customer customer; // 고객
    private Screening Screening; // 상영정보
    private Money fee; // 예매 요금
    private int audienceCount; // 인원 수

    public Reservation(Customer customer, Screening Screening, Money fee, int audienceCount) {
        this.customer = customer;
        this.Screening = Screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }
}
```

위의 작성된 예제처럼 상영, 영화, 예매 정보는 서로의 메서드를 호출하며 상호작용하는데 이를 협력(Collaboration)이라고 부른다.

<img src="./image/그림 2.5.png">

### 협력에 관한 짧은 이야기
객체의 내부 상태는 외부에서 접근하지 못하지만 외부에 공개하는 퍼블릭 인터페이스를 통해 내부 상태에 접근할 수 있도록 허용해야 한다.
객체는 다른 객체의 인터페이스에 행동을 요청하고 다른 객체는 요청을 처리한 후 응답한다.

#### 메서드
객체끼리 상호작용할 수 있는 유일한 방법은 `메시지를 전송`하고, `메시지를 수신`받는 것인데 수신받은 객체는 스스로의 결정에 따라 자율적으로 메시지를 처리할 방법을 `메서드`라고 부른다.

#### 다형성
메시지와 메서드의 구분은 매우 중요하다. 메시지와 메서드의 구분에서부터 다형성의 개념이 출발한다.
* 메시지
  * 이름과 인자로 구성되고, 메시지를 표현한다면 책을 읽어라(오브젝트)와 같은 형태로 구성된다.
  * 메시지를 수신받는다는 것은 수신받은 객체가 책임을 수행할 수 있다는 것을 의미한다.
* 메서드
  * 메시지를 수신받은 객체가 내부적으로 처리하는 방법이다.
  * 어떻게 수행할지는 메서드가 결정하지만 무엇을 수행할지는 수신자가 결정한다.
* 다형성
  * 서로 다른 객체가 동일한 메시지에 대해 다르게 행동하는 것을 말한다.
  * 서로 다른 타입의 객체들이 동일한 메시지를 수신받을 경우 서로 다른 메서드를 이용해서 다르게 처리한다.

#### 호출과 전송
앞서 상영이 영화의 예매 요금 계산 메서드를 호출한다고 표현했지만 메시지를 전송한다는 표현이 더 적절하다.
예매는 영화안에 메서드가 존재하는지도 모르고, 영화가 응답할 수 있을거라고 믿고 메시지를 전송할 뿐이다.
메시지를 전송하면 수신받은 영화는 스스로 적절한 메서드를 선택하는데 결국 메시지를 처리하는 방법을 결정하는 것은 수신받은 객체 스스로의 문제기 때문에 자율적으로 결정할 수 있다고 표현한느 것이다.

## 03. 할인 요금 구하기

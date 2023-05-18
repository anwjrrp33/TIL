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

#### [금액](./movie/step01/Money.java)
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
### 할인 요금 계산을 위한 협력 시작하기
예매 요금을 계산하는 협력을 살펴본다.

#### [영화](./movie/step01/Movie.java)
* 영화는 영화에 대한 정보인 제목, 상영시간, 기본요금, 할인 정책를 가지고 있는다.
* 할인 요금을 
```
public class Movie {
    private String title; // 제목
    private Duration runningTime; // 상영시간
    private Money fee; // 기본요금
    private DiscountPolicy discountPolicy; // 할인 정책

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) { // 할인요금을 반환하는 메서드
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
```

위 코드에는 어떤 할인 정책을 사용할지에 대한 정보가 없고 단순히 할인정책에게 예매정보를 담아서 메시지를 전송할 뿐이다.

이 코드에는 상속, 다형성 그리고 추상화의 원리가 숨겨져 있다. 
객체지향 패러다임에 익숙하다면 당연히 메시지만 전달하고 어떤 할인 정책을 사용할지 객체에게 판단할 것이라고 생각할 것이다.

### 할인 정책과 할인 조건
할인 정책은 금액 할인 정책과 비율 할인 정책으로 구분된다.
금액 할인 정책과 비율 할인 정책은 할인 정책이라는 요금을 할인시켜주는 역할을 하기 때문에 코드가 유사하고 계산하는 방식만 다르다.
필연적으로 중복코드가 발생할텐데 이를 제거하기 위해서 공통 코드가 필요하다.

#### [할인 정책](./movie/step01/DiscountPolicy.java)
* 할인 정책은 할인되는 조건에 부합하면 할인 요금을 반환한다.
* 추상 메서드를 통해서 할인 요금을 계산한다.
* 부모 클래스에 알고리즘 흐름을 구현하고 필요한 처리를 자식 클래스에 위임하는 디자인 패턴을 `TEMPLATE METHOD 패턴`이라고 부른다. 
```
public abstract class DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>(); // 할인 정책 목록

    public DiscountPolicy(DiscountCondition ... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public Money calculateDiscountAmount(Screening screening) { // 할인 정책을 통해서 할인 요금을 계산해서 반환하는 메서드
        for(DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening Screening); // 할인된 금액을 반환하는 추상 메서드
}
```

#### [할인 조건](./movie/step01/DiscountCondition.java)
* 추상 메서드를 통해서 할인 여부를 반환한다.
```
public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening); // 할인 여부를 판단하는 추상 메서드
}
```

#### [순번 조건](./movie/step01/SequenceCondition.java)
* 순번을 통해서 상영의 상영 순번과 일치하는지 여부를 판단한다.
```
public class SequenceCondition implements DiscountCondition { // 할인조건 인터페이스를 상속
    private int sequence; // 순번

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    public boolean isSatisfiedBy(Screening screening) { // 할인 여부를 반환하는 메서드
        return screening.isSequence(sequence);
    }
}
```

#### [기간 조건](./movie/step01/PeriodCondition.java)
* 요일, 시작 시간, 종료 시간을 통해서 특정한 기간 안에 포함하는지 여부를 판단한다.
```
public class PeriodCondition implements DiscountCondition { // 할인조건 인터페이스를 상속
    private DayOfWeek dayOfWeek; // 요일
    private LocalTime startTime; // 시작 시간
    private LocalTime endTime; // 종료 시간

    public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isSatisfiedBy(Screening screening) { // 할인 여부를 반환하는 메서드
        return screening.getStartTime().getDayOfWeek().equals(dayOfWeek) &&
                startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getStartTime().toLocalTime()) >= 0;
    }
}
```

#### [금액 할인 정책](./movie/step01/AmountDiscountPolicy.java)
* 할인 조건을 만족할 경우 일정한 금액을 할인해준다.
```
public class AmountDiscountPolicy extends DiscountPolicy {
    private Money discountAmount; // 할인 요금

    public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) { // 할인 요금을 반환하는 메서드
        return discountAmount;
    }
}
```

#### [비율 할인 정책](./movie/step01/PercentDiscountPolicy.java)
* 할인 조건을 만족할 경우 일정한 비율의 금액을 할인해준다.
```
public class PercentDiscountPolicy extends DiscountPolicy {
    private double percent; // 할인 비율

    public PercentDiscountPolicy(double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) { // 할인 요금을 반환하는 메서드
        return screening.getMovieFee().times(percent);
    }
}
```

#### 영화 가격 계산 클래스 다이어그램
<img src="./image/그림 2.6.png">

## 04. 상속과 다형성
영화 클래스는 어떤 할인 정책을 사용할 것인지 판단하지 않고, 할인 정책을 결정하는 조건문 없이 할인 정책을 선택한다. ➔ 상속과 다형성을 통해서 할인 정책의 구현체들을 하나로 묶고, 외부에서 의존성을 주입받아서 어떤 정책을 사용할 것인지 판단한다.

### 컴파일 시간 의존성과 실행 시간 의존성
어떤 클래스가 다른 클래스에 접근할 수 있는 경로를 가지거나 해당 클래스의 객체의 메서드를 호출할 경우 두 클래스 사이에 의존성이 존재한다고 말한다.

영화 클래스가 할인 정책 클래스와 연결돼 있어서 금액 할인 정책과 비율 할인 정책과 의존성을 가지지 않지지 않는다. 금액 할인 정책과 비율 할인 정책은 할인 정책과 연결돼 있고, 의존성을 가진다.

<img src="./image/그림 2.7.png">

하지만 영화의 요금을 구하기 위해서는 금액 할인 정책과 비율 할인 정책 두 클래스 하나에 의존해야 한다. 
UML로 따졌을때는 영화가 할인 정책을 의존하고 있지만, 객체를 만들고나면 자식클래스를 의존하게 되므로 달라진다.
```
Movie avatar = new Movie(
    "아바타", 
    Duration.ofMinutes(120),
    Money.wons(10000),
    new AmountDiscountPolicy(Money.wons(800), ...) // 실제로 주입하는 할인 정책의 구현 클래스
);
```

위 코드가 실행되면 영화 인스턴스는 금액 할인 정책 인스턴에 의존하게 된다. ➔ 만약 비율 할인 정책을 주입하면? 당연히 비율 할인 정책 인스턴스에 의존하게 된다.

즉 코드의 의존성과 실행 시점(인스턴스)의 의존성은 서로 다를 수 있다 ➔ 클래스 사이의 의존성과 객체 사이의 의존성을 동일하지 않을 수 있다.

#### 의존성과 유연성
코드의 의존성과 실행 시점의 의존성이 다를수록
* 코드가 유연해지고 변경에 영향이 가지 않고 확장이 쉬워진다. ➔ 의존 관계 역전이 일어났기 때문에 구현체 클래스 코드에 변경이 일어나도 다른 코드에 영향을 주지않고 기능 추가가 일어났을 때도 기존 코드를 수정하는 것이 아닌 클래스만 추가하면 된다.
* 코드를 이해하기 어려워진다는 단점이 있다. ➔ 클래스가 추상화되기 때문에 의존하고 있는 객체의 정확한 타입을 알기 위해서 인스턴스를 생성하는 부분을 찾아서 의존성 대상을 확인해야 한다.

설계가 유연해질수록
* 재사용성과 확장 가능성이 높아진다. ➔ 공통된 기능이라면 재사용할 수 있고 기능이 추가될 떄 코드의 수정없이 추가하면 된다.
* 디버깅 하기 어려워진다. ➔ 디버깅을 할 때 가장 편한것은 절차지향으로 코드가 순서대로 동작할 때인데 객체지향은 유연성을 가졌지만 추상적이기 때문에 코드의 흐름을 순차적으로 보기가 힘들다.

휼륭한 객체지향 설게자가 되려면 의존성과 유연성 그리고 가독성 사이에서 고민을 하고 현재 상황에 맞게 트레이드오프를 생각해서 설계를 진행해야 한다.
무조건 정답인 설계는 존재하지 않고 상황에 맞는 설계만이 존재한다.

### 차이에 의한 프로그래밍
객체지향의 상속을 이용하면 부모 클래스의 모든 필드와 메서드의 재사용이 가능하고, 변경되는 부분만 추상 메서드를 통해서 메서드를 자식 클래스에서 오버라이딩해서 재정의 할 수 있다.

부모 클래스와 다른 부분만을 추가해서 새로운 클래스를 쉽게 빠르게 만드는 방법을 `차이에 의한 프로그래밍`이라 한다.

### 상속과 인터페이스
상속의 목적은 부모 클래스의 메서드나 인스턴스 변수의 재사용은 부가적인 이유며 공통된 사용을 의미한다.

자식 클래스는 상속을 통해 부모 클래스의 인터페이스를 물려받기 때문에 부모 클래스 대신 사용될 수 있다. 이와 같은 이유 덕분에 영화(Movie)의 생성자에서 인자의 타입이 할인 정책(DiscountPolicy)더라도, 그것의 자식 클래스의 인스턴스를 전달할 수 있게 된다.

이처럼 자식 클래스가 부모 클래스를 대신하는 것을 업캐스팅이라고 부른다. ➔ 부모 객체를 호출하는 동작에서 자식 객체가 부모 객체를 완전히 대체할 수 있다는 원칙인 리스코프 치환 원칙을 지키게 된다.

<img src="./image/그림 2.11.png">

### 다형성
앞서 언급했듯 메시지와 메서드는 다른 개념이다. 영화(Movie)는 할인 정책(DiscountPolicy)의 인스턴스에게 calculateDiscountAmount라는 메시지를 전송한다.

저 메시지에 의해 실행되는 메서드는 금액 할인 정책(AmountDiscountPolicy)과 비율 할인 정책(PercentDiscountPolicy) 중에서 영화(Movie)에서 주입받은 객체에 오버라이딩한 메서드가 실행될 것이다.

영확(Movie)는 동일한 메시지를 전송하지만 실제로 어떤 메서드가 실행될 것인지는 메시지를 수신하는 객체의 클래스가 무엇이냐에 따라 달라진다. ➔ 이를 다형성이라고 부른다.

다형성은 객체지향 프로그램의 컴파일 시간 의존성과 실행 시간 의존성이 다를 수 있다는 사실을 기반으로 한다.

#### 시간에 따른 의존성
컴파일 의존 시간 의존성
* 영화(Movie) ➔ 할인 정책(DiscountPolicy)

실행 시간 의존성
* 영화(Movie) ➔ 금액 할인 정책(AmountDiscountPolicy) 또는 비율 할인 정책(PercentDiscountPolicy)

다형성은 동일한 메시지를 수신했을 때 객체의 타입에 따라 다르게 응답할 수 있는 능력을 의미하고, 다형적인 협력에 참여하는 객체들은 인터페이스가 동일해야 한다.

#### 바인딩
지연 바인딩 & 동적 바인딩
* 메시지에 응답하기 위해 실행될 메서드가 실행 시점에 결정

초기 바인딩 & 정적 바인딩
* 전통적인 함수 호출처럼 컴파일 시점에 실행될 함수나 프로시저를 결정

객체지향이 컴파일 시점과 실행 시점의 의존성을 분리하고, 다형성을 구현할 수 있는 이유가 바로 지연 바인딩이라는 메커니즘을 사용하기 때문이지만 클래스를 상속 받는 것만이 다형성을 구현할 수 있는 유일한 방법은 아니다. ➔ 오버로딩, 함수형 인터페이스가 존재한다.

#### 구현 상속과 인터페이스 상속
구현 상속
* 코드를 재사용하기 위한 목적으로 상속을 사용
* 서브클래싱이라고 부른다.

인터페이스 상속
* 다형적인 협력을 위해 부모 클래스와 자식 클래스가 인터페이스를 공유할 수 있도록 상속을 이용
* 서브타이핑이라고 부른다.

### 인터페이스와 다형성
#### 자바의 인터페이스
* 구현을 공유할 필요가 없고, 순수하게 인터페이스만 공유하고 싶을 때에 사용된다.
* 자바에서 구현에 대한 고려 없이 다형적인 협력에 참여하는 클래스들이 공유 가능한 외부 인터페이스를 정의한 것이다.

#### 다형성
추상 클래스를 사용한 할인 정책과 달리 할인 조건은 공통된 로직인 구현을 공유할 필요가 없기 때문에 인터페이스를 사용해서 타입을 계층을 구현했다.

순번 조건과 기간 조건은 할인 조건이라는 인터페이스를 공유해서 다형적인 협력에 참여하고 있다. ➔ 클라이언트 입장에서 추상 클래스와 구현 클래스는 아무 차이 없다는 소리로 동일한 메세지를 이해할 수 있기 때문이다. 이 경우에 실제 사용하는 클래스는 구현 클래스이고, 이 경우도 업캐스팅이 적용되며, 협력은 다형적이다.

<img src="./image/그림 2.12.png">

## 05. 추상화와 유연성
### 추상화의 힘
#### 추상화 수준
할인 정책은 구체적인 금액 할인 정책, 비율 할인 정책을 포괄하는 추상적인 개념이다.
할인 조건 역시 더 구체적인 순번조건, 기간조건을 포괄하는 추상적인 개념이다.

할인 정책은 할인 조건보다 더 높은 레벨의 추상화 수준이다 ➔ 추상 클래스인 할인 정책은 일부분만 추상화하기 때문이다.

#### 추상화의 장점
1. 요구사항의 정책을 높은 수준에서 서술할 수 있다.
   * 하위 타입의 모든 클래스가 수신할수 있는 메세지를 정의하기 때문으로 세부적인 내용을 무시한 채 상위 정책을 쉽고 간단하게 표현할 수 있다. 
   * 세부사항에 억눌리지 않고 상위 개념만으로 도메인의 중요한 개념을 설명할 수 있다.  
   * 즉 이해하는 데 세부적인 구현없이 쉽게 이해할 수 있다.
  
2. 설계가 유연해진다.
   * 추상화를 이용해 상위 정책을 표현하면 기존 구조를 수정하지 않고도 새로운 기능을 쉽게 추가하고 확장할 수 있다.

<img src= "./image/그림 2.13.png">

#### 추상화를 이용한 협력 흐름
예매 가격을 계산하기 위한 흐름은 항상 영화(Movie)에서 할인 정책(DiscountPolicy)으로 흐르고, 다시 할인 조건(DiscountCondition)을 향해 흐른다.

할인 정책이나 할인 조건의 새로운 자식 클래스들은 추상화를 이용해서 정의한 상위 협력 흐름을 그대로 따르게 돠고, 이 개념은 매우 중요한데, 재사용 가능한 설계의 기본을 이루는 디자인 패턴(design pattern)이나 프레임워크(framework) 모두 추상화를 이용해 상위 정책을 정의하는 객체지향의 메커니즘을 활용하고 있기 때문이다.

### 유연한 설계
아직 할인 정책이 없는 경우를 생각해보지 않았는데 사실 할인 요금을 계산할 필요 없이 영화에 설정된 기본 금액을 그대로 사용하면 된다.

#### [영화](./movie/step02/Movie.java)
* 할인 정책이 없으면 기본 요금을 반환한다.
```
public class Movie {
    public Money calculateMovieFee(Screening screening) { // 할인요금을 반환하는 메서드
        if (discountPolicy == null) { // 할인 정책이 없으면 기본 요금을 반환해주는 조건문
            return fee;
        }

        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
```

위 코드는 문제가 발생하는데 할인 정책이 없는 경우를 예외 케이스를 취급해서 일관성 있던 협력 방식이 무너진다.

할인을 결정하는 책임이 할인 정책에게 있는 것이 아닌 영화에게 있기 때문으로 책임의 위치를 결정하기 위해 조건문을 사용하는 것은 협력의 설계 측면에서 대부분의 경우 좋지 않은 선택이다.

일관성을 지키기 위해서 0원이라는 할인 요금을 계산할 책임 그대로 할인 정책에게 유지시키는 것이다.

#### [예외 할인 정책](./movie/step02/NoneDiscountPolicy.java)
* 할인이 적용되지 않기 때문에 0원을 반환한다.
```
public class NoneDiscountPolicy extends DiscountPolicy {
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
```

이제 영화 인스턴스에 예회 할인 정책 인스턴스를 주입하면 할인되지 않는 영화를 생성 할 수 있다.
```
Movie starWars = new Movie(
    "스타워즈",
    Duration.ofMinutes(210),
    Money.wons(10000),
    new NoneDiscountPolicy()
);
```

중요한 것은 기존의 영화와 할인 정책의 수정없이 예외 할인 정책이라는 새로운 클래스를 추가하는 것만으로도 기능을 확장했다는 것이다. ➔ 추상화를 중심으로 코드의 구조를 설계하면 유연하고 확장 가능한 설계를 만들 수 있다.

추상화가 유연한 설계를 가능한 이유는 설계가 구체적인 상황에 결합되는 것을 방지하기 때문이다.<br>➔ 영화는 특정한 할인 정책에 묶이지 않고 할인 정책을 구현 클래스가 할인 정책을 상속 받고 있다면 어떤 클래스와도 협력이 가능하다. <br>➔ 할인 정책도 할인 조건을 상속받은 어떤 클래스와도 협력이 가능하다.

이러한 개념을 컨텍스트 독립성이라고 부르며 프레임워크와 같은 유연한 설계가 필수적인 분야에서 진가를 발휘한다.

즉 유연성이 필요한 곳에서 추상화를 사용해서 유연하고 확장 가능한 설계를 만들어야 한다.

<img src="./image/그림 2.14.png">

### 추상 클래스와 인터페이스 트레이드오프
할인 정책을(DiscountPolicy)를 살펴보면 할인 조건이 없는 경우 getDiscountAmount()를 호출하지 않아서 0원을 반환할 것이라는 사실을 가정하기 때문에 부모 클래스와 자식 클래스를 개념적으로 결합시킨다.

이를 해결하기 위해서 기존 할인 정책을 인터페이스로 변경하고 예외 할인 정책은 getDiscountAmount() 메서드가 아닌 calculateDiscountAmount() 오퍼레이션을 오버라이딩하도록 변경한다.

#### [할인 정책](./movie/step02/DiscountPolicy.java)
```
public interface DiscountPolicy {
    Money calculateDiscountAmount(Screening screening); // 할인 정책을 통해서 할인 요금을 계산해서 반환하는 추상 메서드
}
```

#### [기본 할인 정책](./movie/step02/DefaultDiscountPolicy.java)
```
public abstract class DefaultDiscountPolicy implements DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>();

    public DefaultDiscountPolicy(DiscountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    @Override
    public Money calculateDiscountAmount(Screening screening) { // 할인 정책을 통해서 할인 요금을 계산해서 반환하는 메서드
        for(DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening Screening); // 할인된 금액을 반환하는 추상 메서드
}
```

#### [예외 할인 정책](./movie/step02/NoneDiscountPolicy.java)
```
public class NoneDiscountPolicy implements DiscountPolicy {
    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
```

<img src="./image/그림 2.15.png">

이상적으론 인터페이스를 사용한 설계가 더 좋지만 현실적으로 예외 할인 정책(NoneDiscountPolicy)만을 위해 인터페이스를 추가하는 것은 과하다고 생각이 들 수도 있다. ➔ 변경 전에도 기능이라는 관점으로는 정상적으로 동작했기 때문이고, 오버 엔지니어링일 수도 있다.

구현과 관련된 모든 것들은 트레이드오프의 대상이 될 수 있는데 비록 아주 사소한 결정이더라고 트레이드오프를 통해 얻어진 결론과 그렇지 않은 결론 사이의 차이는 크기 때문에 항상 고민하고 트레이드오프해야 한다.

### 코드 재사용
상속은 코드를 재사용하기 위해 널리 사용되는 방법이지만 가장 좋은 밥은 아니며, 코드 재사용을 위해서는 상속보다 합성이 더 좋은 방법이라는 이야기를 한다.

합성은 다른 객체의 인스턴스를 자신의 인스턴스 변수로 포함해서 재사용하는 방법을 말한다. 기능적으로 상속과 합성은 동일하지만 믾은 사람들이 상속 대신 합성을 선호한다.

### 상속
합성은 다른 객체의 인스턴스를 자신의 인스턴스 변수로 포함해서 재사용하는 방법을 말한다.

#### 상속의 단점
* 캡슐화를 위반한다.
  * 상속을 이용하기 위해서는 부모 클래스의 내부 구조를 잘 알고 있어야 한다. ➔ 결과적으로 부모 클래스의 구현이 자식 클래스에게 노출되기 때문에 캡슐화가 약해진다.
  * 캡슐화의 약화는 자식클래스가 부모클래스에게 강하게 결합되도록 만든다. ➔ 부모 클래스를 변경할 때 자식 클래스도 함께 변경될 확률을 높인다.
* 설계를 유연하지 못하게 만든다.
  * 부모 클래스와 자식 클래스 사이의 관계를 컴파일 시점에 결정한다. ➔ 실행 시점에 객체의 종류를 변경하는 것이 불가능하다.
  
#### 할인 정책 변경 가정해보기
실행 시점에 금액 할인 정책인 영화를 비율 할인 정책으로 변경한다고 가정해본다.

상속을 사용한 설계에서는 ➔ 대부분의 언어는 이미 생성된 객체의 클래스를 변경하는 기능을 지원하지 않기 때문에 문제를 해결하려면 변경할 객체를 생성해서 기존의 상태를 복사하는 것밖에 없다.

합성을 사용한 설계에서는 ➔ changeDiscountPolicy() 라는 메서드를 추가해서 인스턴스 변수만 변경해주면 해결된다.
```
public class Movie {
    private DiscountPolicy discountPolicy;

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
```
```
Movie avater = new Movie(
    "아바타",
    Duration.ofMinutes(120),
    Money.wons(10000),
    new AmountDiscountPolicy(Money.wons(800), ...)
);

avatar.changeDiscountPolicy(new PercentDiscountPolicy(0.1, ...));
```

이 예제 또한 코드를 재사용하는 방법이고, 이러한 방법을 합성이라고 한다.

### 합성
영화(Movie)는 요금을 계산하기 위해 할인 정책(DiscountPolicy)의 코드를 재사용하는데 인터페이스에 정의된 메시지를 통해서만 코드를 재사용한느 방법을 합성이라고 한다.

#### 상속이 가지는 문제점 해결
* 캡슐화를 위반한다. ➔ 인터페이스에 정의된 메시지를 통해서만 코드를 재사용하기때문에 효과적으로 캡슐화할 수 있다.
* 설계를 유연하지 못하게 만든다. ➔ 의존하는 인터페이스를 교체하기 쉽기 때문에 설계를 유연하게 만든다.

#### 상속과 합성 무엇을 사용해야 할까?
합성이 상속이 가지는 문제점을 모두 해결한다고, 상속을 사용하지 말라는 것은 아니다. ➔ 동일한 코드를 재사용하는 경우에는 상속보다 합성을 선호하는 것이 옳지만 다형성을 위해 인터페이스를 재사용하는 경우에는 상속과 합성을 둘 다 사용하는 것이 좋다.

#### 협력, 역할, 책임
객체지향에서 가장 중요한 것은 애플리케이션의 기능을 구현하기 위해 협력에 참여하는 객체들 사이의 상호작용이다. ➔ 객체들은 협력에 참여하기 위해 역할을 부여받고 역할에 적합한 책임을 수행한다.

결국 좋은 설계란 적절한 협력을 식별하고 협력에 필요한 역할의 정의한 후에 역할을 수행할 수 있는 적절한 객체에게 적절한 책임을 할당하는 것이다.
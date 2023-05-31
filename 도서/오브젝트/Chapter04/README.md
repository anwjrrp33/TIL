# 04. 설계 품질과 트레이드오프
## 객체지향 설계의 핵심
* 객체지향 설계의 핵심은 역할, 책임, 협력이다.
* `협력`은 애플리케이션의 기능을 구현하기 위해 메시지를 주고받는 객체들 사이의 상호작용이다.
* `책임`은 객체가 다른 객체와 협력하기 위해 수행하는 행동이다.
* `역할`은 대체 가능한 책임의 집합이다.

## 책임 주도 설계
* 역할, 책임, 협력 중에서 가장 중요한 것은 `책임`이다.
* 객체들이 수행할 책임이 적절하게 할당되지 못한 상황에서는 원활한 협력도 기대할 수 없다.
* 역할은 책임의 집합이기 때문에 책임이 적절하지 못하면 역할 역시 협력과 조화를 이루지 못한다.
* 결국 `책임이 객체지향 애플리케이션 전체의 품질을 결정`하는 것이다.

## 객체지향 설계
* 올바른 객체에게 올바른 책임을 할당하면서 낮은 결합도와 높은 응집도를 가진 구조를 창조하는 활동이며, 두 가지 관점이 섞여 있다.
  1. 객체지향 설계의 핵심은 책임이다.
  2. 책임을 할당하는 작업이 응집도와 결합도 같은 설계 품질과 깊이 연과돼 있다.
* 설계는 변경을 위해 존재하고 변경에는 어떤 식으로든 비용이 발생하기 때문에 훌륭한 설계란 합리적인 비용 안에서 변경을 수용할 수 있는 구조를 만드는 것이다.
* 적절한 비용 안에서 쉽게 변경할 수 있는 설계는 응집도가 높고 서로 느슨하게 결합돼 있는 요소로 구성된다.

## 결합도와 응집도의 중요 원칙
* 객체의 상태가 아니라 객체의 행동에 초점을 맞추는 것이다.
* 객체를 단순히 데이터의 집합으로 바라보면 객체 내부 구현을 퍼블릭 인터페이스에 노출시키는 결과를 낳기 때문에 결과적으로 설계가 변경에 취약해진다. ➔ 이런 문제를 피하기 위한 좋은 방법은 객체의 책임에 초점을 맞추는 것이다.

## 01. 데이터 중심의 영화 예매 시스템
### 객체지향 설계 분할 기준
* 상태를 분할의 중심축으로 삼는 방법
  * 객체는 자신이 포함하고 있는 데이터를 조자학하는 데 필요한 오퍼레이션을 정의한다. ➔ 객체의 상태에 초점을 맞춘다.
  * 객체를 독립된 데이터 덩어리로 바라본다.
* 책임을 분할의 중심축으로 삼는 방법
  * 다른 객체가 요청할 수 있는 오퍼레이션을 위해 필요한 상태를 보관한다. ➔ 객체의 행동에 초점을 맞춘다.
  * 객체를 협력하는 공동체의 일원으로 바라본다.

### 데이터를 준비하자
데이터 중심의 설계란 객체 내부에 저장되는 데이터를 기반으로 시스템을 분할하는 방식이다. ➔ 객체가 내부에 저장해야 하는 `데이터가 무엇인가`를 묻는 것으로 시작한다.

영화(Movie)에 저장될 데이터를 결정하는 것으로 시작한다.
```
public class Movie {
    private String title; // 제목
    private Duration runningTime; // 상영시간
    private Money fee; // 기본 요금
    private List<DiscountCondition> discountConditions; // 할인 조건의 목록

    private MovieType movieType; // 열거형으로 이루어진 할인 정책의 종류를 결정하는 영화 종류
    private Money discountAmount; // 할인 금액
    private double discountPercent; // 할인 비율
}
```
```
public enum MovieType { 
    AMOUNT_DISCOUNT,  // 금액 할인 정책
    PERCENT_DISCOUNT, // 비율 할인 정책
    NONE_DISCOUNT     // 미적용
}
```

#### 기존과의 차이점
* 할인 조건의 목록(discountConditions)이 인스턴스 변수로 Movie 안에 직접 포함돼 있다.
* DiscountPolicy라는 별도의 클래스로 분리했던 이전 예제와 달리, 금액 할인 정책에 사용되는 할인금액(discountAmount), 할인비율(discountPercent)을 Movie안에서 직접 정의하고 있다.
* 영화별로 단 하나의 할인정책(DiscountPolicy)를 지정해야 하기 때문에 추가적인 MovieType(열거형)이 필요하다.

데이터 중심의 설계에서는 객체가 포함해야 하는 데이터에 집중한다. ➔ 이 객체가 포함해야 하는 데이터는 무엇인가? 객체의 책임을 결정하기 전에 이런 질문의 반복에 휩쓸려 있다면, 데이터 중심의 설계를 하고있을 확률이 높다.

Movie클래스의 경우처럼 객체의 종류를 저장하는 인스턴스 변수(movieType)와 인스턴스의 종류에 따라 배타적으로 사용될 인스턴스 변수(discountAmount, discountPercent)를 하나의 클래스 안에 함께 포함시키는 방식은 데이터 중심의 설계 안에서 흔히 볼 수 있는 패턴이다.

필요한 데이터를 준비했다면 객체지향의 가장 중요한 원칙은 캡슐화를 위해서 가장 간단한 방법은 내부의 데이터를 반환하는 접근자(accessor)와 데이터를 변경하는 수정자(mutator)를 추가하는 것이다.

데이터 중심의 설계에서 할인 조건을 위해 해야하는 질문은 다음과 같다.
할인 조건을 구현하는데 필요한 데이터는 무엇인가? 현재 할인 조건의 종류를 저장할 데이터가 필요할것 이다.
이를 DiscountConditionType으로 만들어 보자.
```
public enum DiscountConditionType {
    SEQUENCE, // 순번 조건
    PERIOD    // 기간 조건
}
```

할인 조건을 구현하는 DIscountCondition은 할인 조건의 타입을 저장할 인스턴스 변수인 type을 포함하고추가적으로 movie와 마찬가지로 데이터를 가지고 있는다. 또한 캡슐화의 원칙에 따라 속성들을 클래스 외부로 노출해서는 안되기 때문에 getter/setter를 추가한다.
```
public class DiscountCondition { 
    private DiscountConditionType type; // 할인 조건의 타입

    private int sequence; // 순번

    private DayOfWeek dayOfWeek; // 요일
    private LocalTime startTime; // 시작 시간
    private LocalTime endTime; // 종료 시간

    ... getter/setter 메서드
}
```

영화를 예매하기 위해서 구현된 클래스는 아래와 같다.
* [예매(Reservation)](./movie/step01/Reservation.java)
* [고객(Customer)](./movie/step01/Customer.java)
* [상영(Screening)](./movie/step01/Screening.java)
* [영화(Movie)](./movie/step01/Movie.java)
* [할인 조건(DiscountCondition)](./movie/step01/DiscountCondition.java)
<img src="./image/그림%204.1.png">

### 영화를 예매하자
ReservationAgency는 데이터 클래스들을 조합헤서 영화 예매 절차를 구현하는 클래스이다.
* Discountcondition에 대해 루프를 돌면서 할인 가능 여부를 확인한다.
* discountable 변수의 값을 체크하고 적절한 할인 정책에 따라 예매 요금을 계산한다.
```
public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer,
                               int audienceCount) {
        Movie movie = screening.getMovie();

        boolean discountable = false;
        for(DiscountCondition condition : movie.getDiscountConditions()) {
            if (condition.getType() == DiscountConditionType.PERIOD) {
                discountable = screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) &&
                        condition.getStartTime().compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                        condition.getEndTime().compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
            } else {
                discountable = condition.getSequence() == screening.getSequence();
            }

            if (discountable) {
                break;
            }
        }

        Money fee;
        if (discountable) {
            Money discountAmount = Money.ZERO;
            switch(movie.getMovieType()) {
                case AMOUNT_DISCOUNT:
                    discountAmount = movie.getDiscountAmount();
                    break;
                case PERCENT_DISCOUNT:
                    discountAmount = movie.getFee().times(movie.getDiscountPercent());
                    break;
                case NONE_DISCOUNT:
                    discountAmount = Money.ZERO;
                    break;
            }

            fee = movie.getFee().minus(discountAmount).times(audienceCount);
        } else {
            fee = movie.getFee().times(audienceCount);
        }

        return new Reservation(customer, screening, fee, audienceCount);
    }
}
```

지금까진 데이터 중심으로 영화 예매 시스템을 설계하는 방법을 살펴봤고, 책임 중심의 설계 방법과 비교하면서 두 방법의 장단점을 살펴본다.

## 02. 설계 트레이드오프
캡슐화, 응징도, 결합도를 사용해서 장단점을 비교하고 캡슐화, 응집도, 결합도 라는 3가지 품질 척도의 의미를 알아본다.

### 캡슐화
* 객체지향에서는 변경 가능성이 높은 부분은 내부에 숨기고, 외부에는 상대적으로 안정적인 부분을 공개함으로써 변경의 여파를 통제할 수 있다. ➔ 이처럼 변경 가능성이 높은 부분을 객체 내부로 숨기는 추상화 기법이 캡슐화이다.
* 변경 가능성이 높은 부분을 구현, 상대적으로 안정적인 부분을 인터페이스라고 부른다. ➔ 이는 변경의 정도에 따라 구현과 인터페이스를 분리하고 외부에서는 인터페이스에만 의존하도록 관계를 조절하는 것이다.
* 객체지향에서 복잡성을 취급하는 주요한 추상화 방법은 캡슐화이다. 이는 객체지향 언어를 사용한다고 해서 애플리케이션이 자동적으로 잘 캡슐화 되지 않는다. 설계 과정동안 지속적으로 캡슐화를 목표로 인식할때만 달성될 수 있다. ➔ 즉 객체지향을 하기위해서 캡슐화를 하는 것이 아닌 캡슐화를 하면 자연스럽게 객체지향적인 설계와 구현이 되는 것이다.
* 요구사항이 변경되기 때문이고, 캡슐화가 중요한 이유는 불안정한 부분과 안정 적인 부분을 분리해서 변경의 영향을 통제할 수 있기 때문이다. ➔ 변경이 일어났을 때 영향을 최소한 줄일 수 있다.

결국 캡슐화란? 변경 가능성이 높은 부분을 객체 내부로 숨기는 추상화 기법이다. 

### 응집도와 결합도
#### 응집도
* 응집도는 모듈에 포함된 내부 요소들이 연관돼 있는 정도를 나타낸다.
* 모듈 내의 요소들이 하나의 목적을 달성하기 위해 협력한다면 그 모듈은 높은 응집도를 갖는다고 할 수 있다.
* 객체지향의 관점에서 응집도는 객체 또는 클래스에 얼마나 관련 높은 책임을 할당했는지를 나타낸다.

#### 결합도
* 결합도는 의존성의 정도를 나타내며, 다른 모듈에 대해 얼마나 많은 지식을 갖고 있는지를 나타내는 척도다.
* 어떤 모듈이 다른 모듈에 대해 너무 자세한 내용까지 알고 있다면, 두 모듈은 높은 결합도를 갖는다.
* 객체지향의 관점에서 결합도는 객체 또는 클래스가 협력에 필요한 적절한 수준의 관계만을 유지하고 있는지를 나타낸다.

#### 높은 응집도와 낮은 결합도를 추구하는 이유
높은 응집도와 낮은 결합도를 가진 설계를 추구해야 하는 이유는 `설계를 변경하기 쉽게 만들기 때문`이다. 

변경의 관점에서 응집도란 변경이 발생할 때 모듈 내부에서 발생하는 변경의 정도로 측정할 수 있다. ➔ 하나의 변경을 수용하기 위해 모듈 전체가 함께 변경된다면 높은 응집도를 갖는다.

결합도 역시 변경의 관점에서 보면, 한 모듈이 변경되기 위해서 다른 모듈의 변경을 요구하는 정도로 측정할 수 있다. ➔ 하나의 모듈을 수정할 때 얼마나 많은 모듈을 함께 수정해야 하는지를 나타낸다.

#### 변경과 응집도 사이의 관계
응집도가 높은 설계에서는 하나의 요구사항 변경을 반영하기 위해 오직 하나의 모듈만 수정하면 되고, 응집도가 낮은 설계에서는 하나의 원인에 의해 변경해야 하는 부분이 다수의 모듈에 분산돼 있기 때문에 여러 모듈을 동시에 수정해야 한다.

응집도가 높을수록? ➔ 변경의 대상과 범위가 명확해지기 때문에 코드를 변경하기 쉬워진다.
<img src="./image/그림%204.2.png">

#### 변경과 결합도 사이의 관계
낮은 결합도를 가진 왼쪽의 설계에서는 모듈 A를 변경했을 때 오직 하나의 모듈만 영향을 받는다는 것을 알 수 있고, 높은 결합도를 가지면 변경했을 때 모든 모듈을 동시에 변경해야 한다.

<img src="./image/그림%204.3.png">

## 03. 데이터 중심의 영화 예매 시스템의 문제점
데이터 중심의 설계가 가진 대표적인 문제점은 다음과 같다.
* 캡슐화 위반
* 높은 결합도
* 낮은 응집도

### 캡슐화 위반
데이터를 중심으로 설계한 영화(Movie) 클래스를 보면 오직 메서드를 통해서만 객체 내부의 상태에 접근할 수 있다. 예를 들어, fee의 값을 읽거나 수정하기 위해서는 getter/setter 메서드를 사용해야 한다.
```
public class Movie {
    private Money fee;

    public Money getFee() {
        return fee;
    }

    public void setFee(Money fee) {
        this.fee = fee;
    }
}
```
위 코드 처럼 getter/setter를 사용하면 캡슐화의 원칙을 지키는 것 처럼 보인다.

하지만 getter, setter 는 객체 내부의 상태에 대한 어떤 정보도 캡슐화하지 못한다. getFee()와 setFee() 메서드는 영화(Movie) 클래스 내부에 Money 타입의 fee라는 이름의 인스턴스 변수가 존재한다는 사실을 퍼블릭 인터페이스에 노골적으로 드러낸다.

Movie가 캡슐화의 원칙을 어기게 되는 근본적인 원인은 객체가 수행할 책임이 아니라 내부에 저장할 데이터에 초점을 맞췄기 때문이다.

설계를 할때 협력에 관해 고민하지 않으면 캡슐화를 위반하는 과도한 접근자와 수정자를 가지게 되는 경향이 있다.

이처럼 `접근자와 수정자에 과도하게 의존하는 설계 방식을 추측에 의한 설계 전략`이라고 부른다. ➔ 이 전략은 객체가 사용될 협력을 생각하지 않고, 다양한 상황에서 사용될 수 있을 것이라는 막연한 추측을 기반으로 한다.

결과적으로 내부 구현이 퍼블릭 인터페이스에 노출되고, 캡슐화의 원칙을 위반하는 변경에 취약한 설계가 된다.

### 높은 결합도
내부 구현이 객체의 인터페이스에 드러난다는 것은? ➔ 강하게 결합된다는 것을 의미하고, 내부 구현이 변경되면 해당 인터페이스를 의존하는 모든 클라이언트가 함께 변경해야 한다.

```
public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        ...
        Money fee;
        if (discountable) {
            ...
            fee = movie.getFee().minus(discountedAmount).times(audienceCount); // getter를 이용한 퍼블릭 인터페이스
        } else {
            fee = movie.getFee(); // getter를 이용한 퍼블릭 인터페이스
        }
    }
}
```
영화(Movie) 클래스에서 Money 타입을 가져오는 getFee()의 타입이 변경되면? ➔ getFee() 메서드의 반환 타입도 함께 수정해야 하기 때문에 데이터 중심 설계는 객체의 캡슐화를 약화시키기 때문에 클라이언트가 객체의 구현에 강하게 결합된다.

#### 데이터 중심 설계의 단점
결합도 측면에서 데이터 중심의 설계가 가지는 단점은 여러 객체들을 사용하는 제어 로직이 특정 객체 안에 집중되기 때문이다. ➔ 하나의 제어 객체가 다수의 데이터 객체에 강하게 결합하고, 어떤 데이터 객체를 변경해도 제어 객체를 함께 변경해야 하는 문제가 발생한다.

#### 데이터 중심 영화 시스템의 단점
* 데이터 중심의 영화 시스템을 보면 대부분의 제어 로직을 가지고 있는 예매(ReservationAgency)가 모든 데이터 객체에 의존한다는 것을 알 수 있다.
* 만약 할인 조건(DiscountCondition), 상영(Screening) 중 어떤 것을 수정해도 예매(ReservationAgency)도 함께 수정해야 한다.
* 예매(ReservationAgency)는 모든 의존성이 모이는 결합의 집결지이다.

<img src="./image/그림%204.4.png">

### 낮은 응집도
서로 다른 이유로 변경되는 코드가 하나의 모듈 안에 공존할 때 모듈의 응집도가 낮다고 말한다.

#### 변경과 응집도 사이의 관계
다음과 같은 수정사항이 발생하면 예매(ReservationAgency)의 코드를 수정해야 한다.
* 할인 정책별로 할인 요금을 계산하는 방법이 변경될 경우
* 할인 정책이 추가될 경우
* 할인 조건이 추가되는 경우
* 할인 조건별로 할인 여부를 판단하는 방법이 변결될 경우
* 예매 요금을 계산하는 방법이 변경될 경우

낮은 응집도는 두 가지 측면에서 설계에 문제를 일으킨다.
1. 변경의 원인이 다른 코드들의 하나의 모듈 안에 뭉쳐있어 변경과 아무 상관 없는 코드들까지 영향을 받는다.
   * 할인 정책을 추가하는 코드가 할인 조건을 판단하는 코드에 영향을 미칠 수 있다.
2. 하나의 요구사항 변경을 위해 여러 모듈을 동시에 수정해야 한다.
   * MoneyType 열거형(Enum) 값 추가
   * ReservationAgency Switch에 case 추가
   * Movie에 새로운 할인 정책 위해 필요한 데이터 추가

현재의 설계는 새로운 할인 정책이나 할인 조건을 추가하기 위해 하나 이상의 클래스를 동시에 수정해야 한다. ➔ 어떤 요구사항 변경을 수용하기 위해 하나 이상의 클래스가 흔들리면 설계의 응집도가 낮다는 증거이다.

#### 단일 책임 원칙(Single Responsibility Principle, SRP)
* 로버트 마틴은 모듈의 응집도가 변경과 연관이 있다는 사실을 강조하기 위해서 단일 책임 원칙이라는 설계 원칙을 제시했다.
* 단일 책임 원칙이란 클래스는 단 한 가지의 변경 이유만 가져야 한다는
것이다. ➔ 한 클래스의 하나의 책임만 가지는 것을 의미하며 목적과 취지에 맞는 속성과 메소드로 구성해야 합니다.
* 단일 책임 원칙을 올바르게 지키면 클래스의 응집도를 높일 수 있다.

## 04. 자율적인 객체를 향해
### 캡슐화를 지켜라
객체에게 의미 있는 메서드는 객체가 책임져야 하는 무언가를 수행하는 메서드다. 속성의 가시성을 private으로 설정했다고 해도 접근자와 수정자를 통해 속성을 외부로 제공하고 있다면 캡슐화를 위반하는 것이다.

아래와 같은 코드를 예시로 어떤 문제가 있는지 살펴본다.
```
public Rectangle {
    private int left;
    private int top;
    private int right;
    private int bottom;

    public int getLeft() { return left; }
    public void setLeft(int left) { this.left = left; }

    public int getTop() { return top; }
    public void setTop(int top) { this.top = top; }

    public int getRight() { return right; }
    public void setRight(int right) { this.right = right; }

    public int getBottom() { return bottom; }
    public void setBottom(int bottom) { this.bottom = bottom; }

}
```

해당 코드를 사용하기 위해서 외부의 코드는 아래와 같이 구현돼 있을 것이다.
```
class AnyClass {
    void anyMethod(Rectangle rectangle, int multiple) {
        rectangle.setRight(rectangle.getRight() * multiple);
        rectangle.setBottom(rectangle.getBottom() * multiple);
        ...
    }
}
```

#### 코드의 문제점
1. 코드 중복이 발생할 확률이 높다.
   * 너비와 높이를 증가시키는 코드가 필요하다면 아마 getRight, getBottom 메서드를 호출하고 setRight, setBottom 메서드를 수정하는 유사한 로직이 존재하고, 코드 중복을 초래할 수 있다.
2. 변경에 취약하다.
   * right와 bottom대신 length와 height를 이용해서 사각형을 표현하거나 type을 변경한다고 하면 해당 메서드를 사용하는 클라이언트들은 모두 수정 해주어야 한다.

#### 해결 방법
해결 방법은 결국 캡슐화를 강화시켜서 내부에서 너비와 높이를 조절하는 로직을 캡슐화하는 것이다.
```
class Rectangle {
    public void enlarge(int multiple) {
        right *= multiple;
        bottom *= multiple;
    }
}
```

### 스스로 자신의 데이터를 책임지는 객체
* 우리가 상태와 행동을 객체라는 하나의 단위로 묶는 이유는 객체 스스로 자신의 상태를 처리할 수 있게 하기 위해서이다.
* 객체 내부의 데이터보다 객체가 협력하며 수행할 책임을 정의하는 오퍼레이션이 더 중요하다.
* 아래 두 질문을 조합하면 새로운 데이터 타입을 만들 수 있다.
  * 이 객체가 어떤 데이터를 포함해야 하는가
  * 이 객체가 데이터에 대해 수행해야 하는 오퍼레이션은 무엇인가

#### 예매(ReservationAgency)로 새어나간 데이터에 대한 책임 개선하기
1. 할인 조건 타입 계산하기
```
public class DiscountCondition {
    private DiscountConditionType type;

    private int sequence;

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    ... 생성자 생략

    public DiscountConditionType getType() {
        return type;
    }

    public boolean isDiscountable(DayOfWeek dayOfWeek, LocalTime time) {
        if (type != DiscountConditionType.PERIOD) {
            throw new IllegalArgumentException();
        }

        return this.dayOfWeek.equals(dayOfWeek) &&
                this.startTime.compareTo(time) <= 0 &&
                this.endTime.compareTo(time) >= 0;
    }

    public boolean isDiscountable(int sequence) {
        if (type != DiscountConditionType.SEQUENCE) {
            throw new IllegalArgumentException();
        }

        return this.sequence == sequence;
    }
}
```

2. 영화 요금 계산하기와 할인 여부 판단하기
```
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private List<DiscountCondition> discountConditions;

    private MovieType movieType;
    private Money discountAmount;
    private double discountPercent;

    ... 생성자 생략

    public MovieType getMovieType() {
        return movieType;
    }

    public Money calculateAmountDiscountedFee() {
        if (movieType != MovieType.AMOUNT_DISCOUNT) {
            throw new IllegalArgumentException();
        }

        return fee.minus(discountAmount);
    }

    public Money calculatePercentDiscountedFee() {
        if (movieType != MovieType.PERCENT_DISCOUNT) {
            throw new IllegalArgumentException();
        }

        return fee.minus(fee.times(discountPercent));
    }

    public Money calculateNoneDiscountedFee() {
        if (movieType != MovieType.NONE_DISCOUNT) {
            throw new IllegalArgumentException();
        }

        return fee;
    }

    public boolean isDiscountable(LocalDateTime whenScreened, int sequence) { // 할인 여부를 판단하는 메서드
        for(DiscountCondition condition : discountConditions) {
            if (condition.getType() == DiscountConditionType.PERIOD) {
                if (condition.isDiscountable(whenScreened.getDayOfWeek(), whenScreened.toLocalTime())) {
                    return true;
                }
            } else {
                if (condition.isDiscountable(sequence)) {
                    return true;
                }
            }
        }

        return false;
    }
}
```

3. 

#### 개선 전 설계와 개선 후 설계
<img src="./image/그림%204.4.png"><img src="./image/그림%204.5.png">

## 05. 하지만 여전히 부족하다

### 캡슐화 위반

#### 캡슐화의 진정한 의미
캡슐화란 변할 수 있는 어떤 것이라도 감추는 것이다. 그것이 속성의 타입이건, 할인 정책의 종류건 상관 없이 `내부 구현의 변경으로 인해 외부의 객체가 영향을 받는다면 캡슐화를 위반한 것`이다. 설계에서 변하는 것이 무엇인지 고려하고 변하는 개념을 캡슐화해야 한다.

### 높은 결합도
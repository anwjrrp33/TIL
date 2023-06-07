# 05. 책임 할당하기
## 데이터 중심 설계의 문제점
* 객체에 상태에 초점을 맞춰서 캡슐화를 위반하기 쉽다.
* 요소들 사이의 결합도가 높아진다.
* 코드를 변경하기 어려워진다.

## 데이터 중심 설계의 문제점 해결 방법
* 데이터가 아닌 책임에 초점을 맞춰서 책임 중심 설계를 해야 한다.

## GRASP 패턴
* 각 객체에 책임을 부여하는 것으로 책임을 부여하는 원칙들을 말하고 있는 패턴이다.
* 구체적인 구조는 없지만 총 9가지의 원칙이 있다.

## 01. 책임 주도 설계를 향해
데이터 중심의 설계에서 책임 중심의 설계로 전환하기 위해서는 다음의 두 가지 원칙을 따라야 한다.
* 데이터보다 행동을 먼저 결정하라
* 협력이라는 문맥 안에서 책임을 결정하라

### 데이터보다 행동을 먼저 결정하라
* 책임 중심의 설계에서는 `이 객체가 수행해야 하는 책임은 무엇인가`를 결정한 후에 `이 책임을 수행하는 데 필요한 데이터는 무엇인가`를 결정하는데 객체의 행동, 즉 책임을 먼저 결정한 후에 객체의 상태를 결정한다.
* 객체지향 설계에서는 객체에게 적절한 책임을 할당해야 하는데 `객체에게 어떤 책임을 할당해야 하는가?`의 해결은 협력에서 찾을 수 있다.

### 협력이라는 문맥 안에서 책임을 결정하라
* 객체에게 할당된 책임이 협력에 어울리지 않는다면 그 책임은 나쁜 것이며, 객체에게 할당된 책임이 조금 어색해 보이더라도 협력에 적합하다면 그 책임은 좋은 것이기 때문에 `책임은 객체의 입장이 아니라 객체가 참여하는 협력에 적합`해야 한다.
* 객체를 가지고 있기 때문에 메시지를 보내는 것이 아니며, 메시지를 전송하기 때문에 객체를 갖게 된 것이기 때문에 `객체가 메시지를 선택하는 것이 아니라 메시지가 객체를 선택`해야 한다.

### 책임 주도 설계
책임 주도 설계의 흐름은 아래와 같다.
* 시스템이 사용자에게 제공해야 하는 기능인 시스템 책임을 파악한다.
* 시스템 책임을 더 작은 책임으로 분할한다.
* 분할된 책임을 수행할 수 있는 적절한 객체 또는 열학을 찾아 책임을 할당한다.
* 객체가 책임을 수행하는 도중 가른 객체의 도움이 필요한 경우 이를 책임질 적절한 객체 또는 역할을 찾는다.
* 해당 객체 또는 역할에게 책임을 할당함으로써 두 객체가 협력하게 한다.

`책임 주도 설계의 핵심은 책임을 결정한 후에 책임을 수행할 객체를 결정`하는 것이다.

## 02. 책임 할당을 위한 GRASP 패턴
GRASP 패턴은 "General Responsibility Assignment Software Pattern(일반적인 책임 할당을 위한 소프트웨어 패턴)"의 약자로 `객체에게 책임을 할당할 때 지침으로 삼을 수 있는 원칙들의 집합을 패턴 형식으로 정리`한 것이다.

### 도메인 개념에서 출발하기
* 설계를 시작하기 전에 도메인에 대한 개략적인 모습을 그려 보는 것이 유용한데, 도메인 안에서는 많은 개념들이 존재해서 도메인 개념들을 할당의 대상으로 사용하면 코드에 도메인의 모습을 투영하기 수월해진다.
* `설계를 시작하는 단계에서는 개념들의 의미와 관계가 정확하거나 완벽할 필요가 없다.`
* `이 단계에서는 책임을 할당받을 객체들의 종류와 관계에 대한 유용한 정보를 제공할 수 있다면 충분하고, 중요한 것은 설계를 시작하는 것이지 도메인 개념들을 완벽하게 정리하는 것이 아니다.`

<img src="./image/그림%205.1.png">

### 정보 전문가에게 책임을 할당하라
책임 주도 설계 방식의 첫 단계는 애플리케이션이 제공해야 하는 기능을 애플리케이션의 책임으로 생각하는 것으로 이 책임을 애플리케이션에 대해 전송된 메시지로 간주하고 이 메시지를 책임질 첫 번째 객체를 선택하는 것으로 설계를 시작한다.

메시지는 메시지를 수신할 객체가 아니라 메시지를 전송할 객체의 의도를 반영해서 결정해야 하기 때문에 두 질문에 집중하면 된다.
* 메시지를 전송할 객체는 무엇을 원하는가?
  * 영화를 예매한다라는 기능이 주어졌을 때를 생각해보면 협력을 시작하는 객체는 미정이지만 객체가 원하는 것은 분명해 보이고, 메시지의 이름을 예매하라가 적절하다고 생각할 수 있다.
* 메시지를 수신할 적합한 객체는 누구인가?
  * 객체는 자신의 상태를 스스로 처리하는 자율적인 존재여야 하고, 객체의 책임과 책임을 수행하는 데 필요한 상태는 동일한 객체 안에 존재해야 한다.
  * 객체에게 책임을 할당하는 첫 번째 원칙은 책임을 수행할 정보를 알고 있는 객체에게 할당하는 것이다.

#### INFORMATION EXPERT(정보 전문가) 패턴
기본 원칙은 정보 전문가에게 책임을 할당하는 것으로, INFORMATION EXPERT 패턴에 따라 메시지를 수신할 적당한 객체를 선택해서 책임을 지게해야 한다.

여기서 이야기하는 정보는 데이터와 다르다는 사실에 주의해야 하는데 책임을 수행하는 객체가 정보를 '알고' 있다고 해서 그 정보를 '저장'하고 있을 필요는 없다.

어떤 방식이건 `정보 전문가가 데이터를 반드시 저장하고 있을 필요는 없다`는 사실을 이해하는 것이 중요하다.


아래 그림은 영화 예매 프로그램의 정보 전문가 패턴이다.
1. 예매하라
   * 상영(Screening)은 영화 예매를 위한 위한 정보 전문가라서 예매에 관한 책임을 할당 받았다.
   * 가격을 계산하는 것은 상영(Screening)이 처리하지 못하기 때문에 외부에 도움을 요청하면 외부로 전송하는 메시지가 추가되고, 새로운 메세지가 새로운 객체의 책임으로 할당되는데 이를 통해서 연쇄적인 메시지  전송과 수신을 통한 협력 공동체가 구성된다.
2. 가격을 계산하라
    * 가격에 대한 정보를 알고 있는 정보 정문가는 영화(Movie)이기 때문에 메시지를 수신할 객체로 선택된다.
    * 스스로 처리할 수 없는 한 가지 일은 할인 여부를 판단하는 것으로 외부의 도움을 요청해야 한다.
3. 할인 여부를 판단하라
   * 할인 여부에 대한 정보를 알고 있는 정보 전문가는 할인 여부(DiscountCondition)이기 때문에 메시지를 수신할 객체로 선택되고, 책임을 지게 된다.

<img src="./image/INFORMATION%20EXPERT.png">

INFORMATION EXPERT 패턴은 객체에게 책임을 할당할 때 가장 기본이 되는 책임 할당 원칙으로, INFORMATION EXPERT 패턴에서 객체란 상태와 행동을 함께 가지는 단위라는 객체지향의 가장 기본적인 원리를 책임 할당의 관점에서 표현한다.

INFORMATION EXPERT 패턴을 따르는 것만으로도 자율성이 높은 객체들로 구성된 협력 공동체를 구축할 가능성이 높아진다.

### 높은 응집도와 낮은 결합도
설계는 트레이드오프 활동으로 무수히 많은 설계가 존재하는데 설계를 진행하다 보면 설계 중에서 한가지를 선택하는 경우가 발생하고, 올바른 책임 할당을 위해 정보 전문가 패턴 이외의 다른 책임 할당 패턴들을 함께 고려해야 한다.

예를 들어, 영화(Movie) 대신 상영(Screening)이 직접 할인 여부(DiscountCondition)와 협력하면 기능적인 측면에서는 동일하지만 협력하는 객체가 다르다는 차이점이 존재한다.

<img src="./image/그림%205.2.png">

이런 설계를 도입하지 않은 이유는 응집도와 결합도에 차이 때문으로 협력 패턴 중에서 높은 응집도와 낮은 결합도를 얻을 수 있는 설계가 있다면 그 설계를 선택해야 한다.

GRASP에서는 이를 `LOW COUPLING(낮은 결합도)` 패턴과 `HIGH COHESION(높은 응집도)` 패턴이라고 부른다.
* LOW COUPLING(낮은 결합도) 패턴
  * 객체 간의 낮은 의존성을 의미한다.
  * 의존성을 줄이면 코드의 변경이 있을 경우 사이드 이펙트를 줄이고, 객체의 재사용성을 증가시킨다.
  * 영화와 할인 여부는 이미 결합되어 있는데 상영이 할인 여부와 협력하게 되면 새로운 결합도가 추가된다.
* HIGH COHESION(높은 응집도) 패턴
  * 높은 응집도를 유지할 수 있게 책임을 할당해야 한다.
  * 같은 책임을 갖는 객체들끼리 묶어서 객체에게 책임을 할당하는 과정을 올바르게 해야 높은 응집도를 가진다.
  * 할인 여부에서 예매 요금을 계산하는 방식이 변경되면 상영도 함께 변경을 해야 하는데 결과적으로 상영이 자신이 짊어지던 책임과는 다른 이유로 변경되는 책임을 짊어지게 된다.
  
LOW COUPLING 패턴과 HIGH COHESION 패턴은 설계를 진행하면서 책임과 협력의 품질을 검 토하는 데 사용할 수 있는 중요한 평가 기준으로 책임을 할당하고 `코드를 작성하는 매순간마다 LOW COUPLING과 HIGH COHESION의 관점에서 설계 품질을 검토하면 단순하면서도 재사용 가능하고 유연한 설계를 얻을 수 있다.`

### 창조자에게 객체 생성 책임을 할당하라
영화 예매 협력은 예매(Reservation) 인스턴스를 생성하는 것으로 어떤 객체에게는 인스턴스를 생성할 책임을 할당해야 하는데 `CREATOR(창조자) 패턴`은 이 같은 경우에 사용할 수 있는 책임 할당 패턴으로서 `객체를 생성할 책임을 어떤 객체에게 할당할지에 대한 지침을 제공`한다.

#### CREATOR(창조자) 패턴
객체 A를 생성해야 할 때 어떤 객체에게 객체 생성 책임을 할당할 지는 아래 조건을 가장 많이 만족하는 B에게 생성 책임을 할당하면 된다.
* B가 A를 포함하거나 참조한다.
* B가 A를 기록한다.
* B가 A를 긴밀하게 사용한다.
* B가 A를 초기화하는 데 필요한 데이터를 가지고 있다.(B가 A에 대한 정보 전문가)

CREATOR(창조자) 패턴의 조건을 최대한 만족하는 객체에게 객체 생성 책임을 할당하면 되는데 영화 예매의 경우 상영이 창조자로 선택된다.

<img src="./image/그림%205.3.png">

## 03. 구현을 통한 검증
### 상영(Screening)
```
public class Screening {
    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;

    public Reservation reserve(Customer customer, int audienceCount) {
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }

    private Money calculateFee(int audienceCount) {
        return movie.calculateMovieFee(this).times(audienceCount);
    }

    public LocalDateTime getWhenScreened() {
        return whenScreened;
    }

    public int getSequence() {
        return sequence;
    }
}
```
### 영화(Movie)
```
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private List<DiscountCondition> discountConditions;

    private MovieType movieType;
    private Money discountAmount;
    private double discountPercent;

    public Money calculateMovieFee(Screening screening) {
        if (isDiscountable(screening)) {
            return fee.minus(calculateDiscountAmount());
        }

        return fee;
    }

    private boolean isDiscountable(Screening screening) {
        return discountConditions.stream()
                .anyMatch(condition -> condition.isSatisfiedBy(screening));
    }

    private Money calculateDiscountAmount() {
        switch(movieType) {
            case AMOUNT_DISCOUNT:
                return calculateAmountDiscountAmount();
            case PERCENT_DISCOUNT:
                return calculatePercentDiscountAmount();
            case NONE_DISCOUNT:
                return calculateNoneDiscountAmount();
        }

        throw new IllegalStateException();
    }

    private Money calculateAmountDiscountAmount() {
        return discountAmount;
    }

    private Money calculatePercentDiscountAmount() {
        return fee.times(discountPercent);
    }

    private Money calculateNoneDiscountAmount() {
        return Money.ZERO;
    }
}
```

### 할인 정책(MovieType)
```
public enum MovieType {
    AMOUNT_DISCOUNT,    // 금액 할인 정책
    PERCENT_DISCOUNT,   // 비율 할인 정책
    NONE_DISCOUNT       // 미적용
}
```

### 할인 조건(DiscountCondition)
```
public class DiscountCondition {
    private DiscountConditionType type;
    private int sequence;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public boolean isSatisfiedBy(Screening screening) {
        if (type == DiscountConditionType.PERIOD) {
            return isSatisfiedByPeriod(screening);
        }

        return isSatisfiedBySequence(screening);
    }

    private boolean isSatisfiedByPeriod(Screening screening) {
        return dayOfWeek.equals(screening.getWhenScreened().getDayOfWeek()) &&
                startTime.compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getWhenScreened().toLocalTime()) <= 0;
    }

    private boolean isSatisfiedBySequence(Screening screening) {
        return sequence == screening.getSequence();
    }
}
```

### 할인 조건(DiscountCondition) 개선하기
영화 예매의 구현은 완료됬지만 몇 가지 문제점이 존재하는데 가장 큰 문제점은 변경에 취약한  할인 조건(DiscountCondition) 클래스를 포함하고 있다는 점으로 세 가지 이유가 존재한다.
* 새로운 할인 조건 추가
  * if ~ else 구문을 수정해야 하고, 새로운 데이터를 요구하면 속성을 추가해야 한다.
* 순번 조건을 판단하는 로직 변경
  * 순번 조건을 판단하는 데 필요한 데이터가 변경되면 속성을 변경해야 한다.
* 기간 조건 판단하는 로직이 변경되는 경우
  * 기간 조건을 판단하는 데 필요한 데이터가 변경된다면 속성을 변경해야 한다.

할인 조건(DiscountCondition)은 하나 이상의 변경 이유를 가지기 때문에 응집도가 낮다는 것을 의미하고, `응집도가 낮다는 것은 서로 연관성이 없는 기능이나 데이터가 하나의 클래스 안에 뭉쳐져 있다는 것을 의미`한다. 따라서 `낮은 응집도가 초래하는 문제를 해결하기 위해서는 변경의 이유에 따라 클래스를 분리`해야 한다.

일반적으로 설계를 개선하는 작업은 변경의 이유가 하나 이상인 클래스를 찾는 것으로부터 시작하는 것이 좋지만 변경의 이유를 찾는 것이 생각보다 어렵지만 몇 가지 패턴이 존재해서 패턴을 이해하면 생각보다 쉽게 알아낼 수 있다.

첫 번째는 인스턴스 변수가 초기화되는 시점을 살펴보는 것이다.
* 응집도가 높은 클래스는 인스턴스를 생성할 때 모든 속성을 함께 초기화하지만, 응집도가 낮은 클래스는 객체의 속성 중 일부만 초기화한다. 따라서 `함께 초기화되는 속성을 기준으로 코드를 분리`해야 한다.

두 번째는 메서드들이 인스턴스 변수를 사용하는 방식을 살펴보는 것이다.
* 모든 메서드가 객체의 모든 속성을 사용한다면 클래스의 응집도는 높다고 볼 수 있다. 반면 메서드들이 인스턴스 변수를 사용하는 속성에 따라 그룹이 나뉜다면 클래스의 응집도가 낮다고 볼 수 있다.
* 이 경우 클래스의 응집도를 높이기 위해서는 `속성 그룹과 해당 그룹에 접근하는 메서드 그룹을 기준으로 코드를 분리`해야 한다.

#### 클래스 응집도 판단하기
* 클래스가 하나 이상의 이유로 변경된다면, 변경의 이유를 기준으로 클래스를 분리하라.
* 클래스의 인스턴스를 초기화하는 시점에 경우에 따라 서로 다른 속성들을 초기화 하고 있다면, 초기화되는 속성의 그룹을 기준으로 클래스를 분리하라.
* 메서드 그룹이 속성 그룹을 사용하는지 여부로 나뉜다면, 속성 그룹을 기준으로 클래스를 분리하라.

할인 조건(DiscountCondition) 클래스에는 낮은 응집도를 암시하는 세 가지 징후가 모두 들어있고, 변경의 이유에 따라 여러 개의 클래스로 분리해야 한다.

### 타입 분리하기
#### 순번 조건(SequenceCondition)과 기간 조건(PeriodCondition)
* 할인 조건의 가장 큰 문제는 순번 조건과 기간 조건이라는 두 개의 독립적인 타입이 하나의 클래스 안에 공존하고 있다는 점으로 두 개의 클래스를 분리하는 것이다.
```
public class SequenceCondition {
    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    public boolean isSatisfiedBy(Screening screening) {
        return sequence == screening.getSequence();
    }
}
```
```
public class PeriodCondition {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isSatisfiedBy(Screening screening) {
        return dayOfWeek.equals(screening.getWhenScreened().getDayOfWeek()) &&
                startTime.compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
    }
}
```

#### 영화(Movie)
* 첫 번쨰 방법은 영화 안에서 순번 조건과 기간 조건 목록을 따로 유지하는 것이다.
```
public class Movie {
    ... 생략

    private boolean isDiscountable(Screening screening) {
        return checkPeriodConditions(screening) ||
                checkSequenceConditions(screening);
    }

    private boolean checkPeriodConditions(Screening screening) {
        return periodConditions.stream()
                .anyMatch(condition -> condition.isSatisfiedBy(screening));
    }

    private boolean checkSequenceConditions(Screening screening) {
        return sequenceConditions.stream()
                .anyMatch(condition -> condition.isSatisfiedBy(screening));
    }

    ... 생략
}
```

<img src="./image/그림%205.4.png">

하지만 두 가지의 문제점이 발생한다.
1. 영화(Movie) 클래스 양쪽 모두에게 결합된다.
   * 기존에는 하나의 클래스에만 결합돼 있었지만 클래스를 분리한 후에 설계의 관점에서 전체적인 결합도가 높아졌다.
2. 수정 후에 새로운 할인 조건을 추가하기가 더 어려워졌다.
   * 새로운 할인 조건 클래스가 추가되면 List를 Movie의 인스턴스 변수로 추가해야 하고, 할인 조건을 만족하는지 여부를 판단하는 메서드도 추가해야 한다.

### 다형성을 통해 분리하기
영화(Movie)의 입장에서 보면 순번 조건(SequenceCondition)과 기간 조건(PeriodCondition)은 아무 차이도 없는데 할인 가능 여부를 반환해 주기만 하면 영화(Movie) 입장에서는 아무런 상관없기 때문이다.

이 시점엔 자연스럽게 역할의 개념이 무대 위로 등장하는데 순번 조건(SequenceCondition)과 기간 조건(PeriodCondition)은 동일한 책임을 수행한다는 것은 동일한 역할을 수행한다는 것을 의마하고, 이를 통해서 `영화(Movie)는 구체적인 클래스는 모르지만 역할에 대해서만 결합되도록 의존성을 제한`한다.

<img src="./image/그림%205.5.png">

할인 조건(Discountcondition)의 경우에서 알 수 있듯이 객체의 암시적인 타입에 따라 행동을 분기해야 한다면 암시적인 타입을 명시적인 클래스로 정의하고 행동을 나눔으로써 응집도 문제를 해결할 수 있다.

할인 조건(DiscountCondition)을 암시적인 타입으로 다시 만들어주고 변화하는 행동에 따라 분리한 타입에 책임을 할당해주면 된다. 객체의 타입에 따라 변하는 행동이 있다면 타입을 분리하고 변화하는 행동을 각 타입의 책임으로 할 당하라는 것이다. GRASP에서는 이를 POLYMORPHISM(다형성) 패턴이라고 부른다.

<img src="./image/그림%205.6.png">

### 변경으로부터 보호하기

### Movie 클래스 개선하기

### 변경과 유연성

## 04. 책임 주도 설계의 대안

### 메서드 응집도

### 객체를 자율적으로 만들자
# 08. 의존성 관리하기
### 잘 설계된 애플리케이션
* 책임의 초점이 명확하고 한 가지 일만 잘하는 작고 응집도가 높은 객체들로 구성된다.
* 작고 응집도가 높은 객체들은 단독으로 수행할 수 있는 작업이 거의 없어서 애플리케이션 구현을 위해서는 다른 객체에게 도움을 요청해야하고 이런 요청이 객체 사이의 협력을 낳는다.

### 협력과 의존성
* 협력은 필수적이지만 설계를 곤경에 빠뜨리는데 객체가 다른 객체에 대해 알 것을 강요한다.
* 다른 객체와 협력하기 위해서는 그런 객체가 존재 여부와 객체가 수신할 수 있는 메시지에 대해서 지식이 필요해서 객체 사이의 의존성을 낳는다.
* 협력을 위해선 의존성이 필요하지만 과도한 의존성은 애플리케이션을 수정하기 어렵게 만든다.
* 객체지향 설계의 핵심은 협력을 위해 필요한 의존성은 유지하면서도 변경을 방해하는 의존성은 제거하는 데 있다.

> 객체지향 설계란 의존성을 관리하는 것이고 객체가 변화를 받아들일 수 있게 의존성을 정리하는 기술이라고 할 수 있다.

## 01. 의존성 이해하기
### 변경과 의존성
어떤 객체가 협력하기 위해 다른 객체를 필요로 할 때 두 객체 사이에 의존성이 존재하는데 의존성은 실행 시점과 구현 시점에 서로 다른 의미를 가진다.
* 실행 시점
  * 의존 객체가 정상적으로 동작하기 위해서는 실행 시에 의존 대상 객체가 반드시 존재해야 한다.
* 구현 시점
  * 의존 대상 객체가 변경될 경우 의존하는 객체도 함께 변경된다.


예를 들어 아래 코드를 보면 기간 조건(Periodcondition)은 DayOfWeek, LocalTime, Screening에 대한 의존성을 가지고 있고, Screening에게 getStartTime 메시지를 전송한다.

실행 시점에 기간 조건(Periodcondition)의 인스턴스가 정상적으로 동작하기 위해선 Screening의 인스턴스가 존재해야하고, Screening의 인스턴스가 존재하지 않거나 getStratTime 메시지를 이해할 수 없다면 isSatisfiedBy 메서드가 예상대로 동작하지 않는다.
```
-- 기간 조건
public class Periodcondition implements Discountcondition {     
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public boolean isSatisfiedBy(Screening screening) {
        return screening.getStartTime(),getDayOfWeek().equals(dayOfWeek) && 
        startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 && 
        endTime.compareTo(screening.getStartTime().toLocalTime())〉= 0;
    } 
}
```

* 의존성
  * 어떤 객체가 예정된 작업을 정상적으로 수행하기 위해 다른 객체를 필요로 하는 경우 두 객체 사이에 의존성이 존재한다고 말한다.
  * 의존성을 방향성을 가지며 항상 단방향이다.
  * 두 요소 사이의 의존성은 `의존되는 요소가 변경될 때 의존하는 요소도 함께 변경될 수 있다는 것을 의미하는데 변경에 의한 영향의 전파 가능성을 암시`한다.

의존성을 다른 방식으로 표기했지만 의존성이 가지는 근본적인 특성은 동일하고, 의존하는 이유에 따라서 UML에서는 다르게 표현할 수 있다.
* 실체화 관계(realization)
* 연관 관계(association)
* 의존 관계(dependency)
* 일반화/특수화 관계(generalization/specialization)
* 합성 관계(composition)
* 집합 관계(aggregation)

<img src="./image/그림%208.3.png">

이번 장에서 다루고 있는 `의존성`은 UML의 의존 관계와는 다르다.
* UML은 두 요소 사이에 존재할 수 있는 다양한 관계의 하나로 `의존 관계`를 정의한다.
* 의존성은 두 요소 사이에 변경에 의해 영향을 주고받는 힘의 역학관계가 존재한다는 사실에 초점을 맞춘다.
* UML에 정의된 모든 관계는 의존성이라는 개념을 포함한다.

> 이번 장에서 말하는 의존성은 단순히 UML에서 이야기하는 의존 관계로 해석하기보다는 UML에서 정의하는 모든 관계가 가지는 공통적인 특성으로 바라봐야 한다.

### 의존성 전이
의존성은 전이될 수 있기 때문에 의존성의 종류는 아래와 같이 나눌 수 있다.
* 직접 의존성(direct dependency)
  * 한 요소가 다른 요소에 직접 의존하는 경우를 가르킨다.
* 간접 의존성(indirect dependency)
  * 직접적인 관계는 존재하지 않지만 의존성 전이에 의해 영향이 전파되는 경우를 가르킨다.

<img src="./image/그림%208.4.png">

의존성의 대상은 객체일 수도 있고 모듈이나 더 큰 규모의 실행 시스템일 수도 있지만 의존성의 본질은 변하지 않는다. 의존성이란 `의존하고 있는 대상의 변경에 영향을 받을 수 있는 가능성`이다.

### 런타임 의존성과 컴파일타임 의존성
* 런타임
  * 애플리케이션이 실행되는 시점을 의미한다.
* 컴파일타임
  * 일반적으로 컴파일타임이란 작성된 코드를 컴파일하는 시점을 가리키지만 문맥에 따라서는 코드 그 자체를 가리키기도 한다.
  * 컴파일이 진행되는 시점을 가리키는 것인지 아니면 코드를 작성하는 시점을 가리키는 것인지를 파악하는 것이 중요하다.
* 객체지향 애플리케이션에서 런타임의 주인공은 객체로 런타임 의존성이 다루는 주제는 객체 사이의 의존성이다.
* 코드 관점에서 주인공은 클래스로 컴파일타임 의존성이 다루는 주제는 클래스 사이의 의존성이다.
* 런타임 의존성과 컴파일타임 의존성은 다를 수 있으며, 유연하고 재사용 가능한 코드를 설계를 위해서는 두 종류의 의존성이 서로 달라야 한다.

영화 예매 시스템을 예를 들면 코드 작성 시점의 컴파일 의존성에서는 Movie는 추상클래스인 DiscountPolicy에만 의존하도록 설계되있고, 실제 코드를 봐도 AmountDiscountPolicy나 PercentDiscountPolicy에 대해서 언급조차 하지 않는다.
<img src="./image/그림%208.5.png">

```
public class Movie {
  ...
  private DiscountPolicy discountPolicy;
  
  public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
    ...
    this.discountPolicy = discountPolicy;
  }

  public Money calculateMovieFee(Screening screening) {
    return fee.minus(discountPolicy.calculateDiscountAmount(screening));
  }
}
```

하지만 런타임 의존성에서는 Movie 인스턴스는 실행시점에 DiscountPolicy가 아닌 AmountDiscountPolicy 인스턴스와 PercentDiscountPolicy 인스턴스와 협력해야 한다.

<img src="./image/그림%208.6.png">


Movie는 DiscountPolicy라는 추상 클래스에 컴파일타임 의존성을 가지고, 이를 실행 시에 AmountDiscountPolicy, PercentDiscountPolicy 인스턴스에 대한 런타임 의존성으로 대체하는데 `유연하고 재사용 가능한 설계를 위해서는 동일한 소스코드 구조를 가지고 다양한 실행 구조`를 만들 수 있어야 한다.

어떤 클래스의 인스턴스가 다양한 클래스의 인스턴스와 협력하기 위해서는 협력할 인스턴스의 구체적인 클래스를 알아서는 안 되는데 실제로 협력할 객체가 어떤 것인지는 런타임에 해결해야 한다.

클래스가 협력할 객체의 클래스를 명시적으로 드러내고 있다면 다른 클래스의 인스턴스와 협력할 가능성 자체가 없어지기 때문에 `컴파일타임 구조와 런타임 구조 사이의 거리가 멀면 멀수록 설계가 유연해지고 재사용이 가능`해진다.

### 컨텍스트 독립성
* 구체적인 클래스를 알면 알수록 그 클래스가 사용되는 특정한 문맥에 강하게 결합되기 때문에 클래스는 자신과 협력할 객체의 구체적인 클래스에 대해 알아서는 안 된다.
* 클래스가 사용될 특정한 문맥에 대해 최소한의 가정만으로 이뤄져 있다면 다른 문맥에서 재사용하기가 더 수월해지고 이를 `컨텍스트 독립성`이라고 부른다.
* 설계가 유연해지기 위해서는 가능한 한 자신이 실행될 컨텍스트에 대한 구체적인 정보가 적으면 적을수록 더 다양한 컨텍스트에서 재사용될 수 있기 떄문에 설계가 유연해지고 변경에 탄력적으로 대응할 수 있다.

### 의존성 해결하기
컴파일타임 의존성을 실행 컨텍스트에 맞는 적절한 런타임 의존성으로 교체하는 것을 `의존성 해결`이라고 부르며 의존성을 해결하기 위해서 일반적으로 세 가지 방법을 사용한다.
* 객체를 생성하는 시점에 생성자를 통해 의존성 해결
* 객체 생성 후 setter 메서드를 통해 의존성 해결
* 메서드 실행 시 인자를 이용해 의존성 해결

#### 1. 객체를 생성하는 시점에 생성자를 통해 의존성 해결
* 객체를 생성할 때 인스턴스를 생성자에 인자로 전달하면 된다.
* 인스턴스 모두를 선택적으로 전달 받을 수 있도록 부모 클래스 타입의 인자를 받는 생성자를 정의한다.
```
Movie avatar = new Movie("아바타", 120, 10000, new AmountDiscountPolicy(...));
Movie starWars = new Movie("스타워즈", 180, 11000, new PercentDiscountPlicy(...));
```
```
public class Movie {
  public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
    ...
    this.discountPolicy = discountPolicy; 
  }
}
```

#### 2. 객체 생성 후 setter 메서드를 통해 의존성 해결
* 인스턴스를 생성한 후에 메서드를 이용해서 해결할 수도 있다.
* setter를 이용하면 객체를 생성한 이후에도 의존하고 있는 대상을 변경하고 싶을 때 유용하다.
* 하지만 객체가 새성된 후 협력에 필요한 의존 대상을 설정하기 때문에 설정하기 전까지는 객체의 상태가 불완전할 수 있다.
```
Movie avatar = new Movie(...);
avatar.setDiscountPolicy(new AmountDIscountPlicy(...));
```
```
public class Movie {
  ...
  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
    this.discountPolicy = discountPolicy;
  }
}
```
* 생성자와 setter 메서드 방식을 혼합해서 사용하면 안정적이면서도 유연하게 설계할 수 있기 때문에 의존성 해결을 위해 가장 선호되는 방법이다.
```
Movie avatar = new Movie(..., new PercentDiscountPlicy(...));
...
avatar.setDiscountPolicy(new AmountDIscountPlicy(...));
```

#### 3. 메서드 실행 시 인자를 이용해 의존성 해결
* 항상 알 필요까지는 없고 특정 경우에만 일시적으로 알아도 된다면 이 방법을 사용하면 된다.
* 하지만 클래스의 메서드를 호출하는 대부분의 경우에 매번 동일한 객체를 인자로 전달히고 있다면 생성자를 이용하는 방식이나 setter 메서드를 이용해 의존성을 지속적 으로 유지하는 방식으로 변경하는 것이 좋다.
```
public class Movie {
  public Money calculateMovieFee(Screening screening, DiscountPolicy discountPolicy) {
    return fee.minus(discountPolicy.calculateDiscountAmount(screening));
  }
}
```

## 02. 유연한 설계
설계를 유연하고 재사용 가능하게 만들려면 의존성을 관리하는 데 유용한 몇 가지 원칙과 기법을 익혀야 한다.

### 의존성과 결합도
* 의존성은 객체들의 협력을 가능하게 만드는 매개체라는 관점에서는 바람직하지만 의존성이 과하면 문제가 될 수 있다.
* 예를 들어 영화(Movie)가 비율 할인 정책(PercentDiscountPolicy)을 직접 의존하면 다른 종류의 할인 정책이 필요한 문맥에서 영화(Movie)를 재사용할 수 있는 가능성을 없애 버리기 때문에 비율 할인 정책(PercentDiscountPolicy)은 바람직 하지 않고, 추상 클래스인 할인 정책(DiscountPolicy)에 대한 의존성은 바람직하다.
* 바람직한 의존성은 재사용성과 관련이 있고, 다양한 환경에서 재사용할 수 있다면 그 의존성은 바람직한 것이고 할 수 있다.
* 바람직하지 못한 의존성은 컨텍스트에 강하게 결합해서 독립적이지 못하고 재사용하기 위해서 코드를 수정해야하고 구현을 변경하게 만든다.
* 의존성이 바람직할 때 두 요소는 `느슨한/약한 결합도`를 가지며, 의존성이 바람직하지 못할 때 `단단한/강한 결합도`를 가진다.

### 지식이 결합을 낳는다
* 결합도의 정도는 한 요소가 자신이 의존하는 다른 요소에 대해 알고 있는 정보의 양으로 결정된다.
* 많이 알수록 두 요소는 강하게 결합된다는 적은 컨텍스트에서만 재사용이 가능하다는 것을 의미하고, 어울리지 않는 컨텍스트에서 캘래스의 인스턴스를 사용하기 위해서는 할 수 있는 유일한 방법은 클래스를 수정하는 것뿐이다.
* 결합도를 느슨하게 유지하려면 협력하는 대상에 대 해 더 적게 알아야 하고 느슨하게 만들기 위해서는 협력하는 대상에 대해 필요한 정보 외에는 최대한 감추는 것이 중요하며 추상화를 해야 한다.

### 추상화에 의존하라
* 추상화
  * 추상화란 어떤 양상, 세부사항, 구조를 좀 더 명확하게 이해하기 위해 특정 절차나 물체를 의도적으로 생략하거나 감춤으로써 복잡도를 극복하는 방이다.
  * 추상화를 사용하면 현재 다루고 있는 문제를 해결하는 데 불필요한 정보를 감출 수 있다.
  * 대상에 대해 알아야 하는 지식의 양을 줄일 수 있기 때문에 결합도를 느슨하게 유지할 수 있다.

추상화와 결합도의 관점에서 의존 대상을 아래와 같이 구분하는 것이 유용하다. (아래로 갈수록 결합도 ⬇️)
* 구체 클래스 의존성
  * 내부 구현과 종류를 클라이언트가 알아야해서 결합도가 높다.
* 추상 클래스 의존성
  * 내부 구현과 자식 클래스의 종류에 대한 지식을 클라이언트에게 숨길 수 있어서 구체 클래스보다 결합도가 낮다.
  * 클라이언트는 여전히 협력하는 대상이 속한 클래스 상속 계층이 무엇인지에 대해서는 알아야 한다.
* 인터페이스 의존성
  * 인터페이스에 의존하면 상속 계층을 몰라도 협력이 가능해진다.
  * 협력 객체가 어떤 메시지를 수신할 수 있는지에 대한 지식만 남기기 때문에 추상 클래스 의존성보다 결합도가 낮다.

중요한 것은 실행 컨텍스트에 대해 알아야 하는 정보를 줄일수록 결합도가 낮아진다는 것으로 결합도를 느슨하게 만들려면 구체적인 클래스 < 추상 클래스 < 인터페이스 순으로 의존하는 것이 효과적이다.

> 의존하는 대상이 더 추상적일수록 결합도는 더 낮아진다.

### 명시적인 의존성
* 느슨한 결합도를 위해서는 인스턴스 변수의 타입을 추상 클래스나 인터페이스로 선언하고 클래스 안에서 구체 클래스에 대한 모든 의존성을 제거해야 한다.
* 인스턴스 변수의 타입은 추상 클래스나 인터페이스로 정의하고 setter 메서드와 메서드 인자로 의존성을 해결할 때는 추상 클래스를 상속받거나 구체 클래스를 전달하면 된다.
```
public class Movie {
  ...
  private DiscountPolicy discountPolicy;

  public Movie(String title, Duration runningTime, Money fee) {
    ...
    this.discountPolicy = new AmountDiscountPolicy(...); // 잘못된 경우
  }

  public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
    ...
    this.discountPolicy = discountPolicy; // 올바른 경우
  }
}
```

* 명시적인 의존성
  * 생성자의 인자로 선언하거나 setter 메서드 또는 메서드 인자를 사용하는 방법은 의존성이 퍼블릭 인터페이스에 노출된다.
* 숨겨진 의존성
  * 객체 내부에서 의존하는 객체의 인스턴스를 직접 생성하는 방식은 의존성을 감춘다.

의존성이 명시적이지 않으면 내부 구현을 직접 살펴볼 수밖에 없고 커다란 클래스의 정의된 긴 메서드 내부 어딘가에서 인스턴스를 생성하는 코드를 파악하는 것은 어렵다. `의존성이 명시적이지 않으면 클래스를 다른 컨텍스트에서 재사용하기 위해 내부 구현을 직접 변경`해야 한다.

> 의존성은 명시적으로 표현돼야 퍼블릭 인터페이스를 통해 컴파일타임 의존성을 적절한 런타임 의존성으로 교체할 수 있다.

### new는 해롭다
클래스의 인스턴스를 생성하는 new 연산자를 잘못 사용하면 클래스 사이의 결합도가 극단적으로 높아진다.
* new 연산자를 사용하기 위해서는 구체 클래스의 이름을 직접 기술해야 한다. 따라서 new를 사용하는 클라이언트는 추상화가 아닌 구체 클래스에 의존할 수밖에 없어진다.
* new 연산자는 생성하려는 구체 클래스뿐만 아니라 어떤 인자를 이용해 클래스의 생성자를 호출해야 하는지도 알아야 한다. 따라서 클라이언트가 알아야 하는 지식의 양이 늘어나게 된다.

인스턴스를 직접 생성하는 Movie를 살펴보면 Movie는 AmountDiscountPolicy의 생성자에 전달되는 인자를 알고 있어야 하고 AmountDiscountPolicy의 생성자에서 잠조하는 SequenceCondition과 PeriodCondition에도 의존하게 만든다.
```
public class Movie {
  ...
  private discountPolicy: DiscountPolicy;

  public Movie((String title, Duration runningTime, Money fee) {
    ...
    this.discountPolicy = new AmountDiscountPolicy(Money.wons(800),
      new SequenceCondition(1),
      new SequenceCondition(10),
      new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
      new PeriodCondition(DayOfWeek.THURDAY, LocalTime.of(10, 0), LocalTime.of(20, 59)));
  }
}
```

결합도가 높으면 변경에 의해 영향을 받기 쉬워지는데 생성자의 인자 목록이나 순서를 바꾸는 경우에도 함께 변경될 수 있고 구체적인 클래스의 변경에도 영향을 받을 수 있다. 더 많은 것에 의존할수록 점점 더 변경에 취약해지기 때문에 높은 결합도를 피해야 한다.

<img src="./image/그림%208.8.png">

Movie가 DiscountPolicy에 의존하는 이유는 calculateDiscountAmount 메시지를 전송하기 위해서다. 이 메시지에 대한 의존성 외의 모든 다른 의존성은 불필요하다. 그렇기 때문에 인스턴스를 생성하는 로직과 생성된 인스턴스를 사용하는 로직을 분리해서 Movie 클래스에는 AmountDiscountPolicy의 인스턴스에 메시지를 전송하는 코드만 남아야 한다.
```
public class Movie {
  ...
  private discountPolicy: DiscountPolicy;

  public Movie((String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
    ...
    this.discountPolicy = discountPolicy;
  }
}
```
```
-- AmountDiscountPolicy의 인스턴스를 생성하는 책임은 Movie의 클라이언트로 옮겨져야 한다.
Movie avatar = new Movie("아바타", 
  Duration.ofMinutes(120), Money.wons(10000),
  new AmountDiscountPolicy(Money.wons(800),
    new SequenceCondition(1),
    new Sequencecondition(10),
    new PeriodCondition(DayOfWeek.MONDAY,
      LocalTime.of(10, 0), 
      LocalTime.of(11, 59)),
    new Periodcondition(DayOfWeek.THURSDAY,
      LocalTime.of(10, 0), 
      LocalTime.of(20, 59))));
```
생성의 책임을 클라이언트로 옮김으로써 Movie는 DiscountPolicy의 모든 자식 클래스와 혐력할 수 있게 됐다. 사용과 생성의 책임을 분리하고, 의존성을 생성자에 명시적으로 드러내고, 구체 클래스가 아닌 추상 클래스에 의존하게 만들면 설계가 유연해진다.

> 출발은 객체를 생성하는 책임을 객체 내부가 아니라 클라이언트로 옮기는 것에서 시작했다는 점으로 올바른 객체가 올바른 책임을 수행하게 하는 것이 훌륭한 설계를 창조하는 기반이라는 사실이다.

### 가끔은 생성해도 무방하다
`협력하는 기본 객체를 설정하고 싶은 경우에는 클래스 안에서 객체의 인스턴스를 직접 생성하는 방식이 유용한 경우`이다.

Movie가 대부분의 경우에는 AmountDiscountPolicy와 협력하고 가끔씩만 PercentDiscountPolicy와 협력한다고 가정한다면 이런 상황에서 모든 경우에 인스턴스를 생성하는 책임을 클라이언트로 옮긴다면 클라이언트들 사이에 중복 코드가 늘어날 것이다.

이럴 경우 AmountDiscountPolicy를 생성하는 생성자와 DiscountPolicy의 인스턴스를 인자로 받는 생성자를 체이닝할 수 있다.
* 첫 번째 생성자의 내부에서 두 번째 생성자를 호출함으로써 생성자가 체인처럼 연결된다.
* 클라이언트는 AmountDiscountPolicy와 협력하면서도 컨텍스트에 적절한 DiscountPolicy로 의존성을 교체할 수 있다.
```
public class Movie {
  ...
  private DiscountPolicy discountPolicy;

  public Movie(String title, Duration runningTime) { 
    this(title, runningTime, new AmountDiscountPolicy(...));
  }

  public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
    ...
    this.discountPolicy = discountPolicy;
  }
}
```

메서드를 오버로딩하는 경우에도 사용할 수 있다.
* DiscountPolicy의 인스턴스를 인자로 받는 메서드와 기본 값을 생성하는 메서드를 함께 사용하면 클래스의 사용성을 향상시키면서도 다양한 컨텍스트에서 유연하게 사용될 수 있는 여지를 제공한다.
```
public class Movie {
  public Money calculateMovieFee(Screening screening) {
    return calculateMovieFee(screening, new AmountDiscountPolicy(...)); 
  }

  public Money calculateMovieFee(Screening screening, DiscountPolicy discountPolicy) {
    return fee.minus(discountPolicy.calculateDiscountAmount(screening));
  }
}
```

> 설계는 트레이드오프 활동이라는 사실로 여기서 트레이드오프의 대상은 결합도와 사용성이다. 구체 클래스에 의존하게 되더라도 클래스의 사용성이 더 중요하다면 결합도를 높이는 방향으로 코드를 작성할 수 있다. 그럼에도 가급적 구체 클래스에 대한 의존성을 제거할 수 있는 방법을 찾는 것이 좋다.

### 표준 클래스에 대한 의존은 해롭지 않다.
* 의존성이 불편한 이유는 항상 변경에 대한 영향을 암시하기 때문이지 변경될 확률이 거의 없는 클래스라면 의존성이 문제되지 않는다.
* 예를 들어 JDK의 표준 컬렉션 라이브러리에 속하는 ArrayList의 코드가 수정될 확률은 0에 가깝기 때문에 인스턴스를 직접 생성해도 문제가 되지 않는다.
  ```
  public abstract class DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>();
  }
  ```
* 가능한 한 추상적인 타입을 사용하는 것이 확장성 측면에서 유리한데 다양한 List 타입의 객체로 대체할 수 있게 설계의 유연성을 높일 수 있다.
  ```
  public abstract class DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>();

    public void switchconditions(List<DiscountCondition> conditions) {
      this.conditions = conditions;
    }
  }
  ```
* 의존성에 의한 영향이 적은 경우에도 추상화에 의존하고 의존성을 명시적으로 드러내는 것은 좋은 설계 습관이다.

### 컨텍스트 확장하기
Movie가 유연하다는 사실을 입증하기 위해서 Movie를 확장해서 재사용하는 두 가지 예를 살펴본다.

1. 할인 혜택을 제공하지 않는 영화의 예매 요금 계산
```
public class Movie {
  public Movie(String title, Duration runningTime, Money fee) {
    this(title, runningTime, fee, null); 
  }

  public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
    ...
    this.discountPolicy = discountPolicy; 
  }

  public Money calculateMovieFee(Screening screening) { 
    if (discountPolicy == null) {
      return fee; 
    }
    
    return fee.minus(discountPolicy.calculateDiscountAmount(screening)); 
  }
}
```
생성자 체이닝 기법을 이용해 null을 할당하고 있는데 제대로 동작하지만 문제가 발생한다. 지금까지의 Movie와 DiscountPolicy 사이의 협력 방식에 어긋나는 예외 케이스가 추가된 것으로 이 예외 케이스를 처리하기 위해 Movie 내부를 직접 수정했다. 어떤 경우든 코드 내부를 직접 수정하는 것은 버그 발생률을 높인다.

해결 방법은 할인 정책이 존재하지 않는다는 사실을 예외 케이스로 처리하지 말고 기존 협력 방식을 따르도록 만들면 되는데 NoneDiscountPolicy 클래스를 추가하고 DiscountPolicy의 자식 클래스로 만들면 된다. NoneDiscountPolicy의 인스턴스를 Movie 생성자에 전달함으로써 해결할 수 있다.
```
public class NoneDiscountPolicy extends DiscountPolicy { 
  @Override
  protected Money getDiscountAmount(Screening Screening) {
    return Money.ZERO; 
  }
}
```
```
-- if 문을 추가하지 않아도 할인 혜택을 제공하는 영화를 구현했다.
Movie avatar = new Movie("아바타", 
  Duration.ofMinutes(120), 
  Money.wons(10000),
  new NoneDiscountPolicy());
```

2. 중복 적용이 가능한 할인 정책 구현
* Movie가 하나 이상의 DiscountPolicy와 협력할 수 있어야 한다. 가장 간단한 방법은 Movie가 DiscountPolicy의 인스턴스들로 구성된 List를 인스턴스 변수로 갖는 것이지만, 이 방법 또한 기존의 할인 정책의 협력 방식과는 다른 예외 케이스를 추가하게 만든다.
* 중복 할인 정책 또한 할인 정책의 한 가지로 간주해서 OverlappedDiscountPolicy를 DiscountPolicy의 자식 클래스로 만들면 된다.
```
public class OverlappedDiscountPolicy extends DiscountPolicy { 
  private List<DiscountPolicy> discountpolicies = new ArrayList<>();

  public OverlappedDiscountPolicy(DiscountPolicy ... discountPolicies) { 
    this.discountpolicies = Arrays.asList(discountPolicies);
  }

  @Override
  protected Money getDiscountAmount(Screening screening) {
    Money result = Money.ZERO;
    for(DiscountPolicy each : discountPolicies) {
      result = result.plus(each.calculateDiscountAmount(screening)); 
    }
    return result;
  }
}
```
```
-- OverlappedDiscountPolicy의 인스턴스를 생성해서 전달하는 것만으로 중복할인을 쉽게 적용한다.
Movie avatar = new Movie(
  "아바타", 
  Duration.ofMinutes(120), 
  Money.wons(10000),
  new OverlappedDiscountPolicy(
    new AmountDiscountPolicy(...),
    new PercentDiscountPolicy(...)));
```

원하는 기능을 구현한 DiscountPolicy의 자식 클래스를 추가하고 이 클래스의 인스턴스를 Movie에 전달하기만 하면 된다. Movie가 협력해야 하는 객체를 변경하는 것만 으로도 Movie를 새로운 컨텍스트에서 재사용할 수 있기 때문에 Movie는 유연하고 재사용 가능하다.

설계를 유연하게 만든 이유는 아래와 같다.
* Movie는 DiscountPolicy라는 추상화에 의존했다.
* 생성자를 통해 DiscountPolicy에 대한 의존성을 명시적으로 드러냈다.
* new와 같이 구체 클래스를 직접적으로 다뤄야 하는 책임을 Movie 외부로 옮겼다.

> 결합도를 낮춤으로써 얻게 되는 컨텍스트의 확장이라는 개념이 유연하고 재사용 가능한 설계를 만드는 핵심이다.

### 조합 가능한 행동
* 다양한 종류의 할인 정책이 필요한 컨텍스트에서 Movie를 재사용할 수 있었던 이유는 코드를 직접 수정 하지 않고도 협력 대상인 DiscountPolicy 인스턴스를 교체할 수 있었기 때문이다. 어떤 DiscountPolicy의 인스턴스를 Movie에 연결하느냐에 따라 Movie의 행동이 달라진다. 
* 어떤 객체와 협력하느냐에 따라 객체의 행동이 달라지는 것은 유연하고 재사용 가능한 설계가 가진 특징으로 체들을 다양한 방식으로 연 결함으로써 애플리케이션의 기능을 쉽게 확장할 수 있다.
* 유연하고 재사용 가능한 설계는 객체의 how를 장황하게 나열하지 않고도 객체들의 조합을 통해 what을 표현하는 클래스들로 구성된다. 따라서 클래스의 인스턴스를 생성하는 코드를 보는 것만으로 객체가 어떤 일을 하는지를 쉽게 파악할 수 있고 선언적으로 객체의 행동을 정의할 수 있는 것이다.
  ```
  new Movie("아바타",
    Duration.ofMinutes(120),
    Money.wons(10000),
    new AmountDiscountPolicy(Money.wons(800),
      new SequenceCondition(1),
      new Sequencecondition(10),
      new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
      new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(21, 0))));
  ```

> 훌륭한 객체지향 설계란 객체가 어떻게 하는지를 표현하는 것이 아니라 객체들의 조합을 선언 적으로 표현함으로써 객체들이 무엇을 하는지를 표현하는 설계다. 이런 설계를 창조하는 데 있어서의 핵심은 의존성을 관리하는 것이다.

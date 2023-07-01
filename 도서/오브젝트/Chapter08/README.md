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

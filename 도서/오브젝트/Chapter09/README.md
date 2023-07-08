# 09. 유연한 설계
이름을 가진 설계 원칙을 통해 기법들을 정리하는 것은 장황하게 설명된 개념과 매커니즘을 또렷하게 정리할 수 있게 도와줄뿐만 아니라 설계를 논의할 때 사용할 수 있는 공통의 어휘를 익하는 점에서 가치가 있다.

## 01. 개방-폐쇄 원칙(Open-Closed Principle, OCP)
소프트웨어 개체(클래스, 모듈, 함수 등)는 확장에 대해 열려 있어야 하고, 수정에 대해서는 닫혀 있어야 한다.
* 확장에 대해 열려 있다
  * 애플리케이션의 요구사항이 변경될 때 새로운 `동작`을 추가해서 기능을 확장할 수 있다.
* 수정에 대해 닫혀 있다
  * 기존 `코드`를 수정하지 않고도 동작을 추가하거나 변경할 수 있다.

### 컴파일타임 의존성을 고정시키고 런타임 의존성을 변경하라
개방-폐쇄 원칙은 런타임 의존성과 컴파일타임 의존성에 관한 이야기다.
* 런타임 의존성
  * 실행 시에 협력에 참여하는 객체들 사이의 관계
* 컴파일타임 의존성
  * 코드에서 드러나는 클래스들 사이의 관계

현재의 할인 정책 설계의 경우 이미 개방-폐쇄 원칙을 따르고 있다.
* 확장에 대해 열려 있다.
  * 새로운 할인 정책을 추가해서 기능을 확장할 수 있도록 허용한다.
* 수정에 대해 닫혀 있다.
  * 기존 코드를 수정할 필요 없이 새로운 클래스를 추가하는 것만으로 새로운 할인 정책을 확장할 수 있다.

<img src="./image/%EA%B7%B8%EB%A6%BC%209.2.png">

* 개방-폐쇄 원칙을 수용하는 코드는 컴파일타임 의존성을 수정하지 않고도 런타임 의존성을 쉽게 변경 할 수 있다. 
* 새로운 할인 정책 클래스를 추가하더라도 Movie는 DiscountPolicy에만 의존하며 새로운 클래스와 협력할 수 있다.
* 의존성 관점에서 개방-폐쇄 원칙을 따르는 설계란 컴파일타임 의존성은 유지하면서 런타임 의존성의 가능성을 확장하고 수정할 수 있는 구조라고 할 수 있다.

### 추상화가 핵심이다
* 개방-폐쇄 원칙의 핵심은 추상화에 의존하는 것이다. 
* 추상화란 핵심적인 부분만 남기고 불필요한 부분은 생략함으로써 복잡성을 극복하는 기법이다.
* 개방-폐쇄 원칙의 관점에서 생략되지 않고 남겨진 추상화의 결과물은 수정에 대해 닫혀있고, 추상화를 통해 생략된 부분은 확장의 여지를 남긴다.

코드를 살펴보면 아래와 같다.
```
public abstract class DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>();

    public DiscountPolicy(Discountcondition... conditions) {    
        this.conditions = Arrays.asList(conditions);
    }

    public Money calculateDiscountAmount(Screening screening) { 
        for (Discountcondition each : conditions) {
            if (each.isSatisfiedBy(screening)) { 
                return getDiscountedFee(screening);
            } 
        }
        return screening.getMovieFee(); 
    }
    
    abstract protected Money getDiscountAmount(Screening Screening); // 할인 요금을 계산하는 방법을 상속을 통해 생략된 부분을 구체화해서 할인 정책을 확장할 수 있다.
}
```
```
public class Movie {
    ...
    private DiscountPolicy discountPolicy; // 추상화된 할인 정책

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        ...
        this.discountPolicy = discountPolicy; 
    }

    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    } 
}
```

DiscountPolicy는 추상화다. 추상화 과정을 통해 생략된 부분은 할인 요금을 계산하는 방법이다. 상속을 통해 생략된 부분을 구체화함으로써 할인 정책을 확장할 수 있다.
* 할인 여부를 판단해서 요금을 계산하는 메서드 calculateDiscountAmount ➡️ 변하지 않는 부분
* 할인된 요금을 계산하는 추상 메서드 getDiscountAmount ➡️ 변하는 부분

변하는 부분을 고정하고 변하지 않는 부분을 생략하는 추상화 메커니즘이 개발-폐쇄 원칙의 기반이 된다는 사실이고, 추상화의 생략된 부분을 채워넣음으로써 새로운 문맥에 맞게 기능을 확장할 수 있다.

하지만 추상화했다고 수정에 대해 닫혀있는 설계를 만들 수 있는 것은 아니다. 폐쇄를 가능하게 하는 것은 의존성의 방향이다. 모든 요소가 추상화에 의존해야 한다.Movie는 DiscountPolicy에만 의존한다. 의존성은 변경의 영향이며 DiscountPolicy는 변하지 않는 추상화다.

> 개방-폐쇄 원칙의 핵심은 추상화다. 변하는 것과 변하지 않는 것이 무엇인지를 이해해야 하고 올바른 추상화를 주의 깊게 선택해야 한다.

## 02. 생성 사용 분리

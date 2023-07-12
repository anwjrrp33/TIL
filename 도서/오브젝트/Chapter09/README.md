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

하지만 추상화했다고 수정에 대해 닫혀있는 설계를 만들 수 있는 것은 아니다. 폐쇄를 가능하게 하는 것은 의존성의 방향이다. 모든 요소가 추상화에 의존해야 한다. Movie는 DiscountPolicy에만 의존한다. 의존성은 변경의 영향이며 DiscountPolicy는 변하지 않는 추상화다.

> 개방-폐쇄 원칙의 핵심은 추상화다. 변하는 것과 변하지 않는 것이 무엇인지를 이해해야 하고 올바른 추상화를 주의 깊게 선택해야 한다.

## 02. 생성 사용 분리
결합도가 높아질수록 개방-폐쇄 원칙을 따르기 어려워진다. 알아야 하는 지식이 많으면 결합도도 높아진다. 특히 객체 생성에 대한 지식은 과도한 결합도를 초래한다. 부적절한 곳에서 객체를 생성하는 것이 문제다.

```
public class Movie {
    ...
    private DiscountPolicy discountPolicy;
    
    public Movie(String title, Duration runningTime, Money fee) {
        ...
        this.discountPolicy = new AmountDiscountPolicy(...); 
    }

    public Money calc니lateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
```

동일한 클래스 안에서 객체 생성과 사용이라는 두 이질적인 목적을 가진 코드가 공존하는 것이 문제이기 때문에 유연하고 재사용 가능한 설계를 원한다면 객체와 관련된 두가지 책임을 서로 다른 객체로 분리해야 한다. 하나는 객체를 생성하는 것이고 다른 하나는 객체를 사용하는 것이다. 즉 객체에 대한 `생성과 사용을 분리`해야 한다.
```
public class Client {
    public Money getAvatarFee() {
        Movie avatar = new Movie("아바타", 
            Duration.ofMinutes(120),
            Money.wons(10000),
            new AmountDiscountPolicy(...));
        return avatar.getFee(); 
    }
}
```

객체 생성의 책임을 클라이언트로 옮기면, 현재의 컨텍스트에 관한 결정권을 가지고 있는 클라이언트로 컨텍스트에 대한 지식이 옮겨지고 특정한 클라이언트에 결합되지 않고 독립적일 수 있다.

<img src="./image/%EA%B7%B8%EB%A6%BC%209.5.png">

> Movie의 의존성을 추상화인 DiscountPolicy로만 제한하기 때문에 확장에 대해서는 열려 있으면서도 수정에 대해서는 닫혀 있는 코드를 만들 수 있는 것이다.

### FACTORY 추가하기
생성 책임을 Client로 옮긴 배경에는 Movie는 특정 컨텍스트에 묶여서는 안 되지만 Client는 묶여도 상관 없다는 전제가 깔려 있지만 Client도 특정한 컨텍스트에 묶이지 않는다고 가정해본다.

Client는 Movie의 인스턴스를 생성하는 동시에 getFee 메시지도 함께 전송한다. 이는 생성과 사용의 책임을 함께 지니고 있는 것이고 Movie의 문제를 해결했던 것처럼 Movie를 생성하는 책임을 Client의 인스턴스를 사용할 문맥을 결정할 클라이언트로 옮기지만 객체 생성과 관련된 지식이 Client와 협력하는 클라이언트에게까지 새어나가기를 원하지 않는다.

객체 생성과 관련된 책임만 전담하는 별도의 객체를 추가하고 Client는 이 객체를 사용하도록 만드는데 이처럼 `생성과 사용을 분리하기 위해 객체 생성에 특화된 객체`를 `FACTORY`라고 부른다.
```
public class Factory {
    public Movie createAvatarMovie() {
        return new Movie("아바타", 
            Duration.ofMinutes(120),
            Money.wons(10000),
            new AmountDiscountPolicy(...));
    }
}
```
```
-- Client는 Factory를 사용해서 생성된 Movie의 인스턴스를 반환받아 사용하기만 하면 된다
public class Client {
    private Factory factory;

    public Client(Factory factory) { 
        this.factory = factory;
    }

    public Money getAvatarFee() {
        Movie avatar = factory.createMadMaxMovie(); 
        return avatar.getFee();
    } 
}
```

FACTORY를 사용하면 Movie와 AmountDiscountPolicy를 생성하는 책임 모두를 FACTORY로 옮길 수 있다. Client는 오직 사용과 관련된 책임만 지고 생성과 관련된 어떤 지식도 가지지 않을 수 있다.

<img src="./image/%EA%B7%B8%EB%A6%BC%209.6.png">

### 순수한 가공물에게 책임 할당하기
GRASP 패턴인 책임 할당의 가장 기본이 되는 원칙은 책임을 정보 전문가(INFORMATION EXPERT)에게 할당하는 것으로 정보 전문가를 찾기 위해 도메인 모델 안의 개념 중에서 적절한 후보를 찾아야 한다.

하지만 FACTORY는 도메인 모델에 속하지 않고 결합도를 찾추고 재사용성을 높이기 위해 도메인 개념에게 할당돼 있던 객체 생성 책임을 도메인 개념과는 상관이 없는 가공의 객체로 이동시킨 것이다.

크레이그 라만은 시스템을 객체로 분해하는 데는 크게 두가지 방식이 존재한다고 설명했다.
* 표현적 분해
* 행위적 분해

#### 표현적 분해
* 도메인에 존재하는 개념을 표현하는 객체들을 이용해 시스템을 분해하는 것이다.

#### 행위적 분해
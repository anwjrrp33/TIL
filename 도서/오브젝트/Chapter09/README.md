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
* 도메인 모델에 담겨 있는 개념과 관계를 따르며 도메인과 소프트웨어 사이의 표현 적 차이를 최소화하는 것을 목적으로 하며 객체지향 설계를 위한 가장 기본적인 접근법이다.
* 데이터베이스 접근을 위한 객체와 같이 도메인 객체들에게만 책임을 할당하는 것만으로는 부족한 경우가 발생하는데 도메인 모델은 단지 출발점이다.

#### 행위적 분해
* 행위적 분해에 의해 생성되는 것이 순수한 가공물이다.
* 모든 책임을 도메인 객체에게 할당하면 낮은 응집도, 높은 결합도, 재사용성 저하가 발생할 수 있다.
* 임의로 만들어낸 가공의 객체에게 책임을 할당해야 한다. 이 객체를 순수한 가공물이라 한다.
* 행동을 책임질 마땅한 도메인 개념이 없다면 순수한 가공물을 추가하고 책임을 할당해야 한다.

객체지향 애플리케이션은 도메인 개념뿐만 아니라 설계자들이 임의적으료 창조한 인공적인 추상화들을 포함하며 객체지향 애플리케이션의 대부분은 실제 도메인에서 발견할 수 없는 순수한 인공물로 가득 차 있다. 

우리의 역할은 도메인 추상화를 기반으로 로직을 설계하는 동시에 품질의 측면에서 균형을 맞추는 데 필요한 객체들을 창조해야 한다.
* 도메인의 본질적인 개념을 표현하는 추상화를 이용해 애플리케이션 구축 시작
* 도메인 개념이 만족스럽지 못하다면 인공적인 객체 창조

> 도메인을 반영하는 애플리케이션의 구조라는 제약 안에서 실용적인 창조성을 발휘할 수 있는 능력은 훌륭한 설계자가 갖춰야 할 기본적인 자질이다.

#### PURE FABRICATION 패턴
* 정보 전문가 패턴에 따라 책임을 할당한 결과가 바람직하지 않을 경우 대안으로 사용된다.
* 어떤 객체가 책임을 수행하는 데 필요한 많은 정보를 가졌지만 해당 책임을 할당할 경우 응집도가 낮아지고 결합도가 높아진다면 가공의 객체를 추가해서 책임을 옮기는 것을 고민해야 한다.
* 순수한 가공물(pure fabrication)이라는 표현은 적절한 대안이 없을 때 사람들이 창조적인 무언가를 만들어낸다는 것을 의미하는 관용적인 표현이다.

## 03. 의존성 주입
생성과 사용을 분리하면 Movie에는 인스턴스를 사용하는 책임만 남게되고, 외부의 다른 객체로부터 생성된 인스턴스를 전달받아야 한다. 이처럼 사용하는 객체가 아닌 외부의 독립적인 객체가 인스턴스를 생성한 후 이를 전달해서 의존성을 해결하는 방법을 의존성 주입이라고 한다.

의존성 주입에서는 의존성을 해결하는 세 가지 방법을 가리키는 별도의 용어를 정의한다.
* 생성자 주입
  * 객체를 생성하는 시점에 생성자를 통한 의존성 해결
  * 생성자 주입으로 설정된 인스턴스 객체의 생명주기 전체에 결쳐 유지한다.
```
Movie avatar = new Movie("아바타", 
    Duration.ofMinutes(120),
    Money.wons(10000),
    new AmountDiscountPolicy(...));
```
* setter 주입
  * 객체 생성 후 setter 메서드를 통한 의존성 해결
  * 언제라도 의존 대상을 교체할 수 있지만, 객체가 올바로 생성되기 위해 어떤 의존성이 필수적인지 명시적으로 표현할 수밖에 없다.
```
avatar.setDiscountPolicy(new AmountDiscountPolicy(...));
```
* 메서드 주입
  * 메서드 실행 시 인자를 이용한 의존성 해결
  * 객체가 올바로 생성되기 위해 필요한 의존성을 명확하게 표현할 수 있지만, 주입된 의존성이 한 두 개의 메서드에서만 사용된다면 각 메서드의 인자로 전달하는 게 낫다.
```
avatar.calculateDiscountAmount(screening, new AmountDiscountPolicy(...));
```

#### 프로퍼티 주입과 인터페이스 주입
* C#에서는 자바의 setter 메서드를 대체할 수 있는 프로퍼티라는 기능을 제공하고 setter 주입 대신 프로퍼티 주입이라는 용어를 사용한다. 근본적으로는 setter나 프로퍼티 주입과 동일하지만, 어떤 대상을 어떻게 주입할지를 명시적으로 선언한다.
* 인터페이스 주입이라는 의존성 주입 기법도 존재하는데 인터페이스 주입의 기본개념은 주입할 의존성을 명시하기 위해 인터페이스를 사용하는 것이다.
```
-- 인터페이스 주입
public interface DiscountPolicyInjectable {
    public void inject(DiscountPolicy discountPolicy)
}

public class Movie implements DiscountPolicyInjectable {        
    private DiscountPolicy discountPolicy;
    
    @Override
    public void inject(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy; 
    }
}
```

### 숨겨진 의존성은 나쁘다
의존성 주입 외에도 의존성을 해결할 수 있는 다양한 방법이 존재한다.

#### SERVICE LOCATOR
* SERVICE LOCATOR는 의존성을 해결 할 객체들을 보관하는 일종의 저장소다.
* 객체가 직접 SERVICE LOCATOR에게 의존성을 해결해줄 것을 요청한다.
* 이 패턴은 서비스를 사용하는 코드로부터 서비스를 구현한 구체 클래스의 타입이 무엇인지, 클래스 인스턴스를 어떻게 얻을지를 몰라도 되게 해준다.
```
public class Movie {
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee) { 
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = ServiceLocator.discountPolicy(); 
    }
}
```
```
public class ServiceLocator {
    private static ServiceLocator solelnstance = new ServiceLocator(); 
    private DiscountPolicy discountPolicy;

    public static DiscountPolicy discountPolicy() { 
        return solelnstance.discountPolicy;
    }

    public static void provide(DiscountPolicy discountPolicy) { 
        solelnstance.discountPolicy = discountPolicy;
    }

    private ServiceLocator() { } 
}
```
```
ServiceLocator.provide(new AmountDiscountPolicy(...)); 
Movie avatar = new Movie("아바타",
    Duration.ofMinutes(120), 
    Money.wons(10000));

ServiceLocator.provide(new PercentDiscountPolicy(...)); 
Movie avatar = new Movie("아바타",
    Duration.ofMinutes(120), 
    Money.wons(10000));
```
Movie는 직접 ServiceLocator의 메서드를 호출해서 DiscountPolicy에 대한 의존성을 해결한다. ServiceLocator에 인스턴스를 등록하면 이후에 생성되는 모든 Movie는 해당 할인 정책을 기반으로 계산한다.

```
Movie avatar = new Movie("아바타",
    Duration.ofMinutes(120), 
    Money.wons(10000));
```
하지만 이 패턴의 `가장 큰 단점은 의존성을 감춘다는 것`이다. Movie는 DiscountPolicy에 의존하고 있지만 어디에도 이 의존성에 대한 정보가 표시돼 있지 않다. ServiceLocator에 인스턴스를 등록하지 않고 Movie를 생성하면 Movie는 온전히 생성된 것처럼 보이지만 discountPolicy가 null이기 때문에 코드를 실행하면 NullPointerException 예외가 던져진다.

#### 의존성을 구현 내부로 감추면 관련 문제가 컴파일 타임이 아닌 런타임에 발견된다
* 문제점을 발견할 수 있는 시점을 코드 실행 시점으로 미루게 된다.

#### 각 단위 테스트는 서로 고립돼야 한다는 단위 테스트의 기본 원칙을 위반한다
* 의존성을 숨기는 코드는 단위 테스트 작성도 어렵다.
* 단위 테스트의 경우에도 테스트 케이스 단위로 테스트에 사용될 객체들을 새로 생성해야 하는데, ServiceLocator는 내부적으로 정적 변수를 사용해 객체들을 관리하기 때문에 모든 단위 테스트 케이스에 걸쳐 ServiceLocator의 상태를 공유하게 된다.
* 단위 테스트가 서로 간섭 없이 실행되기 위해서는 필요한 인스턴스를 추가하고 끝날 때마다 추가된 인스턴스를 제거해야 한다.
  
#### 숨겨진 의존성을 캡슐화를 위반한다
* 단순히 인스턴스 변수의 가시성을 private으로 선언하고 변경되는 내용을 숨겼다고 해서 캡슐화가 지켜지는 것은 아니다.
* 클래스의 퍼블릭 인터페이스만으로 사용 방법을 이해할 수 있는 코드가 캡슐화의 관점에서 훌륭한 코드지만 클래스 사용법을 익히기 위해 구현 내부를 샅샅이 뒤져야 한다면 그 클래스의 캡슐화는 무너진 것이다.
* 따라서 의존성을 구현 내부로 감추도록 강요하는 SERVICE LOCATOR는 캡슐화를 위반할 수밖에 없다.

#### 의존성 주입은 SERVICE LOCATOR의 문제를 해결한다
* 의존성을 이해하기 위해 코드 내부를 읽을 필요가 없기 때문에 의존성 주입은 객 체의 캡슐을 단단하게 보호한다.
* 필요한 의존성을 인자에 추가하지 않을 경우 컴파일 에러가 발생하기 때문에 의존성과 관련된 문제도 최대한 컴파일타임에 잡을 수 있다.
* 단위 테스트를 작성할 때 ServiceLocator에 객체를 추가하거나 제거할 필요도 없이 필요한 인자를 전달해서 필요한 객체를 생성하면 된다.

> 의존성 주입이 SERVICE LOCATOR 패턴보다 좋다는 것이 아닌 명시적인 의존성이 숨겨진 의존성보다 좋다라는 의미이며 의존성을 내부에 숨길수록 코드를 이해하고 수정하기 어려워지기 때문에 가급적 의존성을 객체의 퍼블릭 인터페이스에 노출해야 한다. 가급적 의존성을 명시적으로 표현할 수 있는 기법을 사용하는 것이 유연성을 향상시키는 가장 효과적인 방법이다.

## 04. 의존성 역전 원칙
### 추상화와 의존성 역전

```
public class Movie {
    private AmountDiscountPolicy discountPolicy; -- 구체 클래스
}
```
코드를 보면 구체 클래스에 대한 의존성으로 인해 결합도가 높아지고 재사용성과 유연성이 저해되는 상황으로 상위 수준 클래스인 Movie가 하위 수준 클래스인 AmountDiscountPolicy에 의존하고 있다. 그로 인해 위 설계는 변경에 취약하다.

객체 사이의 협력의 본질을 담고 있는 것은 상위 수준의 정책이다. 본질은 영화의 가격을 계산하는 것이지 어떻게 할인 금액을 계산할 것인지는 아니다. 따라서 `어떤 협력에서 중요한 정책이나 의사결정, 비즈니스의 본질을 담고 있는 것은 상위 수준의 클래스`다.

상위 수준의 클래스가 하위 수준의 클래스에 의존하면 하위 수준의 변경에 의해 상위 수 준 클래스가 영향을 받게 될 것이다. 의존성은 변경의 전파와 관련된 것이기 때문에 설계는 변경의 영향을 최소화하도록 의존성을 관리해야 한다. 문제점은 의존성의 방향이 잘못된 것으로 `상위 수준의 클래스는 어떤 식으로든 하위 수준의 클래스에 의존해서는 안 되는 것`이다.

이 설계는 재사용성에도 문제가 발생하는데 상위 수준의 클래스가 하위 수준의 클래스에 의존하면 상위 수준의 클래스를 재사용 할 때 하위 수준의 클래스도 필요하기 때문에 재사용하기가 어려워진다.

중요한 것은 상위 수준의 클래스 상위 수준의 변경에 의해 하위 수준이 변경되는 것은 닙득할 수 있 지만 하위 수준의 변경으로 인해 상위 수준이 변경돼서는 곤란하다.

#### 해결사는 추상화다
* DiscountPolicy 추상 클래스를 두면 상위 수준 클래스와 하위 수준 클래스 모두 추상화에 의존하게 된다.
* 모든 의존성의 방향은 추상 클래스나 인터페이스와 같은 추상화를 따라야 한다.
* 구체 클래스는 의존성의 시작점이지 목적지가 아니다.
  
<img src="./image/그림%209.8.png">

#### 의존성 역전 원칙
* 상위 수준의 모듈은 하위 수준의 모듈에 의존해서는 안 된다. 둘 모두 추상화에 의존해야 한다.
* 추상화는 구체적인 사항에 의존해서는 안 된다. 구체적인 사항은 추상화에 의존해야 한다.

이를 로버트 마틴은 `의존성 역전 원칙(Dependency Inversion Principle, DIP)`이라고 부르며 전통적인 절차형 프로그래밍과는 의존성이 반대 방향으로 나타난다.

### 의존성 역전 원칙과 패키지
역전은 의존성의 방향뿐만 아니라 인터페이스의 소유권에도 적용되는데 소유권을 결정하는 것은 모듈이다.

<img src="./image/그림%209.9.png">

위 설계는 개발-폐쇄 원칙과 의존성 역전 원칙을 따르고 있기 때문에 유연하고 재사용 가능한 것처럼 보이지만 영화(Movie)는 다양한 컨텍스트에서 재사용하기 위해서는 불필요한 클래스들이 영화(Movie)와 함께 배포돼야만 한다.

Movie를 정상적으로 컴파일하기 위해서 는 DiscountPolicy 클래스가 필요하다. 코드의 컴파일이 성공하기 위해 함께 존재해야 하는 코드를 정의하는 것이 바로 컴파일타임 의존성으로 DiscountPolicy 클래스에 의존하기 위해서는 반드시 같은 패키지에 포함된 AmountDiscountPolicy 클래스와 PercentDiscountPolicy 클래스도 함께 존재해야 한다는 것을 의미한다.

Movie의 재사용을 위해 필요한 것이 DiscountPolicy뿐이라면 DiscountPolicy를 Movie와 같은 패키지로 모으고 AmountDiscountPolicy와 PercentDiscountPolicy를 별도의 패키지에 위치시켜 의존성 문제를 해결할 수 있다.

<img src="./image/그림%209.10.png">

추상화를 별도의 독립적인 패키지가 아니라 클라이언트가 속한 패키지에 포함시키고,
함께 재사용될 필요가 없는 클래스들은 별도의 패키지에 모아야 한다. 마틴 파울러는 이 기법을 가르켜 `분리 인터페이스(SEPARATED INTERFACE) 패턴`이라고 부른다.

전통적인 설계 패러다임은 인터페이스의 소유권을 클라이언트 모듈이 아닌 서버 모듈에 위치시키는 반면 객체지향 애플리케이션은 인터페이스의 소유권을 서버가 아닌 클라이언트에 위치시킨다.

> 훌륭한 객체지향 설계를 위해서는 의존성을 역전시켜야 한다. 그리고 의존성을 역전시켜야만 유연하고 재사용 가능한 설계를 얻을 수 있다.

## 05. 유연성에 대한 조언
### 유연한 설계는 유연성이 필요할 때만 옳다
#### 유연하고 재사용 가능한 설계란?
* 런타임 의존성과 컴파일타임 의존성의 차이를 인식하고, 동일한 컴파일타임 의존성으로부터 다양한 런타임 의존성을 만들 수 있는 코드 구조를 가지는 설계를 의미한다.
* 단순하고 명확한 설계를 가진 코드는 읽기 쉽고 이해하기도 편하기 때문에 유연하고 재사용 가능한 설계가 항상 좋은 것은 아니다.
* 유연한 설계라는 말의 이면에는 복잡한 설계라는 의미가 숨어 있다.

#### 유연성과 복잡성
* 유연하지 않은 설계는 단순하고 명확하고, 유연한 설계는 복잡하고 암시적으로 `유연성은 항상 복잡성을 수반`한다.
* 설계가 유연할수록 클래스 구조와 객체 구조 사이의 거리는 점점 멀어진다.
* 단순하고 명확해야 한다면 유연성을 제거하고, 유연성과 재사용성이 중요하다면 코드의 구조와 실행 구조를 다르게 만들어야 한다.

#### 설계와 유연성
* 설계가 유연할수록 클래스 구조와 객체 구조 사이의 거리는 점점 멀어진다.
* 불필요한 유연성은 불필요한 복잡성을 낳기 때문에 단순하고 명확한 해법이 그런대로 만족스럽다면 유연성을 제거해야 한다.
* 복잡 성에 대한 걱정보다 유연하고 재사용 가능한 설계의 필요성이 더 크다면 코드의 구조와 실행 구조를 다 르게 만들면 된다.

### 협력과 책임이 중요하다
#### 객체의 협력과 책임
* 객체의 협력과 책임이 중요하다.
* 설계를 유연하게 만들기 위해서는 협력에 참여하는 객체가 다른 객체에게 어떤 메시지를 전송하는지가 중요하다.

#### 영화가 할인 정책과 협력할 수 있는 이유
* 모든 할인 정책이 Movie가 전송하는 calculateDiscountAmount 메시지를 이해할 수 있기 때문이다.
* Movie의 입장에서 동일한 역할을 수행할 수 있다.
  
<img src="./image/그림%209.11.png">

설계를 유연하게 만들기 위해서는 역할, 책임, 협력에 초점을 맞춰야 한다. 객체의 역할과 책임이 자리 잡기 전에 성급하게 객체 생성에 집중하지 말아야 한다. 책임 관점에서 객체들 간에 균형이 잡혀 있는 상태라면 생성과 관련된 책임을 지게 될 객체를 선택하는 것은 간단한 작업이 된다.

싱글톤(SINGLETON) 패턴은 객체 생성에 관해 너무 이른 시기에 고민하고 결정할 때 도입되는 경향이 있다. 핵심은 객체를 생성하는 방법에 대한 결정은 모든 책임이 자리를 잡은 후 가장 마지막 시점에 내리는 것이 적절하다.

> 의존성을 관리해야 하는 이유는 역할, 책임, 협력의 관점에서 설계가 유연하고 재사용 가능해야 하기 때문이다. 따라서 역할. 책임, 협력에 먼저 집중해야 한다.
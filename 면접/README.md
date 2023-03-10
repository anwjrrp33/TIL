## Java
### JVM(Java Virtual Machine)
* JVM은 자바 가상 머신으로 자바 프로그램 실행환경을 만들어주는 소프트웨어입니다. 자바 애플리케이션을 컴파일하여 클래스 로더를 통해 실행합니다. 메모리 관리인 GC를 수행하며 스택 기반의 가상머신입니다. 자바는 JVM을 통해서 실행되기 때문에 운영체제에 종속적이지 않고 어떠한 플랫폼에도 영향을 받지 않고 동일하게 수행됩니다.
<br/><img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F56cSc%2FbtruTEtjRXJ%2Fr1JNTkEuEeY8cSKtqcXCRK%2Fimg.png" width=50% height=50%/>

*  JVM 동작 방식
    * 1. 자바 프로그램이 실행되면 JVM은 OS로 부터 메모리를 할당받는다.
    * 2. 자바 컴파일러가 자바 코드(.java)를 자바 바이트코드(.class)로 컴파일한다.
    * 3. 자바 바이트코드를 클래스 로더를 통해서 Runtime Data Area로 로딩한다.
    * 4. Runtime Data Area에 로딩된 자바 바이트 코드를 Execution Engine을 통해서 해석한다.
    * 5. 해석된 바이트 코드는 Runtime Data Area의 각 영역에 배치되어 수행하며 Execution Engine에 의해서 GC 작동과 쓰레드 동기화가 된다.
<br/><img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcQRqku%2Fbtru0vJ6Ixx%2F9qCTW7ChXc80fGfQUrT4B0%2Fimg.png" width=50% height=50%/>

* JVM 구조
    * 클래스 로더(Class Loader): JVM내에 클래스를 로드하고 링크를 통해 배치하는 작업을 수행하는 모듈
    * 실행 엔진(Execution Engine): 바이트 코드를 실행시키는 역할
        * 인터프리터: 바이트 코드를 한줄씩 실행한다.
        * JIT 컴파일러: 인터프리터의 효율을 높이기 위한 컴파일러로 인터프리터가 반복되는 코드를 발견하면 JIT 컴파일러가 반복되는 코드를 네이티브 코드로 변환하고 반복되는 코드를 인터프리터는 네이티브 코드로 변환된 컴파일 코를 바로 사용한다.
    * Runtime Data Areas: 프로그램 실행 중에 사용되는 다양한 영역
        * PC register: 쓰레드가 시작될 때 생성되며 현재 수행 중인 JVM 명령의 주소를 갖고 있는다.
        * Stack Area: 지역 변수, 파라미터 등이 생성되는 영역으로 실제 객체는 Heap에 할당되고 해당 레퍼런스만 Stack에 저장된다.
        * Heap Area: 동적으로 생성된 오브젝트와 배열이 저장되는 곳으로 GC의 대상 영역이다.
        * Method Area: 클래스 멤버 변수, 메소드 정보, Type 정보, Constant Pool, static, final 변수 등이 생성된다. 상수 풀(Constant Pool)은 모든 Symbol Reference를 포함한다.
    * JNI(Java Native Interface): 자바 애플리케이션에서 C, C++, 어셈블리어로 작성된 함수를 사용할 수 있는 방법을 제공해준다. Native 키워드를 통해서 메소드를 호출하고 대표적인 메소드로 currentThread()가 존재한다.
    * Native Method Library: C, C++로 작성된 라이브러리다.


### 가비지 컬렉션(Garbage Collection, GC)
* JVM의 힙 영역에서 불필요한 메모리를 정리해주는 역할입니다. 자바는 개발자가 직접 메모리를 해제하지 않기 때문에 그 역할을 가비지 컬렉션(Garbage Collection, GC)이 해당 역할을 수행합니다.
* GC의 종류 중 한 종류인 Serial GC로 설명을 하면 Minor GC와 Major GC로 구분되는데 Minor GC는 Young 영역 Major GC는 Old 영역에서 일어 납니다.
* GC가 실행되는 동작 방식에는 공통적으로 2단계로 나뉘어지는데 Stop The World와 Mark and Sweep입니다. Stop The World는 GC를 실행하기 위해서 JVM이 애플리케이션의 실행을 멈추는 작업으로 GC를 싱행하는 쓰레드를 제외한 모든 쓰레드가 중단됩니다. GC의 성능 개선을 위해서 튜닝을 한다고하면 보통 stop-the-world의 시간을 줄이는 작업을 하며 JVM에서도 이런 문제를 해결하기 위해서 다양한 실행 옵션을 제공합니다. Stop the World에서 모든 작업이 중단되면 GC는 스캔을 통해서 사용되고 있는 메모리를 식별하고 식별되지 않은 객체들을 메모리에서 제거하는 것을 Mark the Sweep라고 합니다.
* Minor GC는 1개의 Eden 영역과 2개의 Survivor 영역 총 3개의 영역으로 나뉘어집니다. 객체가 생성되면 Eden 영역에 할당되고 Eden 영역이 꽉 차면 Minor GC가 발생하면서 사용되지 않은 메모리는 해체되고 사용중인 객체는 Survivor 영역으로 옮겨집니다. 이 과정이 반복되다가 Survivor 영역이 가득차면 해당 영역에서 Minor GC가 일어나고 다음 Survivor 영역으로 이동시킵니다. 이러한 과정에서 하나의 Survivor 영역은 반드시 빈 상태로 유지합니다. 해당 과정을 반복해서 살아남은 객체는 Old 영역으로 이동됩니다.
![Minor GC](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FCyho2%2FbtqURvZRql6%2F4a7u6mMGofkpuURKQz0RT1%2Fimg.png)
    * Eden: 새로 생성된 객체가 할당되는 영역
    * Survivor: 최소 1번의 GC이상 살아남은 객체가 존재하는 영역
* Major GC는 Young 영역에서 객체들이 이동되서 메모리가 부족해지면 발생하는데 Old 영역은 Young 영역보다 크기가 크기 때문에 Minor GC보다 시간이 오래걸립니다.
![Garbage Collection](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdM4wqf%2FbtqUWs2lW8H%2FGvRECmsUIfZ2jhDoKhSCD0%2Fimg.png)

### GC 종류 별 동작원리
* Serial GC는 예전 싱글코어에서 사용된 GC로 Single Thread로 동작하기 Stop-the-World 시간이 길고 느립니다.
* Parallel GC는 Java8의 Default GC로 Minor GC 에서만 Multi Thread로 동작합니다. Serial GC에 비해 속도가 많이 개선되었고 Mark and Sweep Compact 알고리즘을 사용합니다.
* Parallel old GC 는 Parallel GC와 달리 Major GC에서도 Multi Thread로 동작합니다. Minor GC는 Mark and Sweep Compact, Major GC는 Mark Summary Compact 알고리즘을 사용합니다.
* G1(Garbage First) GC 는 다른 GC 방식과는 다르게 Heap 영역 전체를 1~32MB 의 동일한 사이즈의 지역(Region)들로 나누고 각 지역이 Eden, Survivor, Old, Available/Unused 역할을 수행합니다. Garbage가 꽉찬 지역을 우선적으로 GC가 동작하게 됩니다.
* ZGC는 Region을 2MB의 배수 형태로 ZPage로 정의하여 사용합니다. 최근에는 MSA 구조로 서버를 클라우드에서 기동하기 떄문에 기존 G1 GC는 메모리가 커지면 stw가 늘어나는데 ZGC의 경우 stw 시간이 10ms 내외로 무조건 떨어져서 MSA 구조에서 유리합니다.

### 오버로딩(Overloading)과 오버라이딩(Overriding)
* 오버로딩은 같은 클래스 내에서 메소드의 이름이 중복되더라도 매개변수의 타입또는 개수가 다르면 중복된 이름을 사용해서 정의할 수 있습니다. 또한 컴파일 타임 다형성이기에 정적 다형성입니다.
```
public class Overloading {
    
    public void overloading() {}

    public void overloading(String overloading) {}

    public void overloading(String overloading1, String overloading2) {}

    public void overloading(int overloading) {}
}
```
* 오버라이딩은 상위 클래스의 메소드를 재정의해서 사용하는 것을 의미합니다. 또한 런타임 다형성이기에 동적 다형성입니다.
```
class Parent {

    public void overriding() {}
}

class Child extends Parent {

    @Override
    public void overriding() {}
}
```

### 어노테이션(Annotation)
* 소스코드에 추가해서 사용할 수 있는 메타 데이터의 일종입니다. 주석처럼 코드에 달아 클래스에 특별한 의미를 부여하거나 기능을 주입할 수 있습니다.
* 빌트인 어노테이션과, 메타 어노테이션이 존재하며 빌트인 어노테이션의 경우 자바에서 제공하는 어노테이션을 뜻하며 메타 어노테이션의 경우 커스텀해서 사용할 어노테이션을 만들 때 사용됩니다.

### SOLID 원칙(OOP의 5가지 원칙)
* 단일 책임 원칙(SRP)은 한 클래스의 하나의 책임만 가지는 것을 의미하며 목적과 취지에 맞는 속성과 메소드로 구성해야 합니다.
* 개방-폐쇠 원칙(OCP)은 확장에는 열려 있어야 하지만 변경에는 닫혀 있는 것을 의미하며 다형성의 성질을 가진 인터페이스를 사용해서 직접적인 연동과 변경을 피하고 메소드를 재정의 해서 사용해야 합니다.
* 리스코프 치환 원칙(LSP)은 하위 클래스의 인스턴스는 상위형 객체 참조 변수에 대입해 상위 클래스의 인스턴스 역할을 하는데 논리적으로 문제가 없어야 합니다. 여기서 상위와 하위를 나누는 것은 계층적인 구조가 아닌 분류를 의미합니다. 즉 상속과 확장은 동일합니다.
```
아버지와 아들 // 아들은 아버지의 한 종류 X
포유류와 고래 // 고래는 포유류의 한 종류 O
```
* 인터페이스 분리 원칙(ISP)은 클라이언트는 자신이 사용하지 않는 메소드에 의존 관계를 맺으면 안되는 원칙입니다. 상관에 관련 있는 메소드만 제공하라는 의미이며 비대한 인터페이스보단 작고 구체적인 인터페이스로 분리해야 합니다.
* 의존관계 역전 원칙(DIP)은 추상적인 것은 자신보다 구체적인 것에 의존하지 않고, 변화하기 쉬운 것에 의존해서는 안된다는 원칙입니다. 자신보다 변하기 쉬운 것에 의존하면 안되며 구현클래스에 의존하는 것이 아닌 다형성의 특징을 가진 인터페이스에 의존을 해서 변화에 영향 받지 않도록 의존 관계를 역전시켜야 합니다.

### OOP의 4가지 특성
* 캡슐화는 데이터와 코드의 형태를 외부로부터 알 수 없게 하고, 데이터의 구조와 역할, 기능을 하나의 캡슐 형태로 만드는 방법입니다.
* 추상화는 클래스들의 공통적인 특성(변수, 메소드)들을 묶어 표현하는 것 입니다.
* 상속화는 부모 클래스에 정의된 변수 및 메서드를 자식 클래스에서 상속받아 사용하는 것 입니다.
* 다형화는 다양한 형태로 표현이 가능한 구조입니다.

### 정적(static)
* static은 클래스 멤버라고 하며, 클래스 로더가 클래스를 로딩해서 메소드 메모리 영역에 적재할 때 클래스별로 관리됩니다.
* Heap 영역이 아닌 Staic 영역에 할당되기에 GC가 관리하는 영역 밖이며 모든 객체가 공유해서 하나의 멤버를 어디서든 참조할 수 있지만 프로그램 종료 시까지 메모리에 할당된 채로 존재하기에 남발하면 성능에 악영향을 미칩니다. 하지만 특정한 상황에서는 시스템 성능을 높일 수 있습니다.

### 접근 제한자
* private, default, protected, public이 있습니다. private은 해당 클래스 내에서만 접근 가능하고, default는 해당 패키지, protected는 상속한 클래스, public은 전체 영역에서 접근 가능합니다.
* 접근 제어자를 사용하는 이유는 객체의 로직인 변수나 메소드를 보호하기 위해서 외부의 접근을 허용하거나 차단하는 보안목적으로 사용됩니다. 결국 접근 제한자는 캡슐화에 해당합니다.

### 인터페이스
* 클래스들이 필수로 구현해야 하는 추상 자료형입니다. 확장에는 열려있고 변경에는 닫혀있는 객체 간 결합도를 낮춘 유연한 방식의 개발이 가능합니다. 인터페이스는 다형성의 특징을 가지고 있습니다.

### 다형성
* 하나의 객체나 메소드가 여러가지 다른 형태를 가질 수 있는 것을 말합니다. 오버로딩과 오버라이딩 그리고 상속받은 객체의 참조변수 형변환 등이 존재합니다.

### 컬렉션 프레임워크(Collection Framework)
* 컬렉션 프레임워크는 다수의 요소를 하나의 그룹으로 묶은 컨테이너입니다. 배열은 고정된 크기를 가지고 있지만 컬렉션 프레임워크는 가변적인 크기를 가지고 있고 삽입, 탐색, 정렬 등 편리한 API 제공한다. `java.util` 패키지에서 지원하며 List, Queue, Set, Map 등을 인터페이스로 제공합니다.

### 싱글톤 패턴(Singleton Pattern)
* 싱글톤 패턴은 애플리케이션이 시작될 때 최초 한번만 메모리에 할당하고 어디에서나 접근해서 사용할 수 있는 패턴입니다.
    * 최초로 한번만 메모리 영역에 할당하고 하나의 인스턴스를 공유해서 사용하기 때문에 메모리 낭비를 방지할 수 있습니다 여러 객체가 하나의 객체만을 바라본다면 객체간의 결합도가 높아지고 변경에 유연하게 대처하기 힘들며 멀티 쓰레드 환경에서 여러 쓰레드가 공유되고 있는 상황이라면 하나의 인스턴스가 아닌 여러 개의 인스턴스가 발생할 수 있습니다.

### 제네릭
* 클래스나 메서드에서 사용할 내부 데이터 타입을 외부에서 지정하는 기법입니다.
* 잘못된 타입이 들어올 수 있는 것을 컴파일 단계에서 방지할 수 있고 불필요한 타입 변환을 제거할 수 있다.  

## Spring
### Spring DI(Dependency Injection 스프링 의존성 주입)와 IOC(Inversion of Control 제어의 역전)
* Spring DI는 객체를 직접 생성하는 방식이 아닌 외부에서 생성한 후 주입 시켜주는 방식으로 이를 통해서 모듈 간의 결합도를 낮추고 유연성을 높일 수 있습니다. 의존성 주입 방법으로는 생성자 주입, 필드 주입, 수정자 주입이 존재합니다.
    * 생성자 주입: 객체의 불변성을 확보하며 생성자 주입 시 단독으로 실행할 때도 의존관계 주입이 성립하기 때문에 테스트에 용이합니다. 또한 A와 B객체가 서로를 참조하고 있을 때 순환참조를 방지하기 위해서 컴파일 에러가 발생하기 때문에 미리 방지할 수 있습니다. 그 외의 주입 방법은 런타임 에러가 발생하기 때문에 사용에 주의가 필요합니다.
    * 필드 주입: 코드가 간결하지만 의존관계를 정확히 파악하기 힘들며 final 키워드를 선언할 수 없어서 객체가 변할 수 있고 주입과 동시에 일어나는 경우 순환 참조 에러가 발생합니다.
    * 수정자 주입: setter를 통해서 주입하며 주입하는 객체가 변경해야 하는 상황에 사용합니다.
![Spring DI 방식](https://velog.velcdn.com/images%2Fgillog%2Fpost%2F08489bda-549e-4dae-851b-8ae1734bf85e%2F21373937580AEF9B37.jpg)
* Spring IOC는 제어의 역전이라는 의미로 메소드나 객체의 호출 작업을 개발자가 결정하는 것이 아니라 외부에서 결정하는 것을 의미합니다. 제어의 역전이라고 말하며 제어의 흐름을 바꾸는 것입니다. 객체의 의존성을 역전시켜 객체 간의 결합도를 줄이고 유연한 코드를 작성할 수 있고 가독성 및 코드 중복 유지 보수를 편하게 할 수 있습니다.
    * 객체 생성 > 클래스 내부에서 의존성 객체 생성 > 의존성 객체 메소드 호출이 기존 방식이였다면 스프링에서는 객체 생성 > 의존성 객체 주입 이떄 스스로 만드는 것이 아닌 스프링에게 위임하여 스프링이 만들어놓은 객체를 주입합니다. > 의존성 객체 메소드 호출 방식으로 이루어집니다. 
    * 스프링이 모든 의존성 객체를 스프링이 실행될 때 다 만들어주며 필요한 곳에 주입시켜주고 IOC 컨테이너 안에 등록된 객체인 Bean들을 싱글톤 패턴을 특징을 가지고 있습니다.

### IOC 컨테이너
* 애플리케이션 실행 시점에 빈 오브젝트를 인스턴스화하고 DI한 후 최초로 애플리케이션을 기동할 빈 하나를 제공해줍니다.

### Spring Bean
* IOC 컨테이너 안의 들어있는 객체로 필요할 때 IOC 컨테이너에서 가져와서 사용하며 @Bean을 사용하거나 xml 설정을 통해서 일반 객체를 Bean으로 등록할 수 있습니다.

### Spring Bean 라이프 사이클
* 객체 생성 -> 의존 설정 -> 초기화 -> 사용 -> 소멸 과정의 생명주기를 가지며 Bean은 스프링 컨테이너의 의해서 생명주기를 관리하고 있습니다.

### Spring Bean Scope
* 스프링 빈이 존재할 수 있는 범위를 뜻하며 싱글톤, 프로토타입, 웹 관련 스코프인 Request, Session, Application이 존재합니다.
    * 싱글톤: 기본 스코프로 스프링 컨테이너 시작과 종료까지 유지되는 가장 넓은 범위의 스코프
    * 프로토타입: 스프링 컨테이너는 프로토타입 빈 생성과 의존관계 주입까지만 관여하고 관여하지 않는 매우 짧은 범위의 스코프
    * 웹 관련 스코프
        * Request: 웹 요청이 들어오고 나갈 때까지 유지되는 스코프
        * Session: 웹 세션이 생성되고 종료될 때까지 유지되는 스코프
        * Application: 웹 서블릿 컨텍스트와 같은 범위로 유지되는 스코프

### Spring MVC와 Dispatcherservlet
* Spring MVC는 기본적으로 MVC 패턴을 사용하는데 Front Controller인 Dispatcherservlet를 제공해서 Dispatcherservlet에서 MVC 아키텍쳐를 관리합니다.
Front Controller은 각 요청에 맞는 컨트롤러를 찾아서 호출시키고 공통 코드에 대해서는 Front Controller에서 처리하고, 서로 다른 코드들만 각 Controller에서 처리할 수 있도록 합니다.
    * 1. 서블릿 컨테어너에서 받은 HTTP 요청을 Dispatcherservlet에 할당한다.
    * 2. Dispatcherservlet은 Handler Mapping을 통해 해당 요청을 알맞은 컨트롤러로 위임한다.
    * 3. HandlerMapping을 통해 요청을 위임받은 컨트롤러는는 필요한 비즈니스 로직을 호출/수행하여 처리 결과를 생성하고 이 모델(M)과 출력될 뷰(View)를 Dispatcherservlet에 반환한다.
    * 4. 컨트롤러로 부터 ModelAndView 정보를 전달받은 Dispatcherservlet은 ViewResolver란 클래스를 이용하여 사용자에게 출력할 View 객체를 얻는다.
    * 5. ViewResolver를 통해 얻은 View객체를 통해 사용자에게 보여줄 화면을 출력한다.
![Spring MVC 흐름](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F990EC6365AF152A503)

### Servlet Filter와 Spring Interceptor의 차이
* 필터(Filter)는 J2EE 표준 스펙 기능으로 디스패처 서블릿(Dispatcher Servlet)에 요청이 전달되기 전/후에 url 패턴에 맞는 모든 요청에 대해 부가작업을 처리할 수 있는 기능을 제공한다. 디스패처 서블릿은 스프링의 가장 앞단에 존재하는 프론트 컨트롤러이므로, 필터는 스프링 범위 밖에서 처리되고 웹 컨테이너이서 관리되지만 빈으로 등록은 된다
    * init 메소드: 필터 객체를 초기화하고 서비스에 추가하기 위한 메소드
    * doFilter 메소드: url-pattern에 맞는 모든 HTTP 요청이 디스패처 서블릿으로 전달되기 전에 웹 컨테이너에 의해 실행되는 메소드
    * destroy 메소드: 필터 객체를 서비스에서 제거하고 사용하는 자원을 반환하기 위한 메소드
![Filter 흐름](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbZQx9K%2Fbtq9zEBsJ75%2FdEAKj1HEymcKyZGZNOiA80%2Fimg.png)
* 인터셉터(Interceptor)은 J2EE 표준 스펙인 필터(Filter)와 달리 Spring이 제공하는 기술로써, 디스패처 서블릿(Dispatcher Servlet)이 컨트롤러를 호출하기 전과 후에 요청과 응답을 참조하거나 가공할 수 있는 기능을 제공한다. 스프링 컨테이너에서 동작하며 디스패처 서블릿이 핸들러 매핑을 통해서 컨트롤러를 찾고 요청해 실행 체인이 반환되면 실행 체인에 등록된 인터셉터를 순차적으로 실행한다.
    * preHandle 메소드: 컨트롤러가 호출되기 전에 실행
    * postHandle 메소드: 컨트롤러를 호출된 후에 실행
    * afterCompletion 메소드: 모든 뷰에서 최종 결과를 생성하는 일을 포함해 모든 작업이 완료된 후에 실행
![Interceptor 흐름](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FSz6DV%2Fbtq9zjRpUGv%2F68Fw4fZtDwaNCZiCFx57oK%2Fimg.png)

![filter, interceptor 차이](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fcjsq60%2FbtrzjoZ0qcq%2FEDsLOVpZNcmFu6prkzALFk%2Fimg.png)

### Spring AOP(Aspect Oriented Programming)
* 관점 지향 프로그래밍이라고 불리며 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 모듈화 하겠다는 것이다. 
    * Aspect : 흩어진 관심사를 모듈화 한 것. 
    * Target : Aspect를 적용하는 곳. 클래스, 메서드 등..
    * Advice : 실질적으로 어떤 일을 해야 할 지에 대한 것, 실질적인 부가기능을 담은 구현체
    * Join Point : Advice가 적용될 위치 혹은 끼어들 수 있는 시점. 메서드 진입 시점, 생성자 호줄 시점, 필드에서 꺼내올 시점 등 끼어들 시점을 의미. 참고로 스프링에서  Join Point는 언제나 메서드 실행 시점을 의미 한다.
    * Point Cut : Join Point의 상세한 스펙을 정의한 것. "A란 메서드의 진입 시점에 호출할 것"처럼 구체적으로 Advice가 실행될 시점을 정함.
![AOP 개념 이미지](https://t1.daumcdn.net/cfile/tistory/994AA3335C1B8C9D28)

### Spring의 프록시, 다이나믹 프록시, AOP
* 스프링에서 말하는 프록시는 리플렉션과 바이트코드 조작을 이용해 실제 타겟의 기능을 대신 수행하면서 기능을 확장하거나 추가할 수도 있는(OCP원칙) 다이나믹 프록시 객체를 의미하며 스프링 AOP는 런타임에 프록시 인스턴스가 동적으로 변경되는 다이나믹 프록시 기법으로 구현되어있다.
* 즉 스프링 AOP는 실제 객체를 리플렉션으로 객체를 생성해서 Controller 동작 이후 공통된 메소드를 먼저 실행해주고 실제 객체로 이동하고 실제 객체가 나머지 로직을 실행하는 방식이다.
![스프링 AOP흐름](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdlLkeB%2Fbtrcf7mSa7f%2FDc4AIKkwUp7SQihKq4cs31%2Fimg.png)

### Spring이 Request마다 쓰레드가 생성되지만 한개의 Controller만 사용하는 이유
* Request 별로 Thread가 따로 생성되고, 이에 따라 각각의 ServletContext를 갖는데 어떻게 Controller가 1개만 생성되는데 사실상 이 Thread들은 그 1개의 Singleton Controller 객체를 공유하기에 최종적으로 1개의 Controller만 사용하는 것이다. 즉 각각의 쓰레드는 singleton으로 생성된 Controller를 참고하여 실행만 하는 것입니다.

### Spring @Transactional과 전파속성, 고립레벨
* Spring의 @Transactional은 선언적 트랜잭션으로 다수의 트랜잭션을 하나의 트랜잭션으로 묶어서 사용할 수 있습니다. @Transactional 사용에는 전파속성과 고립레벨을 고려해야하는데 전파속성을 통해서 이미 트랜잭션이 진행 중일 때 추가 트랜잭션 진행을 어떻게할지 결정할 수 있고 고립 레벨 설정을 통해서 동시서 문제를 해결할 수 있습니다.

### 동기와 비동기 Spring에서의 동기와 비동기
* 동기는 요청과 결과가 한 자리에서 동시에 일어나는 의미이며 비동기는 요청과 결과가 동시에 일어나지 않는다는 의미입니다. Spring은 기본적으로 요청하나에 하나의 쓰레드를 할당하는데 기본적으로 동기적인 방식으로 진행됩니다. 비동기의 경우 Spring AOP 프록시 객체인 @Async를 통해서 사용하는데 쓰레드 풀을 이용해서 요청을 할당 받은 쓰레드가 미리 생성된 쓰레드에 작업을 넘겨주고 다음 요청을 받는 방식으로 동작됩니다. 하지만 일반적으로 DB와 통신과정에서 Transaction으로 인해서 동기적으로 사용됩니다.

### 레이어드 아키텍쳐, 헥사고날 아키텍쳐, 클린 아키텍쳐
* 레이어드 아키텍쳐는 수평적인 레이어로 조직화되어 있는 다층 구조입니다. 계층으로 나누고 응집성을 높이고 의존도를 낮추기 위한 규칙으로 상위 레이어는 하위 레이어에 의존적인 구조입니다. 상위 계층이 하위 계층을 호출하는 단방향성 유지하며 DIP는 만족할 수 있지만 OCP는 만족하지 않습니다. 테스트 시 다른 레이어또한 모킹해야하기 때문에 복잡도가 올라갑니다.
![레이어드 아키텍쳐](https://velog.velcdn.com/images%2Fmay_soouu%2Fpost%2Fa8d19e94-3f17-4f81-aa14-2d428797afc3%2Flayered.png)
* 헥사고날 이키텍쳐는 포트와 어댑터를 통해 여러 소프트웨어 환경에 쉽게 연결할 수 있도록, 느슨하게 결합된 응용 프로그램 구성요소를 만드는 것을 목표
* 클린 아키텍쳐는 외존성 규칙은 외부에서 내부로 고수준 정책을 향해야하며 로직과 도메인이 DB또는 Web에 의존하지 않아야합니다. 

### Spring Batch
* 스프링 배치란 대용량 일괄처리의 편의를 위해 설계된 가볍고 포괄적인 배치 프레임워크로 스프링의 모든 요소를 사용해서 개발이 가능합니다.

## 인프라

### 쿠키, 세션, 캐시(서버상에서의 캐시)
* 쿠키는 브라우저에 저장되는 정보로 키와 값으로 이루어진 텍스트입니다 HTTP 헤더에 포함됩니다. 속도가 빠르지만 탈취당할 위험이 크기 때문에 보안상 적합하지 않습니다.
* 세션은 데이터를 서버에 안전하게 보관해 통신 연결을 지속적으로 유지하는 것처럼 관리하는 방식입니다. 서버에 메모리를 올려야하는 단점으로 과사용시 부하가 일어나 세션의 경우 토큰과 같은 새로운 방식으로 사용 중입니다.
* 캐시는 리소스 파일들의 임시 저장으로 변동되지 않는 정적 리소스들을 다시 사용해서 속도를 높이는 방식입니다. 캐시또한 서버 메모리에 보관하기 때문에 과사용시 부하가 일어나는데 이를 보완하는 방식으로 CDN이 도입되었습니다.
    * CDN: 여러 지역에 설치된 캐시 서버들을 사용하여 본 서버로 들어오는 요청들을 분산 처리하는 서비스

### RESTful API
* HTTP URI를 통해 자원을 명시하고 HTTP Method를 통해 자원에 대한 행위를 표현하는 API입니다. HTTP를 사용하기 때문에 HTTP의 특성을 그대로 반영하고 있지만 RESTful을 완전히 만족하는 6가지의 원칙을 지키면서 만들기는 까다롭고 분산처리에는 적합하지 않습니다.
    * REST 6 가지 원칙
        * 인터페이스 일관성(Uniform Interface): 일관적인 인터페이스로 분리되어야 한다.
        * 무상태(Stateless): 각 요청간 클라이언트의 context, 세션과 같은 상태 정보를 서버에 저장하지 않는다.
        * 캐시 처리 기능(Caching): 클라이언트는 응답을 캐싱할 수 있어야한다. 캐시를 통해 대량의 요청을 효율적으로 처리한다.
        * 클라이언트-서버(Client-Server): 아키텍처를 단순화 시키고 작은 단위로 분리함으로써 클라이언트 서버의 각 파트가 독립적으로 구분하고 서로간의 의존성을 줄인다.
        * 계층화(Hierarchical system): 클라이언트는 대상 서버에 직접 연결되어있는지, Proxy를 통해서 연결되었는지 알 수 없다.
        * Code on demand: 자바 애플릿이나 자바스크립트의 제공을 통해 서버가 클라이언트를 실행시킬 수 있는 로직을 전송하여 기능을 확장시킬수 있다.
* 추천 영상
    * [그런 REST API로 괜찮은가?](https://www.youtube.com/watch?v=RP_f5dMoHFc)

### HTTP와 HTTPS
* HTTP는 하이퍼텍스트를 교환하기 위한 통신 규약으로 서버/클라이언트 모델을 따라 데이터를 주고 받기 위한 프로토콜입니다. 애플리케이션 레벨의 프로토콜로 TCP/IP 위에서 작동하며 HTTP는 상태를 가지고 있지 않는 Stateless 프로토콜이다. Method, Path, Version, Headers, Body 등으로 구성되어 있습니다. 암호화가 되어있지 않기 때문에 정보 탈취의 위험이 존재합니다.
![HTTP](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbkdJ4Q%2FbtqK6AXLEtC%2FjBZzMuJBWzdLYmqILo5Ri1%2Fimg.png)
* HTTPS는 HTTP에 데이터 암호화가 추가된 프로토콜로 네트워크 상에서 중간에 제3자가 정보를 볼 수 없도록 암호화를 지원합니다.

### CORS
* 출처가 다른 사이트에서 자원을 공유할 경우를 의미합니다. 브라우저 상에선 출처가 다른 경우 이를 차단합니다. 해결하는 방법으로는 헤더에 출처를 허용주거나 프록시 서버를 통해서 출처를 같도록 맞춰주는 방식이 존재합니다.

### 프록시 서버
* 프록시 서버는 클라이언트가 자신을 통해서 다른 네트워크 서비스에 간접적으로 접속할 수 있게 해 주는 컴퓨터 시스템이나 응용 프로그램으로 클라이언트와 서버 사이의 중계기로써 대리로 통신을 해주고 있습니다. Forward Proxy 와 Reverse Proxy가 존재하고 있습니다.
    * 포워드 프록시는 클라이언트의 요청을 받고 인터넷에 연결하여 결과를 클라이언트에 전달해줍니다.
        * 클라이언트 보안 (Security): 방화벽같은 개념으로 포워드 프록시 서버에 룰을 추가해서 특정 사이트에 접속을 막을 수 있다.
        * 캐싱 (Caching): 어떤 웹 페이지에 접근하면 정보를 캐싱해두고 또 다시 접근할때 캐싱된 정보를 그대로 반환해서 서버의 부하를 줄이는 이점을 가진다.
        * 암호화 (Encryption): 클라이언트가 포워드 프록시를 지날때 IP 정보가 암호화되기 때문에 클라이언트의 정체를 파악하기 어렵다.
    * 리버스 프록시는 클라이언트가 인터넷에 데이터를 요청하면 리버스 프록시가 이 요청을 받아 내부 서버에서 데이터를 받은 후 클라이언트에 전달해줍니다.
        * 로드 밸런싱 (Load Balancing): 서버에 들어온 요청을 여러 대의 서버로 나누어 처리해줍니다.
        * 서버 보안 (Security): 본래 서버의 IP 주소를 노출시키지 않고 DDos공격을 막는데 유용하다.
        * 캐싱 (Caching): 미리 로드된 캐싱을 사용해서 빠른 성능을 낼 수 있다.
        * 암호화 (Encryption): SSL또는 TSL 암호화 복호화 방식을 리버스 프록시에서 해주기 때문에 본래 서버의 부담을 줄일 수 있다.

### 로드 밸런싱
* 서버가 처리해야 할 업무 혹은 요청(Load)을 여러 대의 서버로 나누어(Balancing) 처리하는 것을 의미합니다. 

### 도커
* 도커는 컨테이너를 만들고 사용할 수 있는 컨테이너 가상화 기술입니다.

### 쿠버네티스
* 쿠버네티스는 컨테이너화된 애플리케이션을 관리하는 컨테이너 오케스트레이션 기술입니다.

## DB

### RDBMS
* RDBMS는 관계형 데이터베이스를 생성하고 수정하고 관리할 수 있는 소프트웨어입니다. 모든 데이터를 2차원 테이블로 표현하며 row(record, tuple)와 column(field, item)으로 구성되어 있습니다. SQL을 사용해 Join 등의 관계형 연산을 하며 하나의 고성능 머신에 데이터를 저장하는 수직적 확장 방식입니다.
* 장점
    * 데이터의 일관성을 보장한다.
    * 데이터베이스 설계 시 불필요한 중복이 삭제된다.
    * 정규화를 전제로 하고 있기 때문에 업데이트 시 비용이 적다.
* 단점
    * 테이블 간 관계를 맺고 있어서 시스템이 커지는 경우 Join문이 많은 복잡한 쿼리가 발생되며 조회 시 성능이 떨어진다.
    * 테이블 스키마로 인해서 데이터가 유연하지 못하기 때문에 테이블 스키마가 변경될 경우 번거롭고 복잡하다.
    * 성능 향상을 위해서 Scale-up만을 지원하기 때문에 서버 비용이 기하급수적으로 증가한다.

### NoSQL
* NoSQL은 테이블 간의 관계가 없고 데이터 모델 자체가 독립적으로 설계되어 있습니다. 데이터를 여러 서버에 분산시키는 분산형 구조에 용이하고 대용량 데이터 처리에 적합합니다.
* 장점
    * 테이블 스키마가 존재하지 않기 때문에 유연한 데이터 구조를 가지고 언제든 저장된 데이터를 조정하고 새로운 필드를 추가할 수 있다.
    * 데이터 분산에 용이하고 성능 향상을 위해서 Scale-up뿐 아니라 Scale-out도 가능하다.
    * 대용량 데이터 처리에 유리하다.
* 단점
    * 데이터 중복이 발생할 수 있고 중복된 데이터가 변경 될 경우 수정을 모든 컬렉션에서 수행해야한다.
    * 테이블 스키마가 존재하지 않기 때문에 명확한 데이터 구조를 보장하지 않으며 데이터 구조 결정이 어렵다. 

### 인덱스의 기본 원리
* 기본적으로 인덱스는 B*Tree 인덱스 구조로 되어있고 제일 위쪽인 Root부터 Branch를 거쳐서 Leaf까지 연결되는 구조로 이루어져있습니다. 루트와 브랜치 블록은 각 하위 노드들의 데이터 값 범위를 나타내는 키 값과 그 키값에 해당하는 블록을 찾는데 필요한 주소정보를 가지고 있습니다. 리프 블록은 인덱스의 키 값과 그 값에 해당하는 테이블 레코드를 찾아가는데 필요한 주소 정보(ROWID)를 가지고 있습니다.
![B*Tree](https://dataonair.or.kr/publishing/img/knowledge/SQL_330.jpg)

### 인덱스 ROWID를 이용한 데이터 블록을 읽는 매커니즘
* ROWID는 디스크 상의 위치정보이며 데이터 블록을 읽을 때 항상 버퍼 캐시를 경우한다.
1. 인덱스에서 하나의 rowid 를 읽고 DBA (디스크 상 블록 위치 정보)를 해시 함수에 적용해 해시값을 확인한다.
2. 해시 값을 이용해 해시 버킷을 찾는다.
3. 해시 버킷에 연결된 해시 체인을 스캔하면서 블록 헤더를 찾는다.
4. 블록 헤더를 찾으면 거기 저장된 포인터를 이용해 버퍼 블록을 읽는다.
5. 블록 헤더를 찾지 못하면, LRU 리스트를 스캔하면서 Free 버퍼를 찾는다. 디스크에서 읽은 블록을 적재하기 위해 빈 캐시 공간을 찾는 것이다.
6. LRU 리스트에서 Free 버퍼를 얻지 못하면 Dirty 버퍼를 디스크에 기록해 Free 버퍼를 확보한다.
7. Free 버퍼를 확보하고 나면 디스크에서 블록을 읽어 캐시에 적재한다.
* rowid 에 의한 테이블 엑세스가 생각만큼 빠르지 않은 이유가 여기에 있다. 특히 다량의 테이블 레코드를 읽을 때의 성능 저하가 심각하다.
![인덱스에 의한 랜덤 엑세스](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FyykJh%2Fbtq7koztulv%2FoK6oFMi3THpeKMHT6tKm31%2Fimg.png)

### 랜덤 엑세스
* 랜덤 엑세스란 데이터를 저장하는 블록을 한번에 여러 개 액세스하는 것이 아니라 한 번에 하나의 블록만을 액세스하는 방식이다. 인덱스를 액세스하여 확인한 ROWID를 이용하여 테이블을 액세스하는 경우 랜덤액세스가 발생하게 된다. 그로인해서 디스크 I/O가 많이 발생하기 때문에 인덱스를 통해서 다량의 데이터 조회할 경우 성능이 좋지 않다.

### 클러스터 인덱스와 넌클러스터 인덱스
* 클러스터 인덱스
    * 테이블 당 1개만 허용하며 기본키 설정 시 자동으로 생성됩니다. 테이블 자체가 인덱스라서 인덱스 페이지가 따로 존재하지 않습니다. 데이터 입력, 수정, 삭제 시 항상 정렬을 유지하고 접근 성능이 좋습니다.
    * 클러스터 인덱스는 항상 정렬을 유지하기 때문에 기본적으로 성능이 보장됩니다. 하지만 테이블을 항상 정렬 상태로 유지해야 하기 때문에 입력, 수정, 삭제하는 경우에 즉각 정렬이 일어나기 때문에 속도가 느려집니다. DML이 자주 일어나는 테이블에 클러스터 인덱스는 신중하게 결정해야 합니다.
    * 클러스터 인덱스는 Root 페이지와 Leaf 페이지로 구성되어 있고 Root 페이지는 Leaf 페이지의 주소로 구성하고 Leaf 페이지는 실제 데이터 페이지로 구성되어 있습니다. 루트페이지에서 조회한 Leaf 페이지(데이터 페이지)의 주소로 바로 이동합니다.
    ![클러스터 인덱스](https://velog.velcdn.com/images/sweet_sumin/post/6c154ac2-71bd-4316-a998-7c7c29157d27/image.png)
* 넌클러스터 인덱스
    * 테이블 당 240개까지 생성 가능하며 인덱스 페이지를 별도로 생성합니다. 테이블 자체는 정렬되지 않고 인덱스 페이지에만 정렬한 상태를 유지합니다.
    * 넌클러스터 인덱스는 DML 작업이 일어나도 별도의 인덱스 페이지에서만 정렬한 상태를 유지하기 때문에 테이블 전체를 정렬하는 것보다 성능면에서 유리합니다. 하지만 조회 속도가 느려질수도 있기 때문에 상황에 맞게 잘 써야합니다.
    * 별도의 인덱스 페이지를 생성하며 리프 페이지에 index로 구성한 열을 정렬한 후 위치 포인터(RID)를 생성합니다. 루트페이지에서 리프페이지(인덱스페이지)로 이동하며 리프 페이지에 저장된 RID를 통해서 데이터 페이지로 이동합니다.
    ![넌클러스터 인덱스](https://velog.velcdn.com/images/sweet_sumin/post/15847837-1d48-4369-8244-303d4596940a/image.png)

### 인덱스
* 추가적인 쓰기작업과 저장 공간을 활용하여 데이터베이스 테이블의 검색 속도를 향상시키는 자료구조 입니다. 조회하는 속도를 높일 수 있지만 저장공간이 및 인덱스를 관리하기 위한 추가 작업이 단점입니다. 추가, 수정, 삭제이 번번하게 일어나면 연산이 일어나면 성능이 감소됩니다.

### 트랜잭션
* 데이터베이스의 상태를 변화시키는 하나의 논리적인 작업 단위또는 한번에 수행되어야하는 연산 단위입니다.
    * 트랜잭션 특징(ACID)
        * 원자성(Atomicity): 트랜잭션이 DB에 모두 반영되던지 반영되지 않아야한다.
        * 일관성(Consistency): 트랜잭션의 작업 처리 결과가 항상 일관성이 있어야한다.
        * 독립성(Isolation): 둘 이상의 트랜잭션이 동시에 실행되고 있을 경우 어떤 하나의 트랜잭션이 다른 트랜잰션의 연산에 끼어들 수 없다.
        * 지속성(Durability): 트랜잭션이 성공적으로 완료되었을 경우 결과는 영구적으로 반영되어야 한다.

### RDBMS와 NoSQL
* RDBMS는 관계형 데이터베이스 관리 시스템으로 다른 테이블들과 관계를 맺고 모여있는 집합체입니다. 무결성에 용이하기 때문에데이터가 자주 변경되는 시스템에 적용합니다.
    * 장점: 명확한 데이터 구조를 보장하며 중복없이 한번만 저장할 수 있다.
    * 단점: 테이블간 관계로 인해서 시스템이 커질 경우 Join문이 많아 복잡한 쿼리가 만들어지며 성능 향상을 위해서 Scale-up만을 지원해 비용이 기하급수적으로 늘어난다. 스키마로 인해서 데이터가 유연하지 못해서 스키마가 변경될경우 매우 번거롭다.
* NoSQL은 비관계형 데이터베이스로 테이블간의 관계를 정의하지 않습니다. 데이터가 자주 변경되지 않은 시스템과 막대한 데이터를 저장해야하는 시스템에 적합합니다.
    * 장점: 자유로운 데이터 구조를 가질 수 있고 언젠든 데이터를 조정하고 새로운 필드를 추가할 수 있다. 데이터 분산에 용이하고 성능향상을 위해서 Scale-up과 Scale-out이 가능하다.
    * 단점: 데이터 중복이 발생할수 있고 중복된 데이터가 변경되면 수정을 모든 컬렉션에서 수행해야한다. 명확한 구조를 보장하지 않아서 데이터 구조를 결정하기 어렵다.

### 파티셔닝과 샤딩
* 파티셔닝은 큰 테이블이나 인덱스를 작은 파티션(Partition) 단위로 나누어 관리하는 기법으로 데이터가 너무 커져서 조회 시간이 길어질 떄 주로 사용합니다.
* 샤딩은 수평 파티셔닝과 비슷하지만 다수의 데이터베이스에 분산하여 저장하는 기법입니다.

### Redis
* Key, Value로 이루어진 비관계형 데이터베이스입니다. 데이터베이스, 캐시, 메세지 브로커로 사용되며 인메모리 데이터 구조로 이루어져있습니다. 싱글스레드로 동작하고 자료구조를 지원하며 데이터의 스냅샷 혹은 AOF 로그를 통해 복구가 가능해서 약간의 영속성또한 보장됩니다. 스프링에서는 세션을 관리하거나 캐싱을 하는데 사용되고 있습니다.

## CS
### TDD
* TDD란 매우 짧은 개발 사이클의 반복에 의존하는 소프트웨어 개발 프로세스로 테스트케이스를 작성하고 해당 테스트를 통과하는 코드를 작성한다. 그 후 상황에 맞게 리팩토링 과정을 거치는데 테스트가 코드를 주도하는 개발 방식입니다.

### MVC(Model And View)
* 어플리케이션의 데이터에 해당하는 모델(M)과 이를 사용자에게 보여주는 뷰(V) 그리고 이를 제어하는 컨트롤러(C)로 구성되어 있으며 사용자 인터페이스와 비즈니스로직을 분리하여 개발하는 방식을 말합니다.

### 디자인 패턴
* 정적 팩토리 메소드
    * 정적 메소드를 통해서 객체를 생성하는 것으로 이름을 가질 수 있고, 매번 새로운 객체를 생성할 필요가 없어서 리소스 낭비를 줄일수 있다.
* 빌더 패턴
    * 생성자를 통해서 객체를 생성하지 않고 빌더라는 내부 클래스를 통해서 객체를 생성하는 패턴으로 인수 전달이 쉬워지며 결합도를 낮출 수 있다.
* 싱글톤 패턴
    * 객체의 인스턴스가 오직 1개만 생성되는 패턴으로 미리 생성된 객체의 인스턴스를 다른 객체의 인스턴스에서 전역으로 사용해 공유한다. 고정된 메모리 영역을 사용하기 때문에 메모리 낭비를 방지할 수 있다. 하지만 사용 시 여러가지 단점들이 있어서 trade-off를 고려해야한다.


## [팩토리 메소드 패턴과 추상 팩토리 패턴](https://fvor001.tistory.com/63)
* `팩토리 메소드 패턴`은 객체를 생성하는 인터페이스는 미리 정의하고 객체 생성은 서브 클래스인 팩토리로 위임하는 패턴입니다. 아래의 코드는 노트북이라는 인터페이스가 존재하고 LG노트북과 삼성노트북은 노트북이라는 인터페이스를 상속받습니다. 하지만 실제로 코드를 구현할때 노트북을 바로 생성하는 것이 아닌 노트북 팩토리를 통해서 노트북을 생성받습니다.
```
public interface Notebook {

}

public class LGNotebook implements Notebook {
	public LGNotebook() {
		System.out.println("LG 노트북");
	}
}

public class SamSungNotebook implements Notebook{
	public SamSungNotebook() {
		System.out.println("SamSung 노트북");
	}
}

public class NotebookFactory {
	public Notebook createNotebook(String type) {
		Notebook notebook = null;
		switch (type) {
		case "LG":
			notebook = new LGNotebook();
			break;
		case "SamSung":
			notebook = new SamSungNotebook();
			break;
		}
		return notebook;
	}
}
```
* `추상 팩토리 패턴`은 서로 연관되거나 의존적인 객체들의 조합을 만드는 인터페이스를 제공하는 패턴입니다. 아래의 코드를 보면 팩토리 인터페이스를 통해서 각각의 구현 인터페이스를 메소드로 제공해주고 있습니다 각각의 구현 인터페이스를 제공해주는 팩토리 클래스에 추상화된 팩토리 인터페이스를 제공해줘서 서로 연관있는 객체들의 조합을 하나의 인터페이스로 구현할 수 있습니다.
```
public interface NewComputerFactory {
	public Notebook createNotebook();
	public Mouse createMouse();
}

public class LGComputerFactory implements NewComputerFactory{

	@Override
	public LGNotebook createNotebook() {
		return new LGNotebook();
	}

	@Override
	public LGMouse createMouse() {
		return new LGMouse();
	}
}

public class SamSungComputerFactory implements NewComputerFactory{
	@Override
	public SamSungNotebook createNotebook() {
		return new SamSungNotebook();
	}

	@Override
	public SamSungMouse createMouse() {
		return new SamSungMouse();
	}
}

public class Factory {
	public void createComputer(String type){
        NewComputerFactory newcomputerFactory= null;
        switch (type){
            case "LG":
            	newcomputerFactory = new LGComputerFactory();
                break;

            case "SamSung":
            	newcomputerFactory = new SamSungComputerFactory();
                break;
        }

        newcomputerFactory.createNotebook();
        newcomputerFactory.createMouse();
    }
}
```
* 두 패턴의 차이는 `팩토리 메소드 패턴`은 각각 다른 객체들이 필요할 때 사용하며, `추상 팩토리 패턴`은 서로 연관있는 객체들의 조합이 필요할 때 사용합니다.
## Java

### JVM(Java Virtual Machine)
* JVM은 자바 가상 머신으로 자바 프로그램 실행환경을 만들어주는 소프트웨어입니다. 자바 애플리케이션을 컴파일하여 클래스 로더를 통해 실행합니다. 메모리 관리인 GC를 수행하며 스택 기반의 가상머신입니다. 자바는 JVM을 통해서 실행되기 때문에 운영체제에 종속적이지 않고 어떠한 플랫폼에도 영향을 받지 않고 동일하게 수행됩니다.
![Java 기본 실행 과정](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F56cSc%2FbtruTEtjRXJ%2Fr1JNTkEuEeY8cSKtqcXCRK%2Fimg.png) 
*  JVM 동작 방식
    * 1. 자바 프로그램이 실행되면 JVM은 OS로 부터 메모리를 할당받는다.
    * 2. 자바 컴파일러가 자바 코드(.java)를 자바 바이트코드(.class)로 컴파일한다.
    * 3. 자바 바이트코드를 클래스 로더를 통해서 Runtime Data Area로 로딩한다.
    * 4. Runtime Data Area에 로딩된 자바 바이트 코드를 Execution Engine을 통해서 해석한다.
    * 5. 해석된 바이트 코드는 Runtime Data Area의 각 영역에 배치되어 수행하며 Execution Engine에 의해서 GC 작동과 쓰레드 동기화가 된다.
![JVM 동작 방식](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcQRqku%2Fbtru0vJ6Ixx%2F9qCTW7ChXc80fGfQUrT4B0%2Fimg.png)
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


## Spring

### Spring DI(Dependency Injection 스프링 의존성 주입)와 IOC(Inversion of Control 제어의 역전)
* Spring DI는 객체를 직접 생성하는 방식이 아닌 외부에서 생성한 후 주입 시켜주는 방식으로 이를 통해서 모듈 간의 결합도를 낮추고 유연성을 높일 수 있습니다. 의존성 주입 방법으로는 생성자 주입, 필드 주입, 수정자 주입이 존재합니다.
    * 생성자 주입: 객체의 불변성을 확보하며 생성자 주입 시 단족으로 실행할 때도 의존관계 주입이 성립하기 때문에 테스트에 용이합니다. 또한 A와 B객체가 서로를 참조하고 있을 때 순환참조를 방지하기 위해서 컴파일 에러가 발생하기 때문에 미리 방지할 수 있습니다. 그 외의 주입 방법은 런타임 에러가 발생하기 때문에 사용에 주의가 필요합니다.
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

## 인프라

## DB


## CS

### RESTful API
* RESTful API란 API 설계의 중심에 자원(Resource)이 있고 HTTP Method를 통해 자원을 처리하도록 설계입니다.
    * REST 6 가지 원칙
        * Uniform Interface
        * Stateless
        * Caching
        * Client-Server
        * Hierarchical system
        * Code on demand
* 추천 영상
    * [그런 REST API로 괜찮은가?](https://www.youtube.com/watch?v=RP_f5dMoHFc)

### TDD
* TDD란 매우 짧은 개발 사이클의 반복에 의존하는 소프트웨어 개발 프로세스로 테스트케이스를 작성하고 해당 테스트를 통과하는 코드를 작성한다. 그 후 상황에 맞게 리팩토링 과정을 거치는데 테스트가 코드를 주도하는 개발 방식입니다.

### MVC(Model And View)
* 어플리케이션의 데이터에 해당하는 모델(M)과 이를 사용자에게 보여주는 뷰(V) 그리고 이를 제어하는 컨트롤러(C)로 구성되어 있으며 사용자 인터페이스와 비즈니스로직을 분리하여 개발하는 방식을 말합니다.




# 12. 다형성
* 상속의 목적은 코드 재사용이 아니다.
* 상속은 타입 계층을 구조화하기 위해 사용해야 한다.
* 타입 계층은 객체 지향 프로그래밍의 중요한 특성 중 하나인 다형성의 기반을 제공한다.
* 상속을 이용해 자식 클래스를 추가하려 한다면 스스로에게 다음과 같은 질문을 해보자
  * 단순히 코드를 재사용하기 위해서인가? 예, 라고 생각한다면 상속을 사용하지 말아라
  * 인스턴스들을 동일하게 행동하는 그룹으로 묶기 위해서인가? 예, 라고 생각한다면 사용해도 된다.
* 많은 시간이 흐른 지금도 여전히 상속은 다형성을 구현할 수 있는 가장 일반적인 방법이지만 최근의 언어들은 상속 이외에도 다형성을 구현할 수 있는 다양한 방법들을 제공하고 있기 때문에 과거에 비해 상속의 중요성이 많이 낮아졌다고 할 수 있다.

## 01. 다형성
다형성이라는 단어는 ‘많은’을 의미하는 ‘poly’와 ‘형태’를 의미하는 ‘morph’의 합성어로 `많은 형태를 가질 수 있는 능력`’`을 의미한다. 다형성은 여러 타입을 대상으로 동작할 수 있는 코드를 작성할 수 있는 방법이라고 할 수 있다.

다형성의 분류
* 임시 다형성
  * 오버로딩 다형성: 하나의 클래스 안에 동일한 이름의 메서드가 존재하는 경우
    ```
    public class Money {
        public Money plus(Money amount) { ... } 
        public Money plus(BigDecimal amount) { ... } 
        public Money plus(long amount) { ... }
    }
    ```
  * 강제 다형성: 언어가 지원하는 자동적인 타입 변환이나 사용자가 직접 구현한 타입 변환을 이용해 동일한 연산자를 다양한 타입에 사용할 수 있는 방식
    * ‘+’ 연산자는 정수형 + 정수형인 경우 뎃셈 연산자, 문자열 + 정수형 연결 연산자로 동작
* 유니버셜 다형성
  * 매개변수 다형성: 제네릭 프로그래밍과 관련이 높은데 클래스의 인스턴스 변수나 메서드의 매개변수 타입을 임의의 타입으로 선언한 후 사용하는 시점에 구체적인 타입으로 지정하는 방식
    * List 인터페이스는 컬렉션에 보관할 요소의 타입을 임의의 타입 주로 지정
  * 포함 다형성: 메시지가 동일 하더라도 수신한 객체의 타입에 따라 실제로 수행되는 행동이 달라 지는 기능
    * 서브타입 다형성이라고도 부른다.
    ```
    public class Movie {
        private DiscountPolicy discountPolicy;

        public Money calculateMovieFee(Screening screening) {
            return fee.minus(discountPolicy.calculateDiscountAmount(screening));
        } 
    }
    ```

> 상속의 진정한 목적은 코드 재사용이 아니라 다형성을 위한 서브 타입 계층을 구축하는 것이다.

## 02. 상속의 양면성
객체지향 프로그램을 작성하기 위해서는 두 가지 관점을 함께 고려해야 한다.
* 데이터
  * 데이터 관점의 상속은 상속을 이용하면 부모 클래스에서 정의한 모든 데이터를 자식 클래스의 인스턴스에 자동으로 포함시킬 수 있다.
* 행동
  * 행동 관점의 상속은 데이터뿐만 아니라 부모 클래스에서 정의한 일부 메서드 역시 자동으로 자식 클래스에 포함시킬 수 있다.

상속의 목적은 코드 재사용이 아니다.
* 로그램을 구성하는 개념들을 기반으로 다형성을 가능 하게 하는 타입 계층을 구축하기 위한 것이다.
* 타입 계층에 대한 고민 없이 코드를 재사용하기 위해 상속을 사용하면 이해하기 어렵고 유지보수하기 버거운 코드가 만들어질 확률이 높다.

상속의 메커니즘을 이해하는 데 필요한 개념은 아래와 같다.
* 업캐스팅
* 동적 메서드 탐색
* 동적 바인딩 
* self 참조
* super 참조

### 상속을 사용한 강의 평가
수강생들의 성적을 계산하는 예제 프로그램을 구현한다.
* `Pass:3 Fail:2z A:1 B:1 C:1 D:0 F:2` 같은 형식으로 성적 통계를 출력한다.
```
public class Lecture {
    private int pass;
    private String title;
    private List<Integer> scores = new ArrayList<>();

    public Lecture(String title, int pass, List<Integer> scores) {
        this.title = title;
        this.pass = pass;
        this.scores = scores;
    }

    public double average() {
        return scores.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    public List<Integer> getScores() {
        return Collections.unmodifiableList(scores);
    }

    public String evaluate() {
        return String.format("Pass:%d Fail:%d", passCount(), failCount());
    }

    private long passCount() {
        return scores.stream().filter(score -> score >= pass).count();
    }

    private long failCount() {
        return scores.size() - passCount();
    }
}

public class Grade {
    private String name;
    private int upper,lower;

    private Grade(String name, int upper, int lower) {
        this.name = name;
        this.upper = upper;
        this.lower = lower;
    }

    public String getName() {
        return name;
    }

    public boolean isName(String name) {
        return this.name.equals(name);
    }

    public boolean include(int score) {
        return score >= lower && score <= upper;
    }
}

public class GradeLecture extends Lecture {
    private List<Grade> grades;

    public GradeLecture(String name, int pass, List<Grade> grades, List<Integer> scores) {
        super(name, pass, scores);
        this.grades = grades;
    }

    @Override
    public String evaluate() {
        return super.evaluate() + ", " + gradesStatistics();
    }

    private String gradesStatistics() {
        return grades.stream().map(grade -> format(grade)).collect(joining(" "));
    }

    private String format(Grade grade) {
        return String.format("%s:%d", grade.getName(), gradeCount(grade));
    }

    private long gradeCount(Grade grade) {
        return getScores().stream().filter(grade::include).count();
    }

    public double average(String gradeName) {
        return grades.stream()
                .filter(each -> each.isName(gradeName))
                .findFirst()
                .map(this::gradeAverage)
                .orElse(0d);
    }

    private double gradeAverage(Grade grade) {
        return getScores().stream()
                .filter(grade::include)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }
}
```

GradeLecture와 Lecture에 구현된 두 evaluate 메서드의 시그니처가 완전히 동일 하다는 것으로 동일한 시그니처를 가진 메서드가 존재할 경우 자식 클래 스의 메서드 우선순위가 더 높다.
* 메시지를 수신했을 때 부모 클래 스의 메서드가 아닌 자식 클래스의 메서드가 실행된다는 것을 의미
* 자식 클래스 안에 상속받은 메서드와 동일한 시그니처의 메서드를 재정해서 부모 클래스의 구현 을 새로운 구현으로 대체하는 것을 `메서드 오버라이딩`이라고 부른다.

### 데이터 관점의 상속
* 데이터 관점에서 상속은 자식 클래스의 인스턴스 안에 부모 클래스의 인스턴스를 포함하는 것으로 볼 수 있다.
* 자식 클래스의 인스턴스는 자동으로 부모 클래스에서 정의한 모든 인스턴스 변수를 내부에 포함하게 되는 것이다.

<img src="./image/그림%2012.3.png">

### 행동 관점의 상속
* 행동 관점의 상속은 부모 클래스가 정의한 일부 메서드를 자식 클래스의 메서드로 포함시키는 것을 의미한다.
* 부모 클래스에서 구현한 메서드를 자식 클래스의 인스턴스에서 수행할 수 있는 이유는 런타임에 시스템이 자식 클래스에 정의되지 않은메서드가 있을 경우 이 메서드를 부모 클래스 안에서 탐색하기 때문이다.

<img src="./image/그림%2012.6.png">

## 03. 업캐스팅과 동적 바인딩
### 같은 메시지, 다른 메서드
성적 계산 프로그램에 기능을 추가한다.
* 교수별로 강의에 대한 성적 통계를 계산하는 기능을 추가
* 통계를 계산하는 책임은 Professor 클래스
```
public class Professor {
    private String name;
    private Lecture lecture;

    public Professor(String name, Lecture lecture) {
        this.name = name;
        this.lecture = lecture;
    }

    public String compileStatistics() {
        return String.format("[%s] %s - Avg: %.1f", name,
                lecture.evaluate(), lecture.average());
    }
}
```

동일한 객체 참조인 lecture 에 대해 동일한 evaluate 메세지를 전송하는 동일한 코드 안에서, 서로 다른 클래스 안에 구현된 메서드를 실행할 수 있다.
```
Professor professor01 = new Professor("다익스트라", new Lecture(...))
Professor professor02 = new Professor("다익스트라", new GradeLecture(...))
  
professor01.compileStatistics();
professor02.compileStatistics();
```

코드 안에서 선언된 참조 타입과 무관하게 실제로 메세지를 수신한 객체의 타입에 따라 실행되는 메서드가 달라질 수 있는 것은 다음 두 메커니즘이 작용하기 때문이다.
* 업캐스팅
  * 부모 클래스 타입으로 선언된 변수에 자식 클래스의 인스턴스를 할당하는 것이 가능
* 동적 바인딩
  * 메세지를 처리할 적절한 메서드를 컴파일 시점이 아니라 실행시점에 결정

### 업캐스팅
* 부모 클래스의 인스턴스 대신 자식 클래스 인스턴스를 사용하더라도 메시지를 처리하는 데는 아무런 문제가 없으며, 컴파일러는 명시적인 타입 변환 없이도 자식 클래스가 부모 클래스를 대체할 수 있게 허용한다.
* 부모 클래스의 인스턴스를 자식 클래스 타입으로 변환하기 위해서는 명시적인 타입 캐스팅이 필요한데 이를 `다운캐스팅(downcasting)`이라고 부른다.
* 자식 클래스는 현재 상속 계층에 존재하는 자식 클래스뿐만 아니라 앞으로 추가될지도 모르는 미래의 자식 클래스들을 포함하기 때문에 어떤 자식 클래스와도 협력할 수 있는 무한한 확장 가능성을 가진다.
  
<img src="./image/그림%2012.7.png">

<img src="./image/그림%2012.8.png">

> 업 캐스팅은 유연하며 확장이 용이하다.

### 동적 바인딩
전통적인 언어에서 함수를 실행하는 방법은 함수를 호출하는 것으로 호출될 함수를 컴파일 타임에 결정한다.
* 정적 바인딩(static binding)
* 초기 바인딩(early biding)
* 컴파일타임 바인딩(compile-time binding)

객체지향 언에서는 메시지를 수신했을 때 실행될 메서드가 런타임에 결정된다.
* 동적 바인딩(dynamic binding)
* 지연 바인딩(late binding)

> 객체지향 언어가 제공하는 업캐스팅과 동적 바인딩을 이용하면 부모 클래스 참조에 대한 메시지 전송을 자식 클래스에 대한 메서드 호출로 변환할 수 있다.

## 04. 동적 메서드 탐색과 다형성
객체지향 시스템은 다음 규칙에 따라 실행할 메서드를 선택한다.
* 메시지를 수신한 객체는 먼저 자신을 생성한 클래스에 적합한 메서드가 존재하는지 검사한다. 존재하면 메서드를 실행하고 탐색을 종료한다.
* 메서드를 찾지 못했다면 부모 클래스에서 메서드 탐색을 계속한다. 이 과정은 적합한 메서드를 찾을 때까지 상속 계층을 따라 올라가며 계속된다.
* 상속 계층의 가장 최상위 클래스에 이르렀지만 메서드를 발견하지 못한 경우 예외를 발생시키며 탐색을 중단한다.

메시지 탐색과 관련해서 이해해야 하는 중요한 변수인 `self 참조(self reference)`가 있다.
* 객체가 메시지를 수신하면 컴파일러는 self 참조라는 임시 변수를 자동으로 생성한 후 메시지를 수신한 객체를 가리키도록 설정한다. 
* 자바에서는 self 참조를 this라고 부른다.

동적 메서드 탐색은 두 가지 원리로 구성된다.
* 자동적인 메세지 위임
  * 자식 클래스는 이해할 수 없는 메세지를 전송 받으면 상속 계층을 따라 부모 클래스에 처리를 위임한다.
  * 클래스 사이의 위임은 프로그래머의 개입 없이 상 속 계층을 따라 자동으로 이뤄진다.
* 동적인 문맥
  * 메세지를 수신했을 때, 실제로 어떤 메서드가 실행될지 결정하는 것은 컴파일 시점이 아니라 실행시점에 이뤄진다.
  * 메서드를 탐색 하는 경로는 self 참조를 이용해서 결정한다.

> 객체의 타입에 따라 메서드를 탐색하는 문맥이 동적으로 결정되며, 여기서 가장 중요한 역할을 하는 것이 바로 self 참조다.

### 자동적인 메시지 위임
#### 메서드 오버라이딩
* 자식 클래스의 메서드가 부모 클래스의 메서드를 감추게 된다.
```
-- 메서드 탐색은 self 참조가 가리키는 객체의 클래스인 Lecuture 에서 시작한다.
-- Lecture 클래스 안에 evaluate 메서드가 존재하기 때문에, 메서드 실행한 후 탐색은 종료한다.
Lecture lecture = new Lecture(...);
lecture.evaluate();
```

<img src="./image/그림%2012.11.png">

```
-- Lecture 에 정의된 메서드가 아닌 실제 객체를 생성항 클래스인 GradeLecture 에 정의된 메서드가 실행된다.
-- self 참조가 가리키는 객체의 클래스인 GradeLecture 에서 탐색을 시작하고 GradeLecture 클래스 안에 evaluate 메서드가 구현되어 있기 때문이다.
Lecture lecture = new GradeLecture(...);
lecture.evaluate();
```

<img src="./image/그림%2012.13.png">

#### 메서드 오버로딩
* 자식 클래스의 메서드와 부모 클래스의 메서드가 공존한다.

<img src="./image/그림%2012.15.png">

동일한 이름의 메서드가 공존하는 경우를 메서드 오버로딩이라고 부른다.
* average() 메서드와 average(String grade) 메서드는 이름은 같지만 시그니처가 다르다.

C++는 상속 계층 안에서 동일한 이름의 메서드를 숨겨서 클라이언트가 호출하지 못하게 막는데 이를 `이름 숨기기`라고 부른다.
* 이름 숨기기 문제를 해결하는 방법은 부모 클래스에 정의된 모든 메서드를 자식 클래스에서 오버로딩 하는 것이다.

> 동적 메서드 탐색과 관련된 규칙이 언어마다 다를 수 있다는 점으로 사용하는 언어의 문법과 메서드 탐색 규칙을 주의깊게 살펴봐야 한다.

### 동적인 문맥
* 메시지를 수신한 객체가 무엇이냐에 따라 메서드 탐색을 위한 문맥이 동적으로 바뀌며 이 동적인 문맥을 결정하는 것이 메세지를 수신한 객체를 가리키는 self 참조이다.
* self 참조가 동적 문맥을 결정한다는 것은 종종 어떤 메서드가 실행될지 예상하기 어렵게 만들며 대표적인 경우가 `self 전송(self send)`이다.
```
public class Lecture { 
    public String stats() {
        return String.format("Title: %s, Evaluation Method: %s", title, getEvaluationMethod());
     }

    public String getEvaluationMethod() { 
        return "Pass or Fail";
    } 
}
```
```
public class GradeLecture extends Lecture {
    @Override
    public String getEvaluationMethod() {
        return "Grade";
    }
}
```

1. self 참조는 GradeLecture 인스턴스를 가리키도록 설정되고 탐색은 GradeLecture 부터 시작한다.
2. GradeLecture 클래스에는 stats 메세지를 처리할 메서드가 없기 때문에 부모 클래스인 Lecture 에서 메서드 탐색을 계속하고 Lecture 에서 stats 메서드를 발견하고 실행한다.
3. 실행 중에, self 참조가 가리키는 getEvaluationMethod 메세지를 전송하는 구문과 마주친다.
4. 메서드 탐색은 self 참조가 가리키는 객체에서 다시 시작한다.
   
<img src="./image/그림%2012.17.png">

> self 전송은 자식 클래스에서 부모 클래스 방향으로 진행되는 동적 메서드 탐색 경로를 다시 self 참조가 가리키는 원래의 자식 클래스로 이동시킨다.
> self 전송이 깊은 상속 계층과 계층 중간중간에 함정처럼 숨겨져 있는 메서드 오버라이딩과 만나면 극 단적으로 이해하기 어려운 코드가 만들어진다.

### 이해할 수 없는 메시지
클래스는 자신이 처리할 수 없으면 부모 클래스에게 위임하지만 정상에 도착해야 메시지를 처리할 수 없다는 사실을 알게 됐다면 프로그래밍 언어에 따라서 처리하는 방법이 다르다.
1. 정적 타입 언어와 이해할 수 없는 메시지
   * 코드를 컴파일 할 때 상속 계층 안의 클래스들이 메세지를 이해할 수 있는지 여부를 판단한다.
   * 상속 계층 전체를 탐색한 후에도 메시지를 처리할 메서드를 발견하지 못하면 컴파일 에러가 발생하기 때문에 안정적이다.
   ```
   Lecture lecture = new GradeLecture(...);
   lecture.unknownMessage(); // 컴파일 에러!
   ```
2. 동적 타입 언어와 이해할 수 없는 메시지
   * 실제로 코드 실행 전에는 메시지 처리 가능 여부를 판단할 수 없지만 이해할 수 없는 메세지에 대해 예외를 던지는 것 외에도 doesNotUnderstand, method_missing 메시지를 응답 할 수 있는 메서드를 구현할 수 있기 때문에 유연하다.
   * 이해할 수 없는 메시지를 처리할 수 있는 동적 타입 언어의 특징은 메타 프로그래밍 영역에서 진가를 발휘한다. 마틴 파울러는 동적 타입 언어의 이러한 특징을 이용해 도메인-특화 언어를 개발하는 방식을 동적 리셉션이라고 부른다.

> 객체지향 프로그래밍 언어는 업캐스팅, 동적 바인딩과 같은 메커니즘의 도움을 받아 일한 메시지에 대해 서로 다른 메서드 를 실행할 수 있는 다형성을 구현한다.

### self 대 super
* self 참조의 가장 큰 특징은 동적이라는 점으로 self 참조는 메시지를 수신한 객체의 클래스에 따라 메서드 참색을 위한 문맥을 실행 시점에 결정한다.
* super 참조는 부모 클래스의 메서드를 호출하는 것으로 자식 클래스에서 부모 클래스의 인스턴스 변수나 메서드에 접근하기 위해 사용한다.

```
public class GradeLecture extends Lecture { 
    @Override
    public String evaluate() {
        // super 참조를 이용해 부모 클래스에게 evaluate 메시지를 전송한다.
        return super.evaluate() + ", " + gradesStatistics();
    }
}
```

메서드를 호출한다고 표현하지 않고 super 참조를 이용해 `메시지를 전송`한다고 표현한 것은 단순히 super 참조가 부모 클래스의 메서드를 호출하는 것이 아닌 더 상위에 위치상 조상 클래스의 메서드일 수도 있다.

<img src="./image/그림%2012.19.png">

super 참조의 정확한 의도는 `부모 클래스에서부터 메서드를 탐색을 시작`하라는 의미이며 실행하고자 하는 메서드가 반드시 부모 클래스에 위치하지 않아도 되는 유연 성을 제공하는데 조상 클래스 어딘가에만 있다면 성공적으로 탐색된다.

부모 클래스의 메서드를 호출하는 것과 부모 클래스에서 메서드 탐색을 시작하는 것은 의미가 다른데 이처럼 super 참조를 통해서 메세지를 전송하는 것은 부모 클래스의 인스턴스에게 메시지를 전송하는 것처럼 보이기 때문에 이를 `super 전송(super send)`라고 한다.

self 전송이 메시지를 수신하는 객체의 클래스에 따라 메서드를 탐색할 시작 위치를 동적으로 결정한다면 super 전송은 항상 메시지를 전송하는 클래스의 부모 클래스에서부터 시작된다라는 차이점이 존재한다.

> self 참조, super 참조는 상속을 이용해 다형성을 구현하고 코드를 재상하기 위한 핵심적인 재료이다.

## 05. 상속 대 위임
### 위임과 self 참조
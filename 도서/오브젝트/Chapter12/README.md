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
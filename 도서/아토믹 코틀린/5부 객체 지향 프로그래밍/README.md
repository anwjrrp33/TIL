## 아톰 55. 인터페이스
### 인터페이스
* 인터페이스를 `구현하는 모든 클래스의 프로토타입`이다.
  1. 클래스가 무엇을 하는지 기술하지만 어떻게 하는지는 기술하지 않는다.
  2. 클래스의 형태를 제시하지만 일반적으로 구현은 포함하지 않는다.
  3. 객체의 동작을 지정하지만 어떻게 수행하는지에 대한 세부 사항은 제시하지 않는다.
  4. 존재의 목표나 임무를 기술하며 클래스는 세부적인 구현 사항을 포함한다.
* `시스템의 여러 부품이 서로 의사소통하는 수단`이다.
* `애플리케이션 프로그래밍 인터페이스`는 `여러 소프트웨어의 구성 요소 사이에 명확히 정의된 통신 경로의 집합`으로 `객체 지향 프로그래밍에선 객체의 API는 객체가 다른 객체와 상호 작용할 때 사용하는 공개 멤버의 집합`이다.
* 인터페이스를 사용하는 코드는 인터페이스에서 호출할 수 있는 함수가 무엇인지에 대한 정보만 알고 있으며, 인터페이스는 클래스 사이의 프로토콜을 확립한다.
* interface 키워드를 사용해 만들며 구현하는 클래스를 정의 시에는 콜론과 인터페이스 이름을 넣는다.
  * 인터페이스 멤버 구현 시 override 변경자 필요
  * 각 함수를 구체적인 함수, 구상 함수라고 한다.
  ```
  interface Computer {
    fun prompt(): String
    fun calculateAnswer(): Int
  }
  
  class Desktop : Computer {
    override fun prompt() = "Hello!"
    override fun calculateAnswer() = 11
  }
  
  class DeepThought : Computer {
    override fun prompt() = "Thinking..."
    override fun calculateAnswer() = 42
  }
  
  class Quantum : Computer {
    override fun prompt() = "Probably..."
    override fun calculateAnswer() = -1
  }
  ```
  * 인터페이스가 프로퍼티를 선언할 수 있다.
  ```
  interface Player {
    val symbol: Char
  }
  
  class Food : Player {
    override val symbol = '.'
  }
  
  class Robot : Player {
    override val symbol get() = 'R'
  }
  
  class Wall(override val symbol: Char) : Player
  ```
  * 이넘도 인터페이스를 구현할 수 있다.
  ```
  interface Hotness {
    fun feedback(): String
  }
  
  enum class SpiceLevel : Hotness {
    Mild {
      override fun feedback() =
        "It adds flavor!"
    },
    Medium {
      override fun feedback() =
        "Is it warm in here?"
    },
    Hot {
      override fun feedback() =
        "I'm suddenly sweating a lot."
    },
    Flaming {
      override fun feedback() =
        "I'm in pain. I am suffering."
    }
  }
  ```

### SAM 변환
* 단일 추상 메서드(SAM) 인터페이스는 자바 개념으로 자바에선 멤버 함수를 메서드라 부른다.
* 코틀린에선 SAM 인터페이스를 정의하는 fun interface 문법이 존재한다.
  ```
  fun interface ZeroArg {
    fun f(): Int
  }
  
  fun interface OneArg {
    fun g(n: Int): Int
  }
  
  fun interface TwoArg {
    fun h(i: Int, j: Int): Int
  }
  ```
* fun interface를 붙이면 멤버 함수가 하나만 들어있는지 확인한다.
* 람다를 사용하는 경우 SAM 변환이라고 한다.
  ```
  fun interface ZeroArg {
    fun f(): Int
  }
  
  fun interface OneArg {
    fun g(n: Int): Int
  }
  
  fun interface TwoArg {
    fun h(i: Int, j: Int): Int
  }

  class VerboseZero : ZeroArg {
    override fun f() = 11
  }
  
  val verboseZero = VerboseZero()
  
  val samZero = ZeroArg { 11 }
  
  class VerboseOne : OneArg {
    override fun g(n: Int) = n + 47
  }
  
  val verboseOne = VerboseOne()
  
  val samOne = OneArg { it + 47 }
  
  class VerboseTwo : TwoArg {
    override fun h(i: Int, j: Int) = i + j
  }
  
  val verboseTwo = VerboseTwo()
  
  val samTwo =  TwoArg { i, j -> i + j }
  ```
* 자주 쓰이는 구문을 SAM 변환을 사용해 간결한 구문으로 작성할 수 있고, 객체를 한번만 쓰면 클래스를 억지로 정의할 필요가 없다.
* 람다를 SAM 인터페이스가 필요한 곳에 넘길 수 있다.
  ```
  fun interface Action {
    fun act()
  }
  
  fun delayAction(action: Action) {
    trace("Delaying...")
    action.act()
  }

  fun main() {
    delayAction { trace("Hey!") }
  }
  ```

## 아톰 56. 복잡한 생성자
### 복잡한 생성자
* 생성자는 새 객체를 만드는 특별한 함수다.
* var, val을 파라미터 목록에 있는 파라미터에 붙이면 파라미터를 프로퍼티로 만들면서 객체 외부에서 접근할 수 있다.
* 생성 과정을 좀 더 제어하고 싶으면 클래스 본문에 생성자 코드를 추가하면 되는데 객체 생성 중 `init 블록 안의 코드가 실행`된다.
  * 생성자 파라미터에 var, val이 붙여있지 않아도 init 사용 가능
  * content는 val 정의지만 정의시점에 초기화하지 않고 이런 경우 코틀린이 어느 지점에서 한번만 초기화가 일어나도록 보장한다.
  ```
  Class Message(text: String) {
    private val content: STring

    init {
        counter += 10
        conent = "[$counter] $text"
    }

    override fun toString() = content
  }
  ```
* 생성자는 생성자 파라미터 목록, init 블록을 합친 것이고 객체를 생성하는 동안 실행된다.
* init 블록은 클래스 본문에 정의된 순서대로 실행된다.
* 크고 복잡한 클래스에서 init을 분산시키면 유지보수 문제를 야기할 수 있다.

## 아톰 57. 부생성자
### 부생성자
* 객체를 생성할 때 이름 붙은 인자나 디폴트 인자를 통해서 생성하는게 쉽지만 오버로드한 생성자를 여러 개만들어야 할 경우도 있다.
* 오버로드한 생성자를 `부생성자`라고 부르며, 생성자 파라미너, 프로퍼티 초기화, init 블록을 합친 생성자는 `주생성자`다.
* 부생성자는 constructor 키워드 다음 파라미터 목록을 넣고 안에서 this 키워드로 주생성자나 다른 부생성자를 호출한다.
  ```
  class WithSecondary(i: Int) {
    init {
      trace("Primary: $i")
    }
    constructor(c: Char) : this(c - 'A') {
      trace("Secondary: '$c'")
    }
    constructor(s: String) :
      this(s.first()) {             // [1]
      trace("Secondary: \"$s\"")
    }
    /* 주생성자를 호출하지 않으면
       컴파일이 되지 않는다
    constructor(f: Float) {         // [2]
      trace("Secondary: $f")
    }
    */
  }
  ```
* init 블록을 사용하지 않아도 되지만 몇가지 주의점이 있다.
  1. 주생성자의 파라미터만 var, val을 붙여 프로퍼티 선언이 가능
  2. 부생성자에 반환 타입 지정 불가능
  3. 프로퍼티의 네이밍이 겹치는 경우 this를 사용해서 모호성 없애기
  4. 부생성자 본문을 적지 않아도 되지만 this() 호출은 필수
* 디폴트 인자를 써서 부생성자를 주생성자 하나로 만들면 클래스를 단순하게 만들 수 있다.

## 아톰 58. 상속
### 상속
* 상속은 `기존 클래스를 재사용하면서 변경해 새로운 클래스를 만드는 매커니즘`이다.
* 객체 지향 언어는 `상속`이라는 재사용 매커니즘 제공하며, 코틀린의 상속 구문은 콜론(:)을 붙인다.
  * 기본적으로 클래스는 상속이 닫혀있어 `기반 클래스는 open을 붙어야 상속을 허용`한다.
  * 자바에선 final을 통해 상속을 명시적으로 금지하지만, 코틀린은 `기본적으로 모든 클래스가 final`이다.
  ```
  open class Base

  class Derived: Base()
  ```
* 상속 관계를 표현하는 용어는 아래와 같다.
  * 기반 클래스 → 부모 클래스, 상위 클래스 
  * 파생 클래스 → 자식 클래스, 하위 클래스
* 상속을 사용하면 파생 클래스들을 기반 클래스와 같은 타입인 것처럼 받아 들이며, 기반 클래스를 상속하는 모든 클래스에서 사용할 수 있는 코드를 작성할 수 있다.
* 상속은 결국 `코드를 단순화하고 재사용할 수 있는 기회를 제공`한다.
* 함수를 오버라이딩할 때 상속은 더 흥미로워진다.
  * 파생 클래스는 기반 클래스의 private 멤버에 접근할 수 없어서 protected 멤버를 사용한다.
  * 기반 클래스의 함수 시그니처와 똑같은 함수를 파생 클래스에 존재하면 오류가 발생한다.
  * 명확한 의도가 있지 않은 한 상속과 오버라이드가 불가능하다.
  * talk()안에서 call()의 타입에 따라 다른 동작을 수행하는데 이를 `다형성`이라고 한다.
  ```
  open class GreatApe {
    protected var energy = 0
    open fun call() = "Hoo!"
    open fun eat() {
      energy += 10
    }
    fun climb(x: Int) {
      energy -= x
    }
    fun energyLevel() = "Energy: $energy"
  }
  
  class Bonobo : GreatApe() {
    override fun call() = "Eep!"
    override fun eat() {
      // 기반 클래스의 var 프로퍼티를 변경한다
      energy += 10
      // 기반 클래스의 함수를 호출한다
      super.eat()
    }
    // 함수를 추가한다
    fun run() = "Bonobo run"
  }
  
  class Chimpanzee : GreatApe() {
    // 새 프로퍼티
    val additionalEnergy = 20
    override fun call() = "Yawp!"
    override fun eat() {
      energy += additionalEnergy
      super.eat()
    }
    // 함수를 추가한다
    fun jump() = "Chimp jump"
  }
  
  fun talk(ape: GreatApe): String {
    // ape.run()  // ape의 함수가 아니다
    // ape.jump() // 역시 ape의 함수가 아니다
    ape.eat()
    ape.climb(10)
    return "${ape.call()} ${ape.energyLevel()}"
  }
  
  fun main() {
    // 'energy'에 접근할 수 없다
    // GreatApe().energy
    talk(GreatApe()) eq "Hoo! Energy: 0"
    talk(Bonobo()) eq "Eep! Energy: 10"
    talk(Chimpanzee()) eq "Yawp! Energy: 20"
  }
  ```
* 오버라이딩한 경우 `기반 클래스 버전을 호출하고 싶을 때 super 키워드를 사용`한다.

## 아톰 59. 기반 클래스 초기화
### 호출되도록 보장하는 생성자
* 멤버 객체들의 생성자
* 파생 클래스에 추가된 객체의 생성자
* 기반 클래스의 생성자

### 기반 클래스 초기화
* 클래스가 다른 클래스를 상속할 때, 두 클래스가 모두 제대로 초기화되도록 보장한다.
* 기반 클래스에 `생성자 파라미터가 있다면 파생 클래스가 생성되는 동안 반드시 기반 클래스의 인자를 제공`해야 한다.
  ```
  open class GreatApe(
    val weight: Double,
    val age: Int
  )
  
  open class Bonobo(weight: Double, age: Int) :
    GreatApe(weight, age)
  
  class Chimpanzee(weight: Double, age: Int) :
    GreatApe(weight, age)
  
  class BonoboB(weight: Double, age: Int) :
    Bonobo(weight, age)
  
  fun GreatApe.info() = "wt: $weight age: $age"
  ```
* 클래스를 상속할 때는 기반 클래스 생성자의 인자 목록을 기반 클래스 이름 뒤에 붙여야 한다.
  * 하위 클래스 객체를 생성하는 중 기반 클래스 생성자를 호출한다.
  * 기반 클래스 생성자 파라미터가 없어도 기반 클래스 이름 뒤에 괄호를 붙이도록 강제한다.
  ```
  open class SuperClass1(val i: Int)
  class SubClass1(i: Int): SuperClass1(i)

  open class SuperClass2
  class SubClass2: SuperClass2()
  ```
* `기반 클래스에 부생성자가 있으면 주생성자 대신 부생성자를 호출`할 수 있다. 
* 파생 클래스의 부생성자는 기반 클래스의 생성자를 호출할 수도 있고, 파생 클래스 자신의 생성자를 호출할 수도 있다.
* 기반 클래스의 생성자를 호출하려면 super 키워드를 적고, 함수를 호출할 때처럼 생성자 인자를 전달하면 되며, 다른 생성자를 호출할 때는 this 호출을 사용한다.

## 아톰 60. 추상 클래스
### 추상 클래스
* 추상 클래스는 `하나 이상의 프로퍼티나 함수가 불완전`하다.
* 클래스 멤버에서 본문이나 초기화를 제거하려면 abstract 변경자를 해당 멤버 앞에 붙여야하며, abstract가 붙은 멤버가 있는 클래스에는 반드시 abstract를 붙여야 한다.
  * WithProperty는 아무 초깃값도 없는 x를 선언하는데 `초기화 코드가 없으면 반드시 abstract로 선언해야`한다.
  * WithFunctions 두 함수도 정의를 제공하지 않기 때문에 abstract를 붙여야하고, 반환 타입을 적지 않으면 Unit이라고 간주한다.
  ```
  abstract class WithProperty {
    abstract val x: Int
  }

  abstract class WithFunctions {
    abstract fun f(): Int
    abstract fun g(n: Double)
  }
  ```
* 추상 클래스를 따라가면 추상 멤버가 어딘가에는 정의가 있는 구체화된 클래스가 존재해야 한다.
* 인터페이스는 추상 클래스와 비슷하며 함수, 프로퍼티에 `abstract를 생략`할 수 있다.
* 인터페이스와 추상 클래스의 `차이점은 추상 클래스에는 상태가 있지만 인터페이스에는 상태가 없다.` 
  * `상태는 프로퍼티 안에 저장된 데이터`를 뜻한다.
  * 인터페이스 안에서 프로퍼티에 값을 저장하는 것은 금지되어 있다.
* 인터페이스와 추상 클래스 모두 구현이 있는 함수를 포함할 수 있으며, 이런 함수는 다른 abstract 멤버를 호출해도 된다.
  ```
  interface Parent {
    val ch: Char
    fun f(): Int
    fun g() = "ch = $ch; f() = ${f()}" // 다른 abstract 멤버를 호출
  }
  ```

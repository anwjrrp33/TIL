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
* 인터페이스가 함수 구현을 포함할 수 있어서 내부에 정의된 프로퍼티가 상태를 바꿀 수 없는 경우에는 인터페이스도 프로퍼티에 커스텀 게터를 포함할 수 있다.
  ```
  interface PropertyAccessor {
    val a: Int
      get() = 11
  }

  class Impl: PropertyAccessor

  fun main() {
    Impl().a eq 11
  }
  ```
* 추상 클래스가 기능은 강력하지만, `클래스는 오직 한 기반 클래스만 상속할 수 있고 인터페이스는 다중 상속을 지원`한다.
  * 최초 자바 설계자는 다중 상속을 좋은 개념으로 보지 않았고, 여러 상태 상속이 복잡성을 증가시켜서 다중 상태 상속을 금지했다.
  ```
  open class Animal
  open class Mammal : Animal()
  open class AquaticAnimal : Animal()
  
  // 기반 클래스가 둘 이상이면 컴파일이 되지 않는다
  // class Dolphin : Mammal(), AquaticAnimal()
  ```
  * 하지만 상태를 포함할 수 없는 인터페이스를 도입해서 다중 상속 문제를 우아하게 해결했다.
  ```
  interface Animal
  interface Mammal: Animal
  interface AquaticAnimal: Animal
  
  class Dolphin : Mammal, AquaticAnimal
  ```
* 인터페이스도 다른 인터페이스를 상속할 수 있지만 시그니처가 같은 둘 이상 동시에 상속할 때 충돌하면 직접 충돌을 해결해야 한다.
* super 키워드를 사용해 서로 다른 기반 클래스를 함께 호출(super 뒤에 부등호로 이름을 지정)할 수도 있다.
* 코틀린은 식별자가 같은데 타입이 다른 식으로 충돌이 일어나는 경우를 허용하지 않고 알려줄수 없다.

## 아톰 61. 업캐스트
### 업캐스트
* `객체 참조를 받아서 그 객체의 기반 타입에 대한 참조처럼 취급하는 것`을 `업캐스트`한다고 말한다.
* 상속과 새 멤버 함수 추가는 스몰토크(최초로 성공적으로 정착한 객체 지향 언어)에서 비롯됐는데 스몰토크의 모든 것이 자바에 큰 영향을 끼쳤다.
  * 모든 것이 객체
  * 클래스를 새로 만드는 방법은 기존 클래스를 상속하는 것과 상속 과정에서 필요하면 새 함수 추가
* 코틀린은 독립적인 함수를 정의할 수 있고 클래스 안에 가두지 않아도 되는데 확장 함수를 쓰면 상속을 쓰지 않아도 기능을 구현할 수 있다.
* 코틀린에서 open은 상속을 위해 필요한 키워드지만 의도적이고 의식적으로 선택해서 사용해야 한다는 의미를 담는다.
* 코틀린은 `단일 상속 계층 내 여러 클래스에서 코드를 재사용할 수 있는 방식으로만 상속을 사용`하게 한다.
  ![alt text](image.png)
  ```
  fun show(shape: Shape) {
    trace("Show: ${shape.draw()}")
  }

  fun main() {
    listOf(Circle(), Square(), Triangle())
      .forEach(::show) // show 메서드를 실행할 때 각 클래스는 기반 클래스 Shape 클래스 처럼 취급되는데 이를 기반 타입으로 업캐스트됐다고 의미
  }
  ```
* `상속의 매커니즘은 오직 기반 타입으로 업캐스트한다는 목적을 당설하기 위해 존재`하며, 이런 추상화로 인해 매번 함수를 작성하지 않아도 되고 객체를 위해 `작성된 코드를 재사용하는 방법이 업캐스트`다.
* 업캐스트를 사용하지 않는데 상속을 사용하는 거의 모든 경우는 상속이 필수적이지 않고 코드를 복잡하게 만들기 때문에 `상속보다 합성을 택해라`라는 명언이 나왔다.
* 치환 가능성(리스코프 치환 원칙)은 업캐스트를 한 다음 파생 타입이 정확히 기반 타입과 똑같이 취급될 수 있다고 말한다.
  * 업캐스트가 파생 클래스에 추가된 멤버 함수를 잘라버리는 효과를 가진다.
  * 추가된 멤버 함수는 기반 클래스에 속해있지 않아서 업캐스트 이후에는 사용할 수 없다.
  * 업캐스트 이후에는 기반 타입의 멤버만 호출할 수 있다.

## 아톰 62. 다형성
### 다형성
* 다형성은 여러 형태를 뜻하며 프로그래밍에선 `객체, 멤버의 여러 구현이 있는 경우를 의미`한다.
* 다형성은 `부모 클래스가 자식 클래스를 바라볼 때 발생`한다.
  * talk 메서드를 통해서 Pet 으로 업캐스팅 됬지만, 실제 출력은 "Pet" 으로 되어야 하지만 `기반 클래스의 메서드가 호출`된다. 
  * 부모 클래스 참조에 대해 멤버를 호출하면 `다형성에 의해 자식 클래스에서 오버라이드한 올바른 멤버가 호출`된다.
  ```
  open class Pet {
    open fun speak() = "Pet"
  }
  
  class Dog : Pet() {
    override fun speak() = "Bark!"
  }
  
  class Cat : Pet() {
    override fun speak() = "Meow"
  }
  
  fun talk(pet: Pet) = pet.speak()
  
  fun main() {
    talk(Dog()) eq "Bark!"
    talk(Cat()) eq "Meow"
  }
  ```
* 함수 호출을 함수 본문과 연결 짓는 작업을 바인딩이라 하며 `다형성의 경우 연산이 타입에 따라 다르게 작동해야 하기 때문에 함수 본문을 동적 바인딩을 사용해 실행 시점에 동적으로 결정`해야 한다.
* `동적 바인딩은 정적 바인딩과 다르게 실행 시점에 타입을 결정하는 추가 로직이 성능에 영향`을 미치기 때문에 코틀린은 디폴트로 상속, 오버라이딩을 닫혀있도록 해서 사용하기 위해서 `코드에 의도를 명확`하게 드러내야 한다.

> 다형성은 클래스의 관계라는 큰 그림 안에 있을 때 조화롭게 작동하며, 객체 지향 기법을 효과적으로 사용하기 위해서는 관점을 한 클래스의 멤버에만 국한되는 것이 아닌 클래스와 클래스 사이의 관계에 의존하는 보편성으로 넓혀야 한다.

## 아톰 63. 합성
> 객체 지향을 사용해야 하는 이유는 코드 재사용이다.

### 합성
* 객체 지향에서는 새 클래스를 만들어서 코드를 재사용하고, 기존 코드를 더럽히지 않고 클래스를 재사용하는 것으로 이를 달성하는 방법 중 하나가 상속이며 상속을 사용해서 기존 클래스 타입에 속하는 새 클래스를 만들고 코드를 추가한다.
* `기존 클래스의 객체를 새 클래스 안에 생성하는 접근 방법`도 존재하는데 새 클래스가 기존 클래스들을 합성한 객체로 이뤄지기 때문에 이런 방법을 `합성`이라고 부르며 이 경우 `기본 코드의 기능을 재사용`한다.
* 합성은 포함(has-a) 관계이며, 상속은 이다(is-a) 관계를 표현한다.
  ```
  interface House: Building { // 집은 건물이다.
    val kitchen: Kitchen // 주방은 집에 포함된다.
  }
  ```
* 상속은 복잡하기 때문에 중요한 개념이라고 생각하지만 `상속보다 합성을 택해라`는 말이 생겼고, `상속대신 합성을 사용해서 설계를 단순하게 만들 수 있는 지 검토해야 한다는 점`이다.
* 합성은 뻔해 보이지만 강력하고 관련 없는 요소를 책임져야 하지만 각 요소를 분리할 때 도움이 되며 클래스의 복잡한 로직을 단순화할 수 있다.

### 합성과 상속 중 선택하기
* 합성과 상속 새 클래스에 하위 객체를 넣는다.
  * 합성은 명시적으로 하위 객체를 선언한다.
  * 상속은 암시적으로 하위 객체가 생긴다.
* 합성은 기존 클래스의 기능을 제공하지만 인터페이스를 제공하지 않으며 새 클레스에서 정의한 인터페이스를 보게 되는데 합성한 객체를 완전히 감추고 싶다면 비공개(private)로 포함시키면 된다. 
  * 사용자는 Form 구현 방식을 알 수 없는데 features 를 제거하고 변경해도 Form 을 사용하는 `코드에는 영향을 미치지 않는다.`
  * 상속을 사용한다면 연결 관계가 명확해지기 때문에 관계를 수정하면 `연결 관계에 의존하는 모든 코드가 망가진다.`
  ```
  class Features {
    fun f1() = "feature1"
    fun f2() = "feature2"
  }
  
  class Form {
    private val features = Features()
    fun operation1() = features.f2() + features.f1()
    fun operation2() = features.f1() + features.f2()
  }
  ```
* 때로는 새 클래스의 합성에 직접 접근하는 게 합리적인 경우도 존재하는데 이런 경우 공개(public)로 만든다.
  * 공개를 해도 멤버 객체가 적절히 정보 은닉을 구현하고 있는 한 상대적으로 안전하다.
  * 시스템에 따라서 멤버 객체를 공개할 때 인터페이스가 더 깔끔해질 수 있다.
  * 하부 구현에 속하는 문제가 아니라 문제 분석의 일부분으로 내부를 노출시킨 설계는 클라이언트가 클래스를 사용하는 방법을 이해할 때 도움이 되고 코드 복잡도를 줄여준다.
  ```
  class Engine {
    fun start() = trace("Engine start")
    fun stop() = trace("Engine stop")
  }
  
  class Wheel {
    fun inflate(psi: Int) = trace("Wheel inflate($psi)")
  }
  
  class Window(val side: String) {
    fun rollUp() = trace("$side Window roll up")
    fun rollDown() = trace("$side Window roll down")
  }
  
  class Door(val side: String) {
    val window = Window(side)
    fun open() = trace("$side Door open")
    fun close() = trace("$side Door close")
  }
  
  class Car {
    val engine = Engine()
    val wheel = List(4) { Wheel() }
    // Two door:
    val leftDoor = Door("left")
    val rightDoor = Door("right")
  }
  
  fun main() {
    val car = Car()
    car.leftDoor.open()
    car.rightDoor.window.rollUp()
    car.wheel[0].inflate(72)
    car.engine.start()
    trace eq """
      left Door open
      right Window roll up
      Wheel inflate(72)
      Engine start
    """
  }
  ```
* `포함 관계는 합성으로, 이다 관계는 상속으로 표현`한다.

> 다형성의 영리함으로 모든 것을 상속으로 처리해야 할 것처럼 느끼기 쉽지만 설계에 짐이 되며 상속을 우선적으로 선택하면 불필요하게 복잡해지기 때문에 합성을 먼저 시도해보는 것이 좋다.

## 아톰 64. 상속과 확장
### 상속과 확장
* 때로는 새로운 함수를 추가해야 할 때 새 함수를 추가하기 위해서 상속을 사용해야 하는데 이로 인해 코드를 이해하고 유지보수하기 어려워진다.
* 객치 지향 언어는 상속을 하는 동안 멤버 함수를 처리하는 메커니즘을 제공해서 추가된 함수는 업캐스트를 하면 잘려나가 기반 클래스에서 쓸 수 없다.
  * 이는 리스코프 치환 원칙(치환 가능성)이다.
  * 치환 가능성으로 하위 클래스 객체를 받아도 아무 문제가 없다.
* 상속을 하는 동안 함수를 추가하는 것을 허용하지만, 이는 코드 냄새다.
  * 함수 추가는 타당하고 편리해 보이지만 함정에 빠뜨릴 수 있다.
  * 나중에 코드를 유지 보수해야 하는 사람에게 악영향을 끼칠 수 있는 문제며 이를 `기술 부채`라고 부른다.
* `상속을 하면서 함수를 추가하는 건` 클래스에 기반 클래스가 있다는 사실을 무시하고 시스템 전반에서 `파생 클래스를 엄격하게 식별해 취급할 때 유용`하다.
  * 상속과 확장 함수가 하는 일이 정확히 일치해, 확장 함수를 쓰면 상속이 필요없다.
  ```
  fun Heater.cool(temperature: Int) = "cooling to $temperature"

  fun warmAndCool(heater: Heater) {
    heater.heat(70) eq "heating to 70"
    heater.cool(60) eq "cooling to 60"
  }

  fun main() {
    val heater = Heater()
    warmAndCool(heater)
  }
  ```
  * 라이브러리 소스 코드를 바꿀 수 잇으면 다른 방식으로 설계해서 클래스를 좀 더 유연하게 만들 수 있다.
  ```
  class TemperatureDelta(
    val current: Double,
    val target: Double
  )

  fun TemperatureDelta.heat() {
    if (current < target)
      trace("heating to $target")
  }

  fun TemperatureDelta.cool() {
    if (current > target)
      trace("cooling to $target")
  }

  fun adjust(deltaT: TemperatureDelta) {
    deltaT.heat()
    deltaT.cool()
  }

  fun main() {
    adjust(TemperatureDelta(60.0, 70.0))
    adjust(TemperatureDelta(80.0, 60.0))
    trace eq """
      heating to 70.0
      cooling to 60.0
    """
  }
  ```
  * `확장 함수가 아닌 멤버 함수로 정의`할 수도 있다.

### 관습에 의한 인터페이스
* 확장 함수를 함수가 하나뿐인 인터페이스를 만드는 것처럼 생각할 수 있다.
  * X, Y에 f()라는 멤버 함수가 있는 것처럼 보이지만 다형적으로 동작하지 않기 때문에 제대로 동작하게 만들려면 오버로드해야 한다.
  ```
  class X

  fun X.f() {}

  class Y

  fun Y.f() {}

  fun callF(x: X) = x.f()

  fun callF(y: Y) = y.f()

  fun main() {
    val x = X()
    val y = Y()
    x.f()
    y.f()
    callF(x)
    callF(y)
  }
  ```
* 코틀린 라이브러리에선 `관습에 의한 인터페이스`를 광범위하게 사용하며 컬렉션을 다룰 때 그렇다.
* 코틀린 컬렉션은 거의 모두 자바 컬렉이지만 다수의 확장 함수를 추가해서 자바 컬렉션을 함수형 스타일의 컬렉션을 변모해준다.
* 이런 확장 함수는 초기에 자바와 호환성을 유지하기 위한 목적으로 쓰였지만 현재는 `필수적인 메서드만 정의해 포함하는 간단한 인터페이스를 만들고 모든 부가 함수를 확장으로 정의하라`라는 코틀린의 철학이 됐다.

### 어댑터 패턴
* 라이브러리에서 타입을 정의하고 그 타입의 객체를 파라미터로 받는 함수를 제공하는 경우가 종종 있다.
  * 아래 방식은 상속하는 과정에서 클래스를 확장하지만 새 멤버 함수는 연결하기 위해서만 쓰인다. (UsefulLibrary 연결)
  ```
  interface LibType {
    fun f1()
    fun f2()
  }

  fun utility1(lt: LibType) {
    lt.f1()
    lt.f2()
  }

  fun utility2(lt: LibType) {
    lt.f2()
    lt.f1()
  }

  open class MyClass {
    fun g() = trace("g()")
    fun h() = trace("h()")
  }

  fun useMyClass(mc: MyClass) {
    mc.g()
    mc.h()
  }

  class MyClassAdaptedForLib :
    MyClass(), LibType {
    override fun f1() = h()
    override fun f2() = g()
  }

  fun main() {
    val mc = MyClassAdaptedForLib()
    utility1(mc)
    utility2(mc)
    useMyClass(mc)
    trace eq "h() g() g() h() g() h()"
  }
  ```
  * 상속에 대해 열린 open 클래스라는 점에 의존하지만 `합성을 사용해 어댑터`를 만들 수 있다. (MyClassAdaptedForLib 안에 MyClass 필드 추가)
  * 아래 코드는 상속보다 깔끔하지는 않지만 새로운 인터페이스에 맞게 전환해 연결하는 문제를 쉽게 해결해준다.
  ```
  class MyClass { // open된 클래스가 아님
    fun g() = trace("g()")
    fun h() = trace("h()")
  }

  fun useMyClass(mc: MyClass) {
    mc.g()
    mc.h()
  }

  class MyClassAdaptedForLib : LibType {
    val field = MyClass()
    override fun f1() = field.h()
    override fun f2() = field.g()
  }

  fun main() {
    val mc = MyClassAdaptedForLib()
    utility1(mc)
    utility2(mc)
    useMyClass(mc.field)
    trace eq "h() g() g() h() g() h()"
  }
  ```
* 확장 함수는 어댑터를 생성할 때 아주 유용할 것 같지만, 확장 함수를 모아서 인터페이스를 구현할 수는 없다.

### 멤버 함수와 확장 함수 비교
* 함수가 `private 멤버에 접근해야 한다면 확장 함수 대신 멤버 함수를 정의`할 수 밖에 없다.
  ```
  class Z(var i: Int = 0) {
    private var j = 0
    fun increment() {
      i++
      j++
    }
  }

  fun Z.decrement() {
    i--
    // j -- // 접근할 수 없음
  }
  ```
* `확장 함수의 가장 큰 한계는 오버라이드할 수 없다는 점`이다.
  ```
  open class Base {
    open fun f() = "Base.f()" // 다형성이 작동하지 않는다.
  }

  class Derived : Base() {
    override fun f() = "Derived.f()" // 다형성이 작동한다.
  }

  fun Base.g() = "Base.g()"
  fun Derived.g() = "Derived.g()"

  fun useBase(b: Base) {
    trace("Received ${b::class.simpleName}")
    trace(b.f())
    trace(b.g())
  }

  fun main() {
    useBase(Base())
    useBase(Derived())
    trace eq """
      Received Base
      Base.f()
      Base.g()
      Received Derived
      Derived.f()
      Base.g()
    """
  }
  ```
* 클래스의 공개 멤버만으로 충분할 때는 이를 멤버 함수로 구현할 수도 있고 확장 함수로 구현할 수도 있지만 스타일의 차이로 `코드의 명확성을 높일 수 있는 방법을 선택`해야 한다.
* 멤버 함수는 타입의 핵심을 반영한다.
  * 함수 없이 타입을 상상할 수 없어야 하고 `확장 함수는 대상 타입을 지원하고 활용하기 위한 외부 연산이나 편리를 위한 연산`이다.
  * 일부 함수를 확장 함수로 정의하면 대상 타입을 깔끔하고 단순하게 유지할 수 있다.
  ```
  interface Device {
    val model: String
    val productionYear: Int
    fun overpriced() = model.startsWith("i") // 멤버, 확장 함수로도 정의될 수 있다.
    fun outdated() = productionYear < 2050 // 멤버, 확장 함수로도 정의될 수 있다.
  }

  class MyDevice(
    override val model: String,
    override val productionYear: Int
  ): Device

  fun main() {
    val gadget: Device =
      MyDevice("my first phone", 2000)
    gadget.outdated() eq true
    gadget.overpriced() eq false
  }
  ```
  * 오버로이드될 가능성이 없다면 확장 함수로 선언이 가능하다.
  ```
  fun Device.overpriced() = model.startsWith("i")

  fun Device.outdated() = productionYear < 2050
  ```
* `확장 함수 또는 멤버 함수로 만들지는 상황과 설계상의 선택`일 뿐이다.
* 자바는 특별히 금지않는 한 상속을 허용하지만 코틀린은 상속을 사용하지 않을 것이라고 가정해서 open 키워드가 아니면 상속과 다형성을 의도적으로 사용을 막는데 이는 코틀린이 나아갈 방향에 대한 통잘이다.

> 특정 상황에서 상속을 사용할지 고민 중이라면 상속보다는 확장 함수와 합성을 택해라라는 격연을 적용해라, 하지만 확장 함수는 의견을 들어도 안티 패턴이라는 의견도 종종 발생한다

## 아톰 65. 클래스 위임
> 합성과 상속 모두 새 클래스 안에 하위 객체를 심고 합성은 하위 객체가 명시적으로 상속은 암시적으로 존재한다.

### 클래스 위임
* 클래스가 `기존의 구현을 재사용하면서 동시에 인터페이스를 구현`해야 하는 경우, 합성은 인터페이스를 노출시키지 않기 때문에 `상속과 클래스 위임이라는 두 가지 선택지`가 있다.
* `클래스 위임은 상속과 합성의 중간 지점`으로 하위 객체의 인터페이스를 노출 시키고 하위 객체의 타입으로 업캐스트할 수 있어서 `코드 재사용성 관점에서 합성을 상속만큼 강력`하게 만들어준다.
  * 확장하거나 함수를 일부 조정하고 싶으면 상속하려고 하겠지만, open 클래스가 아니라서 상속을 할 수 없기 때문에 인스턴스를 프로퍼티로 하고 모든 멤버 함수를 인스턴스에 위임해야 한다.
  ```
  interface Controls {
    fun up(velocity: Int): String
    fun down(velocity: Int): String
    fun left(velocity: Int): String
    fun right(velocity: Int): String
    fun forward(velocity: Int): String
    fun back(velocity: Int): String
    fun turboBoost(): String
  }

  class SpaceShipControls : Controls {
    override fun up(velocity: Int) =
      "up $velocity"
    override fun down(velocity: Int) =
      "down $velocity"
    override fun left(velocity: Int) =
      "left $velocity"
    override fun right(velocity: Int) =
      "right $velocity"
    override fun forward(velocity: Int) =
      "forward $velocity"
    override fun back(velocity: Int) =
      "back $velocity"
    override fun turboBoost() = "turbo boost"
  }

  class ExplicitControls : Controls {
    private val controls = SpaceShipControls()
    // 수동으로 위임 구현하기
    override fun up(velocity: Int) =
      controls.up(velocity)
    override fun back(velocity: Int) =
      controls.back(velocity)
    override fun down(velocity: Int) =
      controls.down(velocity)
    override fun forward(velocity: Int) =
      controls.forward(velocity)
    override fun left(velocity: Int) =
      controls.left(velocity)
    override fun right(velocity: Int) =
      controls.right(velocity)
    // 변형한 구현
    override fun turboBoost(): String =
      controls.turboBoost() + "... boooooost!"
  }

  fun main() {
    val controls = ExplicitControls()
    controls.forward(100) eq "forward 100"
    controls.turboBoost() eq
      "turbo boost... boooooost!"
  }
  ```
* 코틀린은 클래스 위임 과정을 자동화해주며, 직접 함수 구현 작성 대신 위임에 사용할 객체를 지정하기만 하면 되며 `by 키워드를 사용해 위임을 하면 별도의 코드를 작성하지 않아도 멤버 객체의 함수를 외부 객체를 통해 접근`할 수 있다.
  ```
  interface AI 
  class A : AI
  class B(val a: A) : AI by a // 클래스 위임
  ```
  ```
  class DelegatedControls(
    private val controls: SpaceShipControls =
      SpaceShipControls()
  ): Controls by controls {
    override fun turboBoost(): String =
      "${controls.turboBoost()}... boooooost!"
  }

  fun main() {
    val controls = DelegatedControls()
    controls.forward(100) eq "forward 100"
    controls.turboBoost() eq
      "turbo boost... boooooost!"
  }
  ```
* `코틀린은 다중 클래스 상속을 허용하지 않지만, 클래스 위임을 사용해 다중 클래스 상속을 흉내`낼 수 있으며 일반적으로 `다중 상속은 전혀 다른 기능을 가진 여러 클래스를 하나로 묶기`위해 쓰인다.
  * Button은 Rectangle, MouseManager를 구현하며 Button이 비록 내부 프로퍼티 구현을 모두 상속할 수 없지만, 모두 위임할 수는 있다.
  * Button이 두 가지 타입으로 업캐스트할 수 있음을 보여줬고 이는 다중 상속의 목표로 `위임은 다중 상속의 필요성을 해결`해준다.
  ```
  // 직사각형을 그리는 클래스, 마우스 이벤트를 관리하는 클래스를 하나로 묶어서 버튼을 만든다.
  interface Rectangle {
    fun paint(): String
  }

  class ButtonImage(
    val width: Int,
    val height: Int
  ): Rectangle {
    override fun paint() =
      "painting ButtonImage($width, $height)"
  }

  interface MouseManager {
    fun clicked(): Boolean
    fun hovering(): Boolean
  }

  class UserInput : MouseManager {
    override fun clicked() = true
    override fun hovering() = true
  }

  // 앞의 두 클래스를 open으로 정의한다고 해도 하위 타입을
  // 정의할 때는 상위 타입 목록에 클래스를 하나만 넣을 수 있기
  // 때문에 다음과 같이 쓸 수는 없다.
  // class Button : ButtonImage(), UserInput()

  class Button(
    val width: Int,
    val height: Int,
    var image: Rectangle = ButtonImage(width, height), // public이기 때문에 외부에서 변경 위험이 존재
    private var input: MouseManager = UserInput()
  ): Rectangle by image, MouseManager by input

  fun main() {
    val button = Button(10, 5)
    button.paint() eq
      "painting ButtonImage(10, 5)"
    button.clicked() eq true
    button.hovering() eq true
    // 위임한 두 타입으로 업캐스트가 모두 가능하다
    val rectangle: Rectangle = button
    val mouseManager: MouseManager = button
  }
  ```
* 상속은 제약이 될 수 있으며 open이 아니거나 이미 상속을 하는 경우엔 상속이 불가능하지만 클래스 위임을 사용해 제약들을 피할 수 있다.

> 클래스 위임은 조심히 사용해야 하는데 상속, 합성, 클래스 위임 세가지 방법 중에 합성을 먼저 시도하면 대부분의 유즈케이스를 해결해주며 타입 계층가 이 계층 사이의 관계가 필요할 때 상속이 필요하고, 두 가지 선택이 모두 적합하지 않으면 위임을 사용한다.

## 아톰 66. 다운캐스트
### 다운캐스트
* 다운캐스트는 이전에 업캐스트했던 객체의 구체적인 타입을 발견한다.
* 객체 지향 프로그래밍은 업캐스트에 주로 초점을 맞추지만, 상황에 따라 다운캐스트가 유용하고 편리한 접근 방법일 수 있다.
* 다운캐스트는 `실행 시점에 일어나며 실행 시점 타입 식별`이라고 부른다.
* 컴파일러는 하위 타입에 추가된 함수 중에 어떤 함수를 호출해도 안전한지를 결정할 수 없다.
  * 다운캐스트가 올바른지 보장하는 방법이 필요
  * 잘못된 타입으로 다운캐스트를 해서 존재하지 않는 멤버 호출을 방지하는 도움 필요
  ```
  interface Base {
    fun f()
  }

  class Derived1 : Base {
    override fun f() {}
    fun g() {}
  }

  class Derived2 : Base {
    override fun f() {}
    fun h() {}
  }

  fun main() {
    val b1: Base = Derived1() // 업캐스트
    b1.f()    // 기반 클래스의 일부분
    // b1.g() // 기반 클래스에 들어 있지 않음
    val b2: Base = Derived2() // 업캐스트
    b2.f()    // 기반 클래스의 일부분
    // b2.h() // 기반 클래스에 들어 있지 않음
  }
  ```

### 스마트 캐스트
* 코틀린의 스마트 캐스트는 자동 다운캐스트이다.
* is 키워드는 어떤 객체가 특정 타입인지 검사하며, 검사 영역 안에서 객체를 검사에 성공한 타입으로 간주한다.
  ```
  fun main() {
    val b1: Base = Derived1() // 업캐스트
    if(b1 is Derived1)
      b1.g() // 'is' 검사의 영역 내부
    val b2: Base = Derived2() // 업캐스트
    if(b2 is Derived2)
      b2.h() // 'is' 검사의 영역 내부
  }
  ```
* 스마트 캐스트는 is를 통해 when의 인자가 어떤 타입인지 검색하는 when 식 내부에서 유용하다.
  ```
  interface Creature

  class Human : Creature {
    fun greeting() = "I'm Human"
  }

  class Dog : Creature {
    fun bark() = "Yip!"
  }

  class Alien : Creature {
    fun mobility() = "Three legs"
  }

  fun what(c: Creature): String =
    when (c) {
      is Human -> c.greeting()
      is Dog -> c.bark()
      is Alien -> c.mobility()
      else -> "Something else"
    }

  fun main() {
    val c: Creature = Human()
    what(c) eq "I'm Human"
    what(Dog()) eq "Yip!"
    what(Alien()) eq "Three legs"
    class Who : Creature
    what(Who()) eq "Something else"
  }
  ```
* 전통적으로 클래스 계층을 그릴 때 기반 클래스를 맨 위에 두고, 파생 클래스를 아래로 위치시킨다.

### 변경 가능한 참조
* 자동 다운캐스트는 특히 `대상이 상수여야만 제대로 작동`하며, 기반 클래스 타입의 참조가 변경 가능하다면 타입의 참조가 변경 가능하면 타입 검증 시점과 다운캐스트한 객체에 대해 호출한 시점 사이에 참조가 가리키는 객체가 바뀔 가능성이 있다. → `타입 검사와 사용 지점 사이에 객체의 구체적인 타입이 달라질 수 있다는 뜻`
  * [1]에서 val c를 제거하면 [2]에서 `Smart cast to 'Human' is impossible, because 'c' is a mutable property that could have been changed by this time`가 발생한다.
  * 값의 변화는 보통 동시성을 통해 일어나며, 동시성이 사용되는 코드에서는 독립적인 작업이 예측할 수 없는 시간에 c를 바꿀 수 있다.
  ```
  class SmartCast1(val c: Creature) {
    fun contact() {
      when (c) {
        is Human -> c.greeting()
        is Dog -> c.bark()
        is Alien -> c.mobility()
      }
    }
  }

  class SmartCast2(var c: Creature) {
    fun contact() {
      when (val c = c) {           // [1]
        is Human -> c.greeting()   // [2]
        is Dog -> c.bark()
        is Alien -> c.mobility()
      }
    }
  }
  ```
* 상속을 위해 open된 프로퍼티도 하위 클래스에서 오버라이드를 할 수 있고, 그로 인해 프로퍼티에 접근할 때마다 항상 같은 값을 내놓는다고 보장할 수 없으므로 스마트 캐스트가 되지 않는다.

### as 키워드
* as 키워드는 일반적인 타입을 구체적인 타입으로 강제 변환한다.
  ```
  fun dogBarkUnsafe(c: Creature) = (c as Dog).bark()

  fun dogBarkUnsafe2(c: Creature): String {
    c as Dog
    c.bark()
    return c.bark() + c.bark()
  }

  fun main() {
    dogBarkUnsafe(Dog()) eq "Yip!"
    dogBarkUnsafe2(Dog()) eq "Yip!Yip!"
    (capture {
      dogBarkUnsafe(Human())
    }) contains listOf("ClassCastException")
  }
  ```
* 일반 as를 안전하지 않은 캐스트라고 부르며, 안전한 캐스트인 as?는 실패해도 예를 던지지 않고 null을 반환하지만 NullPoninterException을 방지하기 위한 추가 조치가 필요하다.
  ```
  fun dogBarkSafe(c: Creature) = (c as? Dog)?.bark() ?: "Not a Dog"

  fun main() {
    dogBarkSafe(Dog()) eq "Yip!"
    dogBarkSafe(Human()) eq "Not a Dog"
  }
  ```

### 리스트 원소의 타입 알아내기
* 술어에서 is를 사용하면 List나 다른 이터러블의 원소가 주어진 타입의 객체인지 알 수 있다.
  * [1]에서 find를 통한 명시적인 타입 반환, [2]에서 null을 대비한 안전한 호출 연산자
  ```
  val group: List<Creature> = listOf(
    Human(), Human(), Dog(), Alien(), Dog()
  )

  fun main() {
    val dog = group
      .find { it is Dog } as Dog?    // [1]
    dog?.bark() eq "Yip!"            // [2]
  }
  ```
* 지정한 타입에 속하는 모든 원소 filterIsInstance()
  * filter는 기반 클래스의 List를 반환
  * filterIsInstance는 파생 클래스의 대상 타입의 List를 반환
  * null 허용 타입 문제도 해결
  ```
  fun main() {
    val humans1: List<Creature> = group.filter { it is Human }
    humans1.size eq 2
    val humans2: List<Human> = group.filterIsInstance<Human>()
    humans2 eq humans1
  }
  ```

## 아톰 67. 봉인된 클래스
### 봉인된 클래스
* 클래스 계층을 제한하려면 상위 클래스를 sealed로 선언한다.
* 코드에서 다운캐스트가 여기저기 흩어져 있다면 이런 변경으로 인해 유지 보수가 힘들어지는데, sealed 키워드를 사용해 개선할 수 있으며 sealed 키워드로 상속을 제한한 클래스를 봉인된 클래스라고 부른다.
  * sealed 클래스를 직접 상속한 하위 클래스는 반드시 기반 클래스와 같은 패키지와 모듈 안에 있어야 한다.
  * 코틀린은 when 식이 모든 경우를 검사하도록 강요하지만, sealed라서 하위 클래스가 존재할 수 없다는 사실을 확신해서 else가 필요 없다.
  * sealed 계층을 도입하면 새 하위 클래스를 선언할 때 오류를 발견하며, 기존 타입 계층을 사용하던 모든 코드를 손봐야 한다. → Transport에 하위 클래스인 Tram을 추가하면 변경 없이는 travel() 함수가 제대로 작동하지 않는다.
  ```
  sealed class Transport

  data class Train(
    val line: String
  ) : Transport()

  data class Bus(
    val number: String,
    val capacity: Int
  ) : Transport()

  fun travel(transport: Transport) =
    when (transport) {
      is Train ->
        "Train ${transport.line}"
      is Bus ->
        "Bus ${transport.number}: " +
        "size ${transport.capacity}"
    }

  fun main() {
    listOf(Train("S1"), Bus("11", 90))
      .map(::travel) eq
      "[Train S1, Bus 11: size 90]"
  }
  ```
* sealed 키워드는 다운캐스트를 쓸만하게 만들어주지만 과도하게 사용하는 것보다 보통은 다형성을 써서 코드를 깔끔하게 작성할 수 있는 방법이 있다.

### sealed와 abstract 비교
* abstract와 sealed 클래스가 타입이 똑같은 함수, 프로퍼티, 함수가 동일한 경우 아래와 같은 차이 점이 존재한다.
  * sealed 클래스는 하위 클래스가 모두 같은 파일 안에 정의된다는 제약이 abstract에서 추가됬다.
  ```
  abstract class Abstract(val av: String) {
    open fun concreteFunction() {}
    open val concreteProperty = ""
    abstract fun abstractFunction(): String
    abstract val abstractProperty: String
    init {}
    constructor(c: Char) : this(c.toString())
  }

  open class Concrete() : Abstract("") {
    override fun concreteFunction() {}
    override val concreteProperty = ""
    override fun abstractFunction() = ""
    override val abstractProperty = ""
  }

  sealed class Sealed(val av: String) {
    open fun concreteFunction() {}
    open val concreteProperty = ""
    abstract fun abstractFunction(): String
    abstract val abstractProperty: String
    init {}
    constructor(c: Char) : this(c.toString())
  }

  open class SealedSubclass() : Sealed("") {
    override fun concreteFunction() {}
    override val concreteProperty = ""
    override fun abstractFunction() = ""
    override val abstractProperty = ""
  }

  fun main() {
    Concrete()
    SealedSubclass()
  }
  ```
  * sealed 클래스의 간적적인 하위 클래스를 별도의 파일에 정의할 수 있다.
  ```
  class ThirdLevel : SealedSubclass() // 직접 sealed를 상속받지 않아서 가능하다
  ```
* sealed interface 도 유용하며, JVM에서 자바 15부터 sealed interface 도입돼 코틀린 1.5부터 지원한다.

### 하위 클래스 열거하기
* 클래스가 sealed인 경우 모든 하위 클래스를 쉽게 이터레이션할 수 있다.
  ```
  sealed class Top
  class Middle1 : Top()
  class Middle2 : Top()
  open class Middle3 : Top()
  class Bottom3 : Middle3()

  fun main() {
    Top::class.sealedSubclasses
      .map { it.simpleName } eq "[Middle1, Middle2, Middle3]"
  }
  ```
* 클래스를 생성하면 클래스 객체가 생성되고, 클래스 객체의 프로퍼티와 멤버 함수에 접근해서 클래스에 대한 정보를 얻어서 객체를 생성하거나 조작할 수 있다.
  * `클래스이름::class`
* sealedSubclasses를 사용하면 봉인된 클래스의 모든 직접적인 하위 클래스를 반환한다.
* sealedSubclasses는 `리플렉션`을 사용하며 kotlin-reflection.jar라는 의존 관계가 클래스 경로에 있어야 리플렉션을 사용한다.
* 리플렉션은 클래스를 동적으로 찾아내고 찾아낸 클래스의 특성을 동적으로 사용하는 방법을 제공한다.
* sealedSubclasses는 다형적인 시스템을 만들 때 중요한 도구가 될 수 있으며, 새로운 클래스가 모든 적합한 연산에 자동으로 포함되도록 보장할 수 있지만 하위 클래스를 실행 시점에 찾아내기에 시스템의 성능에 영향을 끼친다.
  * 속도 문제가 발생한다면 프로파일러를 사용해 문제의 원인을 분석하고 파악해서 검토해야 한다.
  * 보통 프로퍼일러를 사용하면서 문제의 원인이라고 생각한 부분이 성능 문제의 진짜 원인이 아니라는 사실을 알게 된다.

## 아톰 68. 타입 검사
### 타입 검사
* 코틀린에서는 객체 타입에 기반해 원하는 동작을 쉽게 수행하며, 타입에 따른 동작은 다형성 영역에 속해서 타입 검사를 통한 흥미로운 설계가 가능하다.
* 타입 검사는 특별한 경우에 써먹기 위한 것이었다.
* 곤충은 대부분 날 수 있지만, 날 수 없는 곤충이 극소수 존재한다.
  * 특별한 처리를 위해 별도의 소수 타입을 선택하는 것이 타입 검사의 전형적인 유스케이스다.
  * 시스템에 새 타입을 추가해도 기존 코드에 영향을 끼치지 않는다.
  ```
  interface Insect {
    fun walk() = "$name: walk"
    fun fly() = "$name: fly"
  }

  class HouseFly : Insect

  class Flea : Insect {
    override fun fly() =
      throw Exception("Flea cannot fly")
    fun crawl() = "Flea: crawl"
  }

  fun Insect.basic() =
    walk() + " " +
    if (this is Flea)
      crawl()
    else
      fly()

  interface SwimmingInsect: Insect {
    fun swim() = "$name: swim"
  }

  interface WaterWalker: Insect {
    fun walkWater() =
      "$name: walk on water"
  }

  class WaterBeetle : SwimmingInsect
  class WaterStrider : WaterWalker
  class WhirligigBeetle :
    SwimmingInsect, WaterWalker

  fun Insect.water() =
    when(this) {
      is SwimmingInsect -> swim()
      is WaterWalker -> walkWater()
      else -> "$name: drown"
    }

  fun main() {
    val insects = listOf(
      HouseFly(), Flea(), WaterStrider(),
      WaterBeetle(), WhirligigBeetle()
    )
    insects.map { it.basic() } eq
      "[HouseFly: walk HouseFly: fly, " +
      "Flea: walk Flea: crawl, " +
      "WaterStrider: walk WaterStrider: fly, " +
      "WaterBeetle: walk WaterBeetle: fly, " +
      "WhirligigBeetle: walk " +
      "WhirligigBeetle: fly]"
    insects.map { it.water() } eq
      "[HouseFly: drown, Flea: drown, " +
      "WaterStrider: walk on water, " +
      "WaterBeetle: swim, " +
      "WhirligigBeetle: swim]"
  }
  ```
* 많은 타입을 추가하며 시스테을 진화시켜야 한다면 코드가 지저분 해지기 시작한다.
  * turn1(), turn2()와 같은 함수가 점점 많아진다면 계층 구조 안에 들어 있지 않고 모든 함수에 분산된다.
  * when이 들어 있는 모든 함수를 찾아서 새로 추가한 타입을 처리하게 변경해야하며 제대로 변경하지 못해도 컴파일러는 감지하지 못한다.
  ```
  interface Shape {
    fun draw(): String
  }

  class Circle : Shape {
    override fun draw() = "Circle: Draw"
  }

  class Square : Shape {
    override fun draw() = "Square: Draw"
    fun rotate() = "Square: Rotate"
  }

  fun turn(s: Shape) = when(s) {
    is Square -> s.rotate()
    else -> ""
  }

  class Triangle : Shape {
    override fun draw() = "Triangle: Draw"
    fun rotate() = "Triangle: Rotate"
  }

  fun turn2(s: Shape) = when(s) {
    is Square -> s.rotate()
    is Triangle -> s.rotate()
    else -> ""
  }

  fun main() {
    val shapes =
      listOf(Circle(), Square(), Triangle())
    shapes.map { it.draw() } eq
      "[Circle: Draw, Square: Draw, " +
      "Triangle: Draw]"
    shapes.map { turn(it) } eq
      "[, Square: Rotate, ]"
    shapes.map { turn2(it) } eq
      "[, Square: Rotate, Triangle: Rotate]"
  }
  ```
* 시스템의 모든 타입을 검사하며 코딩하는 것을 `타입 검사 코딩`이라는 방식을 보여주는데 객체 지향 언어에서는 안티패턴으로 간주되며 타입을 추가하거나 변경할 때마다 유지 보수해야 하는 코드가 점점 많아지기 때문에 다형성을 사용해서 변경 내용을 캡슐화해서 변경할 타입에 넣어주면 변경 내용이 시스템 전체에 투명하게 전파된다.
* 코틀린에선 sealed 클래스를 사용해서 타입 검사 코딩 문제를 완벽하지는 않지만 완화할 수 있어서 합리적인 설계를 선택할 수 있게 한다.

### 외부 함수에서 타입 검사하기
* 클래스가 음료수를 저장하고 배달한다고 가정하며, 음료수를 먹고 난 뒤의 음료수 병을 재활용한느 것을 외부 함수로 다룬다.
  * recycle을 외부 함수로 정의해 재활용 동작을 분산시키지 않고 한군데 모아두었다.
  * 하지만, 새 타입을 추가할 때 함수를 수정해야하는 하는데 수정하지 않는 경우가 발생할 수 있다. → 컴파일러가 자동으로 알려줄 순 없을까?
  ```
  interface BeverageContainer {
    fun open(): String
    fun pour(): String
  }

  class Can : BeverageContainer {
    override fun open() = "Pop Top"
    override fun pour() = "Can: Pour"
  }

  open class Bottle : BeverageContainer {
    override fun open() = "Remove Cap"
    override fun pour() = "Bottle: Pour"
  }

  class GlassBottle : Bottle()
  class PlasticBottle : Bottle()

  fun BeverageContainer.recycle() =
    when(this) {
      is Can -> "Recycle Can"
      is GlassBottle -> "Recycle Glass"
      else -> "Landfill"
    }

  fun main() {
    val refrigerator = listOf(
      Can(), GlassBottle(), PlasticBottle()
    )
    refrigerator.map { it.open() } eq
      "[Pop Top, Remove Cap, Remove Cap]"
    refrigerator.map { it.recycle() } eq
      "[Recycle Can, Recycle Glass, " +
      "Landfill]"
  }
  ```
* sealed 클래스를 사용해서 개선하기
  * sealed 클래스를 사용하면 `매번 추가된 타입을 검사`해야 한다.
  ```
  sealed class Shape {
    fun draw() = "$name: Draw"
  }

  class Circle : Shape()

  class Square : Shape() {
    fun rotate() = "Square: Rotate"
  }

  class Triangle : Shape() {
    fun rotate() = "Triangle: Rotate"
  }

  fun turn(s: Shape) = when(s) {
    is Circle -> ""
    is Square -> s.rotate()
    is Triangle -> s.rotate()
  }

  fun main() {
    val shapes = listOf(Circle(), Square())
    shapes.map { it.draw() } eq
      "[Circle: Draw, Square: Draw]"
    shapes.map { turn(it) } eq
      "[, Square: Rotate]"
  }
  ```
  * 하지만, 이 방법 또한 완전한 개선은 아니며, 최상위 sealed 클래스에 중간 클래스가 생긴다면 하나의 when 문으로는 컴파일러가 하위 타입 검사를 보장하지 못하기 때문에 when 안에 다른 when을 사용해야 한다. → 상속을 여러 단계에 걸쳐 수행할 때만 발생한다.
  * 다형성처럼 명확해 보이지는 않지만, 큰 개선이 이루어져 있으며, 이런 기능을 통해서 멤버 함수와 외부 함수 중에 선택할 수 있다.
* 인터페이스를 사용해 클래스 개선하기
  * 하위 클래스가 재활용을 오버라이드하도록 강제한다.
  * 하지만 재활용의 행동 방식이 여러 클래스에 분산했고, 재활용 동작이 자주 바뀌면 한꺼번에 이를 처리하고 싶을 때 외부 함수 안에서 타입 검사를 사용하는 게 맞는 선택일 수도 있다. → 외부 함수보다 간단한 팩토리를 써서 클래스를 반환해서 재활용을 동작하는 것도 좋지 않을까?
  ```
  interface BeverageContainer {
    fun open(): String
    fun pour() = "$name: Pour"
    fun recycle(): String
  }

  abstract class Can : BeverageContainer {
    override fun open() = "Pop Top"
  }

  class SteelCan : Can() {
    override fun recycle() = "Recycle Steel"
  }

  class AluminumCan : Can() {
    override fun recycle() = "Recycle Aluminum"
  }

  abstract class Bottle : BeverageContainer {
    override fun open() = "Remove Cap"
  }

  class GlassBottle : Bottle() {
    override fun recycle() = "Recycle Glass"
  }

  abstract class PlasticBottle : Bottle()

  class PETBottle : PlasticBottle() {
    override fun recycle() = "Recycle PET"
  }

  class HDPEBottle : PlasticBottle() {
    override fun recycle() = "Recycle HDPE"
  }

  fun main() {
    val refrigerator = listOf(
      SteelCan(), AluminumCan(),
      GlassBottle(),
      PETBottle(), HDPEBottle()
    )
    refrigerator.map { it.open() } eq
      "[Pop Top, Pop Top, Remove Cap, " +
      "Remove Cap, Remove Cap]"
    refrigerator.map { it.recycle() } eq
      "[Recycle Steel, Recycle Aluminum, " +
      "Recycle Glass, " +
      "Recycle PET, Recycle HDPE]"
  }
  ```

## 아톰 69. 내포된 클래스
### 내포된 클래스
* `내포된 클래스를 사용하면 객체 안에 더 세분화된 구조를 정의`할 수 있다.
* 내포된 클래스는 외부 클래스의 이름 공간 안에 정의된 클래스이며, 외부 클래스의 구현이 내포된 클래스를 소유한다.
  ```
  class Airport(private val code: String) {
    open class Plane {
      // 자신을 둘러싼 클래스의 private 프로퍼티에 접근할 수 있다
      fun contact(airport: Airport) =
        "Contacting ${airport.code}"
    }
    private class PrivatePlane : Plane()
    fun privatePlane(): Plane = PrivatePlane()
  }

  fun main() {
    val denver = Airport("DEN")
    var plane = Plane()                   // [1]
    plane.contact(denver) eq "Contacting DEN"
    // 다음과 같이 할 수 없다
    // val privatePlane = Airport.PrivatePlane()
    val frankfurt = Airport("FRA")
    plane = frankfurt.privatePlane()
    // 다음과 같이 할 수 없다
    // val p = plane as PrivatePlane      // [2]
    plane.contact(frankfurt) eq "Contacting FRA"
  }
  ```

### 지역 클래스
* `함수 안에 내포된 클래스를 지역 클래스`라고 한다.
  * 단, 지역 인터페이스는 허용되지 않는다.
  ```
  fun localClasses() {
    open class Amphibian
    class Frog : Amphibian()
    val amphibian: Amphibian = Frog()
  }
  ```
* 지역 open 클래스는 (거의) 정의하지 말아야하며, 정의해야하는 경우는 클래스가 중요한 경우이다.
* 지역 클래스의 객체 반환하려면 객체를 함수 밖에서 정의한 인터페이스나 클래스로 업캐스트 해야 한다.
  ```
  interface Amphibian

  fun createAmphibian(): Amphibian {
    class Frog : Amphibian
    return Frog()
  }

  fun main() {
    val amphibian = createAmphibian()
    // 다음과 같이 다운캐스트시 컴파일 오류가 발생한다
    // amphibian as Frog
  }
  ```

### 인터페이스에 포함된 클래스
* 인터페이스 안에 클래스를 내포시킬 수 있다.
  ```
  interface Item {
    val type: Type
    data class Type(val type: String)
  }

  class Bolt(type: String) : Item {
    override val type = Item.Type(type)
  }
  ```

### 내포된 이넘
* 이넘도 클래스기에 다른 클래스 안에 내포가능하다.
  ```
  class Ticket(
    val name: String,
    val seat: Seat = Coach
  ) {
    enum class Seat {
      Coach,
      Premium,
      Business,
      First
    }
    ...
  }
  ```
* 이넘을 함수에 내포시킬 수는 없고, 이넘이 다른 클래스를 상속할 수도 없다.
* 이넘을 인터페이스 안에 내포시킬 수 있다.
  ```
  interface Game {
    enum class State { Playing, Finished }
    enum class Mark { Blank, X ,O }
  }
  
  class FillIt(
    val side: Int = 3, randomSeed: Int = 0
  ): Game {
    private var state = Playing
    private val grid = MutableList(side * side) { Blank }
    private var player = X
    ...
  }
  ...
  ```

## 아톰 70. 객체
### 객체
* object 키워드는 인스턴스를 생성할 수 없고 오직 하나만 존재하며 싱글턴 패턴이라고 한다.
* 인스턴스를 여러 개 생성하는 것을 막고 싶은 경우 한 개체 안에 속한 함수와 프로퍼티를 함께 엮는 방법이다.
  ```
  object JustOne {
    val n = 2
    fun f() = n * 10
    fun g() = this.n * 20   // this 키워드는 유일한 객체 인스턴스를 가리키며 이 경우 JustOne이다.
  }

  fun main() {
    // val x = JustOne() // 인스턴스를 직접 생성하는 경우 Error 발생!
    JustOne.n eq 2
    JustOne.f() eq 20
    JustOne.g() eq 40
  }
  ```
* object에 대해 파라미터 목록을 지정할 수 없다.
  ```
  object JustOne(val n: Int) // 파라미터 목록 지정 금지
  ```
* 보통 object의 이름은 클래스의 이름을 겸해서 첫 글자를 영어 대문자로 표현한다.
* object는 다른 일반 클래스나 인터페이스를 상속 가능하다.
  ```
  open class Paint(val color: String) {
    open fun apply() = "Applying $color"
  }

  object Acrylic: Paint("Blue") {
    override fun apply() =
      "Acrylic, ${super.apply()}"
  }

  interface PaintPreparation {
    fun prepare(): String
  }

  object Prepare: PaintPreparation {
    override fun prepare() = "Scrape"
  }
  ```
* object는 인스턴스를 하나만 만들기 때문에 결과가 동일하다. → 여러 곳에서 연산이 일어나면 모든 연산 합의 최종 값이 결과가 된다.
* object를 private으로 정의해 접근할 수 없도록 할 수 있으며, 클래스 안에 내포해 사용 가능하다. → 내포 클래스 외에 `commpanion object 키워드`를 이용해 사용할 수도 있다.

## 아톰 71. 내부 클래스
### 내부 클래스
* 내부 클래스의 객체는 자신을 둘러싼 클래스의 인스턴스에 대한 참조를 유지한다.
  * 내포된 클래스는 inner(내부) 클래스를 상속할 수 없다.
  * 코틀린은 inner data 클래스를 허용하지 않는다.
  ```
  class Hotel(private val reception: String) {
    open inner class Room(val id: Int = 0) {
      // Room을 둘러싼 클래스의 'reception'을 사용한다
      fun callReception() =
        "Room $id Calling $reception"
    }
    private inner class Closet : Room()
    fun closet(): Room = Closet()
  }
  ```

### 한정된 this
* inner(내부) 클래스에서 this 는 inner 객체나 외부 객체를 가리킬 수 있기 때문에 한정된 this 구문을 사용하며 한정된 this는 this 뒤에 @을 붙이고 대상 클래스 이름을 덧붙인다.
  * `this@대상클래스`

### 내부 클래스 상속
* 내부 클래스는 다른 외부 클래스에 있는 내부 클래스를 상속할 수 있다.
  ```
  open class Egg {
    private var yolk = Yolk()
    open inner class Yolk {
      init { trace("Egg.Yolk()") }
      open fun f() { trace("Egg.Yolk.f()") }
    }
    init { trace("New Egg()") }
    fun insertYolk(y: Yolk) { yolk = y }
    fun g() { yolk.f() }
  }

  class BigEgg : Egg() {
    inner class Yolk : Egg.Yolk() { // 외부 클래스의 내부 클래스의 상속
      init { trace("BigEgg.Yolk()") }
      override fun f() {
        trace("BigEgg.Yolk.f()")
      }
    }
    init { insertYolk(Yolk()) }
  }
  ```

### 지역 내부 클래스와 익명 내부 클래스
* `멤버 함수 안에 정의된 클래스를 지역 내부 클래스라고 하면 객체 식이나 SAM 변환을 사용해 익명으로 생성 가능`하며, 모든 경우에 inner 키워드를 사용하지 않지만 `암시적으로 내부 클래스`가 된다.
  * * 객체 식으로 사용된다는 특징으로 익명 내부 클래스를 식별할 수 있으며, 익명 내부 클래스는 작고 단순해 이름이 있는 클래스로 만들지 않고, 간단한 익명 내부 클래스는 SAM 변환이 있다.
  ```
  object CreatePet {
    fun home() = " home!"
    fun dog(): Pet {
      val say = "Bark"
      // 지역 내부 클래스
      class Dog : Pet {
        override fun speak() = say + home()
      }
      return Dog()
    }
    fun cat(): Pet {
      val emit = "Meow"
      // 익명 내부 클래스
      return object: Pet {
        override fun speak() = emit + home()
      }
    }
    fun hamster(): Pet {
      val squeak = "Squeak"
      // SAM 변환
      return Pet { squeak + home() }
    }
  }
  ```
* 내부 클래스는 외부 클래스 객체에 대한 참조를 저장하므로 `지역 내부 클래스도 자신을 둘러싼 클래스에 속한 객체의 모든 멤버에 접근`할 수 있다.
* SAM 변환으로 선언하는 객체 내부에는 init 블록이 들어갈 수 없다는 한계가 있다.
* 코틀린에서는 한 파일 안에 최상위 클래스, 함수를 정의할 수 있기 때문에 지역 클래스를 거의 사용하지 않아서 `지역 클래스는 아주 단순한 클래스를 사용해야 합리적`이다. → 복잡해지는 경우 일반 클래스로 격상

## 아톰 72. 동반 객체
### 동반 객체
* 멤버 함수는 클래스의 특정 인스턴스에 작동하며 일부 함수는 어떤 객체에 대한 함수가 아닐 수 있기 때문에 `함수는 특정 객체에 매여 있을 필요가 없다.`
* companion object는 동반 객체라고 부르며, 안에 있는 함수와 필드는 클래스에 대한 함수와 필드지만, 일반 클래스는 동반 객체에 접근 가능하지만 그 반대는 불가능하다.
  ```
  class WithCompanion {
    companion object { // 동반 객체 선언
      val i = 3
      fun f() = i * 3
    }
    fun g() = i + f()
  }

  fun WithCompanion.Companion.h() = f() * i
  ```
* 동반 객체는 클래스당 하나만 허용되고, 동반 객체에 이름을 부여할 수 있다.
  ```
  companion object Named { ... }

  클래스명.Named.이름 // 동반 객체에 이름을 붙이는 경우
  클래스명.Companion.이름 // 붙이지 않는 경우 Companion을 디폴트로 부여한다.
  ```
* 동반 객체 안에서 프로퍼티 생성 시 메모리 상에 단 하나만 존재하고 모든 인스턴스가 이 필드를 공유한다.
* 어떤 함수가 오직 동반 객체의 프로퍼티만 사용한다면 해당 함수를 동반 객체로 옮기는 게 좋다.
* 동반 객체가 다른 곳에 정의한 클래스의 인스턴스일 수 있다.
  ```
  interface ZI {
    fun f(): String
    fun g(): String
  }

  open class ZIOpen : ZI {
    override fun f() = "ZIOpen.f()"
    override fun g() = "ZIOpen.g()"
  }

  class ZICompanion {
    companion object: ZIOpen()
    fun u() = trace("${f()} ${g()}")
  }

  class ZICompanionInheritance {
    companion object: ZIOpen() {
      override fun g() =
        "ZICompanionInheritance.g()"
      fun h() = "ZICompanionInheritance.h()"
    }
    fun u() = trace("${f()} ${g()} ${h()}")
  }

  class ZIClass {
    companion object: ZI {
      override fun f() = "ZIClass.f()"
      override fun g() = "ZIClass.g()"
    }
    fun u() = trace("${f()} ${g()}")
  }
  ```
* 동반 객체 클래스가 open이 아니라면 클래스 위임을 사용해 동반 객체가 클래스를 활용할 수 있다. → 위임을 오버라이드하고 확장할 수 있다.
  ```
  class ZIClosed : ZI {
    override fun f() = "ZIClosed.f()"
    override fun g() = "ZIClosed.g()"
  }

  class ZIDelegation {
    companion object: ZI by ZIClosed()
    fun u() = trace("${f()} ${g()}")
  }

  class ZIDelegationInheritance {
    companion object: ZI by ZIClosed() {
      override fun g() =
        "ZIDelegationInheritance.g()"
      fun h() =
        "ZIDelegationInheritance.h()"
    }
    fun u() = trace("${f()} ${g()} ${h()}")
  }
  ```
* 동반 객체의 사용법 중 객체 생성을 제어하는 경우를 들 수 있는 `팩토리 메서드 패턴`에 해당한다.
  * 일반 생성자로 해결할 수 없는 문제를 팩토리 함수가 해결해준다 → 생성자에서 연산 로직 
  ```
  class Numbered2

  private constructor(private val id: Int) {
    override fun toString() = "#$id"
    companion object Factory {
      fun create(size: Int) =
        List(size) { Numbered2(it) }
    }
  }

  fun main() {
    Numbered2.create(0) eq "[]"
    Numbered2.create(5) eq
      "[#0, #1, #2, #3, #4]"
  }
  ```
* 동반 객체 생성자는 동반 객체를 둘러싼 클래스가 `최초로 프로그램에 적재될 때 이뤄진다.`
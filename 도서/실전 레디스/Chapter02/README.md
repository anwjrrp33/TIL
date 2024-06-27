# 02. 자료형과 기능

### 대표적인 다섯 가지 자료형
* String형, List형, Hash형, Set형, Sorted Set형

<table class="table">
    <thead>
        <tr>
            <th>자료형</th>
            <th>설명</th>
            <th>예시</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>String형</td>
            <td>문자열(레디스에서는 숫자값도 포함), 간단한 키와 값의 조합이다.</td>
            <td>세션 정보관리</td>
        </tr>
        <tr>
            <td>List형</td>
            <td>리스트, 문자열 리스트다.</td>
            <td>타임라인</td>
        </tr>
        <tr>
            <td>Hash형</td>
            <td>해시, 프로그래밍 언어에서 연관 배열이나 딕셔너리와 비슷한 개념이다.</td>
            <td>객체 표현</td>
        </tr>
        <tr>
            <td>Set형</td>
            <td>집합, 복수의 값을 순서와 중복 없이 저장한다.</td>
            <td>태그 관리</td>
        </tr>
        <tr>
            <td>Sorted Set형</td>
            <td>정렬된 집합, 순서(랭크)가 있는 집합이다.</td>
            <td>랭킹</td>
        </tr>
    </tbody>
</table>

### 보조 자료형과 기능
* 기본 자료형 내부에서 특정 용도에 사용될 목적으로 만들어진 보조 자료형
    * 비트맵(비트 배열)
        * 비트 단위 연산을 통해 효율적인 메모리 사용과 빠른 비트 조작을 지원하는 데이터 구조
    * 지리적 공간 인덱스
        * 지리적 좌표 데이터를 저장하고 근접 검색을 수행할 수 있는 데이터 구조
* 데이터를 직접 다루는 기능
    * Pub/Sub 기능
        * 메시지를 발행(Publish)하고 구독(Subscribe)하는 방식으로 실시간 메시지 전송을 지원하는 기능
    * HyperLogLog
        * 고유 항목 수를 매우 적은 메모리로 추정할 수 있는 확률적 데이터 구조
    * 레디스 스트림
        * 로그와 같은 시퀀스 데이터를 효율적으로 처리할 수 있는 새로운 데이터 타입

### 레디스의 폭 넓은 데이터 모델 표현성
* 문제에 맞춰 적합한 자료형을 선택하면 데이터를 간단하게 다룰 수 있는 장점이 있다.
* 레디스는 KVS이지만 각 키를 독립적으로 관리하며 키간에는 관련성이 없기 때문에 KVS로 간주되지 않는다.
* 데이터베이스 번호로 식별하는 네임스페이스 같은 데이터를 관리할 수 있지만 기본적으로 전역에서 키와 값 쌍으로 관리한다.
* 키 어노테이션이라는 기능을 통해 키 간 관계성도 레디스에서 다룰 수 있고, 이 기능을 사용해 키 작업의 트리거 용도로 사용할 수 있다.
* 키 간 독립성을 원칙으로 하여, 가능하면 이 원칙에 맞춰 데이터를 저장하는 것이 좋다.
* 하나의 키에 연관된 값들을 범위 내에서 서로 연관성을 가진 단위로 관리할 수 있다.

### 레디스 자료형과 명령어
* String에서는 SET으로 저장하고, GET으로 데이터를 가져온다.
* Hash에서는 HSET으로 저장하고, GET으로 가져온다.
* 여러 키를 조작하기 위해서는 MSET, MGET 명령어를 사용할 수 있고 원자적으로 처리한다.

### 레디스 유틸리티 명령어
* KEYS
    * 키 목록을 확인하고 싶을 떄 사용된다.
        * > KEYS pattern
        * > KEYS *
    * 편리하지만 실행 시간이 오래걸리기 때문에 운용 중인 애플리케이션에서 지양하고 구현 중 동작을 확인하거나 분석할 때 사용하는 것이 좋다.
    * 운영환경에서는 SCAN 계열 명령어 사용을 추천한다.
* EXISTS
    * 키 존재 여부를 확인할 때 사용된다.
        * > EXISTS X
        * > EXISTS X Y
    * 키가 존재하면 1, 아니면 0, 키가 여러 개 있는 경우 매칭된 수를 반환한다.
* TYPE
    * 자료형과 기능을 확인할 수 있다.
        * > TYPE mykey
* DEL
    * 키의 삭제는 모든 자료형에서 공통적으로 DEL을 사용한다.
        * > DEL L
    * 반환 값은 삭제한 키의 개수이다.

## String 형
* 문자열, 이진 데이터 등을 위한 자료형이다.
* 이진 안전 문자열이기에 이미지나 실팽파일 등의 데이터도 저장할 수 있다.
* 숫자값(정수, 부동소수점)도 String 형에 저장한다.
* Bigmap형이라는 비트 단위로 조작할 수 있는 보조 자료형이 존재한다.
* 유스케이스
    * 캐시
        * 키와 값의 쌍으로 대응되는 문자열, 세션 정보, 이미지 데이터 등 이진 데이터
    * 카운터
        * 방문자 수 등 접근 수 카운트
    * 실시간 메트릭스
        * 각 항목의 수치를 파악할 수 있는 지표

#### 레디스의 String 형은 지금도 512MB가 최대인가?
* 512MB 제한은 키와 값 양쪽에 적용되며 SDS 버전의 제한 때문이다.
* 레디스 4.0.7에서 proto-max-bulk-len 지시자로 최대 제한을 해제할 수 있다.

### String 형 활용, 빠른 세션 캐시
* 세션 캐시(쿠키 등), 표시할 웹 페이지, 장바구니 내용 등 일시적인 정보를 저장하는데 사용된다.
  ![image](https://github.com/anwjrrp33/TIL/assets/38122225/13c8f088-8a6f-4490-96bc-6dd0eee6e0bd)

### String 형 주요 명령어
* GET/SET을 여러번 실행하면 RTT가 발생하여 시간 오버헤드가 발생하기 때문에 MGET/MSET을 RTT를 줄여 효율적으로 사용 가능하다.
* 숫자값 String 형 명령어 사용 시 10진수 부호 정수로 해석하지 않으면 오류가 반환된다.
<table class="table">
    <thead>
        <tr>
            <th>명령어</th>
            <th>설명</th>
            <th>예시</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>GET</td>
            <td>키값 가져오기, 존재하지 않는 경우 nil 반환, 시간 복잡도 O(1)</td>
            <td>GET key</td>
        </tr>
        <tr>
            <td>SET key value</td>
            <td>키와 값의 쌍을 지정하여 키에 값을 설정, EX 같은 시간을 지정하는 옵션을 통해 TTL 설정, 시간 복잡도 O(1)</td>
            <td>SET key value [ NX | XX] [GET] [ EX seconds | PX milliseconds | EXAT unix-time- seconds | PXAT unix-time-milliseconds | KEEPTTL]</td>
        </tr>
        <tr>
            <td>MGET</td>
            <td>여러 개의 키 값 가져오기, 시간 복잡도 O(N)</td>
            <td>MGET key [key ...]</td>
        </tr>
        <tr>
            <td>MSET</td>
            <td>여러 개의 키를 지정하여 한번에 값 저장, 키가 존재하는 경우 덮어쓰기, 시간 복잡도 O(N)</td>
            <td>MSET key vale [key value...]</td>
        </tr>
        <tr>
            <td>APPEND</td>
            <td>키에 값 덮어쓰기, 키가 존재하는 경우 키값 끝에 인수 내용을 추가하고, 키가 존재하지 않으면 새로운 키를 만든다, 시간 복잡도 O(1)</td>
            <td>APPEND key value</td>
        </tr>
        <tr>
            <td>STRLEN</td>
            <td>기의 길이 가져오기</td>
            <td>STRLEN key</td>
        </tr>
        <tr>
            <td>GETRANGE</td>
            <td>범위를 지정하여 키값 가져오기, 첫 번째 문자는 0부터 시작, 시간 복잡도 O(N)</td>
            <td>GETRANGE key start end</td>
        </tr>
        <tr>
            <td colspan="3">숫자인 경우에만 사용가능한 명령어</td>
        </tr>
        <tr>
            <td>INCR</td>
            <td>값을 1만큼 증가시키기, 값이 없으면 동작 전 0으로 지정, 시간 복잡도 O(1)</td>
            <td>INCR key</td>
        </tr>
        <tr>
            <td>INCRBY</td>
            <td>값을 지정한 정수만큼 증가시키기, 값이 없으면 동작 전 0으로 지정, 시간 복잡도 O(1)</td>
            <td>INCRBY key increment</td>
        </tr>
        <tr>
            <td>INCRBYFLOAT</td>
            <td>값을 지정한 부동소수점만큼 증가시키기, 값이 없으면 동작 전 0으로 지정, 시간 복잡도 O(1)</td>
            <td>INCRBYFLOAT key increment</td>
        </tr>
        <tr>
            <td>DECR</td>
            <td>값을 1만큼 감소시키기, 값이 없으면 동작 전 0으로 지정, 시간 복잡도 O(1)</td>
            <td>DECR key decrement</td>
        </tr>
        <tr>
            <td>DECRBY</td>
            <td>값을 지정한 정수만큼 감소시키기, 값이 없으면 동작 전 0으로 지정, 시간 복잡도 O(1)</td>
            <td>DECRBY key decrement</td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </tbody>
</table>

* 그외 명령어
  * GETTEX - TTL을 설정한 키 값 가져오기
    * 유효기간 설정도 가능 
    * > GETEX key [ EX seconds | PX milliseconds | EXAT unix-time- seconds | PXAT unix-1ime-mi11iseconds | PERSIST]
  * GETDEL - 키 값을 가져온 후 그 키를 삭제하기
    * 레디스 6.2.0 이상 
    * > GETDEL key
  * MSETNX - 여러 개의 키가 존재하지 않는 것을 확인하고 값을 저장하기
    * 인수에 있는 키 중에 하나라도 이미 존재하면 모든 저장에 실패 
    * > MSETNX key value [key value ...]

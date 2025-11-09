# Longest Substring Without Repeating Characters

## 문제 설명

주어진 문자열 `s`에서 **반복되는 문자가 없는 가장 긴 부분 문자열(substring)의 길이**를 찾는 문제입니다.

### 예제

**Example 1:**
```
Input: s = "abcabcbb"
Output: 3
Explanation: "abc"가 반복 문자 없이 가장 긴 부분 문자열입니다.
```

**Example 2:**
```
Input: s = "bbbbb"
Output: 1
Explanation: "b"가 가장 긴 부분 문자열입니다.
```

**Example 3:**
```
Input: s = "pwwkew"
Output: 3
Explanation: "wke"가 가장 긴 부분 문자열입니다. "pwke"는 부분 문자열이 아니라 subsequence입니다.
```

### 제약 조건
- `0 <= s.length <= 5 * 10^4`
- `s`는 영문자, 숫자, 기호, 공백으로 구성됩니다.

---

## 접근 방법 1: Brute Force (무차별 대입)

### 아이디어
모든 가능한 부분 문자열을 확인하고, 각 부분 문자열이 중복 문자를 포함하는지 검사합니다.

### 알고리즘
1. 시작 인덱스 i를 0부터 n-1까지 순회
2. 끝 인덱스 j를 i부터 n-1까지 순회
3. s[i..j] 부분 문자열에 중복 문자가 있는지 확인
4. 중복이 없으면 최대 길이 갱신

### 시간 복잡도
- **O(n³)**: 모든 부분 문자열 생성 O(n²) × 중복 검사 O(n)

### 공간 복잡도
- **O(min(n, m))**: m은 문자 집합 크기 (Set 사용 시)

### 예제 실행: "abcabcbb"

```
i=0, j=0: "a" → 중복 없음, len=1, max=1
i=0, j=1: "ab" → 중복 없음, len=2, max=2
i=0, j=2: "abc" → 중복 없음, len=3, max=3
i=0, j=3: "abca" → 'a' 중복! 중단
i=1, j=1: "b" → 중복 없음, len=1, max=3
i=1, j=2: "bc" → 중복 없음, len=2, max=3
i=1, j=3: "bca" → 중복 없음, len=3, max=3
i=1, j=4: "bcab" → 'b' 중복! 중단
...
최종 결과: 3
```

### Java 코드

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int maxLen = 0;

        // 모든 시작점에서 시작
        for (int i = 0; i < n; i++) {
            // 각 시작점에서 가능한 모든 끝점까지 확장
            for (int j = i; j < n; j++) {
                // i부터 j까지 부분 문자열이 중복 문자를 포함하는지 확인
                if (allUnique(s, i, j)) {
                    maxLen = Math.max(maxLen, j - i + 1);
                } else {
                    break; // 중복이 발견되면 더 이상 확장 불가
                }
            }
        }

        return maxLen;
    }

    // i부터 j까지 부분 문자열에 중복 문자가 있는지 확인
    private boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i <= end; i++) {
            char c = s.charAt(i);
            if (set.contains(c)) {
                return false;
            }
            set.add(c);
        }
        return true;
    }
}
```

### Kotlin 코드

```kotlin
class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        val n = s.length
        var maxLen = 0

        // 모든 시작점에서 시작
        for (i in 0 until n) {
            // 각 시작점에서 가능한 모든 끝점까지 확장
            for (j in i until n) {
                // i부터 j까지 부분 문자열이 중복 문자를 포함하는지 확인
                if (allUnique(s, i, j)) {
                    maxLen = maxOf(maxLen, j - i + 1)
                } else {
                    break // 중복이 발견되면 더 이상 확장 불가
                }
            }
        }

        return maxLen
    }

    // i부터 j까지 부분 문자열에 중복 문자가 있는지 확인
    private fun allUnique(s: String, start: Int, end: Int): Boolean {
        val set = mutableSetOf<Char>()
        for (i in start..end) {
            val c = s[i]
            if (c in set) {
                return false
            }
            set.add(c)
        }
        return true
    }
}
```

### 문제점
- 시간 복잡도가 너무 높아서 큰 입력에 대해 매우 느립니다.
- 불필요한 반복 검사가 많습니다.

---

## 접근 방법 2: Sliding Window + Set

### 아이디어
두 개의 포인터(left, right)를 사용하여 윈도우를 만들고, 윈도우 내의 문자들을 Set으로 관리합니다.
- right 포인터로 윈도우를 확장
- 중복 문자 발견 시 left 포인터를 이동하여 윈도우 축소

### 알고리즘
1. left, right 포인터를 0으로 초기화
2. Set을 사용하여 현재 윈도우의 문자 관리
3. right 포인터를 오른쪽으로 이동하며:
   - 현재 문자가 Set에 없으면: Set에 추가, 최대 길이 갱신
   - 현재 문자가 Set에 있으면: left 포인터를 이동하며 중복 제거

### 시간 복잡도
- **O(2n) = O(n)**: 최악의 경우 각 문자를 left, right가 각각 한 번씩 방문

### 공간 복잡도
- **O(min(n, m))**: m은 문자 집합 크기

### 예제 실행: "abcabcbb"

```
초기: left=0, right=0, set={}, max=0

right=0, 'a': set={a}, max=1
right=1, 'b': set={a,b}, max=2
right=2, 'c': set={a,b,c}, max=3
right=3, 'a': 'a'가 이미 존재!
  → left=0: 'a' 제거, set={b,c}
  → left=1: 'a' 추가, set={b,c,a}, max=3
right=4, 'b': 'b'가 이미 존재!
  → left=1: 'b' 제거, set={c,a}
  → left=2: 'b' 추가, set={c,a,b}, max=3
right=5, 'c': 'c'가 이미 존재!
  → left=2: 'c' 제거, set={a,b}
  → left=3: 'c' 추가, set={a,b,c}, max=3
right=6, 'b': 'b'가 이미 존재!
  → left=3: 'a' 제거, set={b,c}
  → left=4: 'b' 제거, set={c}
  → left=5: 'b' 추가, set={c,b}, max=3
right=7, 'b': 'b'가 이미 존재!
  → left=5: 'c' 제거, set={b}
  → left=6: 'b' 제거, set={}
  → left=7: 'b' 추가, set={b}, max=3

최종 결과: 3
```

### Java 코드

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);

            // 중복 문자가 있으면 left를 이동하며 제거
            while (set.contains(currentChar)) {
                set.remove(s.charAt(left));
                left++;
            }

            // 현재 문자 추가
            set.add(currentChar);

            // 최대 길이 갱신
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
```

### Kotlin 코드

```kotlin
class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        val set = mutableSetOf<Char>()
        var maxLen = 0
        var left = 0

        for (right in s.indices) {
            val currentChar = s[right]

            // 중복 문자가 있으면 left를 이동하며 제거
            while (currentChar in set) {
                set.remove(s[left])
                left++
            }

            // 현재 문자 추가
            set.add(currentChar)

            // 최대 길이 갱신
            maxLen = maxOf(maxLen, right - left + 1)
        }

        return maxLen
    }
}
```

### 개선점
- O(n) 시간 복杂도로 훨씬 빠름
- Sliding Window 패턴 활용

### 남은 문제점
- while 루프에서 중복 문자를 찾을 때까지 left를 하나씩 이동
- 최악의 경우 O(2n) 연산

---

## 접근 방법 3: Sliding Window 최적화 (HashMap)

### 아이디어
HashMap을 사용하여 각 문자의 **마지막 위치 인덱스**를 저장합니다.
중복 문자 발견 시 left 포인터를 한 번에 적절한 위치로 점프할 수 있습니다.

### 알고리즘
1. HashMap에 문자와 인덱스를 저장
2. right 포인터로 문자열 순회
3. 현재 문자가 HashMap에 있고, 해당 인덱스가 left 이상이면:
   - left를 해당 인덱스 + 1로 이동
4. HashMap 갱신 및 최대 길이 갱신

### 시간 복잡도
- **O(n)**: 각 문자를 정확히 한 번만 방문

### 공간 복잡도
- **O(min(n, m))**: m은 문자 집합 크기

### 예제 실행: "abcabcbb"

```
초기: left=0, map={}, max=0

right=0, 'a': map={a:0}, left=0, max=1
right=1, 'b': map={a:0, b:1}, left=0, max=2
right=2, 'c': map={a:0, b:1, c:2}, left=0, max=3
right=3, 'a': 'a'는 인덱스 0에 있음, left=1로 점프
            map={a:3, b:1, c:2}, max=3
right=4, 'b': 'b'는 인덱스 1에 있음, left=2로 점프
            map={a:3, b:4, c:2}, max=3
right=5, 'c': 'c'는 인덱스 2에 있음, left=3으로 점프
            map={a:3, b:4, c:5}, max=3
right=6, 'b': 'b'는 인덱스 4에 있음, left=5로 점프
            map={a:3, b:6, c:5}, max=3
right=7, 'b': 'b'는 인덱스 6에 있음, left=7로 점프
            map={a:3, b:7, c:5}, max=3

최종 결과: 3
```

### Java 코드

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        Map<Character, Integer> map = new HashMap<>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);

            // 현재 문자가 윈도우 내에 있으면 left를 점프
            if (map.containsKey(currentChar) && map.get(currentChar) >= left) {
                left = map.get(currentChar) + 1;
            }

            // 현재 문자의 인덱스 저장/갱신
            map.put(currentChar, right);

            // 최대 길이 갱신
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
```

### Kotlin 코드

```kotlin
class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        val map = mutableMapOf<Char, Int>()
        var maxLen = 0
        var left = 0

        for (right in s.indices) {
            val currentChar = s[right]

            // 현재 문자가 윈도우 내에 있으면 left를 점프
            if (currentChar in map && map[currentChar]!! >= left) {
                left = map[currentChar]!! + 1
            }

            // 현재 문자의 인덱스 저장/갱신
            map[currentChar] = right

            // 최대 길이 갱신
            maxLen = maxOf(maxLen, right - left + 1)
        }

        return maxLen
    }
}
```

### 장점
- O(n) 시간 복잡도로 최적화
- left 포인터가 불필요하게 이동하지 않음
- 가장 효율적인 솔루션

---

## 접근 방법 4: Sliding Window + Array (ASCII 최적화)

### 아이디어
문자 집합이 제한적일 때(예: ASCII 128문자), HashMap 대신 배열을 사용하여 성능을 더욱 향상시킬 수 있습니다.

### Java 코드

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        // ASCII 문자는 128개
        int[] lastIndex = new int[128];
        Arrays.fill(lastIndex, -1);

        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // 현재 문자가 윈도우 내에 있으면 left를 점프
            if (lastIndex[currentChar] >= left) {
                left = lastIndex[currentChar] + 1;
            }

            // 현재 문자의 인덱스 저장
            lastIndex[currentChar] = right;

            // 최대 길이 갱신
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
```

### Kotlin 코드

```kotlin
class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        // ASCII 문자는 128개
        val lastIndex = IntArray(128) { -1 }

        var maxLen = 0
        var left = 0

        for (right in s.indices) {
            val currentChar = s[right].code

            // 현재 문자가 윈도우 내에 있으면 left를 점프
            if (lastIndex[currentChar] >= left) {
                left = lastIndex[currentChar] + 1
            }

            // 현재 문자의 인덱스 저장
            lastIndex[currentChar] = right

            // 최대 길이 갱신
            maxLen = maxOf(maxLen, right - left + 1)
        }

        return maxLen
    }
}
```

---

## 비교 및 정리

| 접근 방법 | 시간 복잡도 | 공간 복잡도 | 장점 | 단점 |
|---------|-----------|-----------|-----|-----|
| Brute Force | O(n³) | O(min(n,m)) | 구현 간단 | 매우 느림 |
| Sliding Window + Set | O(2n) | O(min(n,m)) | 효율적 | while 루프로 인한 오버헤드 |
| Sliding Window + HashMap | O(n) | O(min(n,m)) | 최적화됨, 한 번만 순회 | HashMap 오버헤드 |
| Sliding Window + Array | O(n) | O(m) | 가장 빠름 | 문자 집합이 제한적일 때만 |

### 추천 솔루션
- **일반적인 경우**: 접근 방법 3 (Sliding Window + HashMap)
- **ASCII 문자만**: 접근 방법 4 (Sliding Window + Array)

### 핵심 개념
1. **Sliding Window**: 윈도우의 크기를 유동적으로 조절하며 조건을 만족하는 최적의 윈도우를 찾는 기법
2. **Two Pointers**: left, right 두 포인터로 범위를 관리
3. **Hash 자료구조**: 문자의 존재 여부나 위치를 O(1)에 확인

### 실전 팁
- 인터뷰에서는 Brute Force부터 설명하고 점진적으로 최적화하는 과정을 보여주는 것이 좋습니다.
- Sliding Window 패턴은 부분 배열/부분 문자열 문제에 자주 사용됩니다.
- HashMap의 key가 현재 윈도우 범위 내에 있는지 확인하는 것이 중요합니다.

---

## 테스트 케이스

```java
// Test cases
"abcabcbb" → 3
"bbbbb" → 1
"pwwkew" → 3
"" → 0
" " → 1
"au" → 2
"dvdf" → 3
"abba" → 2
```

### Edge Cases
- 빈 문자열: `""` → 0
- 모든 문자가 같음: `"aaaa"` → 1
- 모든 문자가 다름: `"abcdef"` → 6
- 공백 포함: `"a b c"` → 3
- 특수 문자: `"!@#$%"` → 5
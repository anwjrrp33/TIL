# Sliding Window & Two Pointers 알고리즘 완벽 가이드

## 목차
1. [Two Pointers (투 포인터)](#two-pointers-투-포인터)
2. [Sliding Window (슬라이딩 윈도우)](#sliding-window-슬라이딩-윈도우)
3. [두 알고리즘의 비교](#두-알고리즘의-비교)
4. [실전 적용 가이드](#실전-적용-가이드)
5. [문제 유형별 접근법](#문제-유형별-접근법)

---

# Two Pointers (투 포인터)

## 핵심 개념

**투 포인터**는 배열이나 리스트에서 **두 개의 포인터(인덱스)**를 사용하여 문제를 해결하는 기법입니다.

### 기본 아이디어
- 하나 또는 두 개의 배열에서 두 개의 포인터를 이동시키며 조건을 만족하는 값을 찾음
- Brute Force O(n²) 대신 O(n) 시간에 해결 가능
- **정렬된 배열**이나 **순차적 탐색**이 필요한 경우 유용

---

## Two Pointers의 3가지 패턴

### 패턴 1: 양 끝에서 중앙으로 (Opposite Directional)

```
[1, 2, 3, 4, 5, 6, 7, 8]
 ↑                    ↑
left               right

→ 양쪽 포인터를 조건에 따라 중앙으로 이동
```

**특징:**
- 두 포인터가 배열의 양 끝에서 시작
- 조건에 따라 left는 오른쪽으로, right는 왼쪽으로 이동
- 주로 **정렬된 배열**에서 사용

**사용 시나리오:**
- 두 수의 합 찾기
- 배열 뒤집기
- 팰린드롬 검사
- Container With Most Water

**예제: Two Sum II (정렬된 배열)**

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1}; // 1-indexed
            } else if (sum < target) {
                left++; // 합이 작으면 왼쪽 포인터 증가
            } else {
                right--; // 합이 크면 오른쪽 포인터 감소
            }
        }

        return new int[]{-1, -1};
    }
}
```

```kotlin
class Solution {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        var left = 0
        var right = numbers.size - 1

        while (left < right) {
            val sum = numbers[left] + numbers[right]

            when {
                sum == target -> return intArrayOf(left + 1, right + 1)
                sum < target -> left++
                else -> right--
            }
        }

        return intArrayOf(-1, -1)
    }
}
```

**실행 과정: numbers = [2,7,11,15], target = 9**
```
초기: left=0, right=3
  sum = 2 + 15 = 17 > 9 → right=2

Step 1: left=0, right=2
  sum = 2 + 11 = 13 > 9 → right=1

Step 2: left=0, right=1
  sum = 2 + 7 = 9 == 9 → 답 찾음!

결과: [1, 2] (1-indexed)
```

---

### 패턴 2: 같은 방향으로 이동 (Equi-Directional)

```
[1, 2, 3, 4, 5, 6, 7, 8]
 ↑  ↑
slow fast

→ 두 포인터가 같은 방향으로 다른 속도로 이동
```

**특징:**
- 두 포인터가 배열의 같은 쪽에서 시작
- 서로 다른 속도나 조건으로 이동
- **Fast & Slow Pointer** 패턴이라고도 함

**사용 시나리오:**
- 중복 제거
- 배열 재배치
- 연결 리스트의 사이클 탐지
- 특정 조건을 만족하는 부분 배열 찾기

**예제: Remove Duplicates from Sorted Array**

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int slow = 0; // 고유한 원소를 저장할 위치

        for (int fast = 1; fast < nums.length; fast++) {
            // 새로운 고유한 원소 발견
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }

        return slow + 1; // 고유한 원소 개수
    }
}
```

```kotlin
class Solution {
    fun removeDuplicates(nums: IntArray): Int {
        if (nums.isEmpty()) return 0

        var slow = 0

        for (fast in 1 until nums.size) {
            if (nums[fast] != nums[slow]) {
                slow++
                nums[slow] = nums[fast]
            }
        }

        return slow + 1
    }
}
```

**실행 과정: nums = [1,1,2,2,3]**
```
초기: slow=0, nums=[1,1,2,2,3]

fast=1: nums[1]=1 == nums[0]=1 → skip
fast=2: nums[2]=2 != nums[0]=1 → slow=1, nums=[1,2,2,2,3]
fast=3: nums[3]=2 == nums[1]=2 → skip
fast=4: nums[4]=3 != nums[1]=2 → slow=2, nums=[1,2,3,2,3]

결과: slow + 1 = 3, nums=[1,2,3,_,_]
```

---

### 패턴 3: 두 개의 배열에서 포인터 사용

```
nums1: [1, 3, 5, 7]
        ↑
        i

nums2: [2, 4, 6, 8]
        ↑
        j

→ 각 배열에 하나씩 포인터를 두고 비교하며 이동
```

**특징:**
- 각 배열마다 하나의 포인터
- 두 배열을 병합하거나 비교할 때 사용
- 보통 **정렬된 배열**에서 사용

**사용 시나리오:**
- Merge Sorted Arrays
- Intersection of Two Arrays
- Median of Two Sorted Arrays

**예제: Merge Sorted Array**

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1; // nums1의 마지막 유효 원소
        int j = n - 1; // nums2의 마지막 원소
        int k = m + n - 1; // 결과 배열의 마지막 위치

        // 뒤에서부터 큰 값을 채워나감
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }

        // nums2에 남은 원소가 있으면 복사
        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }
    }
}
```

```kotlin
class Solution {
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int) {
        var i = m - 1
        var j = n - 1
        var k = m + n - 1

        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--]
            } else {
                nums1[k--] = nums2[j--]
            }
        }

        while (j >= 0) {
            nums1[k--] = nums2[j--]
        }
    }
}
```

**실행 과정: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3**
```
초기: i=2, j=2, k=5

Step 1: nums1[2]=3 vs nums2[2]=6 → nums1[5]=6, j=1, k=4
Step 2: nums1[2]=3 vs nums2[1]=5 → nums1[4]=5, j=0, k=3
Step 3: nums1[2]=3 vs nums2[0]=2 → nums1[3]=3, i=1, k=2
Step 4: nums1[1]=2 vs nums2[0]=2 → nums1[2]=2, j=-1, k=1
nums2 완료, nums1에 [1,2] 남음

결과: nums1 = [1,2,2,3,5,6]
```

---

## Two Pointers 시간/공간 복잡도

| 작업 | Brute Force | Two Pointers | 개선 |
|-----|-------------|--------------|------|
| Two Sum (정렬됨) | O(n²) | O(n) | ✅ |
| 중복 제거 | O(n²) | O(n) | ✅ |
| 배열 병합 | O(nlogn) | O(n) | ✅ |
| 공간 복잡도 | - | O(1) | ✅ |

---

## Two Pointers를 사용해야 하는 신호

✅ **이런 문제라면 Two Pointers를 고려하세요:**
1. 배열이나 리스트에서 **두 개의 원소**를 찾는 문제
2. **정렬된 배열**이 주어지는 경우
3. **In-place** 연산이 필요한 경우 (추가 공간 없이)
4. **선형 시간 O(n)**으로 해결 가능해 보이는 경우
5. "양 끝", "처음과 끝", "두 개의 포인터" 등의 키워드

❌ **Two Pointers가 적합하지 않은 경우:**
1. 배열이 정렬되어 있지 않고, 정렬할 수도 없는 경우
2. 세 개 이상의 포인터가 필요한 복잡한 경우
3. 부분 배열의 **모든 경우의 수**를 검사해야 하는 경우

---

# Sliding Window (슬라이딩 윈도우)

## 핵심 개념

**슬라이딩 윈도우**는 배열이나 문자열에서 **연속된 부분 배열(subarray) 또는 부분 문자열(substring)**을 효율적으로 처리하는 기법입니다.

### 기본 아이디어
- 고정 또는 가변 크기의 "윈도우"를 배열 위에서 이동
- 윈도우 내의 원소들을 추적하며 조건을 만족하는 최적의 윈도우 찾기
- Brute Force O(n²) 또는 O(n³) 대신 O(n) 시간에 해결

### 시각화
```
배열: [1, 2, 3, 4, 5, 6, 7, 8]

윈도우 크기 = 3:
[1, 2, 3] 4, 5, 6, 7, 8  → 처리
 1 [2, 3, 4] 5, 6, 7, 8  → 처리
 1, 2 [3, 4, 5] 6, 7, 8  → 처리
...윈도우가 오른쪽으로 슬라이드
```

---

## Sliding Window의 2가지 패턴

### 패턴 1: 고정 크기 윈도우 (Fixed Size Window)

**특징:**
- 윈도우의 크기가 **고정**됨
- 한 칸씩 오른쪽으로 이동하며 처리
- 구현이 비교적 간단

**사용 시나리오:**
- 크기 k의 부분 배열의 최대/최소/평균
- k개 연속 원소의 합
- 고정 길이 패턴 찾기

**예제: Maximum Average Subarray I**

주어진 배열에서 연속된 k개 원소의 평균이 최대가 되는 값을 찾기

```java
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        // 첫 번째 윈도우의 합 계산
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        int maxSum = windowSum;

        // 윈도우를 슬라이드
        for (int i = k; i < nums.length; i++) {
            // 왼쪽 원소 제거, 오른쪽 원소 추가
            windowSum = windowSum - nums[i - k] + nums[i];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum / (double) k;
    }
}
```

```kotlin
class Solution {
    fun findMaxAverage(nums: IntArray, k: Int): Double {
        // 첫 번째 윈도우의 합 계산
        var windowSum = nums.take(k).sum()
        var maxSum = windowSum

        // 윈도우를 슬라이드
        for (i in k until nums.size) {
            windowSum = windowSum - nums[i - k] + nums[i]
            maxSum = maxOf(maxSum, windowSum)
        }

        return maxSum / k.toDouble()
    }
}
```

**실행 과정: nums = [1,12,-5,-6,50,3], k = 4**
```
초기 윈도우: [1,12,-5,-6] → sum = 2, max = 2

i=4: 윈도우 [12,-5,-6,50]
  sum = 2 - 1 + 50 = 51, max = 51

i=5: 윈도우 [-5,-6,50,3]
  sum = 51 - 12 + 3 = 42, max = 51

결과: 51 / 4 = 12.75
```

**시간 복잡도:**
- Brute Force: O(n × k) - 각 위치에서 k개 원소 합산
- Sliding Window: O(n) - 한 번만 순회

---

### 패턴 2: 가변 크기 윈도우 (Variable Size Window)

**특징:**
- 윈도우의 크기가 **동적으로 변함**
- 조건에 따라 윈도우를 확장(expand)하거나 축소(shrink)
- 보통 **두 포인터(left, right)**로 구현

**템플릿:**
```java
int left = 0, right = 0;
while (right < n) {
    // 윈도우에 nums[right] 추가
    window.add(nums[right]);
    right++;

    // 조건을 위반하면 윈도우 축소
    while (window violates condition) {
        // 윈도우에서 nums[left] 제거
        window.remove(nums[left]);
        left++;
    }

    // 최적의 윈도우 크기 갱신
    maxLen = Math.max(maxLen, right - left);
}
```

**사용 시나리오:**
- 조건을 만족하는 최장/최단 부분 배열
- 부분 문자열 문제
- 특정 합을 만드는 부분 배열

**예제: Longest Substring Without Repeating Characters**
(이전에 풀었던 문제)

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> window = new HashSet<>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // 중복 발견: 윈도우 축소
            while (window.contains(c)) {
                window.remove(s.charAt(left));
                left++;
            }

            // 윈도우 확장
            window.add(c);
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
```

**실행 과정: s = "abcabcbb"**
```
초기: left=0, right=0, window={}, max=0

right=0, 'a': window={a}, left=0, max=1
right=1, 'b': window={a,b}, left=0, max=2
right=2, 'c': window={a,b,c}, left=0, max=3
right=3, 'a': 중복!
  → left=0: 'a' 제거
  → left=1: 'a' 추가, window={b,c,a}, max=3
right=4, 'b': 중복!
  → left=1: 'b' 제거
  → left=2: 'b' 추가, window={c,a,b}, max=3
...
```

---

## Sliding Window 최적화 버전

**HashMap을 사용한 O(n) 최적화:**

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastIndex = new HashMap<>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // 이전에 본 문자이고 현재 윈도우 안에 있으면
            if (lastIndex.containsKey(c) && lastIndex.get(c) >= left) {
                left = lastIndex.get(c) + 1; // 한 번에 점프
            }

            lastIndex.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
```

**개선점:**
- while 루프 없이 **O(n)** 보장
- HashMap으로 이전 위치를 기억하여 left를 한 번에 이동

---

## Sliding Window 다양한 예제

### 예제 1: Minimum Size Subarray Sum

**문제:** 합이 target 이상인 가장 짧은 연속 부분 배열의 길이를 찾기

```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right]; // 윈도우 확장

            // 조건 만족: 윈도우 축소 시도
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
```

```kotlin
class Solution {
    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        var left = 0
        var sum = 0
        var minLen = Int.MAX_VALUE

        for (right in nums.indices) {
            sum += nums[right]

            while (sum >= target) {
                minLen = minOf(minLen, right - left + 1)
                sum -= nums[left]
                left++
            }
        }

        return if (minLen == Int.MAX_VALUE) 0 else minLen
    }
}
```

**실행 과정: target = 7, nums = [2,3,1,2,4,3]**
```
right=0, sum=2, left=0
right=1, sum=5, left=0
right=2, sum=6, left=0
right=3, sum=8 >= 7
  → minLen=4, sum=6, left=1
right=4, sum=10 >= 7
  → minLen=4, sum=7, left=2
  → minLen=3, sum=5, left=3
right=5, sum=8 >= 7
  → minLen=3, sum=5, left=4
  → minLen=2 ← 최종 답

결과: 2 (부분 배열 [4,3])
```

---

### 예제 2: Longest Substring with At Most K Distinct Characters

**문제:** 최대 K개의 서로 다른 문자를 포함하는 가장 긴 부분 문자열 찾기

```java
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) return 0;

        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);

            // 서로 다른 문자가 k개 초과: 윈도우 축소
            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0) {
                    charCount.remove(leftChar);
                }
                left++;
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
```

```kotlin
class Solution {
    fun lengthOfLongestSubstringKDistinct(s: String, k: Int): Int {
        if (k == 0) return 0

        val charCount = mutableMapOf<Char, Int>()
        var left = 0
        var maxLen = 0

        for (right in s.indices) {
            val c = s[right]
            charCount[c] = charCount.getOrDefault(c, 0) + 1

            while (charCount.size > k) {
                val leftChar = s[left]
                charCount[leftChar] = charCount[leftChar]!! - 1
                if (charCount[leftChar] == 0) {
                    charCount.remove(leftChar)
                }
                left++
            }

            maxLen = maxOf(maxLen, right - left + 1)
        }

        return maxLen
    }
}
```

---

## Sliding Window 시간/공간 복잡도

| 작업 | Brute Force | Sliding Window | 개선 |
|-----|-------------|----------------|------|
| 최장 부분 문자열 | O(n³) | O(n) | ✅✅✅ |
| 크기 k 윈도우 합 | O(n×k) | O(n) | ✅ |
| 최소 크기 부분 배열 | O(n²) | O(n) | ✅✅ |
| 공간 복잡도 | - | O(k) ~ O(n) | - |

---

## Sliding Window를 사용해야 하는 신호

✅ **이런 문제라면 Sliding Window를 고려하세요:**
1. **연속된 부분 배열/부분 문자열** 문제
2. "최장", "최단", "최대", "최소" 등의 **최적화** 키워드
3. "연속", "substring", "subarray" 등의 키워드
4. 특정 **조건을 만족하는 윈도우** 찾기
5. 배열 전체를 순회하며 **구간 정보**를 추적

❌ **Sliding Window가 적합하지 않은 경우:**
1. **불연속적인** 부분 집합(subsequence) 문제
2. 윈도우 크기가 너무 크거나 예측 불가능한 경우
3. 원소들이 **순서와 관계없이** 처리되는 경우

---

# 두 알고리즘의 비교

## 공통점

| 특성 | 설명 |
|-----|------|
| **시간 복잡도** | 둘 다 O(n) 달성 가능 |
| **공간 복잡도** | 보통 O(1) 또는 O(k) |
| **포인터 사용** | 인덱스/포인터로 범위 관리 |
| **최적화 목적** | Brute Force 개선 |

## 차이점

| 비교 항목 | Two Pointers | Sliding Window |
|---------|--------------|----------------|
| **주요 용도** | 두 개의 원소/값 찾기 | 연속된 부분 배열/문자열 찾기 |
| **포인터 개수** | 2개 (독립적 이동) | 2개 (윈도우 범위 표시) |
| **이동 방식** | 조건에 따라 각각 이동 | 윈도우 전체가 슬라이드 |
| **정렬 필요** | 종종 필요 (패턴 1) | 필요 없음 |
| **추적 대상** | 개별 원소 | 윈도우 내 전체 상태 |
| **전형적 문제** | Two Sum, Merge Arrays | Substring, Subarray |

### 관계도

```
Two Pointers
    │
    ├─ 양 끝에서 중앙으로 (Two Sum)
    ├─ 같은 방향 Fast & Slow (Remove Duplicates)
    └─ 두 배열 비교 (Merge Arrays)

Sliding Window
    │
    ├─ 고정 크기 (Max Average Subarray)
    └─ 가변 크기 (Longest Substring)
        └─ Two Pointers 기법 활용! ← 여기서 연결됨
```

**핵심 인사이트:**
- **Sliding Window는 Two Pointers의 특수한 형태**
- Sliding Window의 가변 크기 패턴은 Two Pointers를 사용하여 구현
- Two Pointers는 더 일반적인 개념, Sliding Window는 특정 문제 유형에 특화

---

## 두 알고리즘의 깊은 연관관계

### 왜 항상 함께 검색될까?

**핵심 이유: Sliding Window는 Two Pointers를 "구현 수단"으로 사용합니다.**

```
개념적 계층 구조:

┌─────────────────────────────────────┐
│    Two Pointers (구현 기법)          │  ← 더 일반적인 개념
│  두 개의 포인터로 범위를 관리         │
└────────────┬────────────────────────┘
             │
             ├─ 양 끝에서 중앙으로
             ├─ Fast & Slow
             ├─ 두 배열 비교
             │
             └─ 같은 방향으로 윈도우 형성
                        │
                        ▼
        ┌───────────────────────────┐
        │  Sliding Window (문제 패턴) │  ← 특정 문제 유형
        │  연속된 구간을 처리          │
        └───────────────────────────┘
```

### 1. 구현 레벨에서의 관계

**가변 크기 슬라이딩 윈도우 = Two Pointers (같은 방향)**

코드를 비교해보면 완전히 동일한 구조입니다:

#### Two Pointers 관점의 코드
```java
// Fast & Slow 포인터로 보는 관점
int slow = 0;  // 느린 포인터
int fast = 0;  // 빠른 포인터

while (fast < n) {
    // fast 포인터 이동
    // 조건 체크
    while (조건 위반) {
        slow++;  // slow 포인터 이동
    }
    fast++;
}
```

#### Sliding Window 관점의 같은 코드
```java
// 윈도우의 경계로 보는 관점
int left = 0;   // 윈도우 시작점
int right = 0;  // 윈도우 끝점

while (right < n) {
    // 윈도우 확장 (right 이동)
    // 조건 체크
    while (조건 위반) {
        left++;  // 윈도우 축소 (left 이동)
    }
    right++;
}
```

**👉 코드는 똑같습니다! 단지 관점이 다를 뿐입니다.**

---

### 2. 개념적 차이: "어떻게" vs "무엇을"

| 관점 | Two Pointers | Sliding Window |
|-----|-------------|----------------|
| **수준** | 구현 기법 (How) | 문제 패턴 (What) |
| **질문** | "어떻게 구현할까?" | "어떤 문제를 풀까?" |
| **초점** | 포인터 이동 방식 | 윈도우 내부 상태 |
| **추상화** | 낮음 (구현 상세) | 높음 (문제 패턴) |

#### 예시: 같은 코드, 다른 설명

**문제:** 중복 문자가 없는 최장 부분 문자열 찾기

```java
int left = 0;
Set<Character> seen = new HashSet<>();

for (int right = 0; right < s.length(); right++) {
    while (seen.contains(s.charAt(right))) {
        seen.remove(s.charAt(left));
        left++;
    }
    seen.add(s.charAt(right));
}
```

**Two Pointers 관점으로 설명:**
> "두 개의 포인터 `left`와 `right`를 같은 방향으로 이동시킵니다.
> `right`는 항상 앞으로 가고, 조건 위반 시 `left`를 증가시킵니다."

**Sliding Window 관점으로 설명:**
> "윈도우 `[left, right]`를 유지하며, 윈도우 내에 중복이 없도록 관리합니다.
> 윈도우를 확장하다가 중복 발견 시 윈도우를 축소합니다."

**👉 같은 코드지만, Sliding Window 설명이 문제의 본질을 더 잘 표현합니다!**

---

### 3. 언제 같고 언제 다를까?

#### 같을 때 (거의 동의어)

```
Two Pointers (같은 방향) ≈ Sliding Window (가변 크기)
```

- "연속된 부분 배열/문자열" 문제
- left, right 두 포인터 사용
- 조건에 따라 범위 조절

**예:**
- Longest Substring Without Repeating Characters
- Minimum Size Subarray Sum
- Longest Substring with K Distinct Characters

#### 다를 때 (명확히 구분)

**Two Pointers만 해당:**
```java
// 양 끝에서 중앙으로
int left = 0, right = n - 1;
while (left < right) {
    if (arr[left] + arr[right] == target) {
        // 답 찾음
    }
    // left++, right-- 둘 다 가능
}
```
→ 이건 Sliding Window가 아닙니다! (윈도우 개념이 없음)

**Sliding Window만 해당:**
```java
// 고정 크기 윈도우
for (int i = 0; i < n - k + 1; i++) {
    // [i, i+k-1] 윈도우 처리
    int windowSum = 0;
    for (int j = i; j < i + k; j++) {
        windowSum += arr[j];
    }
}
```
→ Two Pointers로 설명하기 어색함 (윈도우 크기가 고정)

---

### 4. 실전에서 어떻게 구분할까?

#### 문제를 보고 판단하는 기준

```
┌─────────────────────────────────────┐
│ 문제: 배열에서 합이 target인 두 수   │
│       (정렬됨)                       │
└──────────────┬──────────────────────┘
               │
      "두 개의 값" 찾기
               │
               ▼
        Two Pointers
      (양 끝에서 중앙)
```

```
┌─────────────────────────────────────┐
│ 문제: 중복 없는 최장 부분 문자열     │
└──────────────┬──────────────────────┘
               │
      "연속된 구간" + "최장"
               │
               ▼
        Sliding Window
         (가변 크기)
               │
         실제 구현은
               ▼
        Two Pointers
       (같은 방향)
```

#### 이름 붙이기 가이드

| 코드 패턴 | 문제 특성 | 부를 이름 |
|----------|---------|----------|
| `left=0, right=n-1, 양쪽 이동` | 정렬된 배열, 두 값 | **Two Pointers** |
| `slow=0, fast=0, 같은 방향` | 배열 재배치 | **Two Pointers (Fast&Slow)** |
| `left=0, right=0, 같은 방향` | 연속 구간 처리 | **Sliding Window** 또는 **Two Pointers** 둘 다 OK |
| `for (i=0; i<n-k)` 고정 크기 | k개씩 처리 | **Sliding Window** |

---

### 5. 검색에서 함께 나오는 이유

#### 학습 경로가 자연스럽게 연결됨

```
학습 단계:

1단계: Two Pointers 기초
   → 양 끝에서 중앙으로 (Two Sum)
   → 개념 이해 쉬움

2단계: Two Pointers 응용
   → 같은 방향으로 (Fast & Slow)
   → 조금 더 복잡

3단계: Sliding Window 도입
   → "아, 이게 Sliding Window 패턴이구나!"
   → Two Pointers를 활용한 더 구체적인 문제 패턴

4단계: 통합 이해
   → Sliding Window는 Two Pointers로 구현
   → 하지만 문제 유형이 명확함
```

#### 같이 검색되는 이유 정리

1. **구현이 동일** → 코드 예제가 겹침
2. **학습 순서** → Two Pointers 배운 후 Sliding Window 배움
3. **용어 혼용** → 사람마다 다르게 부름
4. **문제 태그** → LeetCode 등에서 둘 다 태그됨

---

### 6. 실전 조언: 어떻게 부를까?

#### 권장 사항

**인터뷰/설명할 때:**
```
"이 문제는 Sliding Window 패턴으로 접근하겠습니다.
가변 크기 윈도우를 유지하기 위해 Two Pointers를 사용하여,
left와 right 포인터로 윈도우를 조절하겠습니다."
```

**코드 주석:**
```java
// Sliding Window approach using two pointers
int left = 0;
int right = 0;
```

**문제 유형:**
- "연속 구간" → **Sliding Window**라고 부르기
- "두 값 찾기" → **Two Pointers**라고 부르기
- "배열 재배치" → **Two Pointers**라고 부르기

#### 틀린 것은 없습니다!

```
✅ "Sliding Window로 풀겠습니다"
✅ "Two Pointers로 풀겠습니다"
✅ "Two Pointers를 사용한 Sliding Window로 풀겠습니다"

❌ "이건 Two Pointers가 아니라 Sliding Window야!" (싸우지 마세요 😅)
```

---

### 7. 핵심 정리

#### 관계 요약

```
           Two Pointers
          (구현 기법)
               │
    ┌──────────┼──────────┐
    │          │          │
양 끝→중앙   Fast&Slow  같은 방향
              │          │
              │          └──→ 이것이
              │              Sliding Window!
              │              (문제 패턴)
         배열 재배치
```

#### 비유로 이해하기

```
Two Pointers = "젓가락 사용법"
  - 젓가락 두 개를 어떻게 움직일까?

Sliding Window = "음식 집는 방법"
  - 젓가락으로 반찬을 집는 특정 패턴
  - 젓가락(Two Pointers)을 사용하지만
  - 목적은 음식 집기(연속 구간 처리)
```

#### 결론

1. **Sliding Window는 Two Pointers의 하위 개념**
2. **구현은 같지만 문제 패턴이 다름**
3. **둘 다 알아야 하지만 명확히 구분할 필요는 없음**
4. **문제 유형으로 판단하면 자연스럽게 결정됨**

---

# 실전 적용 가이드

## 문제 분석 체크리스트

### Step 1: 문제 유형 파악

```
질문 1: 배열/문자열에서 "연속된" 부분을 다루는가?
  └ YES → Sliding Window 고려
  └ NO → Two Pointers 고려

질문 2: "두 개"의 원소나 값을 찾는가?
  └ YES → Two Pointers 고려

질문 3: 배열이 정렬되어 있는가?
  └ YES → Two Pointers (양 끝에서 중앙으로) 고려

질문 4: "최장", "최단", "최대", "최소" 등의 최적화 문제인가?
  └ YES & 연속 부분 → Sliding Window
  └ YES & 두 값 → Two Pointers
```

### Step 2: 패턴 선택

**Two Pointers 패턴 선택:**
```
정렬된 배열 + 두 값의 합/차 → 양 끝에서 중앙
배열 재배치/제거 → Fast & Slow
두 개의 정렬된 배열 → 각 배열에 포인터
```

**Sliding Window 패턴 선택:**
```
정확히 k개 원소 → 고정 크기
조건을 만족하는 최대/최소 → 가변 크기
```

### Step 3: 구현 템플릿 적용

**Two Pointers (양 끝) 템플릿:**
```java
int left = 0, right = n - 1;
while (left < right) {
    if (condition) {
        // 답 찾음
        return;
    } else if (needIncrease) {
        left++;
    } else {
        right--;
    }
}
```

**Sliding Window (가변) 템플릿:**
```java
int left = 0, result = 0;
Map/Set window = new HashMap/HashSet();

for (int right = 0; right < n; right++) {
    // 윈도우에 right 추가
    window.add(arr[right]);

    // 조건 위반 시 윈도우 축소
    while (violatesCondition) {
        window.remove(arr[left]);
        left++;
    }

    // 결과 갱신
    result = Math.max(result, right - left + 1);
}
```

---

# 문제 유형별 접근법

## 유형 1: 합(Sum) 관련 문제

| 문제 | 알고리즘 | 이유 |
|-----|---------|------|
| Two Sum (정렬됨) | Two Pointers | 양 끝에서 합 비교 |
| Subarray Sum Equals K | Prefix Sum | 연속이지만 음수 가능 |
| Max Sum of k Elements | Sliding Window | 고정 크기 윈도우 |
| Min Size Subarray Sum | Sliding Window | 가변 크기, 조건 만족 |

## 유형 2: 문자열 문제

| 문제 | 알고리즘 | 이유 |
|-----|---------|------|
| Longest Substring Without Repeating | Sliding Window | 연속, 조건 만족 최대 |
| Valid Palindrome | Two Pointers | 양 끝에서 비교 |
| Minimum Window Substring | Sliding Window | 가변 크기, 조건 만족 |
| Longest Palindromic Substring | Expand Around Center | 중심에서 확장 |

## 유형 3: 배열 재배치

| 문제 | 알고리즘 | 이유 |
|-----|---------|------|
| Remove Duplicates | Two Pointers | Fast & Slow |
| Move Zeroes | Two Pointers | Fast & Slow |
| Sort Colors | Two Pointers | 3-way partition |
| Merge Sorted Arrays | Two Pointers | 두 배열 비교 |

## 유형 4: 최적화 문제

| 문제 | 알고리즘 | 이유 |
|-----|---------|------|
| Container With Most Water | Two Pointers | 양 끝에서 최대 면적 |
| Max Consecutive Ones III | Sliding Window | K개 뒤집기, 최장 |
| Fruits Into Baskets | Sliding Window | 최대 2종류, 최장 |
| Longest Repeating Character | Sliding Window | K번 변경, 최장 |

---

# LeetCode 연습 문제 추천

## Two Pointers

### Easy
1. **Two Sum II** (LC 167) - 정렬된 배열에서 두 수의 합
2. **Valid Palindrome** (LC 125) - 팰린드롬 검사
3. **Move Zeroes** (LC 283) - 0을 끝으로 이동
4. **Remove Duplicates from Sorted Array** (LC 26) - 중복 제거

### Medium
5. **3Sum** (LC 15) - 세 수의 합이 0
6. **Container With Most Water** (LC 11) - 최대 물 용량
7. **Sort Colors** (LC 75) - 3색 정렬
8. **Merge Sorted Array** (LC 88) - 정렬된 배열 병합

### Hard
9. **Trapping Rain Water** (LC 42) - 빗물 가두기
10. **4Sum** (LC 18) - 네 수의 합

## Sliding Window

### Easy
1. **Maximum Average Subarray I** (LC 643) - 크기 k의 최대 평균
2. **Longest Substring with At Most Two Distinct** (LC 159) - 최대 2종류 문자

### Medium
3. **Longest Substring Without Repeating** (LC 3) - 반복 없는 최장 부분 문자열 ⭐
4. **Minimum Size Subarray Sum** (LC 209) - 최소 크기 부분 배열
5. **Longest Repeating Character Replacement** (LC 424) - K번 변경
6. **Max Consecutive Ones III** (LC 1004) - K개 뒤집기
7. **Fruit Into Baskets** (LC 904) - 최대 2종류

### Hard
8. **Minimum Window Substring** (LC 76) - 최소 윈도우 부분 문자열 ⭐
9. **Sliding Window Maximum** (LC 239) - 슬라이딩 윈도우 최대값
10. **Longest Substring with At Most K Distinct** (LC 340) - K종류 문자

---

# 핵심 정리

## Two Pointers 핵심
1. **두 개의 원소나 값**을 찾을 때 사용
2. 정렬된 배열에서 특히 유용
3. O(n²) → O(n)으로 최적화
4. In-place 연산 가능

## Sliding Window 핵심
1. **연속된 부분 배열/문자열** 문제
2. 윈도우 상태를 추적하며 슬라이드
3. O(n²) 또는 O(n³) → O(n)으로 최적화
4. HashMap/Set으로 윈도우 내 상태 관리

## 공통 팁
- ✅ 먼저 Brute Force로 생각한 후 최적화
- ✅ 경계 조건 (빈 배열, 크기 1 등) 체크
- ✅ 포인터 초기화와 이동 조건 명확히
- ✅ 오버플로우 주의 (합 계산 시)
- ✅ 문제를 작은 예제로 손으로 풀어보기

---

## 마무리

두 알고리즘 모두 **효율적인 범위 탐색**을 위한 강력한 도구입니다.

**학습 순서:**
1. Two Pointers Easy 문제로 기본 이해
2. Sliding Window 고정 크기로 개념 파악
3. Sliding Window 가변 크기로 확장
4. 다양한 변형 문제로 응용력 키우기

**인터뷰 팁:**
- 처음부터 완벽한 솔루션을 제시하지 말고
- Brute Force → Two Pointers → Sliding Window 순으로 **점진적 최적화** 과정을 보여주세요!

이 두 기법을 마스터하면 많은 배열/문자열 문제를 효율적으로 해결할 수 있습니다! 🚀

# Median of Two Sorted Arrays

## 문제 설명

두 개의 정렬된 배열 `nums1`과 `nums2`가 주어졌을 때, 두 배열을 합친 후의 **중앙값(median)**을 찾는 문제입니다.

전체 실행 시간 복잡도는 **O(log (m+n))** 이어야 합니다.

### 중앙값이란?
- 정렬된 배열에서 가운데 위치한 값
- 배열 길이가 **홀수**면: 정확히 가운데 값
- 배열 길이가 **짝수**면: 가운데 두 값의 평균

### 예제

**Example 1:**
```
Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: 합친 배열 = [1,2,3], 중앙값은 2
```

**Example 2:**
```
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: 합친 배열 = [1,2,3,4], 중앙값은 (2 + 3) / 2 = 2.5
```

**Example 3:**
```
Input: nums1 = [], nums2 = [1]
Output: 1.00000
```

### 제약 조건
- `nums1.length == m`
- `nums2.length == n`
- `0 <= m <= 1000`
- `0 <= n <= 1000`
- `1 <= m + n <= 2000`
- `-10^6 <= nums1[i], nums2[i] <= 10^6`

---

## 접근 방법 1: Brute Force (합치고 정렬)

### 아이디어
두 배열을 하나로 합친 후 정렬하고, 중앙값을 찾습니다.

### 알고리즘
1. nums1과 nums2를 하나의 배열로 합침
2. 합친 배열을 정렬
3. 배열 길이가 홀수면 중간 값, 짝수면 중간 두 값의 평균 반환

### 시간 복잡도
- **O((m+n)log(m+n))**: 정렬 시간

### 공간 복잡도
- **O(m+n)**: 합친 배열 저장

### 예제 실행: nums1 = [1,3], nums2 = [2]

```
1. 배열 합치기: [1, 3, 2]
2. 정렬: [1, 2, 3]
3. 전체 길이: 3 (홀수)
4. 중앙값: 인덱스 3/2 = 1, 값은 2
결과: 2.0
```

### 예제 실행: nums1 = [1,2], nums2 = [3,4]

```
1. 배열 합치기: [1, 2, 3, 4]
2. 정렬: [1, 2, 3, 4]
3. 전체 길이: 4 (짝수)
4. 중앙값: (인덱스 1의 값 + 인덱스 2의 값) / 2 = (2 + 3) / 2 = 2.5
결과: 2.5
```

### Java 코드

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        // 두 배열을 합침
        int[] merged = new int[m + n];
        System.arraycopy(nums1, 0, merged, 0, m);
        System.arraycopy(nums2, 0, merged, m, n);

        // 정렬
        Arrays.sort(merged);

        int totalLen = m + n;

        // 중앙값 계산
        if (totalLen % 2 == 1) {
            // 홀수: 중간 값
            return merged[totalLen / 2];
        } else {
            // 짝수: 중간 두 값의 평균
            return (merged[totalLen / 2 - 1] + merged[totalLen / 2]) / 2.0;
        }
    }
}
```

### Kotlin 코드

```kotlin
class Solution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        // 두 배열을 합침
        val merged = nums1 + nums2

        // 정렬
        merged.sort()

        val totalLen = merged.size

        // 중앙값 계산
        return if (totalLen % 2 == 1) {
            // 홀수: 중간 값
            merged[totalLen / 2].toDouble()
        } else {
            // 짝수: 중간 두 값의 평균
            (merged[totalLen / 2 - 1] + merged[totalLen / 2]) / 2.0
        }
    }
}
```

### 문제점
- 시간 복잡도가 O((m+n)log(m+n))로 요구사항 O(log(m+n))을 만족하지 못함
- 이미 정렬된 배열인데 다시 정렬하는 것은 비효율적
- 추가 메모리 O(m+n) 사용

---

## 접근 방법 2: Two Pointers (병합하며 중앙값 찾기)

### 아이디어
두 정렬된 배열을 병합(merge)하면서 중앙값 위치까지만 진행합니다.
실제로 전체 배열을 만들 필요 없이, 중앙값 위치의 값만 추적합니다.

### 알고리즘
1. 두 배열을 merge하듯이 두 포인터로 순회
2. 중앙값 위치(totalLen/2)까지만 진행
3. 홀수면 해당 위치 값, 짝수면 두 값을 기억해서 평균 계산

### 시간 복잡도
- **O(m+n)**: 두 배열을 한 번씩 순회

### 공간 복잡도
- **O(1)**: 추가 배열 없이 변수만 사용

### 예제 실행: nums1 = [1,3], nums2 = [2]

```
totalLen = 3, medianPos = 1 (0부터 시작)

i=0, j=0:
  nums1[0]=1 < nums2[0]=2 → 현재값=1, i=1, count=0

i=1, j=0:
  nums1[1]=3 > nums2[0]=2 → 현재값=2, j=1, count=1
  count == medianPos → 중앙값 = 2

결과: 2.0
```

### 예제 실행: nums1 = [1,2], nums2 = [3,4]

```
totalLen = 4, medianPos = 2, 짝수이므로 인덱스 1과 2의 값 필요

i=0, j=0:
  nums1[0]=1 < nums2[0]=3 → 현재값=1, i=1, count=0

i=1, j=0:
  nums1[1]=2 < nums2[0]=3 → 이전값=1, 현재값=2, i=2, count=1

i=2(범위 초과), j=0:
  이전값=2, 현재값=3, j=1, count=2
  count == medianPos → 중앙값 = (2 + 3) / 2 = 2.5

결과: 2.5
```

### Java 코드

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int totalLen = m + n;

        int i = 0, j = 0;
        int prev = 0, curr = 0;

        // 중앙값 위치까지만 진행
        for (int count = 0; count <= totalLen / 2; count++) {
            prev = curr;

            if (i < m && (j >= n || nums1[i] <= nums2[j])) {
                curr = nums1[i];
                i++;
            } else {
                curr = nums2[j];
                j++;
            }
        }

        // 홀수: 현재 값, 짝수: 이전 값과 현재 값의 평균
        if (totalLen % 2 == 1) {
            return curr;
        } else {
            return (prev + curr) / 2.0;
        }
    }
}
```

### Kotlin 코드

```kotlin
class Solution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val m = nums1.size
        val n = nums2.size
        val totalLen = m + n

        var i = 0
        var j = 0
        var prev = 0
        var curr = 0

        // 중앙값 위치까지만 진행
        for (count in 0..totalLen / 2) {
            prev = curr

            curr = when {
                i < m && (j >= n || nums1[i] <= nums2[j]) -> {
                    nums1[i].also { i++ }
                }
                else -> {
                    nums2[j].also { j++ }
                }
            }
        }

        // 홀수: 현재 값, 짝수: 이전 값과 현재 값의 평균
        return if (totalLen % 2 == 1) {
            curr.toDouble()
        } else {
            (prev + curr) / 2.0
        }
    }
}
```

### 개선점
- O(m+n)으로 시간 복잡도 개선
- 공간 복잡도 O(1)로 최적화
- 이미 정렬된 특성 활용

### 남은 문제점
- 여전히 O(log(m+n)) 요구사항을 만족하지 못함
- 선형 탐색을 하고 있음

---

## 접근 방법 3: Binary Search (최적 솔루션)

### 아이디어
**핵심 개념**: 중앙값은 배열을 두 부분으로 나눴을 때, 왼쪽 부분의 모든 값이 오른쪽 부분의 모든 값보다 작거나 같아야 합니다.

두 배열을 각각 적절한 위치에서 나눠서:
- **왼쪽 파티션**: nums1의 앞부분 + nums2의 앞부분
- **오른쪽 파티션**: nums1의 뒷부분 + nums2의 뒷부분

이때 왼쪽 파티션의 최대값 ≤ 오른쪽 파티션의 최소값이 되도록 하는 지점을 **이진 탐색**으로 찾습니다.

### 핵심 조건
1. `len(왼쪽 파티션) == len(오른쪽 파티션)` (또는 차이가 1)
2. `max(왼쪽 파티션) <= min(오른쪽 파티션)`

### 알고리즘
1. 더 짧은 배열(nums1)에서 이진 탐색 수행
2. nums1에서 i개, nums2에서 j개를 왼쪽 파티션에 포함
   - `j = (m + n + 1) / 2 - i`
3. 조건 확인:
   - `nums1[i-1] <= nums2[j]` AND `nums2[j-1] <= nums1[i]`
4. 조건이 만족되면 중앙값 계산:
   - 홀수: `max(nums1[i-1], nums2[j-1])`
   - 짝수: `(max(nums1[i-1], nums2[j-1]) + min(nums1[i], nums2[j])) / 2`

### 시간 복잡도
- **O(log(min(m, n)))**: 더 짧은 배열에서 이진 탐색

### 공간 복잡도
- **O(1)**: 추가 공간 사용 안 함

### 예제 실행: nums1 = [1,3], nums2 = [2]

```
m=2, n=1, totalLen=3
nums1에서 이진 탐색 (더 짧은 배열인 nums2를 nums1으로 스왑)
실제로: nums1=[2], nums2=[1,3]로 진행

이진 탐색:
left=0, right=1

시도 1: i=0 (nums1에서 0개를 왼쪽에)
  j = (1+2+1)/2 - 0 = 2 (nums2에서 2개를 왼쪽에)
  파티션:
    왼쪽: [] (nums1) + [1,3] (nums2)
    오른쪽: [2] (nums1) + [] (nums2)
  조건: nums2[j-1]=3 <= nums1[i]=2? NO
  → nums2의 왼쪽이 너무 큼, left = i+1 = 1

시도 2: i=1 (nums1에서 1개를 왼쪽에)
  j = 2 - 1 = 1 (nums2에서 1개를 왼쪽에)
  파티션:
    왼쪽: [2] (nums1) + [1] (nums2)
    오른쪽: [] (nums1) + [3] (nums2)
  조건: nums1[i-1]=2 <= nums2[j]=3? YES
        nums2[j-1]=1 <= nums1[i]=INF? YES

  totalLen=3 (홀수)
  중앙값 = max(nums1[i-1], nums2[j-1]) = max(2, 1) = 2

결과: 2.0
```

### 예제 실행: nums1 = [1,2], nums2 = [3,4]

```
m=2, n=2, totalLen=4

이진 탐색:
left=0, right=2

시도 1: i=1 (nums1에서 1개를 왼쪽에)
  j = (2+2+1)/2 - 1 = 1 (nums2에서 1개를 왼쪽에)
  파티션:
    왼쪽: [1] (nums1) + [3] (nums2)
    오른쪽: [2] (nums1) + [4] (nums2)
  조건: nums1[i-1]=1 <= nums2[j]=4? YES
        nums2[j-1]=3 <= nums1[i]=2? NO
  → nums2의 왼쪽이 너무 큼, left = i+1 = 2

시도 2: i=2 (nums1에서 2개를 왼쪽에)
  j = 2 - 2 = 0 (nums2에서 0개를 왼쪽에)
  파티션:
    왼쪽: [1,2] (nums1) + [] (nums2)
    오른쪽: [] (nums1) + [3,4] (nums2)
  조건: nums1[i-1]=2 <= nums2[j]=3? YES
        nums2[j-1]=-INF <= nums1[i]=INF? YES

  totalLen=4 (짝수)
  maxLeft = max(nums1[i-1], nums2[j-1]) = max(2, -INF) = 2
  minRight = min(nums1[i], nums2[j]) = min(INF, 3) = 3
  중앙값 = (2 + 3) / 2 = 2.5

결과: 2.5
```

### Java 코드

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // nums1이 더 짧은 배열이 되도록 보장
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int left = 0, right = m;

        while (left <= right) {
            // nums1에서 i개를 왼쪽 파티션에 포함
            int i = (left + right) / 2;
            // nums2에서 j개를 왼쪽 파티션에 포함
            int j = (m + n + 1) / 2 - i;

            // 경계값 처리를 위한 변수
            int maxLeft1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int minRight1 = (i == m) ? Integer.MAX_VALUE : nums1[i];

            int maxLeft2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int minRight2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            // 올바른 파티션을 찾았는지 확인
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // 홀수: 왼쪽 파티션의 최대값
                if ((m + n) % 2 == 1) {
                    return Math.max(maxLeft1, maxLeft2);
                }
                // 짝수: 왼쪽 최대값과 오른쪽 최소값의 평균
                else {
                    return (Math.max(maxLeft1, maxLeft2) +
                           Math.min(minRight1, minRight2)) / 2.0;
                }
            }
            // nums1의 왼쪽 파티션이 너무 큼
            else if (maxLeft1 > minRight2) {
                right = i - 1;
            }
            // nums1의 왼쪽 파티션이 너무 작음
            else {
                left = i + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}
```

### Kotlin 코드

```kotlin
class Solution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        // nums1이 더 짧은 배열이 되도록 보장
        if (nums1.size > nums2.size) {
            return findMedianSortedArrays(nums2, nums1)
        }

        val m = nums1.size
        val n = nums2.size
        var left = 0
        var right = m

        while (left <= right) {
            // nums1에서 i개를 왼쪽 파티션에 포함
            val i = (left + right) / 2
            // nums2에서 j개를 왼쪽 파티션에 포함
            val j = (m + n + 1) / 2 - i

            // 경계값 처리를 위한 변수
            val maxLeft1 = if (i == 0) Int.MIN_VALUE else nums1[i - 1]
            val minRight1 = if (i == m) Int.MAX_VALUE else nums1[i]

            val maxLeft2 = if (j == 0) Int.MIN_VALUE else nums2[j - 1]
            val minRight2 = if (j == n) Int.MAX_VALUE else nums2[j]

            // 올바른 파티션을 찾았는지 확인
            when {
                maxLeft1 <= minRight2 && maxLeft2 <= minRight1 -> {
                    // 홀수: 왼쪽 파티션의 최대값
                    return if ((m + n) % 2 == 1) {
                        maxOf(maxLeft1, maxLeft2).toDouble()
                    }
                    // 짝수: 왼쪽 최대값과 오른쪽 최소값의 평균
                    else {
                        (maxOf(maxLeft1, maxLeft2) +
                         minOf(minRight1, minRight2)) / 2.0
                    }
                }
                // nums1의 왼쪽 파티션이 너무 큼
                maxLeft1 > minRight2 -> {
                    right = i - 1
                }
                // nums1의 왼쪽 파티션이 너무 작음
                else -> {
                    left = i + 1
                }
            }
        }

        throw IllegalArgumentException("Input arrays are not sorted")
    }
}
```

### 장점
- O(log(min(m, n)))으로 문제 요구사항 만족
- 공간 복잡도 O(1)
- 가장 효율적인 솔루션

### 이해하기 위한 시각화

```
nums1 = [1, 3, 8, 9, 15]
nums2 = [7, 11, 18, 19, 21, 25]

목표: 두 배열을 합쳤을 때 왼쪽 절반과 오른쪽 절반로 나누기

올바른 파티션:
nums1: [1, 3, 8] | [9, 15]
nums2: [7, 11] | [18, 19, 21, 25]

왼쪽: [1, 3, 8, 7, 11] (5개)
오른쪽: [9, 15, 18, 19, 21, 25] (6개)

조건 확인:
- max(왼쪽) = max(8, 11) = 11
- min(오른쪽) = min(9, 18) = 9
- 11 > 9 이므로 잘못된 파티션!

올바른 파티션을 찾을 때까지 이진 탐색 계속...
```

---

## 접근 방법 4: Kth Element 방식

### 아이디어
중앙값을 찾는 것은 결국 k번째 작은 원소를 찾는 문제입니다.
- 전체 길이가 홀수면: (n+1)/2 번째 원소
- 전체 길이가 짝수면: n/2 번째와 n/2+1 번째 원소의 평균

재귀적으로 k를 줄여가며 이진 탐색처럼 문제를 축소합니다.

### 알고리즘
1. k번째 작은 원소를 찾는 재귀 함수 작성
2. 각 배열에서 k/2 위치의 값을 비교
3. 작은 쪽의 k/2개 원소는 확실히 k번째보다 작으므로 제외
4. k를 k - k/2로 줄이고 재귀 호출

### 시간 복잡도
- **O(log(m+n))**: 매번 k를 절반으로 줄임

### 공간 복잡도
- **O(log(m+n))**: 재귀 호출 스택

### Java 코드

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;

        if (total % 2 == 1) {
            // 홀수: k번째 원소 (0-indexed이므로 total/2)
            return findKth(nums1, 0, nums2, 0, total / 2 + 1);
        } else {
            // 짝수: k번째와 k+1번째 원소의 평균
            int mid1 = findKth(nums1, 0, nums2, 0, total / 2);
            int mid2 = findKth(nums1, 0, nums2, 0, total / 2 + 1);
            return (mid1 + mid2) / 2.0;
        }
    }

    // k번째 작은 원소 찾기 (1-indexed)
    private int findKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
        // nums1이 범위를 벗어나면 nums2에서 찾기
        if (start1 >= nums1.length) {
            return nums2[start2 + k - 1];
        }
        // nums2가 범위를 벗어나면 nums1에서 찾기
        if (start2 >= nums2.length) {
            return nums1[start1 + k - 1];
        }

        // k=1이면 두 배열의 첫 원소 중 작은 값
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        // 각 배열에서 k/2 위치의 값 가져오기
        int mid1 = start1 + k / 2 - 1 < nums1.length
                   ? nums1[start1 + k / 2 - 1]
                   : Integer.MAX_VALUE;
        int mid2 = start2 + k / 2 - 1 < nums2.length
                   ? nums2[start2 + k / 2 - 1]
                   : Integer.MAX_VALUE;

        // 작은 쪽의 k/2개를 제외하고 재귀
        if (mid1 < mid2) {
            return findKth(nums1, start1 + k / 2, nums2, start2, k - k / 2);
        } else {
            return findKth(nums1, start1, nums2, start2 + k / 2, k - k / 2);
        }
    }
}
```

### Kotlin 코드

```kotlin
class Solution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val total = nums1.size + nums2.size

        return if (total % 2 == 1) {
            // 홀수: k번째 원소
            findKth(nums1, 0, nums2, 0, total / 2 + 1).toDouble()
        } else {
            // 짝수: k번째와 k+1번째 원소의 평균
            val mid1 = findKth(nums1, 0, nums2, 0, total / 2)
            val mid2 = findKth(nums1, 0, nums2, 0, total / 2 + 1)
            (mid1 + mid2) / 2.0
        }
    }

    // k번째 작은 원소 찾기 (1-indexed)
    private fun findKth(
        nums1: IntArray, start1: Int,
        nums2: IntArray, start2: Int,
        k: Int
    ): Int {
        // nums1이 범위를 벗어나면 nums2에서 찾기
        if (start1 >= nums1.size) {
            return nums2[start2 + k - 1]
        }
        // nums2가 범위를 벗어나면 nums1에서 찾기
        if (start2 >= nums2.size) {
            return nums1[start1 + k - 1]
        }

        // k=1이면 두 배열의 첫 원소 중 작은 값
        if (k == 1) {
            return minOf(nums1[start1], nums2[start2])
        }

        // 각 배열에서 k/2 위치의 값 가져오기
        val mid1 = if (start1 + k / 2 - 1 < nums1.size) {
            nums1[start1 + k / 2 - 1]
        } else {
            Int.MAX_VALUE
        }
        val mid2 = if (start2 + k / 2 - 1 < nums2.size) {
            nums2[start2 + k / 2 - 1]
        } else {
            Int.MAX_VALUE
        }

        // 작은 쪽의 k/2개를 제외하고 재귀
        return if (mid1 < mid2) {
            findKth(nums1, start1 + k / 2, nums2, start2, k - k / 2)
        } else {
            findKth(nums1, start1, nums2, start2 + k / 2, k - k / 2)
        }
    }
}
```

---

## 비교 및 정리

| 접근 방법 | 시간 복잡도 | 공간 복잡도 | 장점 | 단점 |
|---------|-----------|-----------|-----|-----|
| Brute Force | O((m+n)log(m+n)) | O(m+n) | 이해하기 쉬움 | 비효율적, 요구사항 불만족 |
| Two Pointers | O(m+n) | O(1) | 공간 효율적 | 여전히 선형 시간 |
| Binary Search (Partition) | O(log(min(m,n))) | O(1) | 최적의 시간/공간 | 구현이 복잡함 |
| Kth Element | O(log(m+n)) | O(log(m+n)) | 직관적인 재귀 접근 | 재귀 스택 사용 |

### 추천 솔루션
- **인터뷰/실전**: 접근 방법 3 (Binary Search - Partition) ⭐
- **이해하기 쉬운**: 접근 방법 4 (Kth Element)
- **설명용**: 접근 방법 2 (Two Pointers) → 접근 방법 3

### 핵심 개념
1. **Binary Search on Answer**: 답을 직접 찾지 않고, 조건을 만족하는 파티션을 이진 탐색
2. **Partition 개념**: 배열을 두 부분으로 나누고, 각 부분의 크기와 경계값 관리
3. **중앙값의 정의**: 정렬된 배열을 반으로 나눴을 때의 경계값
4. **경계 조건 처리**: 배열의 시작/끝 인덱스를 벗어나는 경우 처리

### 실전 팁
1. **시작은 간단하게**: Brute Force나 Two Pointers부터 설명
2. **점진적 최적화**: "시간 복잡도를 더 줄일 수 있을까요?" 라는 질문에 Binary Search 도입
3. **더 짧은 배열 선택**: Binary Search를 더 짧은 배열에 적용하면 log(min(m,n)) 보장
4. **경계 조건 주의**: i=0, i=m, j=0, j=n인 경우 처리 필수
5. **Integer overflow 주의**: (left + right) / 2 대신 left + (right - left) / 2 사용 가능

### 왜 Binary Search가 작동하는가?

중앙값의 본질은 **배열을 두 개의 같은 크기 그룹으로 나누는 것**입니다.

```
합친 배열을 상상:
[작은 값들...] | [큰 값들...]
    왼쪽           오른쪽

조건:
1. 왼쪽 개수 == 오른쪽 개수 (또는 차이 1)
2. max(왼쪽) <= min(오른쪽)
```

nums1과 nums2를 각각 나누면:
```
nums1: [...i개...] | [...m-i개...]
nums2: [...j개...] | [...n-j개...]

i + j = (m + n) / 2 가 되도록 j를 결정
```

이진 탐색으로 조건 2를 만족하는 i를 찾으면 중앙값 계산 가능!

---

## 테스트 케이스

```java
// Test cases
[1,3], [2] → 2.0
[1,2], [3,4] → 2.5
[], [1] → 1.0
[2], [] → 2.0
[1,2,3,4,5], [6,7,8,9,10] → 5.5
[1,3,5,7,9], [2,4,6,8,10] → 5.5
[1,2], [1,2] → 1.5
[1], [2,3,4,5,6] → 3.5
```

### Edge Cases
- **한쪽이 빈 배열**: `[]`, `[1]` → 1.0
- **모든 원소가 한쪽에**: `[1,2,3]`, `[100,101,102]` → 3.0
- **중복 값**: `[1,1,1]`, `[1,1,1]` → 1.0
- **하나의 원소**: `[1]`, `[1]` → 1.0
- **음수 포함**: `[-5,-3,-1]`, `[0,2,4]` → -0.5

### 디버깅 팁
1. **파티션 확인**: i, j 값과 각 경계값(maxLeft1, minRight1 등) 출력
2. **인덱스 검증**: i-1, i, j-1, j가 배열 범위 내인지 확인
3. **중앙값 계산**: 홀수/짝수 케이스 각각 테스트
4. **스왑 확인**: nums1이 더 짧은지 확인

---

## 추가 학습 자료

### 관련 문제
- LeetCode 4: Median of Two Sorted Arrays (이 문제)
- LeetCode 295: Find Median from Data Stream
- LeetCode 480: Sliding Window Median
- LeetCode 378: Kth Smallest Element in a Sorted Matrix

### 확장 개념
- **분할 정복 (Divide and Conquer)**
- **이진 탐색의 응용 (Binary Search on Answer)**
- **Kth Element 문제**
- **Quick Select 알고리즘**
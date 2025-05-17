# Container With Most Water - Problem Documentation

## Problem Statement

You are given an integer array `height` of length `n`. There are `n` vertical lines drawn such that the two endpoints of the `ith` line are `(i, 0)` and `(i, height[i])`.

Find two lines that together with the x-axis form a container, such that the container contains the most water.

Return the maximum amount of water a container can store.

**Note**: You may not slant the container.

## Visual Representation

```
        |                   
        |                   |
        |   |               |
        |   |       |       |
    |   |   |       |       |
    |   |   |   |   |   |   |
---------------------------------
    1   8   6   2   5   4   8   3   7
    0   1   2   3   4   5   6   7   8
```

In this visualization of `[1,8,6,2,5,4,8,3,7]`, the maximum water container (shown in blue in the original problem) is formed between the lines at indices 1 and 8, with heights 8 and 7.

## Examples

### Example 1
```
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The maximum area is formed between heights 8 (at index 1) and 7 (at index 8).
Area = min(8, 7) * (8 - 1) = 7 * 7 = 49
```

### Example 2
```
Input: height = [1,1]
Output: 1
Explanation: The maximum area is formed between heights 1 (at index 0) and 1 (at index 1).
Area = min(1, 1) * (1 - 0) = 1 * 1 = 1
```

## Constraints
- `n == height.length`
- `2 <= n <= 10^5`
- `0 <= height[i] <= 10^4`

## Solution Approaches

### 1. Brute Force Approach

**Algorithm**:
1. Consider every possible pair of lines
2. Calculate the area between them
3. Return the maximum area

**Implementation**:
```java
public int maxAreaBruteForce(int[] height) {
    int maxArea = 0;
    for (int i = 0; i < height.length; i++) {
        for (int j = i + 1; j < height.length; j++) {
            int area = Math.min(height[i], height[j]) * (j - i);
            maxArea = Math.max(maxArea, area);
        }
    }
    return maxArea;
}
```

**Complexity Analysis**:
- Time Complexity: O(n²) where n is the length of the height array
- Space Complexity: O(1)

### 2. Two-Pointer Approach (Optimal)

**Algorithm**:
1. Initialize two pointers, one at the beginning and one at the end of the array
2. Calculate the area formed by the lines at these two pointers
3. Move the pointer pointing to the shorter line inward
4. Continue until the pointers meet
5. Return the maximum area found

**Implementation**:
```java
public int maxArea(int[] height) {
    int left = 0;
    int right = height.length - 1;
    int maxArea = 0;
    
    while (left < right) {
        int area = Math.min(height[left], height[right]) * (right - left);
        maxArea = Math.max(area, maxArea);
        
        if (height[left] < height[right])
            left++;
        else
            right--;
    }
    
    return maxArea;
}
```

**Complexity Analysis**:
- Time Complexity: O(n) where n is the length of the height array
- Space Complexity: O(1)

## Proof of Correctness for Two-Pointer Approach

### Key Insight
The area of any container is limited by the shorter of the two boundary lines. When we have two lines, moving the taller line inward can only result in a smaller container because:
1. The width decreases
2. The height is still limited by the shorter line (which we didn't move)

Therefore, to potentially find a larger container, we must move the pointer at the shorter line.

### Why We Don't Miss the Optimal Solution
Consider any two lines `height[i]` and `height[j]` where `i < j`:
- If we're currently looking at lines at positions `left` and `right` where `left ≤ i < j ≤ right`
- If `height[left] < height[right]`, we move `left` inward
- Any container formed with the original `left` and any position between `left+1` and `right` cannot be larger than what we've already calculated
- Similarly, if `height[left] ≥ height[right]`, moving `right` inward eliminates only suboptimal solutions

By this logic, our algorithm guarantees finding the optimal solution.

## Algorithm Visualization

Let's trace through the example: `height = [1,8,6,2,5,4,8,3,7]`

1. **Iteration 1**:
   - `left = 0`, `right = 8`
   - `height[left] = 1`, `height[right] = 7`
   - `area = min(1, 7) * (8 - 0) = 1 * 8 = 8`
   - `maxArea = 8`
   - Since `height[left] < height[right]`, increment `left`

2. **Iteration 2**:
   - `left = 1`, `right = 8`
   - `height[left] = 8`, `height[right] = 7`
   - `area = min(8, 7) * (8 - 1) = 7 * 7 = 49`
   - `maxArea = 49`
   - Since `height[left] > height[right]`, decrement `right`

3. **Iteration 3**:
   - `left = 1`, `right = 7`
   - `height[left] = 8`, `height[right] = 3`
   - `area = min(8, 3) * (7 - 1) = 3 * 6 = 18`
   - `maxArea` remains `49`
   - Since `height[left] > height[right]`, decrement `right`

4. **Iteration 4**:
   - `left = 1`, `right = 6`
   - `height[left] = 8`, `height[right] = 8`
   - `area = min(8, 8) * (6 - 1) = 8 * 5 = 40`
   - `maxArea` remains `49`
   - Heights are equal; we decrement `right`

5. **Iteration 5**:
   - `left = 1`, `right = 5`
   - `height[left] = 8`, `height[right] = 4`
   - `area = min(8, 4) * (5 - 1) = 4 * 4 = 16`
   - `maxArea` remains `49`
   - Since `height[left] > height[right]`, decrement `right`

The algorithm continues in this manner, but no larger area is found. The maximum area remains 49.

## Common Questions and Edge Cases

### Q: What if all heights are the same?
A: If all heights are the same, the container with the maximum width (first and last elements) will hold the most water.

### Q: What if there is only one very tall line and the rest are short?
A: The area is always limited by the shorter of the two lines. Even a very tall line paired with short lines will result in small areas.

### Q: Are there any special considerations for edge cases?
A: 
- Minimum array length is 2 (given in constraints)
- Heights can be zero, in which case the area would be zero
- The maximum area can never be negative

## Additional Optimization Considerations

### Memory Usage
The algorithm uses constant extra space regardless of input size.

### Implementation Details
1. In some languages, using a predefined `min()` and `max()` function may introduce overhead
2. Early termination is not possible since we need to check all possible pairs
3. There's no preprocessing step needed

## Related Problems
1. Trapping Rain Water (LeetCode 42)
2. Container With Most Water II (2D version)
3. Sliding Window Maximum (conceptually related)

## Summary
The Container With Most Water problem demonstrates how a clever algorithmic insight can reduce a seemingly O(n²) problem to O(n). The two-pointer approach leverages the following intuition: when limited by the shorter line, try to find a taller line to potentially increase the area. This approach eliminates suboptimal solutions without having to calculate their areas explicitly.
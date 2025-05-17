# Median of Two Sorted Arrays - Complete Guide

## Problem Overview
- **Problem**: Find the median of two sorted arrays
- **Companies**: Google, Apple, Amazon, Meta, Microsoft, TikTok, TCS, Goldman Sachs, Bloomberg, Uber, Facebook, LinkedIn, ByteDance
- **Difficulty**: Hard
- **Constraints**: 
  - Two sorted arrays `nums1` and `nums2` of sizes M and N
  - Can be of different sizes
  - Required time complexity: O(log(M+N))
  - Actual solution achieves: O(log(min(M,N))) - even better!

## Understanding Median

### Definition
- A median is a position where everything on the left equals everything on the right
- Equal number of elements on both sides

### Visual Examples

#### Odd Length Array
```
[1, 3, 5, 7, 9]
     ↑
   median
1 element left | 1 element right

Median = 5
```

#### Even Length Array
```
[2, 3, 4, 6]
   ↑  ↑
 middle elements
1 left | 1 right

Median = (3 + 4) / 2 = 3.5
```

## Why Binary Search?

### Traditional Binary Search
- **Input**: Sorted array, target value
- **Goal**: Find position of target
- **Time**: O(log n)

### Our Modified Binary Search
- **Input**: Two sorted arrays
- **Goal**: Find correct partition point
- **Time**: O(log(min(m,n)))
- **Key insight**: We binary search on partition positions, not values!

## Divide and Conquer Approach

### Classic Divide and Conquer
1. **Divide**: Split problem into smaller subproblems
2. **Conquer**: Solve subproblems recursively
3. **Combine**: Merge solutions

### Our Application
1. **Divide**: Partition total elements into left and right halves
2. **Conquer**: Use binary search to find correct partition
3. **Combine**: Calculate median from partition boundaries

## Detailed Algorithm Explanation

### Step 1: Choose the Smaller Array
```python
# Always ensure nums1 is smaller
if len(nums1) > len(nums2):
    return findMedianSortedArrays(nums2, nums1)
```
**Why?** Binary search efficiency - searching on smaller array gives better performance

### Step 2: Set Up Binary Search Bounds
```python
start = 0          # Minimum elements from nums1
end = len(nums1)   # Maximum elements from nums1
```

### Step 3: Partition Logic
For total elements T = M + N:
- Left partition size = (T + 1) // 2
- Right partition size = T - left partition size

If we take X elements from nums1, we need (left_size - X) elements from nums2.

## Visual Representation with Examples

### Example 1: Odd Total (M+N = 11)

```
nums1 = [1, 3, 5, 7]     (M = 4)
nums2 = [2, 4, 6, 8, 9]  (N = 5)
Total = 11 (odd)

Step 1: Left partition needs (11 + 1) // 2 = 6 elements

Step 2: Binary search on nums1
start = 0, end = 4

Iteration 1: 
x_part = (0 + 4) // 2 = 2
y_part = 6 - 2 = 4

nums1: [1, 3] | [5, 7]
nums2: [2, 4, 6, 8] | [9]

Left partition:  [1, 3, 2, 4, 6, 8]
Right partition: [5, 7, 9]

Check: 
- max(3, 8) ≤ min(5, 9)? → 8 ≤ 5? NO!
- 8 > 5, so we have too many elements from nums2

Iteration 2:
Update: start = 2 + 1 = 3
x_part = (3 + 4) // 2 = 3
y_part = 6 - 3 = 3

nums1: [1, 3, 5] | [7]
nums2: [2, 4, 6] | [8, 9]

Left partition:  [1, 3, 5, 2, 4, 6]
Right partition: [7, 8, 9]

Check:
- max(5, 6) ≤ min(7, 8)? → 6 ≤ 7? YES!
- min(7, 8) = 7 ≥ max(5, 6) = 6? YES!

Result: Median = max(5, 6) = 6
```

### Example 2: Even Total (M+N = 8)

```
nums1 = [1, 3, 5]       (M = 3)
nums2 = [2, 4, 6, 8, 9] (N = 5)
Total = 8 (even)

Step 1: Left partition needs (8 + 1) // 2 = 4 elements

Step 2: Binary search on nums1
start = 0, end = 3

Iteration 1:
x_part = (0 + 3) // 2 = 1
y_part = 4 - 1 = 3

nums1: [1] | [3, 5]
nums2: [2, 4, 6] | [8, 9]

Left partition:  [1, 2, 4, 6]
Right partition: [3, 5, 8, 9]

Check:
- max(1, 6) ≤ min(3, 8)? → 6 ≤ 3? NO!
- 6 > 3, so we have too many elements from nums2

Iteration 2:
Update: end = 1 - 1 = 0
x_part = (0 + 0) // 2 = 0
y_part = 4 - 0 = 4

nums1: [] | [1, 3, 5]
nums2: [2, 4, 6, 8] | [9]

Left partition:  [2, 4, 6, 8]
Right partition: [1, 3, 5, 9]

Check:
- max(-∞, 8) ≤ min(1, 9)? → 8 ≤ 1? NO!
- 8 > 1, so still too many from nums2

Iteration 3:
Update: start = 0 + 1 = 1
x_part = (1 + 0) // 2 = 0 (wait, start > end, continue...)
Actually, let's fix: start = 0, end = 3, try again properly

Let me recalculate:
x_part = (1 + 3) // 2 = 2
y_part = 4 - 2 = 2

nums1: [1, 3] | [5]
nums2: [2, 4] | [6, 8, 9]

Left partition:  [1, 3, 2, 4]
Right partition: [5, 6, 8, 9]

Check:
- max(3, 4) ≤ min(5, 6)? → 4 ≤ 5? YES!
- min(5, 6) = 5 ≥ max(3, 4) = 4? YES!

Result: Median = (max(3, 4) + min(5, 6)) / 2 = (4 + 5) / 2 = 4.5
```

## Detailed Binary Search Logic

### Search Space
- Not searching for a value, but for a partition position
- Each position represents how many elements to take from nums1
- Binary search narrows down the optimal partition

### Decision Making
```python
if x_left <= y_right and y_left <= x_right:
    # Found correct partition
elif x_left > y_right:
    # Too many elements from nums1, search left half
    end = x_part - 1
else:
    # Too few elements from nums1, search right half  
    start = x_part + 1
```

## Edge Cases and Infinity Values

### Why Use Infinity?
When partition reaches array boundaries:
- Beginning of array: Use -∞ (always ≤ any value)
- End of array: Use +∞ (always ≥ any value)

### Example with Edge Case
```
nums1 = [1, 2]
nums2 = [3, 4, 5, 6]
Total = 6 (even)

If x_part = 0 (take nothing from nums1):
nums1: [] | [1, 2]
nums2: [3, 4, 5] | [6]

Left partition boundaries: -∞ (from nums1), 5 (from nums2)
Right partition boundaries: 1 (from nums1), 6 (from nums2)

Check: max(-∞, 5) ≤ min(1, 6)? → 5 ≤ 1? NO!
```

## Complete Implementation with Comments

```python
def findMedianSortedArrays(nums1, nums2):
    # Ensure nums1 is the smaller array for optimization
    if len(nums1) > len(nums2):
        return findMedianSortedArrays(nums2, nums1)
    
    m, n = len(nums1), len(nums2)
    # Binary search bounds - how many elements to take from nums1
    start, end = 0, m
    
    while start <= end:
        # Divide: Calculate partition points
        x_part = (start + end) // 2  # Elements from nums1
        y_part = (m + n + 1) // 2 - x_part  # Elements from nums2
        
        # Handle edge cases with infinity
        x_left = float('-inf') if x_part == 0 else nums1[x_part - 1]
        x_right = float('inf') if x_part == m else nums1[x_part]
        y_left = float('-inf') if y_part == 0 else nums2[y_part - 1]
        y_right = float('inf') if y_part == n else nums2[y_part]
        
        # Conquer: Check if partition is valid
        if x_left <= y_right and y_left <= x_right:
            # Combine: Calculate median
            if (m + n) % 2 == 1:
                # Odd total: return max of left partition
                return max(x_left, y_left)
            else:
                # Even total: return average of middle two elements
                return (max(x_left, y_left) + min(x_right, y_right)) / 2
        
        # Divide and conquer decision
        elif x_left > y_right:
            # Too many elements from nums1
            end = x_part - 1
        else:
            # Too few elements from nums1
            start = x_part + 1
    
    return 0  # Should never reach here with valid input
```

## Visual Diagram of Algorithm Flow

```
           Start: nums1=[1,3], nums2=[2,4,6]
                        |
           Choose smaller array (nums1)
                        |
           Set search space: start=0, end=2
                        |
    ┌─────────────────────────────────────────┐
    │              Binary Search              │
    │                                         │
    │  Calculate x_part = (start + end) // 2  │
    │  Calculate y_part = total_left - x_part  │
    │                                         │
    │  Create partitions:                     │
    │  nums1: [left_x] | [right_x]           │
    │  nums2: [left_y] | [right_y]           │
    │                                         │
    │  Check condition:                       │
    │  left_x ≤ right_y AND left_y ≤ right_x │
    │                                         │
    │  ┌───────────┬─────────────────────────│
    │  │ Valid?    │ Invalid?                │
    │  │           │                         │
    │  ▼           ▼                         │
    │ Calculate   Adjust search bounds:       │
    │ median      - If left_x > right_y      │
    │            │   end = x_part - 1        │
    │            │ - Else                    │
    │            │   start = x_part + 1      │
    │            └─────────────────────────→┘
    └─────────────────────────────────────────┘
                        |
              Return median value
```

## Time and Space Complexity Analysis

### Time Complexity: O(log(min(M, N)))
- Binary search iterations: log(min(M, N))
- Each iteration: O(1) operations
- Total: O(log(min(M, N)))

### Space Complexity: O(1)
- Only storing partition indices and boundary values
- No additional arrays or recursion stack
- Constant extra space

## Advanced Insights

### Why This Works
1. **Maintained Order**: Both arrays are sorted, so elements within each array maintain order
2. **Cross-Partition Validation**: We only need to check boundaries between arrays
3. **Binary Search Convergence**: Each iteration eliminates half the search space

### Common Pitfalls
1. **Off-by-one errors** in partition calculations
2. **Edge case handling** when partitions are empty
3. **Confusion between indices and counts**
4. **Integer overflow** when calculating averages

### Interview Tips
1. **Draw diagrams** to visualize partitions
2. **Start with brute force** then optimize
3. **Handle edge cases** explicitly
4. **Test with both odd and even examples**
5. **Explain the binary search intuition** clearly

## Practice Problems
1. Start with arrays of equal length
2. Try arrays with different lengths
3. Handle edge cases (empty arrays, single elements)
4. Verify with manual calculations

This problem is considered one of the most challenging binary search problems because it requires thinking about binary search in a non-traditional way - searching for the correct partition rather than a specific value.
## References

- [Median of Two Sorted Arrays on LeetCode](https://leetcode.com/problems/median-of-two-sorted-arrays/?envType=problem-list-v2&envId=array)
- [Median of Two Sorted Arrays: 4 array interview @ google, apple, amazon, meta, microsoft, tiktok, tcs](https://youtu.be/LRM4qiHLYCE?si=XbEFUW3rzp8fz7Uy)

# 3Sum Problem (LeetCode 15) Documentation

## Problem Statement
Given an integer array `nums`, find all unique triplets that sum to zero. Each triplet must use three different positions in the array.

### Examples
1. Input: `[-1,0,1,2,-1,-4]` → Output: `[[-1,-1,2],[-1,0,1]]`
2. Input: `[0,1,1]` → Output: `[]` (no triplets sum to zero)
3. Input: `[0,0,0]` → Output: `[[0,0,0]]` (the only triplet sums to zero)

### Constraints
- 3 ≤ nums.length ≤ 3000
- -10^5 ≤ nums[i] ≤ 10^5

## Solution Approaches

### Brute Force Solution
- Use 3 nested loops to try all possible triplets (i, j, k)
- Time Complexity: O(n³)
- Space Complexity: O(1) excluding output space
- Too inefficient for large inputs and interviews

### Optimal Solution: Sort + Two Pointers
1. **Sort the array** (prerequisite)
2. **Fix one element** and use two-pointer technique for the rest
3. **Handle duplicates** using a HashSet or by skipping identical elements

## Detailed Algorithm Explanation

The key insight to solving this problem efficiently is recognizing that we can convert it into multiple "Two Sum" problems after sorting the array. Here's how it works:

1. **Sorting the array**: This crucial step enables us to use the two-pointer technique and easily skip duplicates.
   ```
   Original: [-1, 0, 1, 2, -1, -4]
   Sorted:   [-4, -1, -1, 0, 1, 2]
   ```

2. **Iteration**: For each position i (up to length-2), we fix nums[i] as our first element.

3. **Two-pointer search**: After fixing the first element, we use two pointers (left and right) to find pairs that, when combined with the fixed element, sum to zero.
   - Left starts at i+1
   - Right starts at the end of array

4. **Sum calculation and pointer movement**:
   - If sum == 0: We found a triplet! Add to result, then move both pointers
   - If sum < 0: Move left pointer right to increase sum
   - If sum > 0: Move right pointer left to decrease sum

5. **Deduplication**: Using a HashSet automatically eliminates duplicate triplets

## Visual Walkthrough

Let's walk through the example: `[-4, -1, -1, 0, 1, 2]`

### Iteration 1: Fix nums[0] = -4
- Target sum for the pair: 4
- Left=1, Right=5: nums[1] + nums[5] = -1 + 2 = 1 < 4 → Move left
- Left=2, Right=5: nums[2] + nums[5] = -1 + 2 = 1 < 4 → Move left
- Left=3, Right=5: nums[3] + nums[5] = 0 + 2 = 2 < 4 → Move left
- Left=4, Right=5: nums[4] + nums[5] = 1 + 2 = 3 < 4 → Move left
- No valid triplets with -4

### Iteration 2: Fix nums[1] = -1
- Target sum for the pair: 1
- Left=2, Right=5: nums[2] + nums[5] = -1 + 2 = 1 == 1 → Found triplet [-1,-1,2]
  - Move left and right
- Left=3, Right=4: nums[3] + nums[4] = 0 + 1 = 1 == 1 → Found triplet [-1,0,1]
  - Move left and right
- Left=4, Right=3: Left > Right → End search

### Iteration 3: Fix nums[2] = -1 (duplicate of previous)
- Target sum for the pair: 1
- Left=3, Right=5: nums[3] + nums[5] = 0 + 2 = 2 > 1 → Move right
- Left=3, Right=4: nums[3] + nums[4] = 0 + 1 = 1 == 1 → Found triplet [-1,0,1]
  - This is a duplicate of a previously found triplet, but the HashSet will eliminate it
  - Move left and right
- Left=4, Right=3: Left > Right → End search

### Iteration 4: Fix nums[3] = 0
- Target sum for the pair: 0
- Left=4, Right=5: nums[4] + nums[5] = 1 + 2 = 3 > 0 → Move right
- No more movement possible → End search

Final result: `[[-1,-1,2],[-1,0,1]]`

## Implementation Details

```java
public List<List<Integer>> threeSum(int[] nums) {
    if (nums == null || nums.length < 3) return new ArrayList<>();
    
    Arrays.sort(nums);  // Sort the array
    Set<List<Integer>> result = new HashSet<>();
    
    for (int i = 0; i < nums.length - 2; i++) {
        int left = i + 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            
            if (sum == 0) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                left++;
                right--;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }
    
    return new ArrayList<>(result);
}
```

### Deep Dive into the Implementation

1. **Input Validation**:
   ```java
   if (nums == null || nums.length < 3) return new ArrayList<>();
   ```
   - Guards against null inputs and arrays too small to form triplets
   - Returns an empty list as specified in the problem requirements

2. **Array Sorting**:
   ```java
   Arrays.sort(nums);
   ```
   - Sorts the array in non-decreasing order (O(n log n) time)
   - Critical for the two-pointer approach to work correctly
   - Enables easy skipping of duplicates (if implemented)

3. **Result Data Structure**:
   ```java
   Set<List<Integer>> result = new HashSet<>();
   ```
   - Uses a HashSet to automatically eliminate duplicate triplets
   - Each element in the set is a List<Integer> representing one triplet
   - HashSet operations are approximately O(1) on average

4. **Main Loop Structure**:
   ```java
   for (int i = 0; i < nums.length - 2; i++) {
   ```
   - Iterates through potential first elements of triplets
   - Stops at length-2 since we need at least 3 elements (i, left, right)

5. **Two-Pointer Setup**:
   ```java
   int left = i + 1;
   int right = nums.length - 1;
   ```
   - left starts immediately after the fixed element
   - right starts at the end of the array
   - This setup covers all possible pairs with the fixed element

6. **Sum Calculation and Comparison**:
   ```java
   int sum = nums[i] + nums[left] + nums[right];
   ```
   - Calculates the sum of the current triplet
   - Compared against target (0) to determine pointer movement

7. **Found Triplet Processing**:
   ```java
   if (sum == 0) {
       result.add(Arrays.asList(nums[i], nums[left], nums[right]));
       left++;
       right--;
   }
   ```
   - When sum is exactly 0, we've found a valid triplet
   - Both pointers are moved after adding to avoid duplicates
   - Even if duplicates are added, the HashSet handles deduplication

8. **Pointer Movement Logic**:
   ```java
   else if (sum < 0) {
       left++;
   } else {
       right--;
   }
   ```
   - If sum < 0: We need a larger sum, so move left pointer right
   - If sum > 0: We need a smaller sum, so move right pointer left
   - This strategy systematically explores all possible pairs

9. **Result Conversion**:
   ```java
   return new ArrayList<>(result);
   ```
   - Converts the HashSet to an ArrayList for the return type
   - Preserves all unique triplets while matching the required return type

### Understanding How Triplets Are Added to Results

In the solution code, when we find a triplet that sums to zero, we use this line:

```java
result.add(Arrays.asList(nums[i], nums[left], nums[right]));
```

Let's break down exactly what's happening:

1. **Creating the Triplet**:
   - `Arrays.asList(nums[i], nums[left], nums[right])` creates a new List containing three integers:
     - `nums[i]`: The first fixed element in our triplet
     - `nums[left]`: The second element (from the left pointer)
     - `nums[right]`: The third element (from the right pointer)

2. **Adding to Results Collection**:
   - `result.add(...)` adds this triplet to our collection of results
   - In our solution, `result` is a `Set<List<Integer>>` (specifically a HashSet)
   - This means we're storing Lists of Integers in a set data structure

3. **Concrete Example with Visual**:
   
   For array `[-4, -1, -1, 0, 1, 2]`, let's say we're at:
   - `i = 1` (value = -1)
   - `left = 3` (value = 0)
   - `right = 4` (value = 1)
   
   We find that `-1 + 0 + 1 = 0`, so we've found a valid triplet.
   
   ```
   [-4, -1, -1, 0, 1, 2]
        ^      ^  ^
        i     left right
   ```
   
   We create a new List: `[-1, 0, 1]`
   
   Our result set before: `[]`
   Our result set after: `[[-1, 0, 1]]`
   
   Then we move both pointers (left++ and right--).

4. **Handling Duplicates**:
   If we later find the exact same triplet again, the add operation won't change the HashSet because sets don't allow duplicates.
   
   For example, when we reach:
   - `i = 2` (value = -1, same value as previous i but different index)
   - `left = 3` (value = 0)
   - `right = 4` (value = 1)
   
   We find `-1 + 0 + 1 = 0`, which is another valid triplet.
   
   ```
   [-4, -1, -1, 0, 1, 2]
             ^  ^  ^
             i left right
   ```
   
   We try to add `[-1, 0, 1]` again, but:
   
   Our result set before: `[[-1, 0, 1]]`
   Our result set after: `[[-1, 0, 1]]` (no change, since it's a duplicate)

This is why using a HashSet is convenient for this problem - it automatically handles the requirement that "the solution set must not contain duplicate triplets."

### Key Code Insights
1. **Early termination check**: Return empty list if array is too small
2. **Sorting**: Essential prerequisite for the two-pointer approach
3. **HashSet for results**: Automatically handles deduplication
4. **Boundary management**: Only iterate to length-2 since we need at least 3 elements
5. **Pointer movement**: Strategically adjust left and right based on sum

### Understanding the `add` Operation

```java
result.add(Arrays.asList(nums[i], nums[left], nums[right]));
```

This line is crucial to the solution and deserves special attention:

1. **Creating a List**: `Arrays.asList(nums[i], nums[left], nums[right])` 
   - Creates a new fixed-size List containing the three integers that form our triplet
   - This List is immutable (cannot be resized)
   - The elements themselves can be modified if they were mutable objects (not applicable for primitive integers)

2. **Adding to the HashSet**: `result.add(...)`
   - The newly created List is added to our HashSet of results
   - When adding to a HashSet, Java uses the object's `hashCode()` and `equals()` methods
   - For Lists, `equals()` checks if two lists contain the same elements in the same order
   - The HashSet will reject the addition if an equivalent List already exists

3. **Duplicate Handling**:
   - If we find the triplet [-1, 0, 1] at one point and later find the same triplet again
   - The second attempt to add it will have no effect as it's considered a duplicate
   - This is why we can use a HashSet to automatically handle deduplication

4. **Return Type Conversion**: 
   - At the end, we convert the HashSet to an ArrayList with `new ArrayList<>(result)`
   - This is because the problem typically asks for the result as a List<List<Integer>>
   - This conversion maintains all unique triplets from our HashSet

To illustrate with our example array `[-4, -1, -1, 0, 1, 2]`, when we find triplet `[-1, -1, 2]`:

```java
// i=1, left=2, right=5
// nums[i]=nums[1]=-1, nums[left]=nums[2]=-1, nums[right]=nums[5]=2

// This creates a new List containing [-1, -1, 2]
List<Integer> triplet = Arrays.asList(nums[i], nums[left], nums[right]);

// This adds the triplet to our result set if it's not already there
result.add(triplet);
```

Later, when examining the second `-1` at index 2, we might find the same triplet again. The HashSet ensures it's only included once in our final result.

## Optimization: Skip Duplicates Without HashSet

While the HashSet provides automatic deduplication, we can optimize by skipping duplicates during the search:

```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    
    for (int i = 0; i < nums.length - 2; i++) {
        // Skip duplicates for first position
        if (i > 0 && nums[i] == nums[i-1]) continue;
        
        int left = i + 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            
            if (sum == 0) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                
                // Skip duplicates for second position
                while (left < right && nums[left] == nums[left+1]) left++;
                // Skip duplicates for third position
                while (left < right && nums[right] == nums[right-1]) right--;
                
                left++;
                right--;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }
    
    return result;
}
```

This approach is more efficient because:
1. It avoids the overhead of HashSet operations
2. It reduces unnecessary iterations by skipping duplicates early
3. It eliminates the need for conversion between collections

## Relationship with Other Problems

### Two Sum Problem
The 3Sum problem is a direct extension of the Two Sum problem. The relationship shows how understanding simpler problems can help solve more complex ones:

**Two Sum (Original)**:
```java
public int[] twoSum(int[] nums, int target) {
    HashMap<Integer, Integer> map = new HashMap<>(); // num -> index
    
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        
        map.put(nums[i], i);
    }
    
    throw new IllegalArgumentException("No solution found");
}
```

Two Sum uses a HashMap for O(n) time complexity but doesn't need to find all solutions. 3Sum adapts this pattern by fixing one element and then finding pairs, but uses the two-pointer approach since we need all solutions and the array is already sorted.

### Container With Most Water
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

Both problems use the two-pointer technique, but with different objectives:
- Container problem maximizes an area value
- 3Sum finds exact sum matches
- Both strategically move pointers to explore the solution space efficiently

### Median of Two Sorted Arrays
This problem uses binary search rather than two pointers but demonstrates another technique for working with sorted arrays:

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) {
        return findMedianSortedArrays(nums2, nums1);
    }
    
    int x = nums1.length;
    int y = nums2.length;
    int start = 0;
    int end = x;
    
    while (start <= end) {
        int partX = (start + end) / 2;
        int partY = (x + y + 1) / 2 - partX;
        
        int xleft = partX == 0 ? Integer.MIN_VALUE : nums1[partX - 1];
        int xright = partX == x ? Integer.MAX_VALUE : nums1[partX];
        int yleft = partY == 0 ? Integer.MIN_VALUE : nums2[partY - 1];
        int yright = partY == y ? Integer.MAX_VALUE : nums2[partY];
        
        if (xleft <= yright && yleft <= xright) {
            if ((x + y) % 2 == 0) {
                return ((double) Math.max(xleft, yleft) + Math.min(xright, yright)) / 2;
            } else {
                return Math.max(xleft, yleft);
            }
        } else if (xleft > yright) {
            end = partX - 1;
        } else {
            start = partX + 1;
        }
    }
    
    return 0;
}
```

The connection is less direct but demonstrates different search techniques for sorted arrays.

## My Additional Insights

### Pattern Recognition
The 3Sum problem exemplifies a critical skill in algorithm development: identifying and adapting patterns. By recognizing that 3Sum is essentially multiple Two Sum problems, we transform a potentially O(n³) solution into O(n²).

### Why Sort First?
Sorting might seem counterintuitive since it adds O(n log n) complexity, but it enables:
1. Easy deduplication by skipping adjacent duplicates
2. Efficient two-pointer approach that's impossible with unsorted data
3. Systematic reduction of search space

### Common Pitfalls
1. **Forgetting to sort**: The two-pointer approach requires sorted data
2. **Incorrect duplicate handling**: Duplicates need special handling to avoid missing or duplicating triplets
3. **Poor boundary management**: Off-by-one errors are common when setting up pointers
4. **Wrong pointer movement**: Moving both pointers is essential when a match is found

### Beyond 3Sum: kSum Problems
The technique generalizes to kSum problems:
- For k=2: Use HashMaps (unsorted) or two pointers (sorted)
- For k=3: Sort + fix first element + two pointers
- For k=4: Sort + fix two elements + two pointers
- For k>4: Sort + recursively reduce to k-1 problem

## Major Topics Covered

1. **Two-pointer technique** - A fundamental approach for traversing arrays efficiently
2. **Sorting as preprocessing** - Using sorting to enable more efficient algorithms
3. **Problem reduction** - Breaking down complex problems into simpler sub-problems
4. **Deduplication strategies** - Multiple ways to handle duplicate results
5. **Search space reduction** - Systematically eliminating impossible solutions
6. **Time and space complexity analysis** - Understanding algorithm efficiency
7. **Array manipulation** - Working with array indices and boundary conditions
8. **Algorithm generalization** - Extending techniques to related problem families

## Complexity Analysis
- **Time Complexity**: O(n²)
  - Sorting: O(n log n)
  - Two-pointer traversal: O(n²)
  - Overall: O(n log n + n²) = O(n²)
- **Space Complexity**: O(1) excluding the output space
  - O(n) if including result storage
## References

- [3sum on LeetCode](https://leetcode.com/problems/3sum/)
- [3 Sum (LeetCode 15) | Full solution with examples and visuals | Interview Essential](https://youtu.be/cRBSOz49fQk?si=sbnzzEdZ4_4uJ4T7)

## Conclusion
The 3Sum problem demonstrates the power of technique adaptation and problem reduction. By transforming a seemingly complex problem into a series of simpler ones, we create an elegant and efficient solution. This problem provides excellent practice for array manipulation, pointer management, and deduplication strategies that are applicable across many algorithmic challenges.

Understanding this problem helps develop the pattern-recognition skills essential for solving a wide range of algorithmic problems efficiently, making it a staple in coding interviews and algorithm study.

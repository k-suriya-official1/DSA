# History of Linked List

## Definition of Array:

An **array** is a collection of variables of the same data type stored in **contiguous memory locations**. Arrays allow **random access** to elements using an index.

## Problems with Array:

1.  **Fixed Size**: Once an array is declared, its size is fixed and cannot be changed during runtime. Example:

```java
int arr[100]; // Fixed size array

```

Alternatively, we can ask the user for the size at runtime, but even then, once allocated, the size remains constant.

2.  **Contiguous Memory Allocation**: Arrays require memory to be allocated in one continuous block. Example memory addresses:

```
2000, 2002, 2004, 2006, ...

```

This contiguous requirement makes resizing difficult or impossible without creating a new array and copying the data.

## Solution: Linked List

The **linked list** was introduced to solve the limitations of arrays, particularly the fixed size and contiguous memory requirement.

### Key Features:

-   **Dynamic memory allocation** at runtime.
-   Uses **random (non-contiguous) memory allocation**.
-   Works like a **chain**:
    -   1st block → Address 2000
    -   2nd block → Address 10005 (Memory locations are scattered, not sequential.)

### Structure of a Linked List Node:

In a linked list:

-   Each node is an **individual block**.
-   Each node has **two parts**:
    -   **Data** (stores the value)
    -   **Pointer** (stores the address of the next node)

C++ Implementation:

```c++
struct Node {
    int data;
    Node* next;
};

```

Pointers are **crucial** in linked list operations, as they manage the connection between nodes.

## Access Time Comparison: Array vs Linked List

### Accessing the 5th Element:

-   In **array**:
    -   We directly access `arr[5]`.
    -   This works in **constant time** (O(1)) due to contiguous memory.

```java
int x = arr[5]; // O(1)

```

Internally:

```
arr[5] = base_address + size_of_element * 5

```

-   In **linked list**:
    -   Direct access is **not possible**.
    -   We only store the address of the **head (first node)**. Storing addresses of all nodes is impractical.
    -   To access the 5th node, we must **traverse** from the head node one by one (O(n) time).

Example:

```cpp
Node* temp = head;
for (int i = 0; i < 5; i++) {
    temp = temp->next;
} // temp now points to 5th node -> O(n)

```

| Operation  | Array (O(1)) | Linked List (O(n)) |
|------------|-------------|--------------------|
| **Access** | Constant time | Linear traversal |
| **Insertion** | Costly (shift elements) | Fast at beginning, slow at middle/end |
| **Deletion** | Costly (shift elements) | Traversal needed |



### Why?

-   Arrays use **indexing** ➔ direct calculation
-   Linked lists use **pointers** ➔ traversal required

### Similar Time Complexity in Other Operations:

-   **Insertion**:
    -   Array: Inserting in the middle requires shifting elements (O(n)).
    -   Linked list: Inserting after a node is O(1), but finding the node takes O(n).
-   **Search**:
    -   Array: O(n) (unless sorted ➔ then binary search O(log n))
    -   Linked list: O(n)
-   **Deletion**:
    -   Array: Shifting elements required ➔ O(n).
    -   Linked list: Traversal + pointer update ➔ O(n).

## Solution for Linked List Problems: Dynamic Array

To combine the **random access power** of arrays with **dynamic resizing**, we use **dynamic arrays**.

In C++:

-   `std::vector` is a dynamic array.
-   It provides:
    -   **Dynamic resizing** at runtime
    -   **Contiguous memory allocation**
    -   **Constant time access** like arrays
    -   Automatically handles resizing and memory management internally.

```cpp
#include <vector>
std::vector<int> v;
v.push_back(10); // Adds element dynamically

```

### Why vectors are powerful?

-   They grow as needed (resize).
-   Provide **O(1)** access time.
-   Efficient for most general-purpose use cases.

## Summary:


| Feature             | Array    | Linked List | Vector (Dynamic Array) |
|---------------------|---------|-------------|------------------------|
| Size               | Fixed   | Dynamic     | Dynamic                |
| Memory Allocation  | Contiguous | Non-contiguous | Contiguous          |
| Access Time       | O(1)    | O(n)        | O(1)                   |
| Insertion/Deletion | Costly (O(n)) | Flexible but O(n) | Flexible (Amortized O(1) for push_back) |
| Pointer Usage      | No      | Yes         | No                      |
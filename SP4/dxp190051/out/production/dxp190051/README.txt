Short Project 4:

Team Members -
Andres Daniel Uriegas(adu170030)
Dhara Patel(dxp190051)

File included - BinaryHeap.java

Methods implemented -

BinaryHeap(int maxCapacity): Constructor class 
add(T x): Resizes the queue if needed, adds an element and return true/false upon successfully adding the element
remove(): calls poll() method and return its results
poll(): removes and returns the minimum element
min(): calls peek() and returns its results
peek(): returns the minimum element
perlocateUp(int index): moves elements based on the priority when adding a new element
getSmaller(int parent, int n): returns the smaller child(left or right) of the parent index
perlocateDown(int index): Moves elements based on the priority
resize(): resizes the queue

To test SP4 in IDE:
- Please run BinaryHeap main()
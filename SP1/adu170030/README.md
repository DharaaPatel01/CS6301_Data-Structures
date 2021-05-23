

Prerequisites

Before you continue, ensure you have met the following requirements:

* You have a IDE for Java.
* You are using JDK 15.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

SinglyLinkedList

The class SinglyLinkedList implements the basic operations for a linked list with the use of iterators.
The methods used are:
a. hasNext() - checks if the linked list has any element after the current pointer position and returns boolean value accordingly.
b. next() - returns the next element of the list
c. add() - adds an element at the end of the list
d. remove() - removes the current element from the list
e. unzip() - rearranges the list in order to bring the elements of even index in front, followed by elements of odd index.


DoublyLinkedList

This class inherits SunglyLinkedList and overrides its iterator and all the methods(hasNext(), next(), add(), remove(), unzip()) of that class.
Unlike SinglyLinkedList, every node here will have two pointers, previous and next.
Additionally, DoublyLinkedList has the following methods:
a. hasPrev() - checks if the linked list has any element after the current pointer position and returns boolean value accordingly.
b. prev() - returns the previous element of the list
c. add() - adds an element in between the list, right before the element returned by the next() call

Note: To test DLL, please run DoublyLinkedList main() in IDE.

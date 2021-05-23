Prerequisites

Before you continue, ensure you have met the following requirements:

You have a IDE for Java.
You are using JDK 15.

SP3
This class implements multiple usages of mergeSort. The class implements the following mergeSort takes:

mergeSort0(int[] arr): implements textbook version of mergeSort w/ array copy in each merge 
                  
mergeSort3(int[] arr): implements textbook version of mergeSort
		     : only copies array at initial function call and not in each merge

mergeSort4(int[] arr): implements threshold variable to run insertion sort or merge sort on given array
		     : runs insertion sort on given array if size of array is equal to or less than threshold value
		     : runs merge sort on given array if size of array is larger than array size

mergeSort6(int[] arr): implements combination of insertion sort and iterative merge sort to sort given array
		     : first divides array into sorted portions using insertion sort
		     : then uses iterative merge sort to sort the array portions

*To test SP3 in IDE:
- Please run SP3 main()
- Be sure to set values for args[] input to main() so that:
	1. the given array size isnt set to default
	2. and so choice for merge sort isnt random from 0-6

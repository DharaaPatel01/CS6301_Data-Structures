/** Sample starter code for SP3.
 *  @author rbk
 *  @author SA
 * shuffling the array after every trail takes lot of time
 * create worst case input and copy to input array after every trail
 * not the best option. but saves time
 * if space is an issue, use csgrads1.utdallas.edu server
 */

/**
 * Program implements a Merge Sort
 * It consists of following methods: merge0(), mergeNoCopy(), mergeSort0(), mergeSort3(), mergeSort4(), mergeSort6()
 * @author Andres Daniel Uriegas(adu170030)
 * @author Dhara Patel(dxp190051)
 * @param
 * */

package adu170030;
import java.util.Arrays;
import java.util.Random;

class SP3 {
	public static Random random = new Random();
	public static int numTrials = 50;
	public static int[] wcInput; //inp array
	public static int threshold = 4; //must be in power of 2 for case 6

	public static void main(String[] args) {
		int n = 128000000; //must be in power of 2 for case 6
		int choice = random.nextInt(7);
		if(args.length > 0) { n = Integer.parseInt(args[0]); }
		if(args.length > 1) { choice = Integer.parseInt(args[1]); }
		int[] arr = new int[n]; // initially inp. finally sorted
		int[] inp = arr;
		wcInput = new int[n];
		wcInitArray(wcInput, 0, wcInput.length);
		Timer timer = new Timer();

		switch(choice) {
			case 0:
				for(int i=0; i<numTrials; i++) {
					initArray(arr);
					mergeSort0(arr);
				}
				break;
			case 3:
				for(int i=0; i<numTrials; i++) {
					initArray(arr);
					mergeSort3(arr);
				}
				break;
			case 4:
				for(int i=0; i<numTrials; i++) {
					initArray(arr);
					mergeSort4(arr);
				}
				break;
			case 6:
				for(int i=0; i<numTrials; i++) {
					initArray(arr);
					mergeSort6(arr, inp);
				}
				break;
		}
		timer.end();
		timer.scale(numTrials);

		System.out.println("Choice: " + 0 + "\n" + timer);
	}

	/***
	 * Method: insertionSort()
	 * Sorts all the elements using insertion sort
	 * @param: arr: array to be sorted; p: start of the array; r: end of the array
	 * @return: null
	 */
	public static void insertionSort(int[] arr) {
		insertionSort(arr, 0, arr.length);
	}

	private static void insertionSort(int[] arr, int p, int r){
		for (int i = p+1; i < r; i++){
			int current = arr[i];
			int j = i-1;
			while (j >= p && arr[j] > current) {
				arr[j+1] = arr[j];
				j = j-1;
			}
			arr[j+1] = current;
		}
	}

	/***
	 * Method: mergeSort0()
	 * @param: arr: first array ;p: start of the array; q: middle of the array; r: end of the array
	 * @return: null
	 */
	public static void mergeSort0(int[] arr) {
		mergeSort0(arr, 0, arr.length-1);
	}

	private static void mergeSort0(int[] arr, int p, int r) {
		if (p < r) {
			int q = p + (r-p) / 2;
			mergeSort0(arr, p, q);
			mergeSort0(arr, q+1, r);
			merge0(arr, p, q, r);
		}
	}

	private static void merge0(int[] arr1, int p, int q, int r) {
		// Create array copy of arr1
		int[] arr2 = new int[arr1.length];
		for (int i = 0; i < arr2.length; i++){
			arr2[i] = arr1[i];
		}

		int i = p;
		int k = p;
		int j = q+1;
		while (i <= q && j <= r){
			if (arr2[i] <= arr2[j]){
				arr1[k++] = arr2[i++];
			} else {
				arr1[k++] = arr2[j++];
			}
		}
		while (i <= q) arr1[k++] = arr2[i++];
		while (j <= r) arr1[k++] = arr2[j++];
	}

	/***
	 * Method: mergeSort3()
	 * @param: arr: first array ; arr2: second array ;p: start of the array; q: middle of the array; r: end of the array
	 * @return: null
	 */
	public static void mergeSort3(int[] arr) {
		int[] arr2 = new int[arr.length];
		System.arraycopy(arr, 0, arr2, 0, arr.length);
		mergeSort3(arr, arr2, 0, arr.length-1);
	}

	private static void mergeSort3(int[] arr1, int[] arr2, int p, int r){
		if (p < r) {
			int q = p + (r - p) / 2;
			mergeSort3(arr2, arr1, p, q);
			mergeSort3(arr2, arr1, q+1, r);
			mergeNoCopy(arr1, arr2, p, q, r);
		}
	}

	private static void mergeNoCopy(int[] arr1, int[] arr2, int p, int q, int r){
		int i = p;
		int k = p;
		int j = q+1;
		while (i <= q && j <= r){
			if (arr2[i] <= arr2[j]){
				arr1[k++] = arr2[i++];
			} else {
				arr1[k++] = arr2[j++];
			}
		}
		while (i <= q) arr1[k++] = arr2[i++];
		while (j <= r) arr1[k++] = arr2[j++];
	}

	/***
	 * Method: mergeSort4()
	 * @param: arr: first array ; arr2: second array ;p: start of the array; r: end of the array
	 * @return: null
	 */
	public static void mergeSort4(int[] arr) {
		int[] arr2 = new int[arr.length];
		System.arraycopy(arr, 0, arr2, 0, arr.length);
		mergeSort4(arr, arr2, 0, arr.length-1);
	}

	private static void mergeSort4(int[] arr1, int[] arr2, int p, int r){
		if (r-p+1 < threshold) {
			insertionSort(arr1, p, r);
		}
		if (p < r) {
			int q = p + (r - p) / 2;
			mergeSort4(arr2, arr1, p, q);
			mergeSort4(arr2, arr1, q+1, r);
			mergeNoCopy(arr1, arr2, p, q, r);
		}
	}

	/***
	 * Method: mergeSort6()
	 * @param: arr: first array ; inp: second array
	 * @return: null
	 */
	public static void mergeSort6(int[] arr, int[] inp) {
		int[] arr2 = new int[arr.length];
		int length = arr.length;
		for (int j = 0; j < length; j = j + threshold){
			insertionSort(arr, j, j + threshold);
		}
		int[] t;
		System.arraycopy(arr, 0, arr2, 0, length);
		for (int i = threshold; i < length; i = 2*i) {
			for (int j = 0; j < length; j = j + 2 * i) {
				mergeNoCopy(arr2, inp, j, j + i - 1, j + 2 * i - 1);
			}
			t = inp; inp = arr2; arr2 = t;
		}
		if (!Arrays.equals(arr, inp)) System.arraycopy(inp, 0, arr, 0, length);
	}

	public static void wcInitArray(int[] arr, int start, int sz){
		if (sz == 1) { arr[start] = 1; return;}
		int lsz = sz/2;
		//int rsz = (sz%2 == 0 ? lsz : lsz+1);
		wcInitArray(arr, start, lsz);
		wcInitArray(arr, start + lsz, (sz%2 == 0 ? lsz : lsz+1));
		for ( int i = start; i < start + lsz; i++){
			arr[i] *= 2;
		}
		for ( int i = start + lsz; i < start + sz; i++){
			arr[i] = arr[i] * 2 - 1;
		}
	}

	// copy array inp to arr
	public static void initArray(int[] arr){
		System.arraycopy(wcInput, 0, arr, 0, arr.length);
	}

	/** Timer class for roughly calculating running time of programs
	 *  @author rbk
	 *  Usage:  Timer timer = new Timer();
	 *          timer.start();
	 *          timer.end();
	 *          System.out.println(timer);  // output statistics
	 */

	public static class Timer {
		long startTime, endTime, elapsedTime, memAvailable, memUsed;
		boolean ready;

		public Timer() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public void start() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public Timer end() {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime-startTime;
			memAvailable = Runtime.getRuntime().totalMemory();
			memUsed = memAvailable - Runtime.getRuntime().freeMemory();
			ready = true;
			return this;
		}

		public long duration() { if(!ready) { end(); }  return elapsedTime; }

		public long memory()   { if(!ready) { end(); }  return memUsed; }

		public void scale(int num) { elapsedTime /= num; }

		public String toString() {
			if(!ready) { end(); }
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
		}
	}

	/** @author rbk : based on algorithm described in a book
	 */


	/* Shuffle the elements of an array arr[from..to] randomly */
	public static class Shuffle {

		public static void shuffle(int[] arr) {
			shuffle(arr, 0, arr.length-1);
		}

		public static<T> void shuffle(T[] arr) {
			shuffle(arr, 0, arr.length-1);
		}

		public static void shuffle(int[] arr, int from, int to) {
			int n = to - from  + 1;
			for(int i=1; i<n; i++) {
				int j = random.nextInt(i);
				swap(arr, i+from, j+from);
			}
		}

		public static<T> void shuffle(T[] arr, int from, int to) {
			int n = to - from  + 1;
			Random random = new Random();
			for(int i=1; i<n; i++) {
				int j = random.nextInt(i);
				swap(arr, i+from, j+from);
			}
		}

		static void swap(int[] arr, int x, int y) {
			int tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		static<T> void swap(T[] arr, int x, int y) {
			T tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		public static<T> void printArray(T[] arr, String message) {
			printArray(arr, 0, arr.length-1, message);
		}

		public static<T> void printArray(T[] arr, int from, int to, String message) {
			System.out.print(message);
			for(int i=from; i<=to; i++) {
				System.out.print(" " + arr[i]);
			}
			System.out.println();
		}
	}
}



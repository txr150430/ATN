package ShortestPath;


//Ver 1.0:  Wec, Feb 3.  Initial description.

import java.util.Comparator;

public class BinaryHeap<T> implements PQ<T> {

 T[] pq;
 Comparator<T> c;
 private int size;

 /**
  * Build a priority queue with a given array q
  */
 BinaryHeap(T[] q, Comparator<T> comp) {
     pq = q;
     c = comp;
     size = q.length - 1;
     buildHeap();

 }

 /**
  * Create an empty priority queue of given maximum size
  */
 BinaryHeap(int n, Comparator<T> comp) {
     pq = (T[]) new Comparator[n + 1];
     c = comp;
     size = 0;
 }

 /**
  * Insert Element into the heap
  *
  * @param x
  */
 public void insert(T x) {
     add(x);
 }

 /**
  * Delete the Root Element from the heap which can be min or max based on
  * Comparator
  *
  * @return
  */
 public T deleteMin() {
     return remove();
 }

 /**
  * Peek the min or max element from the heap without removing it
  *
  * @return
  */
 public T min() {
     return peek();
 }

 /**
  * Insert Element into the heap
  *
  * @param x
  */
 public void add(T x) {

     if (size == pq.length - 1) {
         resize();
     }
     pq[++size] = x;
     percolateUp(size);

 }

 /**
  * Resize the array when the size is over
  */
 private void resize() {
     T[] oldArray = pq;
     pq = (T[]) new Comparator[oldArray.length * 2];
     System.arraycopy(oldArray, 1, pq, 1, size);
 }

 /**
  * Delete the Root Element from the heap which can be min or max based on
  * Comparator
  *
  * @return
  */
 public T remove() {
     T min = peek();
     assignIndex(1, size--);
     percolateDown(1);
     return min;
 }

 /**
  * Peek the min or max element from the heap without removing it
  *
  * @return
  */
 public T peek() {
     if (size == 0) {
         System.out.println("Heap has no elements");
         return null;
     }
     return pq[1];
 }

 /**
  * pq[i] may violate heap order with parent
  */
 void percolateUp(int index) {

     assignIndex(0, index);
     while (c.compare(pq[index / 2], pq[0]) > 0) {
         assignIndex(index, index / 2);
         index = index / 2;
     }
     assignIndex(index, 0);

 }

 /**
  * pq[i] may violate heap order with children
  */
 void percolateDown(int idx) {

     pq[0] = pq[idx];

     while (2 * idx <= size) {
         int child = 2 * idx;

         //get the minimum out of the left and right child
         if (child < size && c.compare(pq[child], pq[child + 1]) > 0) {
             child++;
         }

         //if the child element is less than the parent, then swap
         if (c.compare(pq[child], pq[0]) < 0) {
             assignIndex(idx, child);
         } else {
             break;
         }

         idx = child;
     }
     assignIndex(idx, 0);

 }

 /**
  * Create a heap. Precondition: none.
  */
 void buildHeap() {

     for (int i = size / 2; i > 0; i--) {
         percolateDown(i);
     }
 }

 //assign the index
 public void assignIndex(int srcIdx, int destIdx) {
     pq[srcIdx] = pq[destIdx];
 }

 /* sort array A[1..n].  A[0] is not used. 
    Sorted order depends on comparator used to buid heap.
    min heap ==> descending order
    max heap ==> ascending order
  */
 public static <T> void heapSort(T[] A, Comparator<T> comp) {
     /* to be implemented */

     BinaryHeap<T> heap = new BinaryHeap<>(A, comp);

     for (int i = heap.size; i >= 0; i--) {

         T temp = A[1];
         A[1] = A[i];
         A[i] = temp;

         heap.size--;
         heap.percolateDown(1);
     }
 }

 //check if the heap is empty or not
 /**
  * Check if the heap is empty or not
  *
  * @return
  */
 public boolean isEmpty() {
     return size == 0;
 }

 /**
  * Throws an error if a we try to reduce the key in Binary Heap
  *
  * @param x
  */
 public void decreaseKey(T x) {
     throw new UnsupportedOperationException();
 }

}
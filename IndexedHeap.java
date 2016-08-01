package ShortestPath;



import java.util.Comparator;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {

 /**
  * Build a priority queue with a given array q
  */
 IndexedHeap(T[] q, Comparator<T> comp) {
     super(q, comp);
 }

 /**
  * Create an empty priority queue of given maximum size
  */
 IndexedHeap(int n, Comparator<T> comp) {
     super(n, comp);
 }

 /**
  * restore heap order property after the priority of x has decreased
  */
 public void decreaseKey(T x) {
     percolateUp(x.getIndex());
 }

 //overriding the assignIndex method 
 public void assignIndex(int srcIdx, int destIdx) {
     super.assignIndex(srcIdx, destIdx);
     pq[srcIdx].putIndex(srcIdx);
 }

}
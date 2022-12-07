package project3;

import java.util.Iterator;
import java.lang.IllegalArgumentException;

/**
 * This class is a linear collection that supports elements insertion and
 * removal at three points: front, middle and back.
 * It has no fixed limits on the number of elements it contains.
 * It stores its values in a doubly linked list.
 * This class implements the Iterable interface and can return iterators
 * that iterate in sequential or reverse order
 * All push, peek and pop functions operate in O(1) time
 * 
 * @author Navarre Frede
 * 
 */

public class MDeque<E> implements Iterable<E> {
	
	/**
	 * This class is the doubly linked list that will hold 
	 * and connect the values in the MDeque Queue
	 * @author Navarre Frede 
	 * @param <E> specifies the type that the list stores
	 */
	private class Node<E>{
		E value;
		private Node<E> next;
		private Node<E> previous;
		
		public Node(E value, Node<E> previous){
			this.value = value;
			this.previous = previous;
			next = null;
		}
	}
	
	private int midpos;
	private int size;
	private Node<E> head;
	private Node<E> tail;
	private Node<E> middle;
	
	/**
	 * Constructor that creates and empty MDeque object
	 * All variables are initialized to empty linked list values.
	 */
	public MDeque() {
		midpos = size = 0;
		head = tail = middle = null;
	}
	
	/**
	 * Retrieves the back element of this MdDeque.
	 * @returns the value of the last element in linked list
	 * or null if pointer is null
	 */
	
	public E peekBack() {
		if(tail != null) {
			return tail.value;
		}
		return null;
	}
	
	/**
	 * Retrieves the front element of this MdDeque.
	 * @returns the value of the first element in linked list
	 * or null if pointer is null
	 */
	
	public E peekFront() {
		if(head != null) {
			return head.value;
		}
		return null;
	}
	
	/**
	 * Retrieves the middle element of this MdDeque.
	 * @returns the value of the middle element in linked list
	 * or null if pointer is null
	 */
	
	public E peekMiddle() {
		if(middle == null) {
			return null;
		}
		return middle.value;
	}
	
	/**
	 * Retrieves and removes the back element of this MDeque
	 * @returns the removed last element of the linked list
	 * or null if list is empty
	 */
	
	public E popBack() {
		if(tail == null) {
			return null;
		}
		E value = tail.value;
		tail = tail.next;
		size--;
		if(tail == null) {
			head = tail = middle = null;
			midpos = 0;
			size = 0;
			return value;
		}
		tail.previous = null;
		midpos--;
		if(midpos > (size -1) /2) {
			midpos--;
			middle = middle.previous;
		}
		if(midpos < (size -1) / 2) {
			midpos++;
			middle = middle.next;
		}
		return value;
	}
	
	/**
	 * Retrieves and removes the front element of this MDeque
	 * @returns the removed first element of the linked list
	 * or null if list is empty
	 */
	
	public E popFront() {
		if(head == null) {
			return null;
		}
		E value = head.value;
		head = head.previous;
		size--;
		if(head == null) {
			head = tail = middle = null;
			midpos = 0;
			size = 0;
			return value;
		}
		head.next = null;
		if(midpos > (size -1) /2) {
			midpos--;
			middle = middle.previous;
		}
		if(midpos < (size -1) / 2) {
			midpos++;
			middle = middle.next;
		}
		return value;
		
	}
	
	/**
	 * Retrieves and removes the middle element of the list
	 * @return the middle element of the list or null if the list is empty
	 */
	
	public E popMiddle() {
		boolean isTail = false;
		if(middle == null) {
			head = middle = tail = null;
			midpos = 0;
			size = 0;
			return null;
		}
		E value = middle.value;
		if(tail == middle) {
			isTail = true;
		}
		if(middle.previous != null && middle.next != null) {
			middle.previous.next = middle.next;
			middle.next.previous = middle.previous;
			middle = middle.next;

		}
		else if(middle.previous != null) {
			middle = middle.previous;
			middle.next = null;
			midpos = 0;
			if(isTail) {
				tail = middle;
			}
		}
		else if(middle.next != null) {
			middle = middle.next;
			middle.previous = null;
			midpos = 0;
			if(isTail) {
				tail = middle;
			}
		}
		else {
			head = middle = tail = null;
			size = 0;
			midpos = 0;
			return value;
			
		}
		
		size--;
		
		if(midpos < (size -1) / 2) {
			midpos++;
			middle = middle.next;
		}
		if(midpos > (size -1) /2) {
			midpos--;
			middle = middle.previous;
		}
		return value;
	}
	
	/**
	 * Reverses the order of the elements of the list
	 * Switches the head and tail and switches the 
	 * previous and next pointers
	 */
	
	public void reverse() {
		Node<E> current = head;
		head = tail;
		tail = current;
		
		while(current != null){
			Node<E> temp = current.previous;
			current.previous = current.next;
			current.next = temp;
			current = temp;
		}
		
		if(size % 2 == 0) {
			if(middle.next != null) {
				middle = middle.next;
			}
		}
	}
	/**
	 * Inserts the specified item at the front of this MDeque
	 * @param item is the element to add
	 * @throws IllegalArgumentException if item is null
	 */
	
	public void pushFront(E item) throws IllegalArgumentException{
		
		if(item == null) {
			throw new IllegalArgumentException("Cannot push a null item");
		}
		
		if(head == null) {
			head = middle = tail = new Node<E>(item, null);
			size = 1;
			midpos = 0;
			return;
		}
		
		head.next = new Node<E> (item, head);
		head = head.next;
		size++;
		if(midpos > (size -1) /2) {
			midpos--;
			middle = middle.previous;
		}
		if(midpos < (size -1) / 2) {
			midpos++;
			middle = middle.next;
		}
	}
	
	/**
	 * Inserts the specified item at the back of this MDeque
	 * @param item is the element to add
	 * @throws IllegalArgumentException if item is null
	 */
	
	public void pushBack(E item) throws IllegalArgumentException {
		if(item == null) {
			throw new IllegalArgumentException("Cannot push a null item");
		}
		if(head == null) {
			head = tail = middle = new Node<E>(item, null);
			size = 1;
			midpos = 0;
			return;
		}
		Node<E> temp = new Node<E> (item, null);
		tail.previous = temp;
		temp.next = tail;
		tail = temp;
		size++;
		midpos++;
		if(midpos > (size -1) /2) {
			midpos--;
			middle = middle.previous;
		}
		if(midpos < (size -1) / 2) {
			midpos++;
			middle = middle.next;
		}
	}
	
	/**
	 * Inserts the specified item at the middle of this MDeque
	 * @param item is the element to add
	 * @throws IllegalArgumentException if item is null
	 */
	
	public void pushMiddle(E item) throws IllegalArgumentException {
		if(item == null) {
			throw new IllegalArgumentException("Cannot push a null item");
		}
		if(head == null) {
			head = tail = middle = new Node<E> (item, null);
			size = 1;
			midpos = 0;
			return;
		}
		Node<E> temp = new Node<E>(item, null);
		
		if(size % 2 == 0) {
			temp.previous = middle;
			temp.next = middle.next;
			middle.next = temp;
			middle = temp;
			if(temp.next != null) {
				temp.next.previous = temp;
			}
		}
		else {
			temp.previous = middle.previous;
			temp.next = middle;
			middle.previous = temp;
			if(temp.previous != null) {
				temp.previous.next = temp;
			}
		}
		
		if(tail == middle && tail.previous != null) {
			tail = middle.previous;
		}
		size++;
		midpos++;
		if(midpos > (size -1) /2) {
			midpos--;
			middle = middle.previous;
		}
		if(midpos < (size -1) / 2) {
			midpos++;
			middle = middle.next;
		}

	}
	
	
	/**
	 * returns an iterator over the elements in this MDeque in reverse sequential order
	 * The elements will be returned in order of back to front
	 * @return and iterator over the elements in this MDeque in reverse sequence
	 */
	
	public Iterator<E> reverseIterator() {

		return new Iterator<E>() {
			
			private Node<E> current = tail;
			
			@Override
			public E next() {
				
				if(current != null) {
					E value = current.value;
					current = current.next;
					return value;
				}
				return null;
				
			}
			
			
			@Override
			public boolean hasNext() {
				if(current != null) {
					return true;
				}
				return false;
			}
		};
	}
	
	/**
	 * returns an iterator over the elements in this MDeque in proper sequential order
	 * The elements will be returned in order of front to back
	 * @return and iterator over the elements in this MDeque in proper sequence
	 */
	

	@Override
	public Iterator<E> iterator() {
		
		return new Iterator<E>(){
			
			private Node<E> current = head;
			
			@Override
			public E next() {
				
				if(current != null) {
					E value = current.value;
					current = current.previous;
					return value;
				}
				return null;
				
			}
			
			
			@Override
			public boolean hasNext() {
				if(current != null) {
					return true;
				}
				return false;
			}
		};
	}
	
	/**
	 * @returns the size of the list
	 */
	
	public int size() {
		return size;
	}
	
	/**
	 * returns a string representation of the list
	 * calls a recursive function that iterates over
	 * the list
	 */

	public String toString() {
		if(head == null) {
			return "[]";
		}
		Iterator<E> itr = this.iterator();
		return "[" + recToString(itr);
		
	}
	
	/**
	 * recursively works its way through the iterator adding
	 * the lists elements to a string
	 * @param itr is the iterator that it iterates through
	 * @returns a string that represents the elements in the list
	 */
	private String recToString(Iterator<E> itr) {
		String answer = "";
		if(itr.hasNext()) {
			answer += itr.next();
			if(itr.hasNext()) {
				return answer + ", " + recToString(itr);
			}
			return answer + "]";
		}
		else {
			return answer + "]";
		}
		
	}
	
}

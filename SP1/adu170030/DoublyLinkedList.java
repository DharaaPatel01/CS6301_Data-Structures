
//Andres Uriegas: adu170030
//Dhara Patel: dxp190051

package adu170030;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {

    /** Class Entry holds a previous and next node of the list */
    static class Entry<E> {
        E element;
        Entry<E> next;
        Entry<E> prev;

        Entry(E x, Entry<E> nxt, Entry<E> prv) {
            element = x;
            next = nxt;
            prev = prv;
        }
    }

    // Dummy header is used.  tail stores reference of tail element of list
    Entry<T> head, tail;

    public DoublyLinkedList() {
        head = new Entry<>(null, null, null);
        tail = head;
        size = 0;
    }

    public Iterator<T> iterator() { return new DLLIterator(); }

    protected class DLLIterator extends SLLIterator {

        Entry<T> cursor;

        DLLIterator() {
            cursor = head;
            ready = false; // Flag to know if item is ready to be removed
        }

        // Checks if list has anymore elements or not
        @Override
        public boolean hasNext() {
            return cursor.next != null;
        }

        // Returns the next element(one next to the cursor) of the list
        @Override
        public T next() {
            cursor = cursor.next;
            ready = true;
            return cursor.element;
        }

        // Removes the current element (retrieved by the most recent next())
        // Remove can be called only if next has been called and the element has not been removed
        @Override
        public void remove() {
            if(!ready) {
                throw new NoSuchElementException();
            }
            // Handle case when tail of a list is deleted
            cursor.prev.next = cursor.next;
            cursor.next.prev = cursor.prev;
            if(cursor == tail) {
                tail = cursor.prev;
            }
            cursor = cursor.prev;
            ready = false;  // Calling remove again without calling next will result in exception thrown
            size--;
        }

        //Checks if cursor is at head of the list
        public boolean hasPrev() {
            return cursor.prev != null;
        }

        // Returns the previous(one previous to the cursor) element of the list
        public T prev() {
            cursor = cursor.prev;
            if(cursor == head){
                ready = false;
            } else {
                ready = true;
            }
            return cursor.element;
        }

        //Adds an element in between the list, right before the element returned by the next() call
        public void add(T x) {
            Entry<T> entry = new Entry<>(x, null, null);

            entry.prev = cursor;
            entry.next = cursor.next;
            cursor.next = entry;
            cursor.next.prev = entry;

            next();
        }
    }

    // Adds the element to the end of the list
    @Override
    public void add(T x) {
        add(new Entry<>(x, null, tail));
    }

    public void add(Entry<T> entry) {
        //entry.prev = tail;
        tail.next = entry;
        tail = tail.next;
        size++;
    }

    Integer.class;

    // Rearrange the elements of the list by linking the elements at even index
    // followed by the elements at odd index. Implemented by rearranging pointers
    // of existing elements without allocating any new elements.
    @Override
    public void unzip() {
        if(size < 3) { // To few elements. No Change.
            return;
        }

        // Assigning head, tail, cursor and additional pointers
        //Assigning nodes to both next and previous pointers
        Entry<T> tail0 = head.next;
        //tail0.prev = head;
        Entry<T> head1 = tail0.next;
        Entry<T> tail1 = head1;
        //head1.next = tail1;
        //tail1.prev = head1;
        Entry<T> c = tail1.next;
        int state = 0;

        // Invariant: tail0 is the tail of the chain of elements with even index.
        // tail1 is the tail of odd index chain.
        // c is current element to be processed.
        // state indicates the state of the finite state machine
        // state = i indicates that the current element is added after taili (i=0,1).
        while(c != null) {
            if(state == 0) {
                tail0.next = c;
                tail0.next.prev = tail0;
                //c.prev = tail0;
                tail0 = c;
                c = c.next;
            } else {
                tail1.next = c;
                tail1.next.prev = tail1;
                //c.prev = tail1;
                tail1 = c;
                c = c.next;
            }
            state = 1 - state;
        }
        head1.prev = tail0;
        tail0.next = head1;
        tail1.next = null;
        // Update the tail of the list
        tail = tail1;
    }

    public void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        DoublyLinkedList<Integer> lst = new DoublyLinkedList<>();
        for(int i=1; i<=n; i++) {
            lst.add(Integer.valueOf(i));
        }
        lst.printList();

        Iterator<Integer> it = lst.iterator();
        DLLIterator dllIterator = (DLLIterator)it;
        Scanner in = new Scanner(System.in);
        whileloop:
        while(in.hasNext()) {
            int com = in.nextInt();
            switch(com) {
                case 1:  // Move to next element and print it
                    if (it.hasNext()) {
                        System.out.println(it.next());
                    } else {
                        break whileloop;
                    }
                    break;
                case 2:  // Remove element
                    it.remove();
                    lst.printList();
                    break;
                case 3:  // Remove element
                    ((DLLIterator) it).add(44);
                default:  // Exit loop
                    break whileloop;
            }
        }
        lst.printList();
        lst.unzip();
        lst.printList();
    }
}

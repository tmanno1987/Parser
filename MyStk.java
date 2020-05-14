package Parser;

public class MyStk<T>
{
   // private embedded Node class specific to the Stack Classes requirements.
   private class Node
   {
      private T info;
      private Node next;
   }
   
   // declare the node for top of stack and an integer for size of stack
   private Node top;
   private int size;
   
   // default constructor for generic stack class
   public MyStk()
   {
      top = null;
      size = 0;
   }
   
   // method that adds new data to top of the stack
   public void push(T obj)
   {
      // set Node temp to data held in the current top node
      Node temp = top;
      
      // re-initialize top node as a new node with obj in its info field
      top = new Node();
      top.info = obj;
      
      // aim tops next field to point to the address of the temp node
      top.next = temp;
      
      // increment the size field
      size++;
   }
   
   // method to remove data from top of the stack
   public T pop()
   {
      // declare element to hold data that will be returned
      T data = null;
      
      // check to see if top is null
      if ( top == null )
      {
         // error occurs due to lack of nodes to pop off stack
         System.err.println("Error: No data to pop off stack!");
      }
      else // data to be removed from top of stack
      {
         // retrieve data from the top nodes info field
         data = top.info;
         
         // set top node to the next nodes address
         top = top.next;
         
         // decrement the size field
         size--;
      }
      
      // return the data that was retrieved if any
      return data;
   }
   
   public int getSize()
   {
      // returns the size of the stack
      return size;
   }
   
   // method to delete all data held in stack at once
   public void popAll()
   {
      top = null;
      size = 0;
   }
   
   // method to view the item held on top of the stack
   public T peek()
   {
      //T data = top.info;
      return top.info;
   }
}
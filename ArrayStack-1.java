import java.util.EmptyStackException;

public class ArrayStack<T> implements StackInterface<T> {

	private T[] stack;
	private int size = 0;
	
	public ArrayStack() {
		stack = (T[]) new Object[0];
	}
	
	@Override
	public void push(T newEntry) {
		if (size >= stack.length) resize();
		stack[size] = newEntry; // TODO: Figure out generics
		size++;
	}
	
	private void resize() {
		if (stack.length == 0) {
			stack = (T[]) new Object[1];
		}
		T[] copy = (T[]) new Object[stack.length];
		
		for (int i = 0; i < stack.length; i++) {
			copy[i] = stack[i];
		}
		
		stack = (T[]) new Object[copy.length*2];
		for (int i = 0; i < copy.length; i++) {
			stack[i] = copy[i];
		}
	}

	@Override
	public T pop() {
		if (this.isEmpty()) throw new EmptyStackException();
		
		T ret = (T) stack[--size];
		stack[size] = null;
		return ret;
	}

	@Override
	public T peek() {
		if (this.isEmpty()) throw new EmptyStackException();
		
		return (T) stack[size-1];
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public void clear() {
		stack = (T[]) new Object[0];
		size = 0;
	}

}
import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 第一种修改方式，将所有Object替换成E，并在构造函数中加入一处强转，否则构造函数会由于数组和泛型不兼容
 * (见item28)而报error. 修改之后再编译，原先error变为了warning, 这是因为编译器无法保证其安全性。
 * 但是，该数组对象存于私有域中，仅能通过{@link #push(Object)}储存，而push方法可以保证其参数类型合法，
 * 这样我们就能够证明这里的unchecked cast是没有问题的，可以使用@SuppressWarning注解放心去掉，
 * 并附上注释说明解释原因，但该注解的作用域应尽可能地小。
 *
 * 本例中，@SuppressWarnings注解放在了构造方法上。
 *
 * @author LightDance
 * @date 2018/9/24
 */
public class ModifiedStack<E> {

    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * 该数组对象存于私有域中，仅能通过{@link #push(Object)}储存, 而push方法可以保证其E实例参数合法，
     * 但运行时其
     */
    @SuppressWarnings("unchecked")
    public ModifiedStack() {
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        E result = elements[--size];
        elements[size] = null; // Eliminate obsolete reference
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}

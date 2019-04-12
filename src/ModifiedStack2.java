import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 另一种修改方式，将存储元素的private型成员变量从E[]改回Object[], 这样可以避免构造方法中，
 * 类似于{@link ModifiedStack}出现的问题，但p{@link #pop()}中会出现error.
 * 于是需要我们在该方法中强转需要pop的元素。
 *
 * 但强转后，由于E是不可重用类型(non-reifiable),编译器无法在运行时检测强转的安全性，
 * 于是有warning出现。但同样的，我们可以轻松地证明这个warning是安全没问题的，然后用注解+注释说明抑制它。
 * (根据@SuppressWarnings作用域尽可能小的原则，这次我们可以将它放在局部变量声明处)
 *
 * @author LightDance
 * @date 2018/9/24
 */
public class ModifiedStack2<E> {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public ModifiedStack2() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        //这里不强转会提示error, 强转后提示warning.由于push方法要求参数必须为E型实例，因此保证安全
        @SuppressWarnings("unchecked")
        E result = (E) elements[--size];
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

/**
 * 通常借助JDK所提供的泛型方法对自己的类或类成员声明进行泛化并不复杂。自己编写泛型类可能稍微难一点，
 * 但认真学一学受益良多。
 *
 * 例如曾经在item7里面提过的stack的例子{@link OutdatedStack},这个类应当用泛型进行泛化，
 * 以避免各种强转带来的的繁琐与风险。因此我们基于它进行修改，即在兼容非泛化版本的前提下将它参数化。
 *
 * 第一步是向其声明中设置类型参数，表示"元素element"时通常用E代替；
 * 第二步是将所有使用Object的地方用类型参数(E)代替，然后尝试编译程序。这两个是两种不同的修改方式
 * {@link ModifiedStack}，{@link ModifiedStack2}
 *
 * 上面两种方式各有利弊。第一种方式显然更具可读性，清晰表明其只含有E型实例；而且多次取出其中实例时，
 * 这种方式仅需要构造函数中一次强转，更加简洁，于是这种方式的使用更加广泛。
 * 但是，第一种方式同时会造成堆污染(heap pollution, 见item32)：在实际运行时，
 * 数组的类型往往与其编译时的类型不符(除非E恰好为Object型)，这一点对于一部分程序员来讲十分别扭，
 * 所以他们宁可选择第二种方式。尽管在这里，堆污染是无害的。
 *
 * 无论以上哪一种方式，都可以按照如{@link #main(String[])}的模式进行使用，这里使用
 * {@link String#toUpperCase()}方法时无需对每个pop出来的元素强转成String型，
 * 泛型机制会自动将E全部替换成String.
 *
 * 可能会有疑问，item28里面不是说鼓励优先考虑List存储元素以解决泛型与数组的冲突吗，这里为什么不用List？
 * 首先Java本身并不像支持原生的int,double那样支持List，因此List及其子类大多需要在数组的基础上实现；
 * 其他一些例如{@link java.util.HashMap}之类的泛型，也会用数组以提高性能。
 *
 * 大多数泛型对其类型参数没有限制，可以创建诸如List<Object>,List<String[]>,List<List<int[]>>
 * 等各种类型的对象，但类型参数不可以是基本类型，也就是说虽然List<int[]>是合法的，但List<int>就不行，
 * 会导致编译时提示error. 这也是泛型机制中的局限性之一，不过可以通过基本类型的装箱类型Integer，
 * Double等解决它。
 *
 * 有些泛型的类型参数也可以加以限制，比如这里：{@link java.util.concurrent.DelayQueue}，
 * 通过DelayQueue<E extends Delayed>的声明方式，将类型参数限制为Delayed的子类。
 * 这样就允许其实现使用{@link java.util.concurrent.Delayed}的方法，无需显式转换。
 * 这里的E被称为有界类型参数。
 *
 * 注意，由于任何类型都是其本身的子类，因此创建DelayedQueue<Delayed>也是合法的。
 *
 * 总之，泛型要比需要手动转换的类更加安全、易用，因此当发现使用泛型更加合适的时候，
 * 要尽早将非泛型类转换成泛型类，但注意得在兼容原有代码、不妨碍先前代码允许的前提下,
 * 参照上述步骤与样例修改它们，让编写的类更方便使用。
 *
 *
 *
 * @author LightDance
 * @date 2018/9/23
 */
public class FavorGenericTypes {

    public static void main(String[] args) {
        ModifiedStack<String> stack = new ModifiedStack<>();
        for (String arg:args) {
            stack.push(arg);
        }
        while (!stack.isEmpty()){
            System.out.println(stack.pop().toUpperCase());
        }
    }
}

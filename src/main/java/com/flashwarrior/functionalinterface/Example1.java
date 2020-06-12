package com.flashwarrior.functionalinterface;

import lombok.Data;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * 函数式编程示例
 */
public class Example1 {

    //Lambda

    //不包含参数，用()表示没有参数
    //表达式主体只有一个语句，可以省略{}
    Runnable helloWorld = () -> System.out.println("hello World");

    Runnable helloWorlds = () -> {
        System.out.println("hello World1");
        System.out.println("hello World1");
        System.out.println("hello World1");
    };

    Consumer<String> infoConsumer = msg -> System.out.println(msg + "hello");

    BinaryOperator<Integer> add1 = (Integer i, Integer j) -> i + j;

    BinaryOperator<Integer> add2 = Integer::sum;


    //Java内置常用函数

    //Predicate<T> 判断型：类似于if的作用
    //返回类型：Boolean
    Predicate<String> isAdmin = "admin"::equals;


    //Consumer<T> 消费型
    //返回类型：void
    Consumer<String> print = msg -> System.out.println(msg + "kkk");


    //Function<T, R> 功能型
    Function<Long, String> toStr = value -> String.valueOf(value);


    //Supplier<T> 提供型
    Supplier<Date> now = () -> new Date();


    //2.3 方法引用
    //Lambda表达式一种常用方法就是直接调用其他方法，凡是使用Lambda表达式的地方都可以使用方法引用
    //方法引用的标准语法为ClassName::methodName，虽然这是一个方法，但不需要在后面加括号
    //因为这里并不直接调用该方法

    Function<User, String> f1 = user -> user.getName();
    Function<User, String> f2 = User::getName;

    Supplier<User> s1 = () -> new User();
    Supplier<User> s2 = User::new;

    Function<Integer, User[]> sa1 = count -> new User[count];
    Function<Integer, User[]> sa2 = User[]::new;

    //方法引用主要分为如下几种类型：
    //静态方法引用：className::methodName
    //实例方法引用：instanceName::methodName
    //超类实体方法引用：super::methodName
    //构造函数方法引用：className::new
    //数组构造方法引用：ClassName[]::new


    //2.4 类型推断
    //可以省略Lambda表达式中所有参数类型，Javac会根据Lambda表达式式上下文信息自动推断出参数的正确类型
    //但是由于Lambda表达式与函数名无关，只与方法签名有关，因此会出现类型推断失效的情况，
    //这时需要手工类型转换帮助javac进行正确的判断

    //Supplier<String>, Callable<String> 具有相同的方法签名
    private void print(Supplier<String> stringSupplier) {
        System.out.println("Hello " + stringSupplier.get());
    }

    private void print(Callable<String> stringCallable) {
        try {
            System.out.println("Hello " + stringCallable.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    {
        //Error 因为两个print都满足
//        print(() -> "World");
        print((Supplier<String>) () -> "World");
        print((Callable<String>) () -> "World");
    }


    //Stream：从外部迭代到内部迭代
    //Stream是用函数式编程方式在集合类上进行复杂操作的工具

    //Java程序员使用集合时，一个通用模式就是在集合上进行迭代，然后处理返回的每一个元素
    //尽管这种操作可行但存在几个问题：
    //大量的样本代码、模糊了程序本意、串行化执行

    //常见集合遍历：外部迭代
    //常见写法一：
    public void printAll(List<String> msgList) {
        for (int i = 0; i < msgList.size(); i++) {
            String msg = msgList.get(0);
            System.out.println(msg);
        }
    }

    //常见写法二：
    public void printAll2(List<String> msgList) {
        Iterator<String> iterator = msgList.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }
    }

    //常见写法三：
    public void printAll3(List<String> msgList) {
        for (String s : msgList) {
            System.out.println(s);
        }
    }


    //内部迭代
    public void printAll4(List<String> msgList) {
        msgList.stream().forEach(System.out::println);
    }


    //惰性求值 VS 及早求值
    //stream中存在两类方法，不产生值的方法称为惰性方法；从stream中产生值的方法叫做及早求值方法
    //返回值是stream，那么就是惰性方法；如果返回值是另一个值或为空，那么就是及早求值方法
    //惰性方法返回的stream对象不是一个新的集合，而是创建新集合的配方，stream本身不会做任何迭代操作
    //只有调用及早求值方法时，才会开始真正的迭代
    //这点与Builder模式有共通之处，惰性方法负责对Stream进行装配(设置builder属性)，
    //调用及早求值方法时(调用builder的build方法)，按照之前的装配信息进行迭代操作


    //常见的stream操作
    //collect(toList())：及早求值方法，由stream里面的值生成一个列表
    public List<String> getNameListByUserList(List<User> users) {
        return users.stream().map(User::getName).collect(Collectors.toList());
    }


    //count、max、min
    //及早求值方法：求总数、最大值和最小值
    public Long getCount(List<User> users) {
        return users.stream().filter(Objects::nonNull).count();
    }

    //求最小年龄
    public Integer getMinAge(List<User> users) {
        return users.stream().map(User::getAge).min(Integer::compareTo).get();
    }

    //求最大年龄
    public Integer getMaxAge(List<User> users) {
        return users.stream().map(User::getAge).max(Integer::compareTo).get();
    }

    //max和min入参是一个Comparator对象，用于元素之间的比较，返回值是一个Optional<T>，
    //它表示一个可能不存在的值，如果stream为空，那么该值不存在，通过get方法获取Optional的值


    //findAny、findFirst
    //及早求值方法：都以Optional<T>为返回值，表示是否找到
    public Optional<User> getAnyActiveUser(List<User> users) {
        return users.stream()
                .filter(User::isActive)
                .findAny();
    }

    public Optional<User> getFirstActiveUser(List<User> users) {
        return users.stream()
                .filter(User::isActive)
                .findFirst();
    }


    //allMatch、anyMatch、noneMatch
    //及早求值方法：均以Predicate作为输入参数，对集合中的元素进行判断，并返回最终结果

    //所有用户是否都已激活
    public Boolean allMatch(List<User> users) {
        return users.stream().allMatch(User::isActive);
    }

    //是否有激活用户
    public Boolean anyMatch(List<User> users) {
        return users.stream().anyMatch(User::isActive);
    }

    //是否所有用户都没激活
    public Boolean noneMatch(List<User> users) {
        return users.stream().noneMatch(User::isActive);
    }


    //forEach
    //及早求值：以Consumer为参数，对stream中复合条件的对象进行操作
    public void printActiveName(List<User> users) {
        users.stream().filter(User::isActive)
                .map(User::getName)
                .forEach(System.out::println);
    }


    //reduce
    //及早求值方法：reduce操作可以实现从一组值生成一个值，之前提到过的
    //count、min、max方法都是通过reduce完成的


    //filter
    //惰性求值方法：以Predicate作为参数（相当于if语句）对stream中的元素进行过滤
    //只有符合条件的元素才能进入下面的处理流程


    //map
    //及早求值方法：以Function作为参数，将Stream中的元素从一种类型转换成另一种类型


    //peek
    //stream提供的是内迭代，有时候为了调试功能，需要查看每个值，同时能够继续操作流
    //这时就用到peek

    public void printActiveName1(List<User> users) {
        users.stream().filter(User::isActive)
                .peek(user-> System.out.println(user.isActive()))
                .map(User::getName)
                .forEach(System.out::println);
    }


    //其他
    //distinct：进行去重操作
    //sorted：进行排序操作
    //limit：限定结果输出数量
    //skip：跳过n个结果，从n+1开始输出




    //Optional
    //Optional相当于值的容器，而该值可以通过get方法获取，
    //Optional与stream类似，提供的方法也分为惰性和及早求值两种
    //惰性方法主要用于流程组装，及早求值用于最终计算

    //of
    //使用工厂方法of，可以从一个值创建一个Optional对象，如果值为null，会报NullPointerException
    public void testOf(){
        Optional<String> dataOptional = Optional.of("a");
        String data1 = dataOptional.get();

        Optional<String> dataOptional2 = Optional.of(null);
        String o = dataOptional2.get(); //throw NullPointerException
    }


    //empty
    //工厂方法empty，可以创建一个不包含任何值的Optional对象
    public void testEmpty(){
        Optional<String> dataOptional = Optional.empty();
        String o = dataOptional.get(); //throw NoSuchElementException
    }


    //ofNullable
    //工厂方法ofNullable，可将一个null转换为Optional


    //get、orElse、orElseGet、orElseThrow
    //直接求值方法，用于获取Optional中的值，避免空指针的出现
    public void get(){
        Optional<String> dataOptional = Optional.of("a");
        String data = dataOptional.get(); //获取Optional中的值，不存在会抛出NoSuchElementException
        dataOptional.orElse("b");//获取Optional中的值，不存在，返回b
        dataOptional.orElseGet(()->String.valueOf(System.currentTimeMillis()));//获取Optional中的值，不存在返回Supplier传入的值
        dataOptional.orElseThrow(RuntimeException::new); //获取Optional中的值，不存在抛出自定义异常
    }


    //isPresent、ifPresent
    //直接求值方法，isPresent用于判断Optional中是否够有值，ifPresent接收Consumer对象，当Optional有值的情况下执行
    public void testPresent(){

        Optional<String> dataOptional = Optional.of("a");
        String value = null;
        if(dataOptional.isPresent()) {
            value = dataOptional.get();
        }else{
            value= "";
        }

        //等价于
        String value2 = dataOptional.orElse("");
        dataOptional.ifPresent(System.out::println);


    }

    //map
    //惰性求值方法，用于对Optional中的值进行映射处理，从而避免了大量if语句嵌套，多个map组合成链
    //只需对最终结果进行操作，中间过程中如果存在null值，之后的map不会执行
    @Data
    static class Order {
        private Name owner;
    }

    @Data
    static class Buyer {
        private Name name;
    }

    @Data
    static class Name {
        String firstName;
        String midName;
        String lastName;
    }

    private String getFirstName(Order order) {
        if(order == null) {
            return "";
        }

        if(order.getOwner() == null) {
            return "";
        }

        if(order.getOwner().getFirstName() == null) {
            return "";
        }

        return order.getOwner().getFirstName();
    }

    private String getFirstName(Optional<Order> orderOptional) {
        return orderOptional.map(Order::getOwner)
                .map(Name::getFirstName)
                .orElse("");
    }


    //filter
    //惰性求值


}

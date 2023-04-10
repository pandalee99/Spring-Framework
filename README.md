# Spring-Framework
 <br /> 
 这是一个及其简单的Spring框架，只实现了IOC和AOP的基本功能
 <br />
 
 ## 基本特性
 
 项目描述：
 实现了Spring框架中，IOC和AOP原理的核心功能。在IOC部分，实现了Bean的加载、注册、注入属性、实例化等步骤，并解决了循环依赖问题。
 在AOP部分，通过拦截器去处理方法的调用，实现动态代理，并将切面融入到Bean的生命周期。
 
 
 ### IOC基本特性
 
 
 
- 通过Json来配置Bean的信息，并转换为BeanDefinition对象
- 使用Cglib去实现反射，注入属性，将Bean实例化等功能。
- 通过设计二级缓存的方式，解决了循环依赖问题

 
 ### AOP基本特性
 
 - 会将Json配置的文件序列化为AopBeanDefinition对象
 
- 实现拦截器BeforeAdvice和AfterAdvice等方法拦截器

- 实现将方法动态织入Bean生命周期

- 使用Cglib去实现生成代理对象，实现动态代理

  
 
<br />

![test](https://raw.githubusercontent.com/pandalee99/image_store/master/hexo/image.png)


<br />

## 实现步骤
### IOC的实现步骤

1. 初始化 IoC 容器。
2. 读取配置文件。
3. 将配置文件转换为容器识别对的数据结构（BeanDefinition）
4. 利用数据结构依次实例化相应的对象
5. 注入对象之间的依赖关系
 <br /> <br /> <br /> <br /> <br />
 
### AOP的实现步骤

1. 初始化 Aop 容器。
2. 读取配置文件。
3. 将配置文件装换为 Aop 能够识别的数据结构 – Advisor。这里展开讲一讲这个advisor。Advisor对象中包又含了两个重要的数据结构，一个是 Advice，一个是 Pointcut。Advice的作用就是描述一个切面的行为，pointcut描述的是切面的位置。两个数据结的组合就是”在哪里，干什么“。这样 Advisor 就包含了”在哪里干什么“的信息，就能够全面的描述切面了。
4. Spring 将这个 Advisor 转换成自己能够识别的数据结构 – AdvicedSupport。Spirng 动态的将这些方法拦截器织入到对应的方法。
5. 生成动态代理代理。
6. 提供调用，在使用的时候，调用方调用的就是代理方法。也就是已经织入了增强方法的方法

<br />
<br />
<br />

## 用法举例：AOP

<br />
在cglib包下，Enhancer这个类的作用是为指定的类创建代理类。具体来说，Enhancer类可以动态地生成一个指定类的子类，该子类可以用来拦截指定类中的方法调用，从而实现代理模式。
<br />Enhancer类的使用方法类似于Java动态代理中的Proxy类。首先，需要创建一个Enhancer对象，并设置要代理的目标类和回调方法。然后，通过调用Enhancer对象的create方法，生成代理类的实例。
这个代理类会继承目标类，同时实现回调方法，从而实现对目标类方法的拦截和处理。
<br />Enhancer类的使用比较灵活，可以代理任意的类，包括没有实现任何接口的类。
但是，由于Enhancer是通过生成目标类的子类来实现代理的，所以目标类必须有默认的构造函数，并且不能是final类。
<br />假设我们有一个简单的UserService接口，其中定义了一个getUser方法：
<br />

```java
public interface UserService {
        User getUser(int id);
}
```

<br />
现在我们想为该接口创建一个代理类，记录getUser方法的调用次数。我们可以使用cglib的Enhancer类来实现：
<br />


```java
public class UserServiceProxy implements MethodInterceptor {

    private UserService target;
    private int count = 0;

    public UserServiceProxy(UserService target) {
        this.target = target;
    }

    public static UserService createProxy(UserService target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new UserServiceProxy(target));
        return (UserService) enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.getName().equals("getUser")) {
            count++;
        }
        return proxy.invoke(target, args);
    }

    public int getCount() {
        return count;
    }
}
```

<br />
在上面的代码中，我们定义了一个UserServiceProxy类，它实现了MethodInterceptor接口，用于拦截目标对象的方法调用。在构造方法中，我们传入了目标对象target，并使用Enhancer类生成一个UserService的代理对象。在intercept方法中，我们记录了getUser方法的调用次数，并使用MethodProxy类来调用目标对象的对应方法。<br />接下来，我们可以使用该代理类来调用getUser方法，并检查调用次数：
<br />


```java
UserService userService = UserServiceProxy.createProxy(new UserServiceImpl());
userService.getUser(1);
userService.getUser(2);
System.out.println(userService.getCount()); // output: 2
```


上面的代码中，我们首先使用createProxy方法创建了UserService的代理对象，并调用了getUser方法两次。最后，我们打印了代理对象的调用次数，发现它的确记录了两次getUser方法的调用。

<br />

**它为什么要这么做呢，直接new难道不行吗？**
<br />

<br />

使用代理模式的一个主要目的是在不改变原有代码的情况下，为现有对象添加额外的行为或功能。
在上面的例子中，我们通过使用cglib的Enhancer类创建了一个代理对象，使得我们可以在不修改原有UserServiceImpl类的情况下，为getUser方法添加了记录调用次数的功能。
<br />如果直接new一个新的对象，虽然可以实现类似的功能，但是需要对原有代码进行修改，将原有的对象替换成新的对象，这样就不太符合“开闭原则”，即对扩展开放，对修改关闭的设计原则。
同时，在某些情况下，我们可能无法直接访问到原有对象的构造函数，这就导致了不能直接创建新对象的限制。
<br />使用代理模式，可以通过创建代理对象来扩展原有对象的行为，而不需要修改原有对象的代码。同时，代理对象还可以在原有对象的基础上增加额外的行为，提高代码的可扩展性和可维护性。

<br /><br /><br /><br /><br />

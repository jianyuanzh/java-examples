众所周知，Java语言是啰嗦的，写久了就会发现很多地方的代码看着就很冗余。`lombok`提供了一种简化Java代码的解决方案，通过注解的方式，来生成模板化的代码。最典型的就是生成类的构造函数，getter/setter，toString方法等。

<!--more-->
注解是伴随JDK5发布的一个feature，根据生存周期的不同，分为Source，Class(编译阶段)和RUNTIME。lombok提供的注解的存活周期是Class，也就是说会lombok的注解会被编译器(javac)处理并翻译成对应的字节码。说穿了，lombok就像是一个职业写手，按照定义帮你写代码。我们在写代码的时候通过lombok提供的注解，定义好文章的提纲，剩下的交给lombok，他会帮我们把文章(代码)写的有血有肉(构造函数，getter,setter等)。

下面是lombok提供的一些注解和作用：
* `@Getter`/`@Setter`：根据fields创建getters和setters
* `@EqualsAndHashCode`: 实现`equals()`和`hashcode()`
* `@ToString`:实现`toString()`
* `@Data`: 包含上面的四个注解的功能
* `@Cleanup`: 关闭打开的流
* `@Synchronized`: 在对象上加锁
* `@SneakyThrows`: 捕获未处理的异常

下面是一段使用lombok注解的代码：
```java
import lombok.Data;

import java.util.Date;

@Data
public class Person {
    private String name;
    private Date birthday;
    private int gender;
}
```
先编译成字节码：
```
javac -cp lombok-1.18.2.jar Person.java
```
通过javap看下生成的字节码的内容:
```java
 lombok git:(master) ✗ javap Person.class
Compiled from "Person.java"
public class cc.databus.lombok.Person {
  // lombok生成的构造函数
  public cc.databus.lombok.Person();
  // 生成的getter和setter
  public java.lang.String getName();
  public java.util.Date getBirthday();
  public int getGender();
  public void setName(java.lang.String);
  public void setBirthday(java.util.Date);
  public void setGender(int);
  // 生成的equals和hashcode
  public boolean equals(java.lang.Object);
  protected boolean canEqual(java.lang.Object);
  public int hashCode();
  // 生成的toString
  public java.lang.String toString();
}
```


上面这是一个简单的例子，更细节的使用可以参考[lombok提供的注解列表](https://projectlombok.org/features/)。

如果在Intelij下开发，需要安装lombok的一个插件，否则无法在intelij下运行和调试：
1. 依次打开File > Settings > Plugins
2. 选择 Browse repositories...
3. 搜索 Lombok Plugin
4. 安装插件
5. 重启IntelliJ IDEA


# References
1. [lombok提供的注解列表](https://projectlombok.org/features/)
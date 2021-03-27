package top.mcwebsite.java.demo.base.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TestInherited {

}

@TestInherited
class Animal {

}

class Cat extends Animal {  // 这里也会有来自父类的 @TestInherited

}

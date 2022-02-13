一个接口如果仅仅有一个抽象的方法，那么这个接口被称为函数式接口或者仅有又一个抽象方法的接口（Single Abstract Method (SAM) interfaced)。这个接口可以有几个非抽象的方法，但是仅仅只能有一个抽象的方法。

在Kotlin中要声明一个函数式接口，需要使用 `fun`来对接口进行修饰。

```Kotlin
fun interface KRunnable {
    fun invoke()
}

```

## SAM 转换

对于函数式接口，可以使用SAM转换，通过 lambda表达式使代码更加的简洁并且能够提高代码的可读性。

你可以使用lambda表达式，而不是手动创建函数式接口的实现类。通过SAM转换，Kotlin 可以将 lambda表达式转换为对应的函数式接口的实现。

举个例子，如下是一个Kotlin的函数式接口：

```Kotlin
fun interface IntPredicate {
    fun accept(i: Int): Boolean
}
```

如果你不使用SAM转换，你需要写像下面这样的代码：

```Kotlin
// Create an instance of a class
val isEven = object : IntPredicate {
    override fun accept(i: Int): Boolean {
        return i % 2 == 0
    }
}
```

使用Kotlin的SAM转换，你可以通过下面的代码等价替换上面的代码：

```Kotlin
// Creating an instance using lambda
val isEven = IntPredicate { it % 2 == 0 }
```

一个简短的lambda表达式替换所有不必要的代码：

```Kotlin
fun interface IntPredicate {
    fun accept(i: Int): Boolean
}

// Create an instance using lambda
val isEven = IntPredicate { it % 2 == 0 }

fun main() {
    println("7 is even? - ${isEven.accept(7)}")
}
```


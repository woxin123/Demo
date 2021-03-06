package top.mcwebsite.kotlin.demo.coroutine.base;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import org.jetbrains.annotations.NotNull;

public class UseJavaCallKotlinSuspend {

    public static void main(String[] args) throws InterruptedException {
        Object result = CoroutineDispatcherKt.suspendFunc02("a", "b", new Continuation<Integer>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                System.out.println(o);
            }
        });
        System.out.println(result);
        Thread.sleep(1000);
    }
}

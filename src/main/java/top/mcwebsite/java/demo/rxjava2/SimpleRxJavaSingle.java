package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import org.jetbrains.annotations.NotNull;

public class SimpleRxJavaSingle {

    public static void main(String[] args) {
        Single.create((SingleOnSubscribe<Integer>) emitter -> {
            emitter.onSuccess(11);
        }).subscribe(System.out::println);
    }
}

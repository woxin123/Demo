package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SimpleRxJava2DemoZip {

    private static final int stringObservableCount = 5;
    private static final int integerObservableCount = 7;

    public static void main(String[] args) {
        Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    private static Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                for (int i = 0; i < stringObservableCount; i++) {
                    char value = (char) (i + 'A');
                    System.out.println("String emit: " + value);
                    emitter.onNext(value + "");
                }
            }
        });
    }

    private static Observable<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < integerObservableCount; i++) {
                    System.out.println("Integer emit: " + i);
                    emitter.onNext(i);
                }
            }
        });
    }

}

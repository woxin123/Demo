package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.*;
import io.reactivex.functions.Consumer;

import java.util.concurrent.Callable;

public class SimpleRxJava2DemoDefer {

    public static void main(String[] args) {
        SomeType someType = new SomeType();
        Observable<String> value = someType.valueObservable3();
        someType.setValue("123");
        value.subscribe(System.out::println);
    }

}
class SomeType  {

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Observable just 是将传入的 value 封装成一个 Observable，此时返回的 Observable 里面的值已经使用传入的 value 赋值
     * 所以之后再给 SomeType 的 value 赋值，就与返回的 Observable 没有关系
     * @return
     */
    public Observable<String> valueObservable1() {
        return Observable.just(value);
    }

    /**
     * 利用 create 创建一个，当订阅的时候，然后发射 value
     * @return
     */
    public Observable<String> valueObservable2() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(value);
            }
        });
    }

    /**
     * 利用 defer 包装一下，当我们订阅返回的 Observable 的时候， 返回的 Observable 调用 call 方法，然后让 调用返回 Observable 的 Observer 调用 call 方法返回的 Observable
     * @return
     */
    public Observable<String> valueObservable3() {
        return Observable.defer(new Callable<Observable<String>>() {

            @Override
            public Observable<String> call() throws Exception {
                return Observable.just(value);
            }
        });
    }

    public Observable<String> valueObservable4() {
        return new DeferObservable<String>(new Callable<ObservableSource<String>>() {

            @Override
            public Observable<String> call() throws Exception {
                return Observable.just(value);
            }
        });
    }
}

class DeferObservable<T> extends Observable<T> {

    private Callable<ObservableSource<T>> callable;

    public DeferObservable(Callable<ObservableSource<T>> callable) {
        this.callable = callable;
    }

    @Override
    protected void subscribeActual(Observer observer) {
        try {
            ObservableSource<T> pub = callable.call();
            pub.subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
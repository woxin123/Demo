package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class SimpleRxJava2DemoCombineLast {

    public static void main(String[] args) throws InterruptedException {
        Observable.combineLatest(Observable.intervalRange(3, 10, 0, 100, TimeUnit.MILLISECONDS),
                Observable.intervalRange(4, 20, 0, 200, TimeUnit.MILLISECONDS),
                new BiFunction<Long, Long, String>() {
            @NotNull
            @Override
            public String apply(@NotNull Long integer, @NotNull Long integer2) throws Exception {
                return integer.toString() + " " + integer2.toString();
            }
        })
                .subscribe(new Observer<String>() {

                    private Disposable mDisposable = null;
                    /**
                     * 订阅的时候调用
                     * @param d
                     */
                    @Override
                    public void onSubscribe(Disposable d) {
                        // init
                        mDisposable = d;
                    }

                    /**
                     * 获取数据的时候调用
                     * @param s
                     */
                    @Override
                    public void onNext(String s) {
                        System.out.println("accept: " + s);
                    }

                    /**
                     * 当出错的时候调用
                     * @param e
                     */
                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                        e.printStackTrace();
                    }

                    /**
                     * 当数据接收完成的时候调用，但是当调用 Disposable 的 dispose() 方法切断数据流的时候不会调用此方法
                     */
                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        Thread.sleep(100000);
    }

}

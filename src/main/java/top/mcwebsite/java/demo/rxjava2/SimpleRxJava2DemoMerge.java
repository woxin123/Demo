package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class SimpleRxJava2DemoMerge {

    public static void main(String[] args) throws InterruptedException {
        Observable.merge(Observable.interval(100, TimeUnit.MILLISECONDS), Observable.interval(300, TimeUnit.MILLISECONDS))
                .subscribe(new Observer<Number>() {

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
                     * @param number
                     */
                    @Override
                    public void onNext(Number number) {
                        System.out.println("accept: " + number);
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

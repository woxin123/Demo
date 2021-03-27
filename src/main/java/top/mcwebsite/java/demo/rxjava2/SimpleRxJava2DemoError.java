package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SimpleRxJava2DemoError {

    public static void main(String[] args) throws InterruptedException {
        Observable.error(new Exception("你错了"))
                .subscribe(new Observer<Object>() {

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
                     * @param value
                     */
                    @Override
                    public void onNext(Object value) {
                        System.out.println("accept: " + value);
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

        Thread.sleep(10000);
    }

}

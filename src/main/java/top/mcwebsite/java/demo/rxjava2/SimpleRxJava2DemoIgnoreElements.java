package top.mcwebsite.java.demo.rxjava2;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SimpleRxJava2DemoIgnoreElements {

    public static void main(String[] args) {
        Observable.just(1, 2, 3)
                .ignoreElements()
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }
                });
    }

}

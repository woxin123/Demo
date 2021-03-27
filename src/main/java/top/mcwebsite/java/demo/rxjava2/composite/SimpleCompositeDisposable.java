package top.mcwebsite.java.demo.rxjava2.composite;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Disposable 能够主动解除订阅
 * Rxjava 中如果没有及时取消订阅，容易导致内存泄漏
 *
 * 而 CompositeDisposable 可以容纳多个 disposable。在不需要的时候，一起取消添加的 disposable。
 * 如果这个 CompositeDisposable 容器已经处于 disposable 状态，那么所有加进来的 disposable 都会被自动切换
 */
public class SimpleCompositeDisposable {

    public static void main(String[] args) throws InterruptedException {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {

            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NotNull Disposable d) {
                mDisposable = d;
                // 添加到容器中
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NotNull Integer integer) {
                if (!mDisposable.isDisposed()) {
                    System.out.println("accept value = " + integer);
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });
        compositeDisposable.add(Observable.interval(1, 1, TimeUnit.SECONDS)
                .subscribe(value -> {
                    System.out.println("accept value = " + value);
                }));
        Thread.sleep(10000);
        compositeDisposable.clear();
    }

}

/**
 * CompositeDisposable 简单的源码
 * Rxjava 中的更注重多线程安全问题
 */
class MyCompositeDisposable {

    private boolean isDisposable = false;

    // Rxjava 中用的是它自己实现的 OpenHashSet
    private HashSet<Disposable> resource;


    public boolean add(Disposable disposable) {
        if (!isDisposable) {
            synchronized (this) {
                HashSet<Disposable> set = resource;
                if (set == null) {
                    set = new HashSet<>();
                    resource = set;
                }
                set.add(disposable);
                return true;
            }
        }
        disposable.dispose();
        return false;
    }

    public boolean remove(Disposable disposable) {
        if (delete(disposable)) {
            disposable.dispose();
            return true;
        }
        return false;
    }

    public boolean delete(Disposable disposable) {
        if (isDisposable) {
            return false;
        }
        synchronized (this) {
            if (isDisposable) {
                return false;
            }
            HashSet<Disposable> set = resource;
            if (set == null || !set.remove(disposable)) {
                return false;
            }
        }
        return true;
    }

    public void dispose() {
        if (isDisposable) {
            return;
        }
        synchronized (this) {
            HashSet<Disposable> set = resource;
            isDisposable = true;
            if (set == null || set.isEmpty()) {
                return;
            }
            for (Disposable disposable : set) {
                disposable.dispose();
            }
            resource = null;
        }
    }
}

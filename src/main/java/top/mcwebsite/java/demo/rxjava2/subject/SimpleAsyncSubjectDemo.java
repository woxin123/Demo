package top.mcwebsite.java.demo.rxjava2.subject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;
import org.jetbrains.annotations.NotNull;

/**
 * 使用 AsyncSubject 无论输入发射多少数据，永远只输出最后一个参数
 * 如果原始的 Observable 因为错误而终止，AsyncSubject 将不会发送任何数据，只是简单的向前传递这个错误通知
 */
public class SimpleAsyncSubjectDemo {
    public static void main(String[] args) {
        AsyncSubject<Integer> as = AsyncSubject.create();
        as.subscribe(value -> {
            System.out.println("accept value = " + value);
        });
        as.onNext(1);
        as.onNext(2);
        as.onNext(3);
        as.onError(new IllegalArgumentException("错误的参数"));
        // 在这里结束订阅
        as.onComplete();
        // 结束后，在这里订阅也能收到 3
//        as.subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println("accept value = " + integer);
//            }
//        });
        as.subscribe(integer -> {
            System.out.println("accept integer = " + integer);
        });
        as.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NotNull Integer integer) {
                System.out.println("Observer accept value = " + integer);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                System.out.println("onError: " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
        // 不用这样使用 Subject
        // 因为 just(T)、from(T)、create(T) 会把 Subject 转换成 Observable
        // public static <T> Observable<T> just(T item1, T item2, T item3)
        AsyncSubject.just(1, 2, 3).subscribe((value -> {
            System.out.println("accept value = " + value);
        }));

    }
}

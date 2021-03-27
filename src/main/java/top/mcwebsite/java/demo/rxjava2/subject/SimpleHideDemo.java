package top.mcwebsite.java.demo.rxjava2.subject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class SimpleHideDemo {
    public static void main(String[] args) {
        // 我们只想在 main 方法中控制发射数据
        BehaviorSubject<Integer> subject = BehaviorSubject.createDefault(-1);
        subject.onNext(123);
        // 我们将 subject 的发射数据的功能隐藏
        useObservable(subject.hide());
        subject.onNext(456);
    }

    private static void useObservable(Observable<Integer> observable) {
        observable.subscribe(value -> {
            System.out.println("useObservable function accept value = " + value);
        });
    }
}

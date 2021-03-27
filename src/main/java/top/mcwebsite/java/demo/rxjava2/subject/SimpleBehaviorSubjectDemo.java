package top.mcwebsite.java.demo.rxjava2.subject;

import io.reactivex.subjects.BehaviorSubject;

/**
 * BehaviorSubject 会发送离订阅最近的上一个值，如果没有上一个值的时候回发送默认值
 */
public class SimpleBehaviorSubjectDemo {

    public static void main(String[] args) {
        // 不带默认值
        // BehaviorSubject<Integer> bs = BehaviorSubject.create();
        // 下面这种方式带有默认值，BehaviorSubject
        BehaviorSubject<Integer> bs = BehaviorSubject.createDefault(-1);
        // 这里订阅回调 -1, 1, 2, 3
//        bs.subscribe(value -> {
//            System.out.println("accept value = " + value);
//        });
        bs.onNext(1);
        // 这里订阅将回调 1, 2, 3
//        bs.subscribe(value -> {
//            System.out.println("accept value = " + value);
//        });
        bs.onNext(2);
        // 这里订阅将回调 2, 3
//        bs.subscribe(value -> {
//            System.out.println("accept value = " + value);
//        });
        bs.onNext(3);
        // 这里订阅将回调
        bs.subscribe(value -> {
            System.out.println("accept value = " + value);
        });
        bs.onComplete();
        // 这里订阅没有回调
//        bs.subscribe(value -> {
//            System.out.println("accept value = " + value);
//        });
    }

}

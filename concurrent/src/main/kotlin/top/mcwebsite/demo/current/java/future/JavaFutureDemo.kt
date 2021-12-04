package top.mcwebsite.demo.current.java.future

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.FutureTask

private var waterOk = false
private var cupOk = false

fun main() {
    val hotWaterJob = Callable {
        println("洗好水壶")
        println("装入水")
        println("放到火上")
        println("烧水中")

        // 线程睡眠一段时间，代表烧水中
        Thread.sleep(1000L)
        println("水烧开了")
        true
    }

    val washJob = Callable {
        try {
            println("洗茶壶")
            println("洗茶杯")
            println("拿茶叶")

            // 线程睡眠一段时间，代表清洗中
            Thread.sleep(1000L)
            println("洗完了")
        } catch (e: InterruptedException) {
            println(" 清洗工作 发生异常中断")
            return@Callable false
        } finally {
            println("清洗工作正常结束")
        }
        return@Callable true
    }

    val hotWaterTask = FutureTask(hotWaterJob)
    val washTask = FutureTask(washJob)
    val hotWaterThread = Thread(hotWaterTask, "** 烧水-Thread")
    val washThread = Thread(washTask, "$$ 清洗-Thread")

    hotWaterThread.start()
    washThread.start()

    try {
        waterOk = hotWaterTask.get()
        cupOk = washTask.get()

        hotWaterThread.join()
        washThread.join()
        drinkTea()
    } catch (e: InterruptedException) {
        println("${Thread.currentThread().name} 发生异常被中断,")
    } catch (e: ExecutionException) {
        e.printStackTrace()
    }
    println("${Thread.currentThread().name} 运行结束")
}

private fun drinkTea() {
    if (waterOk && cupOk) {
        println("泡茶喝")
    } else if (!waterOk) {
        println("烧水失败，没有茶喝")
    } else if (!cupOk) {
        println("杯子没洗，没有茶喝了")
    }
}
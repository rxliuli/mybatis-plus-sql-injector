package common.util;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


public class ConcurrencyTestUtil {

    public static void assertConcurrent(final String message,
                                        final List<? extends Runnable> runnables,
                                        final int maxTimeoutSeconds, final int maxThreadPoolSize) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(message);

        final int numThreads = runnables.size();
        final List<Throwable> exceptions = Collections
                .synchronizedList(new ArrayList<Throwable>());
        final ExecutorService threadPool = Executors
                .newFixedThreadPool(numThreads > maxThreadPoolSize ? maxThreadPoolSize : numThreads);
        try {
            final CountDownLatch afterInitBlocker = new CountDownLatch(1);
            final CountDownLatch allDone = new CountDownLatch(numThreads);
            for (final Runnable submittedTestRunnable : runnables) {
                threadPool.submit(new Runnable() {
                    public void run() {
                        try {
                            // 相当于加了个阀门等待所有任务都准备就绪
                            afterInitBlocker.await();
                            submittedTestRunnable.run();
                        } catch (final Throwable e) {
                            exceptions.add(e);
                        } finally {
                            allDone.countDown();
                        }
                    }
                });
            }

            // start all test runners
            afterInitBlocker.countDown();
            assertThat(allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS))
                    .overridingErrorMessage(message + " timeout! More than" + maxTimeoutSeconds + "seconds")
                    .isTrue();

            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
        } finally {
            threadPool.shutdownNow();
        }
        assertThat(exceptions.isEmpty())
                .overridingErrorMessage(message + "failed with exception(s)" + exceptions)
                .isTrue();
    }
}
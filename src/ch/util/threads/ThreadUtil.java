package ch.util.threads;

import java.util.concurrent.*;

public class ThreadUtil {

    public static void runTimeoutThread(Runnable runnable, float seconds) throws TimeoutException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future future = executor.submit(runnable);

        long timeout = (long) seconds*1000;

        try {
            future.get(timeout, TimeUnit.MILLISECONDS);
        } catch(InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            future.cancel(true);
            throw e;
        } finally {
            executor.shutdownNow();
        }
    }

}

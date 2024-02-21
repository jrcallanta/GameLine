package game.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

public class ConsoleInput {
    private class ConsoleInputReadTask implements Callable<String> {
        public String call() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input;
            do {
                try {
                    System.out.print("> ");
                    while (!br.ready()) Thread.sleep(200);
                    input = br.readLine();
                } catch (InterruptedException e) {
                    return null;
                }
            } while ("".equals(input));
            return input;
        }
    }

    public String timedReadLine(long timeToRead) throws InterruptedException {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        Future<String> result = ex.submit(new ConsoleInputReadTask());
        String input = null;
        try {
            input = result.get(timeToRead, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
        } catch (TimeoutException e) {
            result.cancel(true);
        } finally {
            ex.shutdownNow();
        }
        return input;
    }
}

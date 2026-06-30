/**
 * Demonstrates a simple fixed-window rate limiter.
 *
 * The rate limiter allows a fixed number of requests in a configured time
 * window. Once the window expires, the counter resets and new requests are
 * allowed again.
 */
public class SimpleFixedWindowRateLimiter {

    private final int maxRequests;
    private final long windowSizeInMillis;

    private long windowStartTime;
    private int requestCount;

    public SimpleFixedWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("Max requests must be greater than zero");
        }
        if (windowSizeInMillis <= 0) {
            throw new IllegalArgumentException("Window size must be greater than zero");
        }

        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.windowStartTime = System.currentTimeMillis();
    }

    public boolean allowRequest() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - windowStartTime >= windowSizeInMillis) {
            windowStartTime = currentTime;
            requestCount = 0;
        }

        if (requestCount < maxRequests) {
            requestCount++;
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleFixedWindowRateLimiter rateLimiter = new SimpleFixedWindowRateLimiter(3, 1000);

        for (int i = 1; i <= 10; i++) {
            boolean allowed = rateLimiter.allowRequest();
            System.out.printf(
                    "Request-%d -> %s%n",
                    i,
                    allowed ? "allowed" : "rejected"
            );

            Thread.sleep(250);
        }
    }
}

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demonstrates a simple round-robin load balancer.
 *
 * The load balancer keeps a list of backend servers and returns the next server
 * for each incoming request. After it reaches the last server, it starts again
 * from the first server.
 */
public class SimpleRoundRobinLoadBalancer {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final List<String> servers;

    public SimpleRoundRobinLoadBalancer(List<String> servers) {
        if (servers == null || servers.isEmpty()) {
            throw new IllegalArgumentException("Server list cannot be empty");
        }
        this.servers = servers;
    }

    public String getServer() {
        int index = Math.floorMod(counter.getAndIncrement(), servers.size());
        return servers.get(index);
    }

    public static void main(String[] args) {
        List<String> servers = List.of("Server-A", "Server-B", "Server-C");
        SimpleRoundRobinLoadBalancer loadBalancer = new SimpleRoundRobinLoadBalancer(servers);

        for (int i = 1; i <= 10; i++) {
            System.out.println("Request " + i + " --> " + loadBalancer.getServer());
        }
    }
}

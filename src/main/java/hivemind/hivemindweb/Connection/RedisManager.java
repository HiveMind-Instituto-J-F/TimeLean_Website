package hivemind.hivemindweb.Connection;

import io.github.cdimascio.dotenv.Dotenv;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;

import java.time.LocalDateTime;

public class RedisManager {

    private static Jedis jedis;

    // [PROCESS] Initialize Redis connection
    public static void initialize(Dotenv dotenv) {
        if (jedis != null && jedis.isConnected()) return;

        try {
            // [VALIDATION] Validate dotenv input
            if (dotenv == null) {
                throw new IllegalArgumentException("Valor Nulo: 'dotenv'");
            }

            String host = dotenv.get("rds_host");
            String password = dotenv.get("rds_password");

            if (host == null) {
                throw new IllegalArgumentException("Valor Nulo: 'rds_host'");
            }

            if (password == null) {
                throw new IllegalArgumentException("Valor Nulo: 'rds_password'");
            }

            // [DATA ACCESS] Configure Redis client
            JedisClientConfig config = DefaultJedisClientConfig.builder()
                    .user("default")
                    .password(password)
                    .build();

            HostAndPort hostAndPort = new HostAndPort(host, 17579);
            jedis = new Jedis(hostAndPort, config);

            // [SUCCESS LOG] Redis connection established
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Connected to remote Redis");

        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [RedisManager] IllegalArgumentException: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar ao Redis: entrada inválida", e);
        } catch (NullPointerException e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [RedisManager] NullPointerException: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar ao Redis: valor nulo", e);
        } catch (Exception e) {
            System.err.println("[ERROR] [" + LocalDateTime.now() + "] [RedisManager] Exception: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar ao Redis: falha inesperada", e);
        }
    }

    // [DATA ACCESS] Retrieve Redis connection
    public static Jedis getConnection(Dotenv dotenv) {
        if (jedis == null || !jedis.isConnected()) {
            initialize(dotenv);
        }
        return jedis;
    }

    // [DATA ACCESS] Save value to Redis
    public static void save(String key, String field, String value, Dotenv dotenv) {
        Jedis redisConnection = getConnection(dotenv);
        redisConnection.hset(key, field, value);
    }

    // [DATA ACCESS] Retrieve field value from Redis
    public static String getField(String key, String field, Dotenv dotenv) {
        Jedis redisConnection = getConnection(dotenv);
        return redisConnection.hget(key, field);
    }

    // [DATA ACCESS] Check if key exists in Redis
    public static boolean exists(String key, Dotenv dotenv) {
        Jedis redisConnection = getConnection(dotenv);
        return redisConnection.exists(key);
    }

    // [DATA ACCESS] Delete key from Redis
    public static void delete(String key, Dotenv dotenv) {
        Jedis redisConnection = getConnection(dotenv);
        redisConnection.del(key);
    }

    // [DATA ACCESS] Set expiration time for key
    public static void expire(String key, int seconds, Dotenv dotenv) {
        Jedis redisConnection = getConnection(dotenv);
        redisConnection.expire(key, seconds);
    }

    // [PROCESS] Close Redis connection
    public static void close() {
        if (jedis != null) {
            jedis.close();
            jedis = null;
            // [SUCCESS LOG] Redis connection closed
            System.out.println("[INFO] [" + LocalDateTime.now() + "] Redis connection closed");
        }
    }
}

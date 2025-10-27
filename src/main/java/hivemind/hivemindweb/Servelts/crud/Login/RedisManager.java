package hivemind.hivemindweb.Servelts.crud.Login;

import io.github.cdimascio.dotenv.Dotenv;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;

public class RedisManager {

    private static Jedis jedis;

    public static void initialize(Dotenv dotenv) {
        if (jedis != null && jedis.isConnected()) return;

        try {
            if (dotenv == null) throw new RuntimeException("Dotenv está nulo!");

            String host = dotenv.get("rds_host");
            String password = dotenv.get("rds_password");

            if (host == null || password == null)
                throw new RuntimeException("rds_host ou rds_password ausentes no .env");

            JedisClientConfig config = DefaultJedisClientConfig.builder()
                    .user("default")
                    .password(password)
                    .build();

            HostAndPort hostAndPort = new HostAndPort(host, 17579);
            jedis = new Jedis(hostAndPort, config);

            System.out.println("✅ Conectado ao Redis remoto!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao Redis: " + e.getMessage(), e);
        }
    }

    public static Jedis getConnection(Dotenv dotenv) {
        if (jedis == null || !jedis.isConnected()) {
            initialize(dotenv);
        }
        return jedis;
    }

    public static void save(String key, String field, String value, Dotenv dotenv) {
        Jedis j = getConnection(dotenv);
        j.hset(key, field, value);
    }

    public static String getField(String key, String field, Dotenv dotenv) {
        Jedis j = getConnection(dotenv);
        return j.hget(key, field);
    }

    public static boolean exists(String key, Dotenv dotenv) {
        Jedis j = getConnection(dotenv);
        return j.exists(key);
    }

    public static void delete(String key, Dotenv dotenv) {
        Jedis j = getConnection(dotenv);
        j.del(key);
    }

    public static void expire(String key, int seconds, Dotenv dotenv) {
        Jedis j = getConnection(dotenv);
        j.expire(key, seconds);
    }

    public static void close() {
        if (jedis != null) {
            jedis.close();
            jedis = null;
            System.out.println("🔒 Conexão com Redis encerrada.");
        }
    }
}

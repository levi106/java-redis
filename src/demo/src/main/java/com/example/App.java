package com.example;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class App 
{
    public static void main( String[] args )
    {
        final String HOST_NAME = System.getenv("HOST_NAME");
        final char[] PASSWORD = System.getenv("PASSWORD").toCharArray();
        final int SSL_PORT = 6380;
        final RedisURI redisUri = RedisURI.Builder.redis(HOST_NAME, SSL_PORT).
            withPassword(PASSWORD).
            withSsl(true).
            withPort(SSL_PORT).
            build();
        RedisClient client = RedisClient.create(redisUri);
        StatefulRedisConnection<String, String> connection = client.connect();
        while (true) {
            try {
                Thread.sleep(1000);
                long time = System.currentTimeMillis() / 1000L;
                connection.sync().set("timestamp", String.valueOf(time));
                System.out.println(connection.sync().get("timestamp"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

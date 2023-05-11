package com.example;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisException;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.TimeoutOptions;

public class App 
{
    public static void main( String[] args )
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String HOST_NAME = System.getenv("HOST_NAME");
        final char[] PASSWORD = System.getenv("PASSWORD").toCharArray();
        final String timeout = System.getenv("TIMEOUT");
        final int SSL_PORT = 6380;
        final RedisURI redisUri = RedisURI.Builder.redis(HOST_NAME, SSL_PORT).
            withPassword(PASSWORD).
            withSsl(true).
            withPort(SSL_PORT).
            build();
        RedisClient client = RedisClient.create(redisUri);
        if (timeout != null) {
            System.out.printf("Timeout: %s\n", timeout);
            client.setDefaultTimeout(Duration.ofSeconds(Integer.parseInt(timeout)));
        }
        StatefulRedisConnection<String, String> connection = client.connect();
        while (true) {
            try {
                Thread.sleep(1000);
                long time = System.currentTimeMillis() / 1000L;
                //RedisCommands<String,String> command = connection.sync();
                //command.set("timestamp", String.valueOf(time));
                connection.sync().set("timestamp", String.valueOf(time));
                System.out.println(sdf.format(Calendar.getInstance().getTime()) + " - " + connection.sync().get("timestamp"));
            } catch (InterruptedException e) {
                System.out.println(sdf.format(Calendar.getInstance().getTime()) + " - " + e.getMessage());
                e.printStackTrace();
                break;
            } catch (RedisCommandTimeoutException e) {
                System.out.println(sdf.format(Calendar.getInstance().getTime()) + " - " + e.getMessage());
                e.printStackTrace();
                break;
            } catch (RedisException e) {
                System.out.println(sdf.format(Calendar.getInstance().getTime()) + " - " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }
}

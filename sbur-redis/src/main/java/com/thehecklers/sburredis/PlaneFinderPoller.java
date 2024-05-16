package com.thehecklers.sburredis;


import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


// Polling 매커니즘을 수행하는 코드
@EnableScheduling
@Component
public class PlaneFinderPoller {

    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    private final RedisConnectionFactory connectionFactory;
    private final RedisOperations<String, AirCraft> redisOperations;

    PlaneFinderPoller(RedisConnectionFactory connectionFactory,
                      RedisOperations<String, AirCraft> redisOperations){
        this.connectionFactory = connectionFactory;
        this.redisOperations = redisOperations;
    }

    @Scheduled(fixedRate = 1000) // 폴링 빈도 1000ms 당 한번 (1초당 한번)
    private void pllPlanes(){
        connectionFactory.getConnection().serverCommands().flushDb();

        client.get()
                .retrieve()
                .bodyToFlux(AirCraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(ac -> redisOperations.opsForValue().set(ac.getReg(),ac));

        redisOperations.opsForValue()
                .getOperations()
                .keys("*")
                .forEach(ac->
                        System.out.println(redisOperations.opsForValue().get(ac)));
    }


}

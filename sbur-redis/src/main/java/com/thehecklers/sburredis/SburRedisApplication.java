package com.thehecklers.sburredis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Instant;


@SpringBootApplication
public class SburRedisApplication {

	/*@Bean
	public RedisOperations<String ,AirCraft> redisOperatations(RedisConnectionFactory factory){
		Jackson2JsonRedisSerializer<AirCraft> serializer = new Jackson2JsonRedisSerializer<>(AirCraft.class);

		RedisTemplate<String, AirCraft> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setDefaultSerializer(serializer);
		template.setKeySerializer(new StringRedisSerializer());


		return template;
	}*/
	
	// reddis 템플릿 에서 repository 를 이용한 프로그램으로 변경하면서 reddis 의 bean 등록이 필요 없어짐

	public static void main(String[] args) {
		SpringApplication.run(SburRedisApplication.class, args);
	}

}

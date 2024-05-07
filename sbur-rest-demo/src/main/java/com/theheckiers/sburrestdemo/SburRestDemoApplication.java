package com.theheckiers.sburrestdemo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SburRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SburRestDemoApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "droid")
	Droid createDroid(){
		return new Droid();
	}

}

@Entity
class Coffee {
	@Id
	private String id;
	private String name;

	public Coffee(){

	}

	public Coffee(String id, String name){
		this.id = id;
		this.name = name;
	}
	public Coffee(String name){
		this(UUID.randomUUID().toString(), name);
		// 랜덤한 id를 보여주고, toString()으로 String 으로 변환
	}
	public void setId(String id){this.id = id;}
	public String getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}

}

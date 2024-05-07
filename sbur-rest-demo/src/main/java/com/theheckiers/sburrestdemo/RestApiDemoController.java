package com.theheckiers.sburrestdemo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
class DataLoader{
    private final CoffeeRepository coffeeRepository;
    public DataLoader(CoffeeRepository coffeeRepository){
        this.coffeeRepository = coffeeRepository;
    }

    @PostConstruct
    private void loadData(){
        this.coffeeRepository.saveAll(List.of(
                new Coffee("Cafe Cereza"),
                new Coffee("Cafe Gandor"),
                new Coffee("Cafe Lareno"),
                new Coffee("cafe Tres Pontas")
        ));
    }

}

@RestController
@RequestMapping("/greeting")
class GreetingController{

    //@Value("${greeting-name:Mirage}")
   // private String name;
    // 원래는 위처럼 @Value 어노테이션을 사용해도 되지만 이런저런 문제 발생 가능 때문에 아래와 같이 사용

    private final Greeting greeting;

    public GreetingController(Greeting greeting){
        this.greeting = greeting;
    }
    @GetMapping
    String getGreeting(){
        return greeting.getName();
    }

    @GetMapping("/coffee")
    String getNameAndCoffee(){
        return greeting.getCoffee();
    }



}


@ConfigurationProperties(prefix = "greeting")
class Greeting{
    private String name;
    private String coffee;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getCoffee(){
        return coffee;
    }
    public void setCoffee(String coffee){
        this.coffee = coffee;
    }

}

@RestController
@RequestMapping("/coffees")
public class RestApiDemoController {

    private final CoffeeRepository coffeeRepository;

    public RestApiDemoController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }


    @GetMapping
    Iterable<Coffee> getCoffees(){
        return coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id){
        return coffeeRepository.findById(id);
    }

    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee){
        return coffeeRepository.save(coffee);
    }

    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee){
        return (coffeeRepository.existsById(id))
                ? new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK)
                : new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id){
        coffeeRepository.deleteById(id);
    }


}

class Droid{
    private String id, description;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

}

@RestController
@RequestMapping("/droid")
class DroidController{
    private final Droid droid;

    public DroidController(Droid droid){
        this.droid = droid;
    }

    @GetMapping
    Droid getDroid(){
        return droid;
    }
}

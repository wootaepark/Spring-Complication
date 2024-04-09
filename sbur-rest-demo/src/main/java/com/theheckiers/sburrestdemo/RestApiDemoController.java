package com.theheckiers.sburrestdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RestApiDemoController {
    private List<Coffee> coffees= new ArrayList<>();

    public RestApiDemoController(){
        coffees.addAll(List.of(
                new Coffee("Cafe Cereza"),
                new Coffee("Cafe Gandor"),
                new Coffee("Cafe Lareno"),
                new Coffee("cafe Tres Pontas")
        ));
    }

    @GetMapping("/coffees")
    Iterable<Coffee> getCoffees(){
        return coffees;
    }

    @GetMapping("/coffees/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id){
        for (Coffee c : coffees){
            if(c.getId().equals(id)){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @PostMapping("/coffees")
    Coffee postCoffee(@RequestBody Coffee coffee){
        coffees.add(coffee);
        return coffee;
    }

    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee){
        int coffeeIndex = -1;

        for (Coffee c : coffees){
            if(c.getId().equals(id)){
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }
        return (coffeeIndex == -1) ?
                new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) : // 201 상태, 해당 정보가 없으면 생성 (create)
                new ResponseEntity<>(coffee, HttpStatus.OK); // 200 상태
    }

    @DeleteMapping("/coffees/{id}")
    void deleteCoffee(@PathVariable String id){
        coffees.removeIf(c->c.getId().equals(id));
    }


}

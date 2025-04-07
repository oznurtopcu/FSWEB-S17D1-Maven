package com.workintech.fswebs17d1.controller;

import com.workintech.fswebs17d1.entity.Animal;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//http://localhost:8585/workintech/animal

@RestController
@RequestMapping(path = "/workintech/animal")
public class AnimalController {
    Map<Integer, Animal> animals;

    @Value("${project.developer.fullname}")
    private String developerName;

    @Value("${course.name}")
    private String courseName;


    //bu anotasyo sayesinde proje ayağa kalkarken method çalışır,
    //bu sayede proje kullanılabilir hale gelir
    //bu annotation methoda verilmek zorundadır, method annotation
    @PostConstruct
    public void loadAll() {
        System.out.println("PostConstruct çalıştı.");
        this.animals  = new HashMap<>();
        this.animals.put(1,new Animal(1, "maymun"));
    }

    @GetMapping("/config")
    public String getCustomeConfigValues() {
        return developerName + " -- " + courseName;
    }

    //http://localhost:8585/workintech/animal
    @GetMapping()
    public List<Animal> getAnimals() {
        System.out.println("--- animals get all triggered!");
        //return animals.values().stream().toList();
        return new ArrayList<>(animals.values());
    }

    //http://localhost:8585/workintech/animal/65
    @GetMapping("/{id}")
    public Animal getAnimalById(@PathVariable("id") Integer id) {
        if(id<0) {
            System.out.println("id cannot be less then zero! ID: " + id);
            return null;
        }
        return this.animals.get(id);
    }


//    @PostMapping()
//    public void addAnimal(@RequestBody Integer id, String name) {
//        animals.put(id, new Animal(id,name));
//    }

    @PostMapping()
    public void addAnimal(@RequestBody Animal animal) {
        System.out.println("add animal is triggered!");
        animals.put(animal.getId(), animal);
    }


//    @PutMapping("/{id}")
//    public void updateAnimals(@PathVariable Integer id, @RequestBody Integer newId) {
//        Animal animal = animals.get(id);
//        animals.remove(id);
//        animal.setId(newId);
//        animals.put(newId, animal);
//    }

    @PutMapping("/{id}")
    public Animal updateAnimals(@PathVariable("id") int id, @RequestBody Animal newAnimal) {
        this.animals.replace(id, newAnimal);
        return this.animals.get(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable("id") Integer id) {
        this.animals.remove(id);
    }
}

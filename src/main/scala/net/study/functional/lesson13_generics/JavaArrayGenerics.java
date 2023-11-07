package net.study.functional.lesson13_generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JavaArrayGenerics {

    public static void main(String[] args) {


        final Cat[] cats = new Cat[10];

        //
        Animal[] animals = cats;

        animals[0] = new Dog("Lord");

        final List<Cat> invariantCatList = new ArrayList<>();

        final List<Dog> invariantDogList = new ArrayList<>();

        final List<Animal> invariantAnimalList = new ArrayList<>();

        final List<Object> invariantAnyObjectList = new ArrayList<>();

        // java covariance

        final List<?> anyAnimalList = invariantCatList;

        Object o = anyAnimalList.get(7);

        final List<Object> someObjects = new ArrayList<Object>();


        final List<? extends Animal> someAnimalList = invariantCatList;

        final List<? extends Animal> someAnimalList2 = invariantDogList;

        // java contrvariance

        final List<? super Animal> contrvariantAnimal = invariantAnimalList;
        final List<? super Animal> contrvariantAnimal2 = invariantAnyObjectList;

        final List<? super Cat> contrvariantCats = invariantCatList;
        final List<? super Cat> contrvariantCats2 = invariantAnimalList;
        final List<? super Cat> contrvariantCats3 = invariantAnyObjectList;

        Optional<Integer> integerOptional = Optional.of(1);

        Optional<? extends Number> covariantOptional = integerOptional;

        Number number = covariantOptional.get();


        contrvariantCats.add(new Cat("Felix"));
        contrvariantCats.add(new SuperCat("Felix"));

        contrvariantAnimal.add(new Dog("Lord"));
        contrvariantAnimal.add(new Cat("Murchik"));
        contrvariantAnimal.add(new SuperCat("Felix"));


    }

}

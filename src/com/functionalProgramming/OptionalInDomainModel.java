package com.functionalProgramming;

import java.util.Optional;
/*Using optional in your domain model is NOT a good practice as they are not serializable*/
class Person
{
    /*Person may or may not have a car*/
    private final Optional<Car> car;

    private final int age;

    public Person(Optional<Car> car,int age) {
        this.car = car;
        this.age=age;
    }

    public Optional<Car> getCar() {
        return car;
    }

    public int getAge() {
        return age;
    }
}
class Car
{
    /*Car may or may not have the insurance*/
    private final Optional<Insurance> insurance;

    public Car(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
class Insurance
{
    /*Insurance must have a name*/
    private final String insuranceName;

    public Insurance(String insuranceName) {
        this.insuranceName = insuranceName;
    }
    public String getInsuranceName() {
        return insuranceName;
    }
}
public class OptionalInDomainModel {

    public static void main(String[] args) {

        Insurance insuranceName=new Insurance("LICInsurance");
        Car car=new Car(Optional.of(insuranceName));
        Person person=new Person(Optional.of(car),15);
        getCarInsuranceName(person);


        Person person1=new Person(Optional.empty(),19);
        getCarInsuranceName(person1);

        Person person2=null;
        getCarInsuranceNameWithOptionalMap(person2);

        getCheapestInsuranceForCar(person2,car);
        checkIfPersonCanOwnACar(person);

    }

    public static void getCheapestInsuranceForCar(Person person,Car car)
    {
        Optional<Person> optionalPerson = Optional.ofNullable(person);
        Optional<Car> optionalCar = Optional.ofNullable(car);
        if(optionalPerson.isPresent() && optionalCar.isPresent())
            findCheapestInsurance(person,car);
        else
            System.out.println("Person and Car both should be present to find out the cheapest insurance");


    }

    private static void findCheapestInsurance(Person person,Car car)
    {
        //logic
        System.out.println("Cheapest Insurance is LIC");
    }

    /*Filter will be applied same as that of Stream, if the predicate returns true then a value otherwise an empty
     optional is obtain*/
    public static void checkIfPersonCanOwnACar(Person person)
    {
        Optional.ofNullable(person).
                filter(person1 -> person1.getAge() > 18).
                ifPresentOrElse(person1 -> System.out.println("Can own a car"),
                ()-> System.out.println("Can't own a car"));
    }

    public static void getCarInsuranceName(Person person)
    {
        /*If present or else was introduces in Java 9*/
        person.getCar()
                .ifPresentOrElse(car -> car.getInsurance().
                                ifPresentOrElse(insurance -> System.out.println(insurance.getInsuranceName()),
                        ()-> System.out.println("No car insurance name")),
                        ()-> System.out.println("Person doesn't have a car"));
    }

    public static void getCarInsuranceNameWithOptionalMap(Person person)
    {
          Optional<String> insuranceName=Optional.empty();
          /*If person is null then it will throw Optional.of(person) will throw Null Pointer Exception
          * hence using Optional.ofNullable*/
          insuranceName=
                  Optional.ofNullable(person).flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getInsuranceName);
         System.out.println(insuranceName.orElse("No insurance found"));
    }
}

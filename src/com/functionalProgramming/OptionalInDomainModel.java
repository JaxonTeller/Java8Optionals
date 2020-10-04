package com.functionalProgramming;

import java.util.Optional;

class Person
{
    /*Person may or may not have a car*/
    private final Optional<Car> car;

    public Person(Optional<Car> car) {
        this.car = car;
    }

    public Optional<Car> getCar() {
        return car;
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
        Person person=new Person(Optional.of(car));
        getCarInsuranceName(person);


        Person person1=new Person(Optional.empty());
        getCarInsuranceName(person1);

        Person person2=null;
        getCarInsuranceNameWithOptionalMap(person2);
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
                  Optional.ofNullable(person).flatMap(Person::getCar).flatMap(Car::getInsurance).map(insurance -> insurance.getInsuranceName());
         System.out.println(insuranceName.orElse("No insurance found"));
    }
}

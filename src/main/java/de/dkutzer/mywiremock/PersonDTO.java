package de.dkutzer.mywiremock;



public class PersonDTO {

    private String name;
    private String lastName;
    private int age;

    public PersonDTO(String name, String lastName, int age) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonDTO [name=");
        builder.append(name);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", age=");
        builder.append(age);
        builder.append("]");
        return builder.toString();
    }

    public PersonDTO() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
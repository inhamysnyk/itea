package fxdog;

public class Dog extends Animal {
    private Owner owner;

    public Dog(String name, String age) {
        super.name = name;
        super.age = age;
    }

    public Dog(Dog dog) {
        name=dog.getName();
        age=dog.getAge();
        owner=new Owner(dog.owner);
    }

    public String getOwner() {
        return String.valueOf(owner);
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Dog [name=" + name + ", age=" + age + ", owner=" + owner + "]";
    }

}

package Asuza.DesignPattern.PrototypePattern;

public class person implements Cloneable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    protected person clone() throws CloneNotSupportedException {
        return (person) super.clone();
    }
}

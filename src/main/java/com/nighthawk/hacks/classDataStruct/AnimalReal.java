package com.nighthawk.hacks.classDataStruct;

public class AnimalReal extends Generics {

    private static String classType = "Animal";
    public static KeyTypes key = KeyTypes.title;

    public static void setOrder(KeyTypes key) {
        AnimalReal.key = key;
    }

    public enum KeyType implements KeyTypes {
        title, uid, name, age
    }

    private String uid;
    private String name;

    public AnimalReal() {
        super.setType(classType);
    }

    public AnimalReal(String uid, String name) {
        this();
        this.uid = uid;
        this.name = name;
    }

    @Override
    protected KeyTypes getKey() {
        return AnimalReal.key;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public String toString() {
        String output = "";
        if (KeyType.uid.equals(this.getKey())) {
            output += this.uid;
        } else if (KeyType.name.equals(this.getKey())) {
            output += this.name;
        } else if (KeyType.age.equals(this.getKey())) {
            output += "0000" + this.getAge();
            output = output.substring(output.length() - 4);
        } else {
            output = super.getType() + ": " + this.uid + ", " + this.name + ", " + this.getAge();
        }
        return output;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        // Implement the age calculation logic if needed
        return -1;
    }

    public static AnimalReal[] init() {
        AnimalReal a1 = new AnimalReal();
        a1.setName("Lion");
        a1.setUid("Panthera leo");

        AnimalReal a2 = new AnimalReal();
        a2.setName("Asian elephant");
        a2.setUid("Elephas maximus");

        AnimalReal a3 = new AnimalReal();
        a3.setName("Wolf");
        a3.setUid("Canis lupus");

        AnimalReal a4 = null;
        try {
            a4 = new AnimalReal("Gorilla beringei", "Eastern gorilla");
        } catch (Exception e) {
        }

        AnimalReal a5 = null;
        try {
            a5 = new AnimalReal("Physeter macrocephalus", "Sperm whale");
        } catch (Exception e) {
        }

        AnimalReal animals[] = { a1, a2, a3, a4, a5 };
        return animals;
    }

    public static void main(String[] args) {
        AnimalReal animals[] = init();
        AnimalReal.setOrder(AnimalReal.KeyType.title);

        for (AnimalReal animal : animals) {
            System.out.println(animal);
        }
    }
}
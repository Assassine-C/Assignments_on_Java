import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Float.parseFloat;

/**
 * @author Anna Meshcheriakova
 * Class Main proceeds all simulations with animals under the condition and determines mistakes in input
 */

public class Main {
    private static int days;
    private static Field field;
    private static int numberOfAnimals;
    private static List<Animal> animals = new ArrayList<Animal>();
    private static final int MIN_NUMBER_OF_DAYS = 1;
    private static final int MAX_NUMBER_OF_DAYS = 30;
    private static final int MIN_NUMBER_OF_ANIMALS = 1;
    private static final int MAX_NUMBER_OF_ANIMALS = 20;
    private static final int NUMBER_OF_FIELDS_IN_LINE = 4;
    private static final int SECOND_FIELD_INT_LINE = 1;
    private static final int THIRD_FIELD_INT_LINE = 2;
    private static final int FORTH_FIELD_INT_LINE = 3;

    /**
     * Method readAnimals reads input.txt, check are inputs values under condition or not, and create a list of animals
     * @return List<Animal> list of all animals
     * @throws IOException is called if there is problems with read input file
     * @throws ExceptionsStopExecution is called if lines in file don't match the conditions of task
     */

    private static List<Animal> readAnimals() throws IOException, ExceptionsStopExecution {
        String filename = "input.txt";
        List<String[]> linesOfAnimals = new ArrayList();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        try {
            String str = reader.readLine();
            if (str.trim().isEmpty()) {
                throw new InvalidInputsException();
            }
            String[] line = str.split("\\s+");
            if (line.length != 1) {
                throw new InvalidInputsException();
            }
            days = Integer.parseInt(line[0]);
            line = reader.readLine().split("\\s+");
            if (line.length != 1) {
                throw new InvalidInputsException();
            }
            field = new Field(Float.parseFloat(line[0]));
            line = reader.readLine().split("\\s+");
            if (line.length != 1) {
                throw new InvalidInputsException();
            }
            numberOfAnimals = Integer.parseInt(line[0]);
            for (int i = 0; i < numberOfAnimals; i++) {
                str = reader.readLine();
                if (str == null) {
                    throw new InvalidInputsException();
                }
                line = str.split("\\s+");
                if (line.length == 1 && !line[0].equals("Lion") && !line[0].equals("Zebra")
                        && !line[0].equals("Boar")) {
                    throw new InvalidInputsException();
                }
                if (line.length != NUMBER_OF_FIELDS_IN_LINE) {
                    throw new InvalidNumberOfAnimalParametersException();
                }
                if (!line[0].equals("Lion") && !line[0].equals("Zebra") && !line[0].equals("Boar")) {
                    throw new InvalidInputsException();
                }
                switch (line[0]) {
                    case "Lion":
                        animals.add(new Lion(parseFloat(line[SECOND_FIELD_INT_LINE]),
                                parseFloat(line[THIRD_FIELD_INT_LINE]), parseFloat(line[FORTH_FIELD_INT_LINE])));
                        break;
                    case "Zebra":
                        animals.add(new Zebra(parseFloat(line[SECOND_FIELD_INT_LINE]),
                                parseFloat(line[THIRD_FIELD_INT_LINE]), parseFloat(line[FORTH_FIELD_INT_LINE])));
                        break;
                    case "Boar":
                        animals.add(new Boar(parseFloat(line[SECOND_FIELD_INT_LINE]),
                                parseFloat(line[THIRD_FIELD_INT_LINE]), parseFloat(line[FORTH_FIELD_INT_LINE])));
                        break;
                    default:
                        break;
                }
            }
            String oneMoreLine = reader.readLine();
            if (oneMoreLine != null) {
                throw new InvalidInputsException();
            }

            if (days < MIN_NUMBER_OF_DAYS || days > MAX_NUMBER_OF_DAYS) {
                throw new InvalidInputsException();
            }
            if (numberOfAnimals < MIN_NUMBER_OF_ANIMALS || numberOfAnimals > MAX_NUMBER_OF_ANIMALS) {
                throw new InvalidInputsException();
            }

        } catch (NullPointerException | InvalidInputsException
                 | InvalidNumberOfAnimalParametersException | NumberFormatException e) {
            if (e instanceof NullPointerException || e instanceof NumberFormatException) {
                throw new InvalidInputsException();
            } else {
                throw e;
            }
        }
        return animals;
    }

    /**
     * Method runSimulation make the task simulation during particular number of days
     * @param days int parameter shows how long simulation lasts
     * @param grassAmount float parameter shows how many grass in simulation at the beginning
     * @param animals List<Animal> list of animals in simulation
     */

    private static void runSimulation(int days, float grassAmount, List<Animal> animals) {
        for (int i = 0; i < days; i++) {

            removeDeadAnimals(Main.animals);
            for (Animal animal : Main.animals) {
                try {
                    if (animal.getEnergy() > 0) {
                        animal.eat(Main.animals, field);
                    }
                } catch (ExceptionNotStopExecution e) {
                    System.out.println(e.getMessage());
                }
            }
            for (Animal animal : Main.animals) {
                animal.decrementEnergy();
            }
            field.makeGrassGrow();
        }
        removeDeadAnimals(Main.animals);

    }

    /**
     * Method printAnimals print sound of animals who survived after simulation
     * @param animals list of all animals who still are alive
     */

    private static void printAnimals(List<Animal> animals) {
        for (Animal animal : Main.animals) {
            animal.makeSound();
        }
    }

    /**
     * Method removeDeadAnimals removes animals with energy 0 from the list of animals
     * @param animals list of all animals who still are alive
     */

    private static void removeDeadAnimals(List<Animal> animals) {
        Main.animals = animals.stream().filter(animal -> (animal.getEnergy() > 0)).toList();
    }

    public static void main(String[] args) {
        try {
            readAnimals();
            runSimulation(days, field.getGrassAmount(), animals);
            printAnimals(animals);
        } catch (ExceptionsStopExecution e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(new InvalidInputsException().getMessage());
        }
    }
}

/**
 * abstract class ExceptionsStopExecution is parent for exceptions that must stop execution of program
 * abstract class ExceptionsNotStopExecution is parent for exceptions that give a message of a mistake,
 * but not stop the program
 */

abstract class ExceptionsStopExecution extends Exception {
}

abstract class ExceptionNotStopExecution extends Exception {
}

class WeightOutOfBoundsException extends ExceptionsStopExecution {
    public String getMessage() {
        return "The weight is out of bounds";
    }
}

class EnergyOutOfBoundsException extends ExceptionsStopExecution {
    public String getMessage() {
        return "The energy is out of bounds";
    }
}

class SpeedOutOfBoundsException extends ExceptionsStopExecution {
    public String getMessage() {
        return "The speed is out of bounds";
    }
}

class GrassOutOfBoundException extends ExceptionsStopExecution {
    public String getMessage() {
        return "The grass is out of bounds";
    }
}

class InvalidNumberOfAnimalParametersException extends ExceptionsStopExecution {
    public String getMessage() {
        return "Invalid number of animal parameters";
    }
}

class InvalidInputsException extends ExceptionsStopExecution {
    public String getMessage() {
        return "Invalid inputs";
    }
}

class SelfHuntingException extends ExceptionNotStopExecution {
    public String getMessage() {
        return "Self-hunting is not allowed";
    }
}

class TooStrongPreyException extends ExceptionNotStopExecution {
    public String getMessage() {
        return "The prey is too strong or too fast to attack";
    }
}

class CannibalismException extends ExceptionNotStopExecution {
    public String getMessage() {
        return "Cannibalism is not allowed";
    }
}

interface Carnivore {
    public <T> Animal choosePrey(List<Animal> animals, T hunter);
    public void huntPrey(Animal hunter, Animal prey) throws ExceptionNotStopExecution;
}

interface Herbivore {
    public void grazeInTheField(Animal grazer, Field field);
}

interface Omnivore extends Carnivore, Herbivore {
}

enum AnimalSound {
    LION("Roar"),
    ZEBRA("Ihoho"),
    BOAR("Oink");

    private String sound;

    AnimalSound(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }
}

abstract class Animal {
    public static final float MIN_SPEED = 5.0f;
    public static final float MAX_SPEED = 60.0f;
    public static final float MIN_ENERGY = 0.0f;
    public static final float MAX_ENERGY = 100.0f;
    public static final float MIN_WEIGHT = 5.0f;
    public static final float MAX_WEIGHT = 200.0f;
    private float weight;
    private float speed;
    private float energy;

    protected Animal(float weight, float speed, float energy) throws ExceptionsStopExecution {
        if (weight >= MIN_WEIGHT && weight <= MAX_WEIGHT) {
            this.weight = weight;
        } else {
            throw new WeightOutOfBoundsException();
        }
        if (speed >= MIN_SPEED && speed <= MAX_SPEED) {
            this.speed = speed;
        } else {
            throw new SpeedOutOfBoundsException();
        }
        if (energy >= MIN_ENERGY && energy <= MAX_ENERGY) {
            this.energy = energy;
        } else {
            throw new EnergyOutOfBoundsException();
        }
    }

    public float getWeight() {
        return weight;
    }

    public float getSpeed() {
        return speed;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        if (energy > MAX_ENERGY) {
            this.energy = MAX_ENERGY;
        } else {
            this.energy = energy;
        }
    }

    public void makeSound() { }

    public void decrementEnergy() {
        energy--;
    }

    public void eat(List<Animal> animals, Field field) throws ExceptionNotStopExecution { }

}

/**
 * Class Field is a class of grass in simulation, it calls GrassOutOfBoundException if initial amount of grass is not
 * under condition of a task, and it grow grass every day of simulation twice but not more than 100
 */

class Field {
    public static final float MIN_GRASS = 0.0f;
    public static final float MAX_GRASS = 100.0f;
    private float grassAmount;

    public Field(float grassAmount) throws GrassOutOfBoundException {
        if (grassAmount >= MIN_GRASS && grassAmount <= MAX_GRASS) {
            this.grassAmount = grassAmount;
        } else {
            throw new GrassOutOfBoundException();
        }
    }

    public float getGrassAmount() {
        return grassAmount;
    }

    public void setGrassAmount(float grassAmount) {
        this.grassAmount = grassAmount;
    }

    public void makeGrassGrow() {
        if (grassAmount * 2 > MAX_GRASS) {
            this.grassAmount = MAX_GRASS;
        } else {
            this.grassAmount = grassAmount * 2;
        }
    }
}

/**
 * Class Lion is a derived class of Animal, also Lion implements interface Carnivore
 */

class Lion extends Animal implements Carnivore {
    public Lion(float weight, float speed, float energy) throws ExceptionsStopExecution {
        super(weight, speed, energy);
    }

    /**
     * Method choosePrey give an animal in front of hunter (carnivore)
     * @param animals list of all animals that are still alive
     * @param hunter is an Animal which is hunting
     * @return an animal in front of hunter in the circle
     * @param <T> can be any Animal who is carnivore or omnivore
     */

    @Override
    public <T> Animal choosePrey(List<Animal> animals, T hunter) {
        return animals.get((animals.indexOf(hunter) + 1) % animals.size());
    }

    /**
     * Method huntPrey checks can hunter hunt and eat the prey or not, and due to the situation call exception or
     * decrease energy of a prey which was hunted
     * @param hunter an Animal that is hunting
     * @param prey an Animal that is in front of hunter in the circle
     * @throws ExceptionNotStopExecution include exceptions correlate with hunting and call it
     */

    @Override
    public void huntPrey(Animal hunter, Animal prey) throws ExceptionNotStopExecution {
        if (hunter == prey) {
            throw new SelfHuntingException();
        } else if (prey.getClass().getSimpleName().equals(hunter.getClass().getSimpleName())) {
            throw new CannibalismException();
        } else if (hunter.getSpeed() <= prey.getSpeed() && hunter.getEnergy() <= prey.getEnergy()) {
            throw new TooStrongPreyException();
        } else {
            hunter.setEnergy(hunter.getEnergy() + prey.getWeight());
            while (prey.getEnergy() > 0) {
                prey.decrementEnergy();
            }
        }
    }

    @Override
    public void eat(List<Animal> animals, Field field) throws ExceptionNotStopExecution {
        huntPrey(this, choosePrey(animals, this));
    }

    @Override
    public void makeSound() {
        System.out.println(AnimalSound.LION.getSound());
    }
}

/**
 * Class Zebra is a derived class of Animal, also Zebra implements interface Herbivore
 */

class Zebra extends Animal implements Herbivore {
    private static final float FRACTION_OF_PRAY = 0.1f;
    public Zebra(float weight, float speed, float energy) throws ExceptionsStopExecution {
        super(weight, speed, energy);
    }

    /**
     * Method grazeInTheField checks is there enough grass for grazer or not, and increase energy of grazer
     * @param grazer an Animal that is herbivore or omnivore
     * @param field object of grass in simulation
     */

    @Override
    public void grazeInTheField(Animal grazer, Field field) {
        if (field.getGrassAmount() > FRACTION_OF_PRAY * grazer.getWeight()) {
            field.setGrassAmount((float) (field.getGrassAmount() - FRACTION_OF_PRAY * grazer.getWeight()));
            grazer.setEnergy((float) (grazer.getEnergy() + FRACTION_OF_PRAY * grazer.getWeight()));
        }
    }

    @Override
    public void eat(List<Animal> animals, Field field) {
        grazeInTheField(this, field);
    }

    @Override
    public void makeSound() {
        System.out.println(AnimalSound.ZEBRA.getSound());
    }
}

class Boar extends Animal implements Omnivore {
    private static final float FRACTION_OF_PRAY = 0.1f;
    public Boar(float weight, float speed, float energy) throws ExceptionsStopExecution {
        super(weight, speed, energy);
    }

    /**
     * Method choosePrey give an animal in front of hunter (carnivore)
     * @param animals list of all animals that are still alive
     * @param hunter is an Animal which is hunting
     * @return an animal in front of hunter in the circle
     * @param <T> can be any Animal who is carnivore or omnivore
     */

    @Override
    public <T> Animal choosePrey(List<Animal> animals, T hunter) {
        return animals.get((animals.indexOf(hunter) + 1) % animals.size());
    }

    /**
     * Method huntPrey checks can hunter hunt and eat the prey or not, and due to the situation call exception or
     * decrease energy of a prey which was hunted
     * @param hunter an Animal that is hunting
     * @param prey an Animal that is in front of hunter in the circle
     * @throws ExceptionNotStopExecution include exceptions correlate with hunting and call it
     */

    @Override
    public void huntPrey(Animal hunter, Animal prey) throws ExceptionNotStopExecution {
        if (hunter == prey) {
            throw new SelfHuntingException();
        } else if (prey.getClass().getSimpleName().equals(hunter.getClass().getSimpleName())) {
            throw new CannibalismException();
        } else if (hunter.getSpeed() <= prey.getSpeed() && hunter.getEnergy() <= prey.getEnergy()) {
            throw new TooStrongPreyException();
        } else {
            hunter.setEnergy(hunter.getEnergy() + prey.getWeight());
            while (prey.getEnergy() > 0) {
                prey.decrementEnergy();
            }
        }
    }

    /**
     * Method grazeInTheField checks is there enough grass for grazer or not, and increase energy of grazer
     * @param grazer an Animal that is herbivore or omnivore
     * @param field object of grass in simulation
     */

    @Override
    public void grazeInTheField(Animal grazer, Field field) {
        if (field.getGrassAmount() > FRACTION_OF_PRAY * grazer.getWeight()) {
            field.setGrassAmount((float) (field.getGrassAmount() - FRACTION_OF_PRAY * grazer.getWeight()));
            grazer.setEnergy((float) (grazer.getEnergy() + FRACTION_OF_PRAY * grazer.getWeight()));
        }
    }

    @Override
    public void eat(List<Animal> animals, Field field) throws ExceptionNotStopExecution {
        grazeInTheField(this, field);
        huntPrey(this, choosePrey(animals, this));
    }

    @Override
    public void makeSound() {
        System.out.println(AnimalSound.BOAR.getSound());
    }
}
import java.util.ArrayList;

abstract class Worker {
    private String lastName;
    private String supervisorLastName;
    protected ArrayList<Worker> subordinates;

    public Worker(String lastName, String supervisorLastName) {
        this.lastName = lastName;
        this.supervisorLastName = supervisorLastName;
        this.subordinates = new ArrayList<>();
    }

    public String getLastName() {
        return lastName;
    }

    public String getSupervisorLastName() {
        return supervisorLastName;
    }

    public void addSubordinate(Worker subordinate) {
        subordinates.add(subordinate);
    }

    // Виртуальные методы
    abstract void displayResponsibilities();

    abstract void displaySubordinates();

    // Другие общие методы или поля могут быть добавлены по необходимости
}

class Manager extends Worker {
    public Manager(String lastName, String supervisorLastName) {
        super(lastName, supervisorLastName);
    }

    @Override
    void displayResponsibilities() {
        System.out.println(getLastName() + " is managing the project.");
    }

    @Override
    void displaySubordinates() {
        System.out.println("Subordinates of " + getLastName() + ":");
        for (Worker subordinate : subordinates) {
            System.out.println(subordinate.getLastName());
        }
    }
}

class Developer extends Worker {
    public Developer(String lastName, String supervisorLastName) {
        super(lastName, supervisorLastName);
    }

    @Override
    void displayResponsibilities() {
        System.out.println(getLastName() + " is developing software.");
    }

    @Override
    void displaySubordinates() {
        // Developers may not have subordinates
        System.out.println(getLastName() + " does not have subordinates.");
    }
}

class Coder extends Worker {
    public Coder(String lastName, String supervisorLastName) {
        super(lastName, supervisorLastName);
    }

    @Override
    void displayResponsibilities() {
        System.out.println(getLastName() + " is coding.");
    }

    @Override
    void displaySubordinates() {
        // Coders may not have subordinates
        System.out.println(getLastName() + " does not have subordinates.");
    }
}

class Group {
    private ArrayList<Worker> workers;

    public Group() {
        workers = new ArrayList<>();
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void displayAll() {
        for (Worker worker : workers) {
            worker.displayResponsibilities();
            worker.displaySubordinates();
            System.out.println();
        }
    }

    public void displayHierarchy(String lastName) {
        for (Worker worker : workers) {
            if (worker.getLastName().equals(lastName)) {
                displayHierarchyRecursive(worker, 0);
                return;
            }
        }
        System.out.println("Worker with last name " + lastName + " not found.");
    }

    private void displayHierarchyRecursive(Worker worker, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("\t");
        }

        System.out.println(indent + worker.getLastName());
        for (Worker subordinate : worker.subordinates) {
            displayHierarchyRecursive(subordinate, depth + 1);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Group group = new Group();

        Worker manager = new Manager("Smith", "Johnson");
        Worker developer1 = new Developer("Johnson", "Smith");
        Worker developer2 = new Developer("Brown", "Smith");
        Worker coder1 = new Coder("Miller", "Brown");
        Worker coder2 = new Coder("Davis", "Brown");

        manager.addSubordinate(developer1);
        manager.addSubordinate(developer2);
        developer1.addSubordinate(coder1);
        developer2.addSubordinate(coder2);

        group.addWorker(manager);
        group.addWorker(developer1);
        group.addWorker(developer2);
        group.addWorker(coder1);
        group.addWorker(coder2);

        System.out.println("Displaying all workers:");
        group.displayAll();

        System.out.println("\nDisplaying hierarchy for worker with last name 'Smith':");
        group.displayHierarchy("Smith");
    }
}
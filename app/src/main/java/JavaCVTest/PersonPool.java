package JavaCVTest;

import java.util.ArrayList;
import java.util.List;

class PersonPool {
    private List<Person> personPool;

    public PersonPool() {

        personPool = new ArrayList<>();

    }

    public void addPerson(Person person) {

        personPool.add(person);

    }

    public void removePerson(Person person) {

        personPool.remove(person);

    }

    public Person getPerson(String name) {

        return personPool.stream().filter(p -> p.getName().equals(name)).findFirst().get();

    }

    public List<Person> getPersonPool() {

        return personPool;

    }

    public void showPeopleGauge() {

        for (Person person : personPool) {

            person.showGauge();

        }

    }


}
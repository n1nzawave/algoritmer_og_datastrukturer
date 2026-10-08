package eksempelklasser;

import java.util.Objects;

public class Person implements Comparable<Person> {
    private final String fornavn;
    private final String etternavn;

    public Person(String fornavn, String etternavn) {
        this.fornavn = Objects.requireNonNull(fornavn, "Fornavn kan ikke være null");
        this.etternavn = Objects.requireNonNull(etternavn, "Etternavn kan ikke være null");
    }

    public String fornavn() { return fornavn; }
    public String etternavn() { return etternavn; }

    public int compareTo(Person p) {
        int cmp = etternavn.compareTo(p.etternavn);
        if (cmp != 0) return cmp;
        return fornavn.compareTo(p.fornavn);
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        Person p = (Person) o;
        return fornavn.equals(p.fornavn) && etternavn.equals(p.etternavn);
    }

    public int hashCode() { return Objects.hash(etternavn, fornavn); }
    public String toString() { return fornavn + " " + etternavn; }
}

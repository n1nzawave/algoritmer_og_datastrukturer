package hjelpeklasser;

import java.util.*;


// Класс EnkeltLenketListe<T>, реализующий Kø<T> (Oppgave 1 til Avsnitt 4.2.4)
public class EnkeltLenketListe<T> implements Kø<T> {

    private static final class Node<T> {
        private T verdi;
        private Node<T> neste;

        private Node(T verdi, Node<T> neste) {
            this.verdi = verdi;
            this.neste = neste;
        }
    }

    private Node<T> hode, hale;
    private int antall;

    public EnkeltLenketListe() {
        hode = hale = null;
        antall = 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        if (antall == 0) hode = hale = new Node<>(verdi, null);
        else hale = hale.neste = new Node<>(verdi, null);
        antall++;
        return true;
    }

    public T hent(int indeks) {
        Node<T> p = hode;
        for (int i = 0; i < indeks; i++) p = p.neste;
        return p.verdi;
    }

    public T fjern(int indeks) {
        T temp;
        if (indeks == 0) {
            temp = hode.verdi;
            hode = hode.neste;
            if (antall == 1) hale = null;
        } else {
            Node<T> p = hode;
            for (int i = 1; i < indeks; i++) p = p.neste;
            Node<T> q = p.neste;
            temp = q.verdi;
            if (q == hale) hale = p;
            p.neste = q.neste;
        }
        antall--;
        return temp;
    }

    // --- Методы из Oppgave 1 (Programkode 4.2.4 b) ---
    @Override
    public T taUt() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return fjern(0); // возвращает и удаляет первый элемент
    }

    @Override
    public T kikk() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return hent(0);  // возвращает первый элемент
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public void nullstill() {
        hode = hale = null;
        antall = 0;
    }

    // Проверка из Programkode 4.2.4 c)
    public static void main(String[] args) {
        Kø<Integer> kø = new EnkeltLenketListe<>();
        for (int i = 1; i <= 10; i++) kø.leggInn(i);
        while (!kø.tom()) {
            System.out.print(kø.taUt() + " ");
        }
    }
}
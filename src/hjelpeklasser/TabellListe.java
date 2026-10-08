package hjelpeklasser;

import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TabellListe<T> implements Liste<T> {
    private T[] a;
    private int antall;

    // 1. Добавь конструктор сразу после переменных a и antall
    public TabellListe() {
        a = (T[]) new Object[10]; // создаем массив на 10 элементов
        antall = 0;               // пока элементов 0
    }

    // --- ОБЯЗАТЕЛЬНЫЕ МЕТОДЫ ИЗ ИНТЕРФЕЙСА LISTE ---

    @Override
    public boolean leggInn(T verdi) {
        a[antall] = verdi;
        antall++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        // Заглушка
    }

    @Override
    public boolean inneholder(T verdi) {
        return false; // Заглушка
    }

    @Override
    public T hent(int indeks) {
        return null; // Заглушка
    }

    @Override
    public int indeksTil(T verdi) {
        return -1; // Заглушка
    }

    @Override
    public T oppdater(int indeks, T verdi) {
        return null; // Заглушка
    }

    @Override
    public boolean fjern(T verdi) {
        return false; // Заглушка
    }

    @Override
    public T fjern(int indeks) {
        return null; // Заглушка
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
        // Заглушка
    }

    @Override
    public Iterator<T> iterator() {
        return new TabellListeIterator();
    }

    public boolean fjernHhis(Predicate<? super T> p){
        int sjekker = 0;
        int skriver = 0;
        int temp = antall;
        while (sjekker < antall){
            if (!p.test(a[sjekker])){
                a[skriver] = a[sjekker];
                skriver++;
                sjekker++;
            }
            else {
                sjekker++;
            }
        }
        antall = skriver;

        for (int i = antall; i < temp; i++){
            a[i] = null;
        }

        return temp > antall;
    }

    @Override
    public void forEach(Consumer<? super T> action){
        if (action == null){
            throw new NullPointerException();
        }

        for (int i = 0; i < antall; i++){
            action.accept(a[i]);
        }
    }


    // --- ВНУТРЕННИЙ КЛАСС ИТЕРАТОРА (ЗАДАНИЕ 1) ---

    private class TabellListeIterator implements Iterator<T> {
        private int denne = 0;
        private boolean fjernOK = false;

        @Override
        public boolean hasNext() {
            return denne < antall;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("Tomt eller ingen verdier igjen!");

            T denneVerdi = a[denne];
            denne++;
            fjernOK = true;

            return denneVerdi;
        }

        @Override
        public void remove() {
            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;
            antall--;
            denne--;

            System.arraycopy(a, denne + 1, a, denne, antall - denne);
            a[antall] = null;
        }

        public void forEachRemaining(Consumer<? super T> action){
            if (action == null){
                throw new NullPointerException();
            }

            for (int i = denne; i < antall; i++){
                action.accept(a[i]);
            }
            denne = antall;
        }
    } // TabellListeIterator
}

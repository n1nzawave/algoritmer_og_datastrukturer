package hjelpeklasser;

import java.util.*;

public class UsortertTabellPrioritetsKø<T> implements PrioritetsKø<T> {
    private T[] a;                       // неотсортированный массив
    private int antall;                  // количество элементов в очереди
    private Comparator<? super T> c;     // компаратор

    @SuppressWarnings("unchecked")
    public UsortertTabellPrioritetsKø(int størrelse, Comparator<? super T> c) {
        a = (T[]) new Object[størrelse];
        antall = 0;
        this.c = c;
    }

    public UsortertTabellPrioritetsKø(Comparator<? super T> c) {
        this(8, c);
    }

    public static <T extends Comparable<? super T>> PrioritetsKø<T> naturligOrdenKø() {
        return new UsortertTabellPrioritetsKø<>(Comparator.naturalOrder());
    }

    @Override
    public void leggInn(T verdi) {
        if (verdi == null) throw new IllegalArgumentException("Nullverdi!");
        if (antall == a.length) a = Arrays.copyOf(a, 2 * antall + 1);
        a[antall++] = verdi;
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    private int min() {
        int m = 0;
        T minverdi = a[0];

        for (int i = 0; i < antall; i++) {
            if (c.compare(a[i], minverdi) < 0) {
                m = i;
                minverdi = a[i];
            }
        }
        return m;
    }

    @Override
    public T kikk() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return a[min()];
    }

    @Override
    public T taUt() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");

        int m = min();
        T verdi = a[m];

        antall--;
        a[m] = a[antall];
        a[antall] = null;

        return verdi;
    }

    @Override
    public void nullstill() {
        while (antall > 0) a[--antall] = null;
    }

    // --- ЗАДАНИЕ 2 (вставь сюда свой метод taUt(T verdi)) ---
    @Override
    public boolean taUt(T verdi) {
        int i = 0;
        while (i < antall) {
            if (a[i].equals(verdi)) {
                antall--;
                a[i] = a[antall];
                a[antall] = null;
                return true;
            } else {
                i++;
            }
        }
        return false;
    }
}

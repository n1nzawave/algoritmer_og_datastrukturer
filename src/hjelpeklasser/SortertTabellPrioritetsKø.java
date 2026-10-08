package hjelpeklasser;

import java.util.*;

public class SortertTabellPrioritetsKø<T> implements PrioritetsKø<T>
{
    private T[] a;                       // массив, отсортированный по убыванию
    private int antall;                  // количество элементов в очереди
    private Comparator<? super T> c;     // компаратор

    @SuppressWarnings("unchecked")
    public SortertTabellPrioritetsKø(int størrelse, Comparator<? super T> c)
    {
        a = (T[]) new Object[størrelse];
        antall = 0;
        this.c = c;
    }

    public SortertTabellPrioritetsKø(Comparator<? super T> c)
    {
        this(8, c);
    }

    public static <T extends Comparable<? super T>> PrioritetsKø<T> naturligOrdenKø()
    {
        return new SortertTabellPrioritetsKø<>(Comparator.naturalOrder());
    }

    @Override
    public void leggInn(T verdi)
    {
        if (verdi == null) throw new IllegalArgumentException("Nullverdi!");
        if (antall == a.length) a = Arrays.copyOf(a, 2 * antall + 1);

        int i = antall - 1;
        for (; i >= 0 && c.compare(verdi, a[i]) > 0; i--) {
            a[i + 1] = a[i];
        }
        a[i + 1] = verdi;
        antall++;
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public T kikk()
    {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return a[antall - 1];
    }

    @Override
    public T taUt()
    {
        if (antall == 0) throw new NoSuchElementException("Køen er tom!");

        T minverdi = a[--antall];
        a[antall] = null;
        return minverdi;
    }

    @Override
    public void nullstill()
    {
        while (antall > 0) a[--antall] = null;
    }

    // --- ЗАДАНИЕ 3 (Oppgave 3): напиши код метода taUt(T verdi) ---
    @Override
    public boolean taUt(T verdi)
    {
        // TODO
        for (int i = 0; i < antall; i++){
            if (a[i].equals(verdi)){
                for (int j = i+1; j < antall; j++){
                    a[j-1] = a[j];
                }
                antall--;
                a[antall] = null;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        char[] c = "VMUSXQCJKOATZPLDHIRBNFEGYW".toCharArray();

        PrioritetsKø<Character> kø
                = UsortertTabellPrioritetsKø.naturligOrdenKø();

        for (int i = 0; i < c.length; i++) kø.leggInn(c[i]);

        while (!kø.tom()) System.out.print(kø.taUt() + " ");
    }
}
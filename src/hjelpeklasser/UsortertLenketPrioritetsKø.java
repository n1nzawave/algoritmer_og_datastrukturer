package hjelpeklasser;

import java.util.*;

public class UsortertLenketPrioritetsKø<T> implements PrioritetsKø<T>
{
    // TODO 1: Добавь внутренний класс Node<T> (verdi, neste)
    private static final class Node<T>{
        private T verdi;
        private Node<T> neste;

        private Node(T verdi, Node<T> neste){
            this.verdi = verdi;
            this.neste = neste;
        }
    }

    // TODO 2: Добавь поля класса (hode, antall, Comparator<? super T> c)
    private Node<T> hode;
    private int antall;
    private Comparator<? super T> c;

    // TODO 3: Добавь конструктор(ы)
    public UsortertLenketPrioritetsKø(Comparator<? super T> c){
        hode = null;
        antall = 0;
        this.c = c;
    }

    public static <T extends Comparable<? super T>> PrioritetsKø<T> naturligOrdenKø() {
        return new UsortertLenketPrioritetsKø<>(Comparator.naturalOrder());
    }

    @Override
    public void leggInn(T verdi)
    {
        // TODO: вставить новый узел в начало списка (hode)
        if (verdi == null) throw new IllegalArgumentException();
        Node<T> p = new Node<>(verdi, hode);
        hode = p;
        antall++;
    }

    @Override
    public T kikk()
    {
        // TODO: найти и вернуть значение с наивысшим приоритетом (без удаления)
        if (antall == 0) throw new NoSuchElementException("Køen er tom!");
        T min_verdi = hode.verdi;
        Node<T> p = hode;

        while (p != null){
            if (c.compare(p.verdi, min_verdi) < 0){
                min_verdi = p.verdi;
                p = p.neste;
            }
            else {
                p = p.neste;
            }
        }
        return min_verdi;
    }

    @Override
    public T taUt()
    {
        // TODO: найти, удалить из списка и вернуть значение с наивысшим приоритетом
        T min_verdi = kikk();
        taUt(min_verdi);
        return min_verdi;
    }

    @Override
    public boolean taUt(T verdi)
    {
        // TODO: найти конкретный элемент verdi, удалить его и вернуть true (или false, если нет)
        if (verdi == null){
            return false;
        }
        Node<T> p = hode;
        Node<T> forrige = null;
        while (p != null){
            if (hode.verdi.equals(verdi)){
                hode.verdi = null;
                hode = hode.neste;
                antall--;
                return true;
            }
            if (p.verdi.equals(verdi)){
                forrige.neste = p.neste;
                p.verdi = null;
                p.neste = null;
                antall--;
                return true;
            }
            else {
                forrige = p;
                p = p.neste;
            }
        }
        return false;
    }

    @Override
    public int antall()
    {
        // TODO
        return antall;
    }

    @Override
    public boolean tom()
    {
        // TODO
        if (antall == 0){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void nullstill()
    {
        // TODO
        Node<T> p = hode;
        while (p != null){
            Node<T> q = p.neste;
            p.verdi = null;
            p.neste = null;
            p = q;
        }
        hode = null;
        antall = 0;
    }
    public static void main(String[] args) {
        char[] c = "VMUSXQCJKOATZPLDHIRBNFEGYW".toCharArray();

        PrioritetsKø<Character> kø = UsortertLenketPrioritetsKø.naturligOrdenKø();

        for (char bokstav : c) {
            kø.leggInn(bokstav);
        }

        while (!kø.tom()) {
            System.out.print(kø.taUt() + " ");
        }
        // Ожидаемый вывод: A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
    }
}



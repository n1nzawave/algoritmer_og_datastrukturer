
package hjelpeklasser;

import java.util.*;

public class SortertLenketPrioritetsKø<T> implements PrioritetsKø<T>
{
    private static final class Node<T> {
        private T verdi;
        private Node<T> neste;

        private Node(T verdi, Node<T> neste) {
            this.verdi = verdi;
            this.neste = neste;
        }
    }

    private Node<T> hode;
    private int antall;
    private Comparator<? super T> c;

    public SortertLenketPrioritetsKø(Comparator<? super T> c) {
        hode = null;
        antall = 0;
        this.c = c;
    }

    public static <T extends Comparable<? super T>> PrioritetsKø<T> naturligOrdenKø() {
        return new SortertLenketPrioritetsKø<>(Comparator.naturalOrder());
    }

    @Override
    public void leggInn(T verdi) {
        // TODO:
        // 1. Проверить verdi == null
        // 2. Если список пуст ИЛИ verdi меньше hode.verdi -> вставить в hode
        // 3. Иначе пройти с указателями forrige и p до нужного места и вставить между ними
        // 4. Не забыть antall++
        if (verdi == null){
            throw new IllegalArgumentException();
        }
        if (antall == 0 || c.compare(verdi, hode.verdi) < 0){
            hode = new Node<>(verdi, hode);
        }
        else {
            Node<T> p = hode;
            Node<T> forrige = null;
            while (p != null){
                if (c.compare(verdi, p.verdi) >= 0){
                    forrige = p;
                    p = p.neste;
                }
                else {
                    break;
                }
            }
            forrige.neste = new Node<>(verdi, p);
        }
        antall++;
    }

    @Override
    public T kikk() {
        // TODO: вернуть значение из hode (предварительно проверив, не пуста ли очередь)
        if (antall == 0){
            throw new NoSuchElementException();
        }
        return hode.verdi;
    }

    @Override
    public T taUt() {
        // TODO: удалить первый узел (hode) и вернуть его значение за O(1)
        if (antall == 0){
            throw new NoSuchElementException("Koen er tom");
        }
        T temp = hode.verdi;
        hode.verdi = null;
        hode = hode.neste;
        antall--;
        return temp;
    }

    @Override
    public boolean taUt(T verdi) {
        // TODO: найти и удалить узел со значением verdi (как в UsortertLenketPrioritetsKø)
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
    public int antall() {
        // TODO
        return antall;
    }

    @Override
    public boolean tom() {
        // TODO
        if (antall == 0){
            return true;
        }
        return false;
    }

    @Override
    public void nullstill() {
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

        PrioritetsKø<Character> kø = SortertLenketPrioritetsKø.naturligOrdenKø();

        for (char bokstav : c) {
            kø.leggInn(bokstav);
        }

        while (!kø.tom()) {
            System.out.print(kø.taUt() + " ");
        }
        // Ожидаемый вывод: A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
    }
}
package hjelpeklasser;

import java.util.*;
import java.util.function.ObjIntConsumer;

public class BinTre<T>           // et generisk binærtre
{
    private static final class Node<T>  // en indre nodeklasse
    {
        private T verdi;            // nodens verdi
        private Node<T> venstre;    // referanse til venstre barn/subtre
        private Node<T> høyre;      // referanse til høyre barn/subtre

        private Node(T verdi, Node<T> v, Node<T> h)    // konstruktør
        {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi) { this.verdi = verdi; }  // konstruktør
    } // class Node<T>

    private Node<T> rot;      // referanse til rotnoden
    private int antall;       // antall noder i treet

    public BinTre() { rot = null; antall = 0; }          // konstruktør

    // Programkode 5.1.5 c)
    public BinTre(int[] posisjon, T[] verdi)  // konstruktør
    {
        if (posisjon.length > verdi.length) throw new
                IllegalArgumentException("Verditabellen har for få elementer!");

        for (int i = 0; i < posisjon.length; i++) leggInn(posisjon[i], verdi[i]);
    }

    public int antall() { return antall; }               // returnerer antallet

    public boolean tom() { return antall == 0; }         // tomt tre?

    // Programkode 5.1.5 b)
    public final void leggInn(int posisjon, T verdi)
    {
        if (posisjon < 1) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") < 1!");

        Node<T> p = rot, q = null;    // nodereferanser

        int filter = Integer.highestOneBit(posisjon) >> 1;   // filter = 100...00

        while (p != null && filter > 0)
        {
            q = p;
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;
            filter >>= 1;  // bitforskyver filter
        }

        if (filter > 0) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") mangler forelder!");
        else if (p != null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes fra før!");

        p = new Node<>(verdi);          // ny node

        if (q == null) rot = p;         // tomt tre - ny rot
        else if ((posisjon & 1) == 0)   // sjekker siste siffer i posisjon
            q.venstre = p;              // venstre barn til q
        else
            q.høyre = p;                // høyre barn til q

        antall++;                       // en ny verdi i treet
    }

    // Programkode 5.1.5 j)
    private Node<T> finnNode(int posisjon)  // finner noden med gitt posisjon
    {
        if (posisjon < 1) return null;

        Node<T> p = rot;   // nodereferanse
        int filter = Integer.highestOneBit(posisjon >> 1);   // filter = 100...00

        for (; p != null && filter > 0; filter >>= 1)
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;

        return p;   // p blir null hvis posisjon ikke er i treet
    }

    public boolean finnes(int posisjon)
    {
        return finnNode(posisjon) != null;
    }

    public T hent(int posisjon)
    {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        return p.verdi;
    }

    public T oppdater(int posisjon, T nyverdi)
    {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new
                IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        T gammelverdi = p.verdi;
        p.verdi = nyverdi;

        return gammelverdi;
    }

    // --- ЗАДАНИЕ 8 (Oppgave 8) ---
    public int nodetype(int posisjon)
    {
        // TODO: вернуть -1 (если нет в дереве), 1 (если лист), 0 (если внутренний узел)
        Node<T> p = finnNode(posisjon);

        if (p == null){
            return -1;
        } else if (p.høyre == null && p.venstre == null) {
            return 1;
        }
        else {
            return 0;
        }
    }

    // --- ЗАДАНИЕ 9 (Oppgave 9) ---
    public T fjern(int posisjon) {
        // TODO: удалить узел (только если это лист!) и вернуть его значение
        Node<T> p = finnNode(posisjon);

        if (p == null || p.venstre != null || p.høyre != null) {
            throw new IllegalArgumentException();
        }

        T temp = p.verdi;


        if (posisjon == 1) {
            rot = null;
        } else {
            Node<T> q = finnNode(posisjon / 2);
            if (posisjon % 2 == 0) {
                q.venstre = null;
                p.verdi = null;
            } else {
                q.høyre = null;
                q.høyre = null;
                p.verdi = null;
            }


        }
        antall--;
        return temp;
    }

    public void preorden(ObjIntConsumer<? super T> oppgave){
        if (tom())return;

        Node<T> p = rot;
        int k = 1;

        while (p != null){
            oppgave.accept(p.verdi, k);

            if (p.venstre != null){
                p = p.venstre;
                k = k*2;
            } else if (p.høyre != null) {
                p = p.høyre;
                k = k*2+1;
            }
            else {
                Node<T> q = null;
                int m = 0;

                int filter = Integer.highestOneBit(k >> 1);

                p = rot;
                int n = 1;

                for (; filter > 0; filter >>= 1){
                    if ((k & filter) == 0){
                        if (p.høyre != null){
                            q = p.høyre;
                            m = 2*n+1;
                        }
                        p = p.venstre;
                        n = n*2;
                    }
                    else {
                        p = p.høyre;
                        n = n*2+1;
                    }
                }
                p = q;
                k = m;
            }
        }
    }

    public void inorden(ObjIntConsumer<? super T> oppgave){
        if (tom())return;

        Node<T> p = rot;
        int k = 1;

        while (p.venstre != null){
            p = p.venstre;
            k = k * 2;
        }
        while (p != null){
            oppgave.accept(p.verdi, k);

            if (p.høyre != null){
                p = p.høyre;
                k = k * 2 + 1;
                while (p.venstre != null){
                    p = p.venstre;
                    k = k*2;
                }
            }
            else {
                Node<T> q = null;
                int m = 0;

                int filter = Integer.highestOneBit(k >> 1);

                p = rot;
                int n = 1;
                for (; filter > 0; filter >>= 1){
                    if ((filter & k) == 0){
                        q = p;
                        m = n;
                        p = p.venstre;
                        n = n * 2;
                    }
                    else {
                        p = p.høyre;
                        n = n * 2 + 1;
                    }
                }
                p = q;
                k = m;
            }
        }
    }

    public void postorden(ObjIntConsumer<? super T> oppgave){
        if (tom()){
            return;
        }

        Node<T> p = rot;
        int k = 1;


        while (p.venstre!=null || p.høyre != null){
            if (p.venstre != null){
                p = p.venstre;
                k = k*2;
            }
            else {
                p = p.høyre;
                k = k*2+1;
            }
        }

        while (p!=null){
            oppgave.accept(p.verdi, k);

            if (k == 1){
                return;
            }


            p = rot;
            int filter = Integer.highestOneBit(k >> 1);
            int n = 0;

            for (; filter > 1; filter >>= 1){
                if ((filter & k) == 0){
                    p = p.venstre;
                    n = n*2;
                }
                else {
                    p = p.høyre;
                    n = n*2+1;
                }
            }

            if (k % 2 == 0 && p.høyre != null){
                p = p.høyre;
                k = k+1;
                while (p.venstre!=null || p.høyre != null){
                    if (p.venstre != null){
                        p = p.venstre;
                        k = k*2;
                    }
                    else {
                        p = p.høyre;
                        k = k*2+1;
                    }
                }
            }
            else {
                k = k/2;
            }
        }
    }

        // Тест для Задания 1 (Programkode 5.1.5 d)
        public static void main (String[]args){
            // Oppgave 2
            int[] posisjon_tre1 = {1, 2, 3, 5, 6, 7, 10, 11, 12, 13, 21, 24, 25, 42, 43};
            Character[] verdi_tre1 = {'D', 'I', 'H', 'L', 'O', 'B', 'A', 'E', 'N', 'G', 'K', 'M', 'J', 'F', 'C'};

            int[] posisjon_tre2 = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 14, 22, 23, 28, 29};
            Character[] verdi_tre2 = {'E', 'I', 'B', 'G', 'A', 'H', 'K', 'L', 'O', 'D', 'N', 'M', 'C', 'J', 'F'};

            BinTre<Character> tre = new BinTre<>(posisjon_tre1, verdi_tre1);
            BinTre<Character> tre_2 = new BinTre<>(posisjon_tre2, verdi_tre2);
            System.out.println("Antall noder: " + tre.antall()); // Должно вывести 22
            System.out.println("Antall noder: " + tre_2.antall());

            //Oppgave 3

            int[] posisjon_tre1_oppgave_3 = {1, 2, 3, 4, 5, 6, 7, 9, 11, 12, 13, 14, 18, 19, 24, 25, 38, 39};
            Character[] verdi_tre1_oppgave3 = {'O', 'G', 'B', 'K', 'R', 'E', 'L', 'I', 'A', 'N', 'H', 'J', 'D', 'P', 'C', 'Q', 'M', 'F'};
            BinTre<Character> tre_oppgave3 = new BinTre<>(posisjon_tre1_oppgave_3, verdi_tre1_oppgave3);
            System.out.println("Antall noder: " + tre_oppgave3.antall());

            int[] posisjon_tre2_oppgave_3 = {1, 2, 3, 5, 6, 7, 10, 11, 13, 15, 23, 26, 30, 31, 52, 53};
            Integer[] verdi_tre2_oppgave3 = {10, 3, 7, 13, 2, 1, 5, 15, 19, 10, 10, 8, 5, 6, 9, 11};
            BinTre<Integer> tre2_oppgave3 = new BinTre<>(posisjon_tre2_oppgave_3, verdi_tre2_oppgave3);
            System.out.println("Antall noder: " + tre2_oppgave3.antall());


            // Oppgave 4
            int[] posisjon_tre_oppgave4 = {1, 2, 3, 5, 10, 11, 22, 23, 44, 47};
            Integer[] verdi_oppgave4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            BinTre<Integer> tre_oppgave4 = new BinTre<>(posisjon_tre_oppgave4, verdi_oppgave4);
            System.out.println("Antall noder: " + tre_oppgave4.antall());

            List<Integer> list = new ArrayList<>();
            int n = 100;
            for (int i = 1; i <= n; i++){
                list.add(i);
            }
            Collections.shuffle(list);



        }
    }
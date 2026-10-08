package hjelpeklasser;

import java.util.NoSuchElementException;

public class LenketToveiskø<T> implements Toveiskø<T>
{
    private static final class Node<T>     // en indre nodeklasse
    {
        T verdi;                             // nodens verdi
        Node<T> forrige;                     // peker til forrige node
        Node<T> neste;                       // peker til neste node

        Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }
    } // class Node

    private Node<T> start;                 // køens start
    private Node<T> slutt;                 // køens slutt
    private int antall;                    // antall i køen

    public LenketToveiskø()                // standardkonstruktør
    {
        start = slutt = null;
        antall = 0;
    }

    public void leggInnFørst(T verdi)
    {
        if (antall == 0)   // køen er tom
            start = slutt = new Node<T>(verdi,null,null);
        else
            start = start.forrige = new Node<T>(verdi,null,start);

        antall++;
    }

    public T taUtFørst()
    {
        if (antall == 0)  // køen er tom
            throw new NoSuchElementException("Køen er tom!");

        T temp = start.verdi;
        start.verdi = null;
        start = start.neste;

        if (antall == 1) slutt = null;
        else start.forrige = null;

        antall--;
        return temp;
    }

    public void leggInnSist(T verdi){
        if (antall == 0){
            start = slutt = new Node<T>(verdi,null,null);
        }
        else {
            Node<T> p = new Node<>(verdi, slutt, null);
            slutt.neste = p;
            slutt = p;
        }
        antall++;

    }

    public T taUtSist(){
        if (antall == 0)  // køen er tom
            throw new NoSuchElementException("Køen er tom!");

        T temp = slutt.verdi;
        slutt.verdi = null;
        slutt = slutt.forrige;

        if (antall == 1){
            start = null;
        }
        else {
            slutt.neste = null;
        }
        antall--;
        return temp;
    }

    public T kikkFørst(){
        if (antall == 0){
            throw new NoSuchElementException("Køen er tom!");
        }
        return start.verdi;
    }

    public T kikkSist(){
        if (antall == 0){
            throw new NoSuchElementException("Køen er tom!");
        }
        return slutt.verdi;
    }

    public boolean tom(){
        if (antall == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public int antall(){
        return antall;
    }

    public void nullstill(){
        Node<T> p = start;
        while (p != null){
            p.verdi = null;
            p.forrige = null;
            Node<T> q = p.neste;
            p.neste = null;
            p = q;
        }
        antall = 0;
        start = slutt = null;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append('[');

        Node<T> p = start;


        while (p!=null){
            if (p.neste != null){
                s.append(p.verdi).append(", ");
            }
            else {
                s.append(p.verdi);
            }
            p = p.neste;
        }

        s.append(']');

        return s.toString();
    }

} // class LenketToveiskø

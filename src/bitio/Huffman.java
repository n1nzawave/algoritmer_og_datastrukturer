package bitio;

import java.io.*;                               // filbehandling
import java.net.URL;                            // internett
import java.util.PriorityQueue;

import hjelpeklasser.*;                         // diverse metoder

public class Huffman                            // klasse for komprimering
{
    private static class Node                     // en basisklasse
    {
        private int frekvens;                       // nodens frekvens

        private Node() {}                           // standardkonstruktør

        private Node(int frekvens)  // konstruktør
        {
            this.frekvens = frekvens;
        }
    }  // class Node

    private static class indreNoder extends Node{
        private Node venstre;
        private Node hoyre;

        private indreNoder(Node venstre, Node hoyre, int frekvens){
            super(frekvens);
            this.hoyre = hoyre;
            this.venstre = venstre;
        }
    }

    private static class BladNode extends Node    // en subklasse
    {
        private final char tegn;                    // bladnodens tegn

        private BladNode(char tegn, int frekvens)   // konstruktør
        {
            super(frekvens);     // basisklassens konstruktør
            this.tegn = tegn;
        }
    }  // class BladNode

    private static Node byggHuffmanTre(int[] frekvens)
    {
        PriorityQueue<Node> kø = new PriorityQueue<>((p, q) -> p.frekvens - q.frekvens);

        for (int i = 0; i < frekvens.length; i++)
            if (frekvens[i] > 0)          // dette tegnet skal være med
                kø.add(new BladNode((char)i, frekvens[i]));

        if (kø.size() < 2)            // må ha minst to noder
            throw new IllegalArgumentException("Det er for få tegn!");

        while (kø.size() > 1)
        {
            Node v = kø.poll();                  // blir venstre barn
            Node h = kø.poll();                  // blir høyre barn
            int sum = v.frekvens + h.frekvens;   // summen av frekvensene

            kø.add(new indreNoder(v, h, sum));     // legger noden inn i køen
        }

        return kø.poll();                      // roten i treet
    }

}  // class Huffman

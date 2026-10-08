import java.util.*;

interface Stakk<T>{
    void leggInn(T verdi);
    T kikk();
    T taUt();
    int antall();
    boolean tom();
    void nullstill();
}


class TabellStakk<T> implements Stakk<T> {
    private T[] a;                     // массив для хранения элементов
    private int antall;                // количество элементов на стеке

    public TabellStakk() {             // конструктор по умолчанию (длина 8)
        this(8);
    }

    @SuppressWarnings("unchecked")
    public TabellStakk(int lengde) {   // конструктор с произвольной длиной
        if (lengde < 0) {
            throw new IllegalArgumentException("Negativ tabellengde!");
        }
        a = (T[]) new Object[lengde];
        antall = 0;
    }

    @Override
    public void leggInn(T verdi) {
        if (antall == a.length) {
            a = Arrays.copyOf(a, antall == 0 ? 1 : 2 * antall);
        }
        a[antall++] = verdi;
    }

    @Override
    public T kikk() {
        if (antall == 0) {
            throw new NoSuchElementException("Stakken er tom!");
        }
        return a[antall - 1];
    }

    @Override
    public T taUt() {
        if (antall == 0) {
            throw new NoSuchElementException("Stakken er tom!");
        }
        antall--;
        T temp = a[antall];
        a[antall] = null;
        return temp;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public int antall() {
        return antall;
    }

    // ==========================================================
    // ТВОИ ЗАДАНИЯ (Oppgaver til Avsnitt 4.1.2)
    // ==========================================================

    // Oppgave 1: Очистить стек (занулить ссылки в массиве 'a' и сбросить 'antall')
    @Override
    public void nullstill() {
        for (int i = 0; i < antall; i++){
            a[i] = null;
        }
        antall = 0;
    }

    // Oppgave 2: Вернуть строку вида "[1, 2, 3]" сверху вниз (от верхушки ко дну)
    @Override
    public String toString() {
        if (antall == 0){
            return "[]";
        }

        StringBuilder s = new StringBuilder();

        s.append('[');

        for (int i = antall - 1; i > 0; i--){
            s.append(a[i]).append(", ");
        }
        s.append(a[0]);
        s.append(']');
        return s.toString();
    }

    // Oppgave 3: Перевернуть стек A, используя два вспомогательных TabellStakk
    public static <T> void snu(Stakk<T> A) {
        TabellStakk<T> B = new TabellStakk<>();
        TabellStakk<T> C = new TabellStakk<>();

        while (!A.tom()){
            C.leggInn(A.taUt());
        }
        while (!C.tom()){
            B.leggInn(C.taUt());
        }
        while (!B.tom()){
            A.leggInn(B.taUt());
        }
    }

    // Oppgave 4: Скопировать стек A в пустой стек B (используя один TabellStakk и одну переменную T)
    public static <T> void kopier(Stakk<T> A, Stakk<T> B) {
        Stakk<T> C = new TabellStakk<>();
        T temp;
        while (!A.tom()){
            C.leggInn(A.taUt());
        }
        while (!C.tom()){
            temp = C.taUt();
            B.leggInn(temp);
            A.leggInn(temp);
        }
    }

    // Метод main для твоих собственных проверок
    public static void main(String[] args) {
            Stakk<Integer> A = new TabellStakk<>();
            Stakk<Integer> B = new TabellStakk<>();

            A.leggInn(1);
            A.leggInn(2);
            A.leggInn(3);
            System.out.println("Исходный стек A (сверху вниз): " + A); // [3, 2, 1]

            kopier(A, B);
            System.out.println("Стек A после копирования:     " + A); // [3, 2, 1]
            System.out.println("Скопированный стек B:         " + B); // [3, 2, 1]

            snu(A);
            System.out.println("Стек A после разворота (snu): " + A); // [1, 2, 3]

            B.nullstill();
            System.out.println("Стек B после nullstill():     " + B); // []
    }
}
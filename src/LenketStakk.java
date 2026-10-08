import java.util.*;

public class LenketStakk<T> implements Stakk<T> {

    // Внутренний класс узла (вагона)
    private static final class Node<T> {
        private T verdi;
        private Node<T> neste;

        private Node(T verdi, Node<T> neste) {
            this.verdi = verdi;
            this.neste = neste;
        }
    } // class Node

    private Node<T> hode;             // верхушка стека (top of the stack)
    private int antall;               // количество элементов в стеке

    public LenketStakk() {            // конструктор пустого стека
        hode = null;
        antall = 0;
    }

    // Метод из учебника (Programkode 4.1.3 c)
    @Override
    public void leggInn(T verdi) {
        hode = new Node<>(verdi, hode);
        antall++;
    }

    // ==========================================================
    // ТВОИ ЗАДАНИЯ (Oppgaver til Avsnitt 4.1.3)
    // ==========================================================

    // Oppgave 1: Посмотреть верхний элемент (без удаления)
    @Override
    public T kikk() {
        // TODO: Напиши код для задания 1 здесь
        if (antall == 0 || hode == null){
            throw new NoSuchElementException("Stakken er tom!!!");
        }
        else {
            return hode.verdi;
        }
        // ответ на вопрос: скорость метода O(1) так как мы просто возвращаем самый верхний элемент и не надо проходить по всему списку и делать какие-то действия.

    }

    // Oppgave 2: Удалить и вернуть верхний элемент
    @Override
    public T taUt() {
        // TODO: Напиши код для задания 2 здесь
        if (antall == 0 || hode == null){
            throw new NoSuchElementException("Stakken er tom!!!");
        }
        T temp = hode.verdi;
        hode.verdi = null;
        hode = hode.neste;
        antall--;
        return temp;

        // ответ на вопрос: как я понимаю то также O(1) потому что мы обращаемся просто к самому верхнему элементу. конечно мы создаем временную переменную и тратим ресурсы на это и на обращение к другим переменным но это временную сложность глобально не повышает.

    }

    // Oppgave 3: Проверить, пуст ли стек
    @Override
    public boolean tom() {
        // TODO: Напиши код для задания 3 здесь
        if (antall == 0){
            return true;
        }
        else {
            return false;
        }
    }

    // Oppgave 3: Вернуть количество элементов
    @Override
    public int antall() {
        // TODO: Напиши код для задания 3 здесь
        return antall;
    }

    // Заглушка для интерфейса Stakk<T> (скорее всего, будет в следующем задании)
    @Override
    public void nullstill() {
        // TODO: Будет реализовано позже
    }

    // Метод main для быстрой проверки
    public static void main(String[] args) {
        Stakk<Integer> s = new LenketStakk<>();
        s.leggInn(10);
        s.leggInn(20);
        s.leggInn(30);

        System.out.println("Antall: " + s.antall()); // Ожидается: 3
        System.out.println("Kikk:   " + s.kikk());   // Ожидается: 30
        System.out.println("Ta ut:  " + s.taUt());   // Ожидается: 30
        System.out.println("Ny topp:" + s.kikk());   // Ожидается: 20
    }
}
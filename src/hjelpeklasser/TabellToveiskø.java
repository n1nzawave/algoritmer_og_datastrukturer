package hjelpeklasser;

import java.util.*;

public class TabellToveiskø<T> implements Toveiskø<T>
{
    private T[] a;      // кольцевой массив
    private int fra;    // индекс первого элемента в очереди
    private int til;    // индекс первой свободной ячейки (за последним элементом)

    @SuppressWarnings("unchecked")
    public TabellToveiskø(int lengde)
    {
        if (lengde < 1)
            throw new IllegalArgumentException("Må ha positiv lengde!");

        a = (T[]) new Object[lengde];
        fra = til = 0;
    }

    public TabellToveiskø()   // стандартный конструктор (длина 8)
    {
        this(8);
    }

    // Удвоение массива при заполнении (из Programkode 4.2.2 d)
    private T[] utvidTabell(int lengde)
    {
        @SuppressWarnings("unchecked")
        T[] b = (T[]) new Object[lengde];

        // копируем интервал a[fra:a.length> в начало b
        System.arraycopy(a, fra, b, 0, a.length - fra);

        // копируем интервал a[0:fra> следом в b
        System.arraycopy(a, 0, b, a.length - fra, fra);

        fra = 0;
        til = a.length;
        return b;
    }

    // Добавление в начало (Programkode 4.3.3 a)
    @Override
    public void leggInnFørst(T verdi)
    {
        if (fra == 0) fra = a.length - 1; else fra--;
        a[fra] = verdi;
        if (fra == til) a = utvidTabell(2 * a.length);
    }

    // Добавление в конец (бывший leggInn из Programkode 4.2.2 c)
    @Override
    public void leggInnSist(T verdi)
    {
        a[til] = verdi;
        til++;
        if (til == a.length) til = 0;
        if (fra == til) a = utvidTabell(2 * a.length);
    }

    // Просмотр первого элемента
    @Override
    public T kikkFørst()
    {
        if (fra == til) throw new NoSuchElementException("Køen er tom!");
        return a[fra];
    }

    // Просмотр последнего элемента (Programkode 4.3.3 a)
    @Override
    public T kikkSist()
    {
        if (fra == til) throw new NoSuchElementException("Køen er tom!");
        if (til == 0) return a[a.length - 1];
        else return a[til - 1];
    }

    // Удаление первого элемента (бывший taUt из Programkode 4.2.2 e)
    @Override
    public T taUtFørst()
    {
        if (fra == til) throw new NoSuchElementException("Køen er tom!");

        T temp = a[fra];
        a[fra] = null;
        fra++;
        if (fra == a.length) fra = 0;
        return temp;
    }

    // Удаление последнего элемента (Programkode 4.3.3 a)
    @Override
    public T taUtSist()
    {
        if (fra == til) throw new NoSuchElementException("Køen er tom!");
        if (til == 0) til = a.length - 1; else til--;
        T temp = a[til];
        a[til] = null;
        return temp;
    }

    // Количество элементов (Programkode 4.2.2 a)
    @Override
    public int antall()
    {
        return fra <= til ? til - fra : a.length + til - fra;
    }

    // Проверка на пустоту
    @Override
    public boolean tom()
    {
        return fra == til;
    }

    // Очистка очереди
    @Override
    public void nullstill()
    {
        while (fra != til) {
            a[fra++] = null;
            if (fra == a.length) fra = 0;
        }
        fra = til = 0;
    }

    // Вывод в строку "[A, B, C]"
    @Override
    public String toString()
    {
        if (fra == til) return "[]";

        StringBuilder s = new StringBuilder();
        s.append('[').append(a[fra]);

        int i = fra + 1;
        if (i == a.length) i = 0;

        while (i != til) {
            s.append(", ").append(a[i]);
            i++;
            if (i == a.length) i = 0;
        }
        s.append(']');
        return s.toString();
    }

    // Тест для проверки работы
    public static void main(String[] args)
    {
        Toveiskø<String> k = new TabellToveiskø<>();
        k.leggInnFørst("B");
        k.leggInnFørst("A");
        k.leggInnSist("C");
        k.leggInnSist("D");

        System.out.println("Очередь: " + k);                     // [A, B, C, D]
        System.out.println("Первый:  " + k.kikkFørst());         // A
        System.out.println("Последн: " + k.kikkSist());          // D
        System.out.println("Удаляем первого: " + k.taUtFørst()); // A
        System.out.println("Удаляем последн: " + k.taUtSist());  // D
        System.out.println("Осталось: " + k + ", antall: " + k.antall()); // [B, C], antall: 2
    }
}

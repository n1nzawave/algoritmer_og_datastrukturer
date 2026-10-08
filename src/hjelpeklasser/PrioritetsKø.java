package hjelpeklasser;


public interface PrioritetsKø<T>
{
    public void leggInn(T verdi);            // добавить элемент
    public T kikk();                         // посмотреть элемент с высшим приоритетом
    public T taUt();                         // удалить и вернуть элемент с высшим приоритетом
    public boolean taUt(T verdi);            // удалить конкретное значение (Задание 2)
    public int antall();                     // количество элементов
    public boolean tom();                    // пуста ли очередь?
    public void nullstill();                 // очистить очередь
}

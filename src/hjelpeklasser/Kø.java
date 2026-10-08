package hjelpeklasser;

// Интерфейс Kø<T> из Programkode 4.2.1 a)
public interface Kø<T> {
    public boolean leggInn(T verdi);
    public T kikk();
    public T taUt();
    public int antall();
    public boolean tom();
    public void nullstill();
}



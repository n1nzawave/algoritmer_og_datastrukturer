package eksempelklasser;

public final class Heltall implements Comparable<Heltall> {
    private final int verdi;    // целое число как переменная экземпляра

    public Heltall(int verdi) { this.verdi = verdi; }   // конструктор

    public int intVerdi() { return verdi; }             // аксессор

    public int compareTo(Heltall h) {
        return verdi - h.verdi;
    }

    public boolean equals(Object o) {
        if (o == this) return true;   // сравнение с самим собой
        if (!(o instanceof Heltall)) return false;  // неверный тип данных
        return verdi == ((Heltall)o).verdi;
    }

    public boolean equals(Heltall h) { return verdi == h.verdi; }

    //public int hashCode() { return 31 + verdi; }

    public String toString() { return Integer.toString(verdi); }
}


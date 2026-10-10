package hjelpeklasser;

import java.util.Comparator;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SBinTre<T> {

    // Внутренний класс для узла
    private static final class Node<T> {
        T verdi;                 // Значение узла
        Node<T> venstre, høyre;  // Левый и правый дети

        private Node(T verdi) {
            this.verdi = verdi;
            venstre = høyre = null;
        }
    }

    private Node<T> rot;                           // Корень дерева
    private int antall;                            // Количество узлов
    private final Comparator<? super T> comp;      // Компаратор для сравнения

    // Конструктор с компаратором
    public SBinTre(Comparator<? super T> c) {
        rot = null;
        antall = 0;
        comp = c;
    }

    // Пустой конструктор (использует естественную сортировку, например для Integer)
    public static <T extends Comparable<? super T>> SBinTre<T> sbintre() {
        return new SBinTre<>(Comparator.naturalOrder());
    }

    // Метод добавления (из твоего параграфа 5.2.3)
    public final boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;
        int cmp = 0;

        while (p != null) {
            q = p;
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }

        p = new Node<>(verdi);

        if (q == null) rot = p;
        else if (cmp < 0) q.venstre = p;
        else q.høyre = p;

        antall++;
        return true;
    }

    // Метод для вычисления высоты дерева
    public int høyde() {
        return høyde(rot);
    }

    // Рекурсивный помощник для высоты
    private int høyde(Node<T> p) {
        if (p == null) return -1; // Пустое поддерево дает высоту -1 (так принято в норв. учебнике)
        return 1 + Math.max(høyde(p.venstre), høyde(p.høyre));
    }

    // Метод для проверки количества элементов
    public int antall() {
        return antall;
    }

    // ==========================================
    // ТУТ ЖЕ МОЖНО ЗАПУСТИТЬ ТВОЕ ЗАДАНИЕ 6
    // ==========================================
    public static void main(String[] args) {
        int[] nVerdier = {100, 1000, 10000}; // Проверим сразу для всех трех вариантов

        for (int n : nVerdier) {
            // 1. Создаем список от 1 до n и перемешиваем
            List<Integer> list = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                list.add(i);
            }
            Collections.shuffle(list); // Случайная перестановка

            // 2. Создаем дерево
            SBinTre<Integer> tre = SBinTre.sbintre();

            // 3. Заполняем дерево
            for (int tall : list) {
                tre.leggInn(tall);
            }

            // 4. Считаем высоту и логарифм
            int hoyde = tre.høyde();
            double log2n = Math.log(n) / Math.log(2);

            // 5. Выводим результат
            System.out.println("n = " + n);
            System.out.println("Фактическая высота: " + hoyde);
            System.out.println("Идеальная высота log2(n): " + String.format("%.2f", log2n));
            System.out.println("----------------------------------");
        }
    }
}
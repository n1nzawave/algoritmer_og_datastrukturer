import eksempelklasser.Heltall;
import eksempelklasser.Person;
import hjelpeklasser.*;

public static void main(String[] args){
    Kø<Integer> kø = new EnkeltLenketListe<>();
    for (int i = 1; i <= 10; i++) kø.leggInn(i);
    while (!kø.tom())
    {
        System.out.print(kø.taUt() + " ");
    }
}

private static void flettesortering(int[] a, int[] b, int fra, int til) {
    // 1. Добавляем вот эту строчку в самое начало:
    System.out.println("Nå legges a[" + fra + ":" + til + "> på programstakken");

    if (til - fra <= 1) return;   // a[fra:til> har maks ett element

    int m = (fra + til)/2;        // midt mellom fra og til

    flettesortering(a,b,fra,m);   // sorterer a[fra:m>
    flettesortering(a,b,m,til);   // sorterer a[m:til>

    // fletter a[fra:m> og a[m:til>
    flett(a,b,fra,m,til);         // Programkode 1.3.11 f)
}

private static void flett(int[] a, int[] b, int fra, int m, int til)
{
    int n = m - fra;                // antall elementer i a[fra:m>
    System.arraycopy(a,fra,b,0,n);  // kopierer a[fra:m> over i b[0:n>

    int i = 0, j = m, k = fra;      // løkkevariabler og indekser

    while (i < n && j < til)        // fletter b[0:n> og a[m:til> og
    {                               // legger resultatet i a[fra:til>
        a[k++] = b[i] <= a[j] ? b[i++] : a[j++];
    }

    while (i < n) a[k++] = b[i++];  // tar med resten av b[0:n>
}

private static void kvikksortering0(int[] a, int venstre, int hoyre)
{
    System.out.println("Kallet med [" + venstre + ":" + hoyre + "] starter!");
    if (venstre >= hoyre) return;   // 0 или 1 элемент — сортировать нечего

    // pivotPos - финальная позиция пивота после партиционирования
    int pivotPos = sParter0(a, venstre, hoyre, (venstre + hoyre) / 2);

    if (pivotPos - venstre > 1){
        kvikksortering0(a, venstre, pivotPos - 1);  // сортируем левую часть
        // сортируем правую часть
    }
    if (hoyre - pivotPos > 1){
        kvikksortering0(a, pivotPos + 1, hoyre);
    }

    System.out.println("Kallet med [" + venstre + ":" + hoyre + "] er ferdig!");
}

private static int sParter0(int[] a, int venstre, int hoyre, int pivotIndeks) {
    // 1. Переносим пивот в конец диапазона — так с ним проще работать
    bytt(a, pivotIndeks, hoyre);

    // 2. Партиционируем всё, КРОМЕ пивота (он сейчас на месте hoyre)
    int pivotPos = parter0(a, venstre, hoyre - 1, a[hoyre]);

    // 3. Ставим пивот на его настоящее финальное место
    bytt(a, pivotPos, hoyre);

    return pivotPos;
}

private static int parter0(int[] a, int venstre, int hoyre, int pivotVerdi) {
    int lagretVerdi = a[venstre]; // сохраняем первый элемент, чтобы не потерять его
    int grense = venstre;         // grense = граница "маленьких" элементов = текущая "дырка"
    venstre++;                    // начинаем проверку со следующего элемента

    while (venstre <= hoyre) {
        if (comp(a[venstre], pivotVerdi)) {
            // элемент a[venstre] меньше пивота -> он должен быть слева от grense

            a[grense] = a[venstre];     // кладём маленький элемент в дырку
            a[venstre] = a[grense + 1]; // дырка "перекатывается" на 1 позицию вправо
            grense++;                   // граница маленьких элементов сдвинулась
        }
        venstre++; // двигаемся дальше в любом случае
    }

    // Кладём сохранённый элемент в его законное место
    a[grense] = lagretVerdi;

    // Проверяем, куда на самом деле относится сохранённый элемент
    if (comp(a[grense], pivotVerdi)) {
        return grense + 1; // он тоже маленький -> граница сдвигается ещё на 1
    } else {
        return grense;     // он большой/равный -> граница остаётся тут
    }
}

public static int teller = 0;

private static boolean comp(int x, int y) {
    teller++;  // увеличиваем счетчик при каждом вызове
    return x < y; // возвращаем результат обычного сравнения
}



public static int B(int n, int k){
    if (k == 0){
        return 1;
    }
    if (k == n){
        return 1;
    }
    return B(n-1, k-1) + B(n-1, k);
}

// Задание 13
public static int fib_new(int n, int a, int b){
    int temp = b;
    b = a + temp;
    a = temp;
    if (n <= 1) return a;
    else return fib_new(n-1, a, b);
}

// Задание 11: когда я запускал код на числах 20 и 30 программа находила их буквально за секунду.
// на числе 40 чуть дольше где-то секунды 2. а на числе 50 программа выдала большое отрицательно число
// и работала очень долго, наверное около 40 секунд.
public static int fib(int n)         // det n-te Fibonacci-tallet
{
    if (n <= 1) return n;              // fib(0) = 0, fib(1) = 1
    else return fib(n-1) + fib(n-2);   // summen av de to foregående
}

// Задание 10
public static int rekursiv_faktorial(int n){
    if (n == 1){
        return 1;
    }
    else {
        return n*rekursiv_faktorial(n-1);
    }
}

// Задание 7
public static int rekursiv_kvadrat(int n){
    if (n == 1){
        return n;
    }
    else{
        return n*n + rekursiv_kvadrat(n-1);
    }
}

// Задание 1
public static int a_i(int n){
    int first_last_number = 1;
    int second_last_number = 2;
    if (n == 0){
        return 1;
    }
    if (n == 1){
        return 2;
    }
    for (int i = 2; i <= n; i++){
        int temp = second_last_number;
        second_last_number = 2*second_last_number + 3*first_last_number;
        first_last_number = temp;
    }
    return second_last_number;
}

public static <T extends Comparable<? super T>>
void innsettingssortering(T[] a) {

    for (int i = 1; i < a.length; i++) {

        T temp = a[i];

        int indeks_for_temp = binærsøk(a, 0, i, temp);

        if (indeks_for_temp < 0)
            indeks_for_temp = -indeks_for_temp - 1;

        System.arraycopy(
                a,
                indeks_for_temp,
                a,
                indeks_for_temp + 1,
                i - indeks_for_temp
        );

        a[indeks_for_temp] = temp;
    }
}

public static <T extends Comparable<? super T>> int binærsøk(T[] a, int fra, int til, T verdi)
{
    fratilKontroll(a.length,fra,til);  // se Programkode 1.2.3 a)
    int v = fra, h = til - 1;  // v og h er intervallets endepunkter

    while (v <= h)    // fortsetter så lenge som a[v:h] ikke er tom
    {
        int m = (v + h)/2;      // heltallsdivisjon - finner midten
        T midtverdi = a[m];   // hjelpevariabel for midtverdien

        if (verdi.compareTo(midtverdi) == 0);         // funnet
        else if (verdi.compareTo(midtverdi) > 0)
            v = m + 1; // verdi i a[m+1:h]
        else  h = m - 1;                           // verdi i a[v:m-1]
    }

    return -(v + 1);    // ikke funnet, v er relativt innsettingspunkt
}

public static <T extends Comparable<? super T>> int maks(T[] a) {
    int m = 0;
    T maksverdi = a[0];
    for (int i = 1; i < a.length; i++) {
        if (a[i].compareTo(maksverdi) > 0) {
            maksverdi = a[i];
            m = i;
        }
    }
    return m;
}


public static void f(int a, float b) {
    System.out.println("Вызван f(int, float)");
}
/*
public static void f(float a, int b) {
    System.out.println("Вызван f(float, int)");
}
*/
public static int forskjellige(int[] a){
    int sborshchik = 1;
    int iskatel = 1;

    if (a.length == 0){
        sborshchik = 0;
    }

    while (iskatel < a.length){
        if (a[iskatel] == a[sborshchik - 1]){
            iskatel++;
        }
        else {
            a[sborshchik] = a[iskatel];
            sborshchik++;
            iskatel++;
        }
    }
    return sborshchik;
}


public static String enkelFletting(String a, String b){
    char[] a_split = a.toCharArray();
    char[] b_split = b.toCharArray();
    char[] c = new char[a_split.length + b_split.length];
    int i = 0, j = 0, k = 0;

    while (i < a_split.length && j < b_split.length){
        c[k++] = a_split[i++];
        c[k++] = b_split[j++];
    }

    if (i < a_split.length){
        System.arraycopy(a_split, i, c, k, a_split.length - i);
    }
    if (j < b_split.length){
        System.arraycopy(b_split, j, c, k, b_split.length - j);
    }

    String result = new String(c);

    return result;
}

public static int[] enkelFletting(int[] a, int[] b) {
    int[] c = new int[a.length + b.length];  // массив правильного размера
    int i = 0, j = 0, k = 0;                 // переменные для циклов

    while (i < a.length && j < b.length) {
        c[k++] = a[i++];      // сначала значение из a
        c[k++] = b[j++];      // затем значение из b
    }
    // мы должны захватить остаток
    if (i < a.length){
        System.arraycopy(a, i, c, k, a.length - i);
    }
    if (j < b.length){
        System.arraycopy(b, j, c, k, b.length - j);
    }

    return c;
}
// Программный код 1.3.11 a)


// 1. Метод, который ставит разделитель на его "идеальное" место


// 2. Основной рекурсивный метод быстрой сортировки
/* private static void kvikksortering0(int[] a, int v, int h) {
    if (h-v < 20) return;



    // базовый случай (остановка рекурсии)
    int k = sParter0(a, v, h, h);  // делим массив
    kvikksortering0(a, v, k - 1);          // сортируем левую часть
    kvikksortering0(a, k + 1, h);          // сортируем правую часть
}
*/
// 3. Публичный метод-обертка для удобного запуска
public static void kvikksortering(int[] a) {
   kvikksortering0(a, 0, a.length - 1);
}


// Публичный метод для разделения части массива
public static int parter(int[] a, int fra, int til, int skilleverdi) {
    fratilKontroll(a.length, fra, til);
    return parter0(a, fra, til - 1, skilleverdi);
}

// Публичный метод для разделения всего массива
public static int parterAll(int[] a, int skilleverdi) {
    return parter0(a, 0, a.length - 1, skilleverdi);
}

// Публичный метод для sParter (ставит разделитель на идеальное место)
public static int sParter(int[] a, int skilleverdi) {
    int indeks = -1;
    // Находим индекс разделителя в массиве
    for (int i = 0; i < a.length; i++) {
        if (a[i] == skilleverdi) { indeks = i; break; }
    }
    if (indeks == -1) throw new IllegalArgumentException("Разделитель не найден!");
    return sParter0(a, 0, a.length - 1, indeks);
}


public static int[] randPerm(int n) {
    java.util.Random r = new java.util.Random();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) a[i] = i + 1; // Заполняем 1, 2, 3... n

    for (int k = n - 1; k > 0; k--) {
        int i = r.nextInt(k + 1); // Случайный индекс от 0 до k
        bytt(a, k, i);
    }
    return a;
}


public static void fratilKontroll(int tablengde, int fra, int til) {
    if (fra < 0) throw new ArrayIndexOutOfBoundsException("fra(" + fra + ") < 0");
    if (til > tablengde) throw new ArrayIndexOutOfBoundsException("til(" + til + ") > tablengde(" + tablengde + ")");
    if (fra > til) throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ")");
}

    public static void innsettingssortering_V3(int[] a, int k){
        for (int i = k-1; i < a.length - 1; i+=k){

            for (int j = i-k+1; j <= i; j++){
                int temp = a[j];
                int b = j-1;
                while (b >= i-k+1 && temp < a[b]){
                    a[b + 1] = a[b];
                    b--;
                }
                a[b + 1] = temp;
            }
            int[] temp_arr = new int[k];
            System.arraycopy(a, i-k+1, temp_arr, 0, k);

            int right_indeks = i;
            int siste_temp_indeks = k-1;
            int siste_a_indeks = i-k;

            while (right_indeks >= 0 && siste_temp_indeks >= 0 && siste_a_indeks >= 0){
                if (a[siste_a_indeks] > temp_arr[siste_temp_indeks]){
                    a[right_indeks] = a[siste_a_indeks];
                    right_indeks--;
                    siste_a_indeks--;
                }else {
                    a[right_indeks] = temp_arr[siste_temp_indeks];
                    siste_temp_indeks--;
                    right_indeks--;
                }
            }

            while (siste_temp_indeks >= 0){
                a[right_indeks] = temp_arr[siste_temp_indeks];
                right_indeks--;
                siste_temp_indeks--;
            }
        }
        int start = (a.length / k) * k;
        if (start == 0) start = 1;

        for (int i = start; i < a.length; i++){
            int temp = a[i];
            int j = i - 1;

            while (j >= 0 && temp <= a[j]){
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = temp;
        }
    }


public static void innsettingssortering_V2(int[] a) {
    for (int i = 1; i < a.length - 1; i += 2){
        int verdi1 = a[i];
        int verdi2 = a[i + 1];
        int storste_tall = 0;
        int minste_tall = 0;
        int j = i - 1;

        if (verdi1 > verdi2){
            storste_tall = verdi1;
            minste_tall = verdi2;
        }
        else {
            storste_tall = verdi2;
            minste_tall = verdi1;
        }

        while (j >= 0 && storste_tall < a[j]){
            a[j+2] = a[j];
            j--;
        }
        a[j + 2] = storste_tall;

        while (j >= 0 && minste_tall < a[j]){
            a[j + 1] = a[j];
            j--;
        }
        a[j + 1] = minste_tall;
    }

    if (a.length % 2 == 0){
        int siste_tall = a[a.length - 1];
        int j = a.length - 2;
        while (siste_tall < a[j] && j > 0){
            a[j + 1] = a[j];
            a[j] = siste_tall;
            j--;
        }
    }

}


public static void skriv(int[] a, int fra, int til) {
    if (fra >= til) return;
    System.out.print(a[fra]);
    for (int i = fra + 1; i < til; i++) {
        System.out.print(" " + a[i]);
    }
}

public static void skrivln(int[] a, int fra, int til) {
    skriv(a, fra, til);
    System.out.println();
}



public static boolean erVokal(char c){
    char[] vokaler = {'a', 'e', 'i', 'o', 'u', 'y'};
    for (int i = 0; i < vokaler.length; i++){
        if (c == vokaler[i]){
            return true;
        }
    }
    return false;
}


public static void bytt(int[] a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
}

public static int min(int[] a, int fra, int til){
    int min_indeks = fra;
    int minste_tall = a[fra];

    for (int i = fra + 1; i < til; i++){
        if (a[i] < minste_tall){
            minste_tall = a[i];
            min_indeks = i;
        }
    }
    return min_indeks;
}

public static void snu(int[] a){
    int start = 0;
    for (int i = a.length - 1; i > 0; i--){
        if (start <= i){
            int temp = a[start];
            a[start] = a[i];
            a[i] = temp;
            start++;
        }
    }
}

public static void utvalgssortering(int[] a) {
    for (int i = 0; i < a.length - 1; i++){
        int min_indeks = i;
        for (int j = i + 1; j < a.length; j++){
            if (a[j] < a[min_indeks]){
                min_indeks = j;
            }
        }
        int temp = a[min_indeks];
        a[min_indeks] = a[i];
        a[i] = temp;
    }
}




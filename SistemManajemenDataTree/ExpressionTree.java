import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;

/**
 * Class ExpressionTree mengimplementasikan struktur data
 * Expression Tree (pohon ekspresi) untuk merepresentasikan dan
 * mengevaluasi ekspresi matematika.
 *
 * Pada Expression Tree:
 *   - setiap daun (leaf) berisi operand (angka),
 *   - setiap node internal berisi operator (+, -, *, /).
 *
 * Alur pembentukan pohon:
 *   1. ekspresi infix dipecah menjadi token,
 *   2. token diubah ke notasi postfix (algoritma shunting-yard),
 *   3. pohon dibangun dari notasi postfix,
 *   4. pohon dievaluasi secara rekursif (postorder).
 */
public class ExpressionTree {

    /**
     * Class Node merepresentasikan satu simpul pada Expression Tree.
     * nilai dapat berupa operator ("+", "-", "*", "/") atau operand.
     */
    private static class Node {
        String nilai; // operator atau operand
        Node kiri;    // operand kiri
        Node kanan;   // operand kanan

        Node(String nilai) {
            this.nilai = nilai;
        }
    }

    // Akar dari Expression Tree
    private Node root;

    /**
     * Membangun Expression Tree dari sebuah ekspresi infix.
     *
     * @param ekspresi ekspresi matematika, contoh: "(3 + 5) * 2"
     * @throws IllegalArgumentException jika ekspresi tidak valid
     */
    public void bangunDariInfix(String ekspresi) {
        List<String> token = tokenisasi(ekspresi);
        List<String> postfix = keNotasiPostfix(token);
        this.root = bangunDariPostfix(postfix);
    }

    /* ============================================================
     * TAHAP 1: TOKENISASI
     * ============================================================ */

    /**
     * Memecah string ekspresi menjadi daftar token berupa angka,
     * operator, dan tanda kurung. Spasi diabaikan.
     *
     * @param ekspresi ekspresi mentah dari pengguna
     * @return daftar token
     */
    private List<String> tokenisasi(String ekspresi) {
        List<String> token = new ArrayList<>();
        int i = 0;
        int n = ekspresi.length();

        while (i < n) {
            char c = ekspresi.charAt(i);

            // Lewati spasi
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // Baca bilangan (mendukung desimal, contoh: 3.14)
            if (Character.isDigit(c) || c == '.') {
                StringBuilder angka = new StringBuilder();
                while (i < n && (Character.isDigit(ekspresi.charAt(i))
                        || ekspresi.charAt(i) == '.')) {
                    angka.append(ekspresi.charAt(i));
                    i++;
                }
                token.add(angka.toString());
                continue;
            }

            // Baca operator dan tanda kurung
            if (c == '+' || c == '-' || c == '*' || c == '/'
                    || c == '(' || c == ')') {
                token.add(String.valueOf(c));
                i++;
                continue;
            }

            // Karakter selain di atas dianggap tidak valid
            throw new IllegalArgumentException(
                    "Karakter tidak dikenal: '" + c + "'");
        }

        return token;
    }

    /* ============================================================
     * TAHAP 2: INFIX -> POSTFIX (SHUNTING-YARD)
     * ============================================================ */

    /**
     * Mengubah daftar token infix menjadi notasi postfix
     * menggunakan algoritma shunting-yard dari Dijkstra.
     *
     * @param infix daftar token dalam notasi infix
     * @return daftar token dalam notasi postfix
     */
    private List<String> keNotasiPostfix(List<String> infix) {
        List<String> output = new ArrayList<>();
        Deque<String> stackOperator = new ArrayDeque<>();

        for (String token : infix) {

            if (isOperator(token)) {

                // Keluarkan operator dengan prioritas >= token saat ini
                while (!stackOperator.isEmpty()
                        && isOperator(stackOperator.peek())
                        && prioritas(stackOperator.peek()) >= prioritas(token)) {
                    output.add(stackOperator.pop());
                }
                stackOperator.push(token);

            } else if (token.equals("(")) {
                stackOperator.push(token);

            } else if (token.equals(")")) {

                // Keluarkan sampai bertemu kurung buka
                while (!stackOperator.isEmpty()
                        && !stackOperator.peek().equals("(")) {
                    output.add(stackOperator.pop());
                }
                if (stackOperator.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Tanda kurung tidak seimbang.");
                }
                stackOperator.pop(); // buang "("

            } else {
                // Token berupa operand (angka)
                output.add(token);
            }
        }

        // Keluarkan sisa operator pada stack
        while (!stackOperator.isEmpty()) {
            String sisa = stackOperator.pop();
            if (sisa.equals("(")) {
                throw new IllegalArgumentException(
                        "Tanda kurung tidak seimbang.");
            }
            output.add(sisa);
        }

        return output;
    }

    /* ============================================================
     * TAHAP 3: MEMBANGUN POHON DARI POSTFIX
     * ============================================================ */

    /**
     * Membangun Expression Tree dari notasi postfix menggunakan stack.
     *
     * @param postfix daftar token postfix
     * @return akar Expression Tree
     */
    private Node bangunDariPostfix(List<String> postfix) {
        Deque<Node> stack = new ArrayDeque<>();

        for (String token : postfix) {

            if (isOperator(token)) {

                // Operator butuh dua operand dari stack
                if (stack.size() < 2) {
                    throw new IllegalArgumentException(
                            "Ekspresi tidak valid.");
                }

                Node node = new Node(token);
                node.kanan = stack.pop(); // operand kanan diambil lebih dulu
                node.kiri = stack.pop();  // lalu operand kiri
                stack.push(node);

            } else {
                // Operand langsung menjadi daun
                stack.push(new Node(token));
            }
        }

        // Setelah selesai, stack harus menyisakan tepat satu node (akar)
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Ekspresi tidak valid.");
        }

        return stack.pop();
    }

    /* ============================================================
     * EVALUASI
     * ============================================================ */

    /**
     * Mengevaluasi Expression Tree dan mengembalikan hasil hitung.
     *
     * @return hasil evaluasi ekspresi
     * @throws IllegalStateException jika pohon belum dibangun
     */
    public double evaluasi() {
        if (root == null) {
            throw new IllegalStateException("Pohon belum dibangun.");
        }
        return evaluasiRekursif(root);
    }

    /**
     * Method rekursif untuk mengevaluasi pohon secara postorder:
     * hitung subtree kiri dan kanan lebih dulu, lalu terapkan operator.
     *
     * @param node node saat ini
     * @return nilai hasil evaluasi subtree
     */
    private double evaluasiRekursif(Node node) {

        // Basis: node daun berisi angka
        if (node.kiri == null && node.kanan == null) {
            return Double.parseDouble(node.nilai);
        }

        // Hitung kedua sisi terlebih dahulu (postorder)
        double kiri = evaluasiRekursif(node.kiri);
        double kanan = evaluasiRekursif(node.kanan);

        // Terapkan operator
        switch (node.nilai) {
            case "+": return kiri + kanan;
            case "-": return kiri - kanan;
            case "*": return kiri * kanan;
            case "/":
                if (kanan == 0) {
                    throw new ArithmeticException("Pembagian dengan nol.");
                }
                return kiri / kanan;
            default:
                throw new IllegalArgumentException(
                        "Operator tidak dikenal: " + node.nilai);
        }
    }

    /* ============================================================
     * TRAVERSAL (untuk memperlihatkan notasi ekspresi)
     * ============================================================ */

    /**
     * Traversal inorder menghasilkan kembali notasi infix.
     * Tanda kurung ditambahkan agar prioritas operasi tetap jelas.
     *
     * @return ekspresi dalam notasi infix
     */
    public String inorder() {
        StringBuilder sb = new StringBuilder();
        inorderRekursif(root, sb);
        return sb.toString().trim();
    }

    private void inorderRekursif(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        boolean operator = isOperator(node.nilai);
        if (operator) {
            sb.append("( ");
        }
        inorderRekursif(node.kiri, sb);
        sb.append(node.nilai).append(" ");
        inorderRekursif(node.kanan, sb);
        if (operator) {
            sb.append(") ");
        }
    }

    /**
     * Traversal preorder menghasilkan notasi prefix (Polish notation).
     *
     * @return ekspresi dalam notasi prefix
     */
    public String preorder() {
        StringBuilder sb = new StringBuilder();
        preorderRekursif(root, sb);
        return sb.toString().trim();
    }

    private void preorderRekursif(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        sb.append(node.nilai).append(" ");
        preorderRekursif(node.kiri, sb);
        preorderRekursif(node.kanan, sb);
    }

    /**
     * Traversal postorder menghasilkan notasi postfix (Reverse Polish).
     *
     * @return ekspresi dalam notasi postfix
     */
    public String postorder() {
        StringBuilder sb = new StringBuilder();
        postorderRekursif(root, sb);
        return sb.toString().trim();
    }

    private void postorderRekursif(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        postorderRekursif(node.kiri, sb);
        postorderRekursif(node.kanan, sb);
        sb.append(node.nilai).append(" ");
    }

    /* ============================================================
     * VISUALISASI POHON
     * ============================================================ */

    /**
     * Menampilkan Expression Tree dalam bentuk gambar pohon
     * dari atas ke bawah ke layar.
     */
    public void cetakTree() {
        if (root == null) {
            System.out.println("(pohon kosong)");
            return;
        }
        for (String baris : gambar(root).baris) {
            System.out.println(baris);
        }
    }

    /**
     * Class bantu penampung hasil penggambaran satu subtree:
     * kumpulan baris teks beserta posisi tengah akar subtree.
     */
    private static class Gambar {
        List<String> baris; // baris-baris teks blok subtree
        int lebar;          // lebar blok (jumlah kolom)
        int tinggi;         // tinggi blok (jumlah baris)
        int tengah;         // kolom posisi tengah akar

        Gambar(List<String> baris, int lebar, int tinggi, int tengah) {
            this.baris = baris;
            this.lebar = lebar;
            this.tinggi = tinggi;
            this.tengah = tengah;
        }
    }

    /**
     * Menghasilkan gambar pohon secara rekursif. Operator dibungkus
     * tanda kurung, contoh (*), agar mudah dibedakan dari operand.
     *
     * Metode ini menggabungkan blok gambar anak kiri dan kanan lalu
     * menempatkan akar di atasnya beserta garis penghubung '/' dan '\'.
     *
     * @param node akar subtree yang digambar
     * @return objek Gambar berisi baris teks subtree
     */
    private Gambar gambar(Node node) {

        String label = isOperator(node.nilai)
                ? "(" + node.nilai + ")" : node.nilai;

        // Kasus daun: cukup satu baris berisi label
        if (node.kiri == null && node.kanan == null) {
            List<String> baris = new ArrayList<>();
            baris.add(label);
            return new Gambar(baris, label.length(), 1, label.length() / 2);
        }

        // Kasus hanya punya anak kiri
        if (node.kanan == null) {
            Gambar kiri = gambar(node.kiri);
            int u = label.length();
            List<String> baris = new ArrayList<>();
            baris.add(ulang(' ', kiri.tengah + 1)
                    + ulang('_', kiri.lebar - kiri.tengah - 1) + label);
            baris.add(ulang(' ', kiri.tengah) + "/"
                    + ulang(' ', kiri.lebar - kiri.tengah - 1 + u));
            for (String b : kiri.baris) {
                baris.add(b + ulang(' ', u));
            }
            return new Gambar(baris, kiri.lebar + u,
                    kiri.tinggi + 2, kiri.lebar + u / 2);
        }

        // Kasus hanya punya anak kanan
        if (node.kiri == null) {
            Gambar kanan = gambar(node.kanan);
            int u = label.length();
            List<String> baris = new ArrayList<>();
            baris.add(label + ulang('_', kanan.tengah)
                    + ulang(' ', kanan.lebar - kanan.tengah));
            baris.add(ulang(' ', u + kanan.tengah) + "\\"
                    + ulang(' ', kanan.lebar - kanan.tengah - 1));
            for (String b : kanan.baris) {
                baris.add(ulang(' ', u) + b);
            }
            return new Gambar(baris, kanan.lebar + u,
                    kanan.tinggi + 2, u / 2);
        }

        // Kasus punya dua anak
        Gambar kiri = gambar(node.kiri);
        Gambar kanan = gambar(node.kanan);
        int u = label.length();

        String barisPertama = ulang(' ', kiri.tengah + 1)
                + ulang('_', kiri.lebar - kiri.tengah - 1)
                + label
                + ulang('_', kanan.tengah)
                + ulang(' ', kanan.lebar - kanan.tengah);

        String barisKedua = ulang(' ', kiri.tengah) + "/"
                + ulang(' ', kiri.lebar - kiri.tengah - 1 + u + kanan.tengah)
                + "\\"
                + ulang(' ', kanan.lebar - kanan.tengah - 1);

        // Samakan jumlah baris kedua blok agar bisa disandingkan
        List<String> barisKiri = new ArrayList<>(kiri.baris);
        List<String> barisKanan = new ArrayList<>(kanan.baris);
        while (barisKiri.size() < barisKanan.size()) {
            barisKiri.add(ulang(' ', kiri.lebar));
        }
        while (barisKanan.size() < barisKiri.size()) {
            barisKanan.add(ulang(' ', kanan.lebar));
        }

        List<String> baris = new ArrayList<>();
        baris.add(barisPertama);
        baris.add(barisKedua);
        for (int i = 0; i < barisKiri.size(); i++) {
            baris.add(barisKiri.get(i) + ulang(' ', u) + barisKanan.get(i));
        }

        return new Gambar(baris, kiri.lebar + kanan.lebar + u,
                Math.max(kiri.tinggi, kanan.tinggi) + 2,
                kiri.lebar + u / 2);
    }

    /* ============================================================
     * METODE BANTU
     * ============================================================ */

    /**
     * Memeriksa apakah sebuah token merupakan operator.
     *
     * @param token token yang diperiksa
     * @return true jika operator
     */
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-")
                || token.equals("*") || token.equals("/");
    }

    /**
     * Menentukan tingkat prioritas sebuah operator.
     * '*' dan '/' memiliki prioritas lebih tinggi daripada '+' dan '-'.
     *
     * @param operator operator yang dinilai
     * @return nilai prioritas
     */
    private int prioritas(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }

    /**
     * Menghasilkan string berisi pengulangan sebuah karakter.
     *
     * @param c     karakter yang diulang
     * @param jumlah banyaknya pengulangan
     * @return string hasil pengulangan (kosong jika jumlah <= 0)
     */
    private String ulang(char c, int jumlah) {
        if (jumlah <= 0) {
            return "";
        }
        return String.valueOf(c).repeat(jumlah);
    }
}

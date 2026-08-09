/*
Nama Kelompok 2:

1. AHMAD WIDAD IZZUDDIN - 2902795331
2. ELVIA DESTIANI - 2902801265
3. MUHAMMAD ZIKRI - 2902794266
4. TIARA ANDINA DALIMUNTHE - 2902772063
5. VINA NAMIRA ANDRINA ANDIDI - 2902789202
*/

import java.util.List;
import java.util.Scanner;

/**
 * Class Main merupakan class utama untuk menjalankan
 * Sistem Manajemen Data Berbasis Tree.
 *
 * Program ini menggabungkan dua struktur data berbasis pohon:
 *   1. Binary Search Tree (BST) untuk sistem indeks pencarian dokumen.
 *   2. Expression Tree untuk merepresentasikan dan mengevaluasi
 *      ekspresi matematika.
 */
public class Main {

    // Scanner digunakan untuk menerima input dari pengguna
    static Scanner scanner = new Scanner(System.in);

    // Struktur data BST untuk mengelola data dokumen
    static BSTDokumen bst = new BSTDokumen();

    /**
     * Method main merupakan titik awal eksekusi program.
     *
     * @param args parameter dari command line
     */
    public static void main(String[] args) {

        int pilihan;

        // Menampilkan menu selama pengguna belum memilih keluar
        do {
            tampilkanMenu();

            System.out.print("Pilih menu: ");
            pilihan = bacaInteger();

            System.out.println();

            switch (pilihan) {

                case 1:
                    tambahDokumen();
                    break;

                case 2:
                    cariDokumen();
                    break;

                case 3:
                    hapusDokumen();
                    break;

                case 4:
                    tampilkanSemuaDokumen();
                    break;

                case 5:
                    tampilkanTraversal();
                    break;

                case 6:
                    muatDataContoh();
                    break;

                case 7:
                    ujiPerformaPencarian();
                    break;

                case 8:
                    evaluasiEkspresi();
                    break;

                case 9:
                    System.out.println("Program selesai. Terima kasih!");
                    break;

                default:
                    System.out.println("Pilihan menu tidak tersedia.");
            }

            System.out.println();

        } while (pilihan != 9);

        // Menutup Scanner setelah program selesai
        scanner.close();
    }

    /**
     * Menampilkan daftar menu utama aplikasi.
     */
    public static void tampilkanMenu() {

        System.out.println("==================================================");
        System.out.println("      SISTEM MANAJEMEN DATA BERBASIS TREE");
        System.out.println("        (BST & EXPRESSION TREE)");
        System.out.println("==================================================");
        System.out.println("--- Indeks Dokumen (Binary Search Tree) ---");
        System.out.println("1. Tambah Dokumen");
        System.out.println("2. Cari Dokumen (berdasarkan ID)");
        System.out.println("3. Hapus Dokumen");
        System.out.println("4. Tampilkan Semua Dokumen");
        System.out.println("5. Tampilkan Traversal Tree");
        System.out.println("6. Muat Data Contoh (15 dokumen)");
        System.out.println("7. Uji Performa Pencarian (BST vs Linear)");
        System.out.println("--- Ekspresi Matematika (Expression Tree) ---");
        System.out.println("8. Evaluasi Ekspresi Matematika");
        System.out.println("--------------------------------------------------");
        System.out.println("9. Keluar");
        System.out.println("==================================================");
    }

    /* ============================================================
     * FITUR BST: MANAJEMEN DOKUMEN
     * ============================================================ */

    /**
     * Menangani proses penambahan dokumen ke dalam BST.
     * Pengguna diminta memasukkan ID, judul, dan kategori dokumen.
     */
    public static void tambahDokumen() {

        System.out.println("=== TAMBAH DOKUMEN ===");

        System.out.print("Masukkan ID       : ");
        int id = bacaInteger();

        System.out.print("Masukkan Judul    : ");
        String judul = scanner.nextLine().trim();
        if (judul.isEmpty()) {
            System.out.println("Judul tidak boleh kosong.");
            return;
        }

        System.out.print("Masukkan Kategori : ");
        String kategori = scanner.nextLine().trim();
        if (kategori.isEmpty()) {
            System.out.println("Kategori tidak boleh kosong.");
            return;
        }

        Dokumen dokumen = new Dokumen(id, judul, kategori);
        boolean berhasil = bst.insert(dokumen);

        if (berhasil) {
            System.out.println("\nDokumen berhasil ditambahkan!");
        } else {
            System.out.println("\nGagal menambahkan data.");
            System.out.println("ID " + id + " sudah digunakan.");
        }
    }

    /**
     * Menangani pencarian dokumen berdasarkan ID melalui BST.
     */
    public static void cariDokumen() {

        System.out.println("=== CARI DOKUMEN ===");

        System.out.print("Masukkan ID dokumen: ");
        int id = bacaInteger();

        // Ukur waktu pencarian agar dapat dibandingkan
        long mulai = System.nanoTime();
        Dokumen dokumen = bst.search(id);
        long selesai = System.nanoTime();

        if (dokumen != null) {
            System.out.println("\nData ditemukan!");
            System.out.println("------------------------------");
            System.out.println(dokumen);
            System.out.println("------------------------------");
        } else {
            System.out.println("\nDokumen dengan ID " + id
                    + " tidak ditemukan.");
        }

        System.out.println("(waktu pencarian BST: "
                + (selesai - mulai) + " ns)");
    }

    /**
     * Menangani penghapusan dokumen berdasarkan ID dari BST.
     */
    public static void hapusDokumen() {

        System.out.println("=== HAPUS DOKUMEN ===");

        System.out.print("Masukkan ID dokumen: ");
        int id = bacaInteger();

        boolean berhasil = bst.delete(id);

        if (berhasil) {
            System.out.println("\nDokumen dengan ID " + id
                    + " berhasil dihapus!");
        } else {
            System.out.println("\nDokumen dengan ID " + id
                    + " tidak ditemukan.");
        }
    }

    /**
     * Menampilkan seluruh dokumen secara terurut menaik berdasarkan ID.
     * Urutan ini diperoleh dari traversal inorder pada BST.
     */
    public static void tampilkanSemuaDokumen() {

        if (bst.isEmpty()) {
            System.out.println("Belum ada data dokumen.");
            return;
        }

        System.out.println("=== DAFTAR DOKUMEN (terurut berdasarkan ID) ===");

        List<Dokumen> daftar = bst.inorder();
        for (Dokumen dokumen : daftar) {
            System.out.println("------------------------------");
            System.out.println(dokumen);
        }
        System.out.println("------------------------------");
        System.out.println("Total dokumen : " + bst.jumlahDokumen());
        System.out.println("Tinggi tree   : " + bst.tinggi());
    }

    /**
     * Menampilkan hasil ketiga jenis traversal pada BST
     * (inorder, preorder, dan postorder).
     */
    public static void tampilkanTraversal() {

        if (bst.isEmpty()) {
            System.out.println("Belum ada data dokumen.");
            return;
        }

        System.out.println("=== TRAVERSAL TREE (menampilkan ID dokumen) ===");

        System.out.print("Inorder   (kiri-akar-kanan) : ");
        cetakIdDokumen(bst.inorder());

        System.out.print("Preorder  (akar-kiri-kanan) : ");
        cetakIdDokumen(bst.preorder());

        System.out.print("Postorder (kiri-kanan-akar) : ");
        cetakIdDokumen(bst.postorder());
    }

    /**
     * Mencetak daftar dokumen dalam bentuk ringkas (ID saja)
     * pada satu baris.
     *
     * @param daftar daftar dokumen yang akan dicetak
     */
    private static void cetakIdDokumen(List<Dokumen> daftar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < daftar.size(); i++) {
            sb.append(daftar.get(i).getId());
            if (i < daftar.size() - 1) {
                sb.append(" -> ");
            }
        }
        System.out.println(sb.toString());
    }

    /**
     * Memuat 15 dokumen contoh ke dalam BST untuk keperluan pengujian.
     */
    public static void muatDataContoh() {

        Dokumen[] contoh = {
            new Dokumen(50, "Pengantar Algoritma", "Ilmu Komputer"),
            new Dokumen(30, "Struktur Data Dasar", "Ilmu Komputer"),
            new Dokumen(70, "Basis Data Relasional", "Database"),
            new Dokumen(20, "Jaringan Komputer", "Jaringan"),
            new Dokumen(40, "Pemrograman Java", "Pemrograman"),
            new Dokumen(60, "Kecerdasan Buatan", "AI"),
            new Dokumen(80, "Keamanan Siber", "Keamanan"),
            new Dokumen(10, "Matematika Diskrit", "Matematika"),
            new Dokumen(25, "Sistem Operasi", "Sistem"),
            new Dokumen(35, "Rekayasa Perangkat Lunak", "Software"),
            new Dokumen(45, "Komputasi Awan", "Cloud"),
            new Dokumen(55, "Pembelajaran Mesin", "AI"),
            new Dokumen(65, "Analisis Data", "Data Science"),
            new Dokumen(75, "Pengembangan Web", "Web"),
            new Dokumen(85, "Grafika Komputer", "Grafika")
        };

        int ditambahkan = 0;
        for (Dokumen dokumen : contoh) {
            if (bst.insert(dokumen)) {
                ditambahkan++;
            }
        }

        System.out.println(ditambahkan
                + " dokumen contoh berhasil dimuat.");
        System.out.println("Total dokumen sekarang : "
                + bst.jumlahDokumen());
        System.out.println("Tinggi tree            : " + bst.tinggi());
    }

    /**
     * Membandingkan waktu eksekusi pencarian menggunakan BST
     * dengan pencarian linear (linear search) pada data yang sama.
     *
     * Tujuan pengujian ini adalah menunjukkan keunggulan BST dalam
     * hal kecepatan pencarian dibandingkan pencarian linear.
     */
    public static void ujiPerformaPencarian() {

        if (bst.isEmpty()) {
            System.out.println("Data masih kosong. "
                    + "Muat data contoh terlebih dahulu (menu 6).");
            return;
        }

        System.out.println("=== UJI PERFORMA PENCARIAN ===");

        System.out.print("Masukkan ID yang dicari: ");
        int id = bacaInteger();

        // Ambil seluruh dokumen sebagai daftar untuk pencarian linear
        List<Dokumen> daftar = bst.inorder();

        // --- Pencarian dengan BST ---
        long mulaiBst = System.nanoTime();
        Dokumen hasilBst = bst.search(id);
        long waktuBst = System.nanoTime() - mulaiBst;

        // --- Pencarian linear (memeriksa satu per satu) ---
        long mulaiLinear = System.nanoTime();
        Dokumen hasilLinear = null;
        for (Dokumen dokumen : daftar) {
            if (dokumen.getId() == id) {
                hasilLinear = dokumen;
                break;
            }
        }
        long waktuLinear = System.nanoTime() - mulaiLinear;

        System.out.println("\nStatus data : "
                + (hasilBst != null ? "ditemukan" : "tidak ditemukan"));
        System.out.println("Jumlah data : " + daftar.size());
        System.out.println("--------------------------------------------");
        System.out.println("Pencarian BST    : " + waktuBst + " ns");
        System.out.println("Pencarian Linear : " + waktuLinear + " ns");
        System.out.println("--------------------------------------------");
        System.out.println("Catatan: BST rata-rata O(log n), "
                + "sedangkan linear O(n).");

        // Hasil kedua metode harus konsisten
        if ((hasilBst == null) != (hasilLinear == null)) {
            System.out.println("Peringatan: hasil kedua metode berbeda.");
        }
    }

    /* ============================================================
     * FITUR EXPRESSION TREE
     * ============================================================ */

    /**
     * Menangani proses pembacaan ekspresi matematika,
     * membangun Expression Tree, menampilkannya sebagai pohon,
     * lalu menghitung hasil evaluasinya.
     */
    public static void evaluasiEkspresi() {

        System.out.println("=== EVALUASI EKSPRESI MATEMATIKA ===");
        System.out.println("Operator didukung: +  -  *  /  dan kurung ( )");
        System.out.print("Masukkan ekspresi matematika: ");

        String ekspresi = scanner.nextLine().trim();
        if (ekspresi.isEmpty()) {
            System.out.println("Ekspresi tidak boleh kosong.");
            return;
        }

        try {
            ExpressionTree pohon = new ExpressionTree();
            pohon.bangunDariInfix(ekspresi);

            System.out.println("\nEkspresi dalam bentuk tree:");
            pohon.cetakTree();

            System.out.println("\nHasil Evaluasi: "
                    + formatAngka(pohon.evaluasi()));

            System.out.println("\n--- Notasi hasil traversal ---");
            System.out.println("Inorder   (infix)   : " + pohon.inorder());
            System.out.println("Preorder  (prefix)  : " + pohon.preorder());
            System.out.println("Postorder (postfix) : " + pohon.postorder());

        } catch (IllegalArgumentException | ArithmeticException
                | IllegalStateException e) {
            System.out.println("\nEkspresi tidak valid: " + e.getMessage());
        }
    }

    /* ============================================================
     * METODE BANTU INPUT/OUTPUT
     * ============================================================ */

    /**
     * Memformat angka hasil evaluasi. Bila nilainya bulat, tampilkan
     * tanpa desimal (contoh: 16), selain itu tampilkan apa adanya.
     *
     * @param nilai nilai yang akan diformat
     * @return teks angka yang rapi
     */
    public static String formatAngka(double nilai) {
        if (nilai == Math.floor(nilai) && !Double.isInfinite(nilai)) {
            return String.valueOf((long) nilai);
        }
        return String.valueOf(nilai);
    }

    /**
     * Membaca input integer dari pengguna dengan validasi.
     * Program tidak berhenti walau pengguna salah memasukkan input.
     *
     * @return nilai integer yang dimasukkan pengguna
     */
    public static int bacaInteger() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Input harus berupa angka bulat. "
                        + "Masukkan kembali: ");
            }
        }
    }
}

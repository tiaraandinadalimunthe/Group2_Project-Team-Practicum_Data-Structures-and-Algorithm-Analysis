/*
Nama Kelompok 2:

1. AHMAD WIDAD IZZUDDIN - 2902795331
2. ELVIA DESTIANI - 2902801265
3. MUHAMMAD ZIKRI - 2902794266
4. TIARA ANDINA DALIMUNTHE - 2902772063
5. VINA NAMIRA ANDRINA ANDIDI - 2902789202
*/

import java.util.Scanner;

/**
 * Class Main merupakan class utama untuk menjalankan
 * Sistem Manajemen Data Mahasiswa menggunakan Hash Table.
 */
public class Main {

    // Scanner digunakan untuk menerima input dari pengguna
    static Scanner scanner = new Scanner(System.in);

    // Objek HashTableMahasiswa untuk mengelola data mahasiswa
    static HashTableMahasiswa hashTable = new HashTableMahasiswa();

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
                    tambahMahasiswa();
                    break;

                case 2:
                    cariMahasiswa();
                    break;

                case 3:
                    hapusMahasiswa();
                    break;

                case 4:
                    hashTable.tampilkanSemuaMahasiswa();
                    break;

                case 5:
                    System.out.println("Jumlah data mahasiswa: "
                            + hashTable.jumlahMahasiswa());
                    break;

                case 6:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan menu tidak tersedia.");
            }

            System.out.println();

        } while (pilihan != 6);

        // Menutup Scanner setelah program selesai
        scanner.close();
    }

    /**
     * Menampilkan daftar menu utama aplikasi.
     */
    public static void tampilkanMenu() {

        System.out.println("==============================================");
        System.out.println("   SISTEM MANAJEMEN DATA MAHASISWA");
        System.out.println("          MENGGUNAKAN HASH TABLE");
        System.out.println("==============================================");
        System.out.println("1. Tambah Mahasiswa");
        System.out.println("2. Cari Mahasiswa");
        System.out.println("3. Hapus Mahasiswa");
        System.out.println("4. Tampilkan Semua Mahasiswa");
        System.out.println("5. Jumlah Data Mahasiswa");
        System.out.println("6. Keluar");
        System.out.println("==============================================");
    }

    /**
     * Menangani proses penambahan data mahasiswa.
     *
     * Pengguna diminta memasukkan NIM, nama, dan IPK.
     * Data kemudian disimpan ke dalam Hash Table.
     */
    public static void tambahMahasiswa() {

        System.out.println("=== TAMBAH MAHASISWA ===");

        System.out.print("Masukkan NIM  : ");
        String nim = scanner.nextLine().trim();

        // Memastikan NIM tidak kosong
        if (nim.isEmpty()) {
            System.out.println("NIM tidak boleh kosong.");
            return;
        }

        System.out.print("Masukkan Nama : ");
        String nama = scanner.nextLine().trim();

        // Memastikan nama tidak kosong
        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
            return;
        }

        System.out.print("Masukkan IPK  : ");
        double ipk = bacaDouble();

        // Memastikan IPK berada pada rentang yang valid
        if (ipk < 0 || ipk > 4) {
            System.out.println("IPK harus berada di antara 0.00 sampai 4.00.");
            return;
        }

        // Membuat objek Mahasiswa
        Mahasiswa mahasiswa = new Mahasiswa(nim, nama, ipk);

        // Menambahkan mahasiswa ke Hash Table
        boolean berhasil = hashTable.tambahMahasiswa(mahasiswa);

        if (berhasil) {
            System.out.println("\nData mahasiswa berhasil ditambahkan!");
        } else {
            System.out.println("\nGagal menambahkan data.");
            System.out.println("NIM " + nim + " sudah terdaftar.");
        }
    }

    /**
     * Menangani proses pencarian mahasiswa berdasarkan NIM.
     *
     * NIM digunakan sebagai key untuk melakukan pencarian
     * pada Hash Table.
     */
    public static void cariMahasiswa() {

        System.out.println("=== CARI MAHASISWA ===");

        System.out.print("Masukkan NIM: ");
        String nim = scanner.nextLine().trim();

        // Mencari mahasiswa berdasarkan NIM
        Mahasiswa mahasiswa = hashTable.cariMahasiswa(nim);

        if (mahasiswa != null) {

            System.out.println("\nData ditemukan!");
            System.out.println("------------------------------");
            System.out.println(mahasiswa);
            System.out.println("------------------------------");

        } else {

            System.out.println("\nData mahasiswa dengan NIM "
                    + nim + " tidak ditemukan.");
        }
    }

    /**
     * Menangani proses penghapusan mahasiswa berdasarkan NIM.
     *
     * Data akan dihapus dari Hash Table jika NIM ditemukan.
     */
    public static void hapusMahasiswa() {

        System.out.println("=== HAPUS MAHASISWA ===");

        System.out.print("Masukkan NIM: ");
        String nim = scanner.nextLine().trim();

        // Menghapus mahasiswa berdasarkan NIM
        boolean berhasil = hashTable.hapusMahasiswa(nim);

        if (berhasil) {

            System.out.println("\nData mahasiswa dengan NIM "
                    + nim + " berhasil dihapus!");

        } else {

            System.out.println("\nData mahasiswa dengan NIM "
                    + nim + " tidak ditemukan.");
        }
    }

    /**
     * Membaca input integer dari pengguna.
     *
     * Method ini digunakan agar program tidak berhenti
     * ketika pengguna memasukkan input yang bukan angka.
     *
     * @return nilai integer yang dimasukkan pengguna
     */
    public static int bacaInteger() {

        while (true) {

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.print("Input harus berupa angka. "
                        + "Masukkan kembali: ");
            }
        }
    }

    /**
     * Membaca input double dari pengguna.
     *
     * Method ini digunakan untuk membaca nilai IPK.
     *
     * @return nilai double yang dimasukkan pengguna
     */
    public static double bacaDouble() {

        while (true) {

            String input = scanner.nextLine().trim();

            try {
                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                System.out.print("Input harus berupa angka. "
                        + "Masukkan kembali: ");
            }
        }
    }
}
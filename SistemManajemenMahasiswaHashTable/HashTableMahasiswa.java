import java.util.HashMap;
import java.util.Map;

/**
 * Class HashTableMahasiswa digunakan untuk mengelola
 * data mahasiswa menggunakan struktur data Hash Table.
 *
 * HashMap menggunakan NIM sebagai key dan objek Mahasiswa
 * sebagai value.
 */
public class HashTableMahasiswa {

    // HashMap untuk menyimpan data mahasiswa
    private HashMap<String, Mahasiswa> dataMahasiswa;

    /**
     * Constructor untuk membuat Hash Table mahasiswa baru.
     */
    public HashTableMahasiswa() {
        dataMahasiswa = new HashMap<>();
    }

    /**
     * Menambahkan data mahasiswa ke dalam Hash Table.
     *
     * @param mahasiswa objek Mahasiswa yang akan ditambahkan
     * @return true jika data berhasil ditambahkan,
     *         false jika NIM sudah terdaftar
     */
    public boolean tambahMahasiswa(Mahasiswa mahasiswa) {

        // Memeriksa apakah NIM sudah ada di dalam Hash Table
        if (dataMahasiswa.containsKey(mahasiswa.getNim())) {
            return false;
        }

        // Menambahkan data menggunakan NIM sebagai key
        dataMahasiswa.put(mahasiswa.getNim(), mahasiswa);

        return true;
    }

    /**
     * Mencari data mahasiswa berdasarkan NIM.
     *
     * @param nim NIM mahasiswa yang ingin dicari
     * @return objek Mahasiswa jika ditemukan,
     *         null jika data tidak ditemukan
     */
    public Mahasiswa cariMahasiswa(String nim) {

        // Mengambil data mahasiswa berdasarkan key NIM
        return dataMahasiswa.get(nim);
    }

    /**
     * Menghapus data mahasiswa berdasarkan NIM.
     *
     * @param nim NIM mahasiswa yang akan dihapus
     * @return true jika data berhasil dihapus,
     *         false jika NIM tidak ditemukan
     */
    public boolean hapusMahasiswa(String nim) {

        // Memeriksa apakah NIM tersedia sebelum menghapus
        if (!dataMahasiswa.containsKey(nim)) {
            return false;
        }

        // Menghapus data berdasarkan NIM
        dataMahasiswa.remove(nim);

        return true;
    }

    /**
     * Menampilkan seluruh data mahasiswa yang tersimpan
     * di dalam Hash Table.
     */
    public void tampilkanSemuaMahasiswa() {

        // Memeriksa apakah Hash Table kosong
        if (dataMahasiswa.isEmpty()) {
            System.out.println("Belum ada data mahasiswa.");
            return;
        }

        System.out.println("\n=== DAFTAR DATA MAHASISWA ===");

        // Melakukan iterasi terhadap seluruh data mahasiswa
        for (Map.Entry<String, Mahasiswa> entry : dataMahasiswa.entrySet()) {

            System.out.println("------------------------------");
            System.out.println(entry.getValue());
        }

        System.out.println("------------------------------");
    }

    /**
     * Mengembalikan jumlah data mahasiswa yang tersimpan
     * di dalam Hash Table.
     *
     * @return jumlah data mahasiswa
     */
    public int jumlahMahasiswa() {
        return dataMahasiswa.size();
    }

    /**
     * Memeriksa apakah Hash Table kosong.
     *
     * @return true jika kosong, false jika memiliki data
     */
    public boolean isEmpty() {
        return dataMahasiswa.isEmpty();
    }
}
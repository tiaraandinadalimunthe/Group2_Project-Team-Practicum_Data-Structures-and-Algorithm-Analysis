/**
 * Class Dokumen digunakan untuk menyimpan data sebuah dokumen
 * pada sistem indeks pencarian.
 *
 * Setiap dokumen memiliki id sebagai kunci (key) yang unik.
 * Nilai id inilah yang digunakan sebagai pembanding di dalam
 * Binary Search Tree (BST).
 */
public class Dokumen {

    // Atribut dokumen
    private int id;          // kunci unik dokumen (dipakai sebagai key BST)
    private String judul;    // judul dokumen
    private String kategori; // kategori / topik dokumen

    /**
     * Constructor untuk membuat objek Dokumen baru.
     *
     * @param id       nomor identitas unik dokumen (key BST)
     * @param judul    judul dokumen
     * @param kategori kategori atau topik dokumen
     */
    public Dokumen(int id, String judul, String kategori) {
        this.id = id;
        this.judul = judul;
        this.kategori = kategori;
    }

    /**
     * Mengembalikan id dokumen.
     *
     * @return id dokumen
     */
    public int getId() {
        return id;
    }

    /**
     * Mengembalikan judul dokumen.
     *
     * @return judul dokumen
     */
    public String getJudul() {
        return judul;
    }

    /**
     * Mengembalikan kategori dokumen.
     *
     * @return kategori dokumen
     */
    public String getKategori() {
        return kategori;
    }

    /**
     * Menampilkan data dokumen dalam bentuk String.
     *
     * @return data dokumen berupa id, judul, dan kategori
     */
    @Override
    public String toString() {
        return "ID       : " + id
                + "\nJudul    : " + judul
                + "\nKategori : " + kategori;
    }
}

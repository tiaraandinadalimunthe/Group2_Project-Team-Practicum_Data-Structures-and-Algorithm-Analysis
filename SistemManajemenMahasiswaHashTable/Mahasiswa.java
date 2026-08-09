/**
 * Class Mahasiswa digunakan untuk menyimpan data mahasiswa.
 */
public class Mahasiswa {

    // Atribut mahasiswa
    private String nim;
    private String nama;
    private double ipk;

    /**
     * Constructor untuk membuat objek Mahasiswa baru.
     *
     * @param nim  Nomor Induk Mahasiswa
     * @param nama Nama mahasiswa
     * @param ipk  Indeks Prestasi Kumulatif mahasiswa
     */
    public Mahasiswa(String nim, String nama, double ipk) {
        this.nim = nim;
        this.nama = nama;
        this.ipk = ipk;
    }

    /**
     * Mengembalikan NIM mahasiswa.
     *
     * @return NIM mahasiswa
     */
    public String getNim() {
        return nim;
    }

    /**
     * Mengembalikan nama mahasiswa.
     *
     * @return nama mahasiswa
     */
    public String getNama() {
        return nama;
    }

    /**
     * Mengembalikan IPK mahasiswa.
     *
     * @return IPK mahasiswa
     */
    public double getIpk() {
        return ipk;
    }

    /**
     * Menampilkan data mahasiswa dalam bentuk String.
     *
     * @return data mahasiswa berupa NIM, nama, dan IPK
     */
    @Override
    public String toString() {
        return "NIM  : " + nim
                + "\nNama : " + nama
                + "\nIPK  : " + String.format("%.2f", ipk);
    }
}
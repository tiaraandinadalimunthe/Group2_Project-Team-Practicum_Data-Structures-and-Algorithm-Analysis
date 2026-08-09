import java.util.ArrayList;
import java.util.List;

/**
 * Class BSTDokumen mengimplementasikan struktur data
 * Binary Search Tree (BST) untuk mengelola data dokumen.
 *
 * BST menyimpan dokumen berdasarkan id sebagai key. Aturan BST:
 *   - dokumen dengan id lebih kecil disimpan di subtree kiri,
 *   - dokumen dengan id lebih besar disimpan di subtree kanan.
 *
 * Dengan aturan tersebut, operasi pencarian rata-rata berjalan
 * dengan kompleksitas O(log n) pada pohon yang seimbang.
 */
public class BSTDokumen {

    /**
     * Class Node merepresentasikan satu simpul (node) di dalam BST.
     * Setiap node menyimpan satu objek Dokumen serta referensi ke
     * anak kiri dan anak kanan.
     */
    private static class Node {
        Dokumen data; // data dokumen yang disimpan
        Node kiri;    // referensi ke anak kiri (id lebih kecil)
        Node kanan;   // referensi ke anak kanan (id lebih besar)

        Node(Dokumen data) {
            this.data = data;
        }
    }

    // Akar (root) dari pohon BST
    private Node root;

    // Jumlah dokumen yang tersimpan di dalam BST
    private int ukuran;

    /**
     * Constructor untuk membuat BST kosong.
     */
    public BSTDokumen() {
        this.root = null;
        this.ukuran = 0;
    }

    /* ============================================================
     * OPERASI INSERT (PENAMBAHAN)
     * ============================================================ */

    /**
     * Menambahkan dokumen baru ke dalam BST.
     *
     * @param dokumen objek Dokumen yang akan ditambahkan
     * @return true jika berhasil, false jika id sudah dipakai
     */
    public boolean insert(Dokumen dokumen) {

        // Cegah id ganda: bila sudah ada, penambahan dibatalkan
        if (search(dokumen.getId()) != null) {
            return false;
        }

        // Sisipkan node secara rekursif mulai dari root
        root = insertRekursif(root, dokumen);
        ukuran++;
        return true;
    }

    /**
     * Method rekursif untuk menyisipkan dokumen pada posisi yang
     * tepat sesuai aturan BST.
     *
     * @param node    node saat ini yang sedang ditelusuri
     * @param dokumen dokumen yang akan disisipkan
     * @return node hasil penyisipan
     */
    private Node insertRekursif(Node node, Dokumen dokumen) {

        // Jika posisi kosong ditemukan, buat node baru di sini
        if (node == null) {
            return new Node(dokumen);
        }

        // Bandingkan id untuk menentukan arah penyisipan
        if (dokumen.getId() < node.data.getId()) {
            node.kiri = insertRekursif(node.kiri, dokumen);   // ke kiri
        } else {
            node.kanan = insertRekursif(node.kanan, dokumen); // ke kanan
        }

        return node;
    }

    /* ============================================================
     * OPERASI SEARCH (PENCARIAN)
     * ============================================================ */

    /**
     * Mencari dokumen berdasarkan id.
     *
     * @param id id dokumen yang dicari
     * @return objek Dokumen bila ditemukan, null bila tidak ada
     */
    public Dokumen search(int id) {
        Node hasil = searchRekursif(root, id);
        return (hasil != null) ? hasil.data : null;
    }

    /**
     * Method rekursif untuk mencari node berdasarkan id.
     *
     * @param node node saat ini yang sedang ditelusuri
     * @param id   id yang dicari
     * @return node bila ditemukan, null bila tidak ada
     */
    private Node searchRekursif(Node node, int id) {

        // Basis: pohon habis (tidak ditemukan) atau id cocok
        if (node == null || node.data.getId() == id) {
            return node;
        }

        // Manfaatkan sifat BST untuk mempersempit pencarian
        if (id < node.data.getId()) {
            return searchRekursif(node.kiri, id);   // cari di kiri
        } else {
            return searchRekursif(node.kanan, id);  // cari di kanan
        }
    }

    /* ============================================================
     * OPERASI DELETE (PENGHAPUSAN)
     * ============================================================ */

    /**
     * Menghapus dokumen berdasarkan id.
     *
     * @param id id dokumen yang akan dihapus
     * @return true jika berhasil dihapus, false jika id tidak ada
     */
    public boolean delete(int id) {

        // Pastikan data ada sebelum dihapus
        if (search(id) == null) {
            return false;
        }

        root = deleteRekursif(root, id);
        ukuran--;
        return true;
    }

    /**
     * Method rekursif untuk menghapus node dengan id tertentu.
     *
     * Ada tiga kasus penghapusan pada BST:
     *   1. Node tanpa anak (daun).
     *   2. Node dengan satu anak.
     *   3. Node dengan dua anak -> diganti dengan penerus inorder
     *      (nilai terkecil pada subtree kanan).
     *
     * @param node node saat ini
     * @param id   id yang akan dihapus
     * @return node hasil pengubahan struktur
     */
    private Node deleteRekursif(Node node, int id) {

        // Basis: node tidak ditemukan
        if (node == null) {
            return null;
        }

        // Telusuri sampai menemukan node yang cocok
        if (id < node.data.getId()) {
            node.kiri = deleteRekursif(node.kiri, id);
        } else if (id > node.data.getId()) {
            node.kanan = deleteRekursif(node.kanan, id);
        } else {

            // Node ditemukan.

            // Kasus 1 & 2: node hanya punya satu anak atau tanpa anak
            if (node.kiri == null) {
                return node.kanan;
            } else if (node.kanan == null) {
                return node.kiri;
            }

            // Kasus 3: node punya dua anak.
            // Cari penerus inorder (nilai terkecil di subtree kanan)
            Node penerus = cariMinimum(node.kanan);

            // Salin data penerus ke node saat ini
            node.data = penerus.data;

            // Hapus node penerus dari subtree kanan
            node.kanan = deleteRekursif(node.kanan, penerus.data.getId());
        }

        return node;
    }

    /**
     * Mencari node dengan id terkecil pada suatu subtree.
     * Node terkecil selalu berada paling kiri.
     *
     * @param node akar subtree
     * @return node dengan id terkecil
     */
    private Node cariMinimum(Node node) {
        while (node.kiri != null) {
            node = node.kiri;
        }
        return node;
    }

    /* ============================================================
     * ALGORITMA TRAVERSAL
     * ============================================================ */

    /**
     * Traversal inorder (kiri - akar - kanan).
     * Menghasilkan daftar dokumen yang terurut menaik berdasarkan id.
     *
     * @return daftar dokumen terurut
     */
    public List<Dokumen> inorder() {
        List<Dokumen> hasil = new ArrayList<>();
        inorderRekursif(root, hasil);
        return hasil;
    }

    private void inorderRekursif(Node node, List<Dokumen> hasil) {
        if (node != null) {
            inorderRekursif(node.kiri, hasil);   // kunjungi kiri
            hasil.add(node.data);                // kunjungi akar
            inorderRekursif(node.kanan, hasil);  // kunjungi kanan
        }
    }

    /**
     * Traversal preorder (akar - kiri - kanan).
     *
     * @return daftar dokumen dengan urutan preorder
     */
    public List<Dokumen> preorder() {
        List<Dokumen> hasil = new ArrayList<>();
        preorderRekursif(root, hasil);
        return hasil;
    }

    private void preorderRekursif(Node node, List<Dokumen> hasil) {
        if (node != null) {
            hasil.add(node.data);                 // kunjungi akar
            preorderRekursif(node.kiri, hasil);   // kunjungi kiri
            preorderRekursif(node.kanan, hasil);  // kunjungi kanan
        }
    }

    /**
     * Traversal postorder (kiri - kanan - akar).
     *
     * @return daftar dokumen dengan urutan postorder
     */
    public List<Dokumen> postorder() {
        List<Dokumen> hasil = new ArrayList<>();
        postorderRekursif(root, hasil);
        return hasil;
    }

    private void postorderRekursif(Node node, List<Dokumen> hasil) {
        if (node != null) {
            postorderRekursif(node.kiri, hasil);   // kunjungi kiri
            postorderRekursif(node.kanan, hasil);  // kunjungi kanan
            hasil.add(node.data);                  // kunjungi akar
        }
    }

    /* ============================================================
     * UTILITAS
     * ============================================================ */

    /**
     * Mengembalikan jumlah dokumen yang tersimpan di dalam BST.
     *
     * @return jumlah dokumen
     */
    public int jumlahDokumen() {
        return ukuran;
    }

    /**
     * Menghitung tinggi (height) pohon BST.
     * Tinggi berguna untuk menilai seberapa seimbang pohon.
     *
     * @return tinggi pohon (0 jika kosong)
     */
    public int tinggi() {
        return tinggiRekursif(root);
    }

    private int tinggiRekursif(Node node) {
        if (node == null) {
            return 0;
        }
        int kiri = tinggiRekursif(node.kiri);
        int kanan = tinggiRekursif(node.kanan);
        return Math.max(kiri, kanan) + 1;
    }

    /**
     * Memeriksa apakah BST kosong.
     *
     * @return true jika kosong, false jika memiliki data
     */
    public boolean isEmpty() {
        return root == null;
    }
}

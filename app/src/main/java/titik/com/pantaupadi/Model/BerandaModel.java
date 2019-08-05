package titik.com.pantaupadi.Model;


public class BerandaModel{
    private String id;
    private String user_id;
    private String nama_penyakit;
    private String solusi;
    private String gambar;
    private String kondisi;
    private String penulis;
    private String tanggal_upload;
    private String value_warna;
    private String usia;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNama_penyakit() {
        return nama_penyakit;
    }

    public void setNama_penyakit(String nama_penyakit) {
        this.nama_penyakit = nama_penyakit;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getTanggal_upload() {
        return tanggal_upload;
    }

    public void setTanggal_upload(String tanggal_upload) {
        this.tanggal_upload = tanggal_upload;
    }

    public String getValue_warna() {
        return value_warna;
    }

    public void setValue_warna(String value_warna) {
        this.value_warna = value_warna;
    }

    public String getUsia() {
        return usia;
    }

    public void setUsia(String usia) {
        this.usia = usia;
    }



    public BerandaModel(String id, String user_id, String nama_penyakit, String solusi, String kondisi, String gambar, String penulis, String tanggal_upload, String value_warna, String usia) {
        this.id = id;
        this.user_id = user_id;
        this.nama_penyakit = nama_penyakit;
        this.solusi = solusi;
        this.gambar = gambar;
        this.kondisi = kondisi;
        this.penulis = penulis;
        this.tanggal_upload = tanggal_upload;
        this.value_warna = value_warna;
        this.usia = usia;
    }

    public BerandaModel(int id, String gambar, String nama_penyakit, int tanggal_upload, String penulis)
    {

    }



}

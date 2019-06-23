package titik.com.pantaupadi.Model;

import android.os.Parcelable;

public class BerandaModel{
    String nama;
    String gambar;
    String tanggal;
    String author;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BerandaModel() {
        this.nama = nama;
        this.gambar = gambar;
        this.tanggal = tanggal;
        this.author = author;
    }
}

package titik.com.pantaupadi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class BerandaModel{
    private String user_id;
    private String jenis_tanaman;//ini jenis penyakit karena malas ganti makanya tetep aja
    private String bwd_range;
    private String solusi;
    private String kondisi;
    private String pic_compare;
    private String warna_daun;
    private String penulis;
    private String tanggal_upload;


    public BerandaModel(String id, String user_id, String jenis_tanaman, String bwd_range, String solusi, String kondisi, String pic_compare, String warna_daun, String penulis, String tanggal_upload) {
        this.id = id;
        this.user_id = user_id;
        this.jenis_tanaman = jenis_tanaman;
        this.bwd_range = bwd_range;
        this.solusi = solusi;
        this.kondisi = kondisi;
        this.pic_compare = pic_compare;
        this.warna_daun = warna_daun;
        this.penulis = penulis;
        this.tanggal_upload = tanggal_upload;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenis_tanaman() {
        return jenis_tanaman;
    }

    public void setJenis_tanaman(String jenis_tanaman) {
        this.jenis_tanaman = jenis_tanaman;
    }

    public String getBwd_range() {
        return bwd_range;
    }

    public void setBwd_range(String bwd_range) {
        this.bwd_range = bwd_range;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getPic_compare() {
        return pic_compare;
    }

    public void setPic_compare(String pic_compare) {
        this.pic_compare = pic_compare;
    }

    public String getWarna_daun() {
        return warna_daun;
    }

    public void setWarna_daun(String warna_daun) {
        this.warna_daun = warna_daun;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public BerandaModel() {

    }


}

package titik.com.pantaupadi.Data;

public class DataPenyakit {
    String []nama = {   "Penyakit 1",
                        "Penyakit 2",
                        "Penyakit 3",
                        "Penyakit 4",
                        "Penyakit 5"
    };

    String []deskripsi = {  "Deskripsi 1",
                            "Deskripsi 2",
                            "Deskripsi 3",
                            "Deskripsi 4",
                            "Deskripsi 5"
    };

    String []tanggal = { "20 july 2019",
            "20 july 2019",
            "20 july 2019",
            "20 july 2019",
            "20 july 2019",
    };

    public String[] getNama() {
        return nama;
    }

    public void setNama(String[] nama) {
        this.nama = nama;
    }

    public String[] getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String[] deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String[] getTanggal() {return tanggal;}

    public void setTanggal(String[] tanggal){this.tanggal = tanggal ;}
}

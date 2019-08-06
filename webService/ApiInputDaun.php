<?php
if($_SERVER['REQUEST_METHOD']=='POST') {

   $response = array();
   //mendapatkan data
   $user_id = $_POST['id_user'];
   $nama_penyakit = $_POST['nama_penyakit'];
   $value_warna = 'value_warna';
   $solusi = $_POST['solusi'];
   $gambar = 'daun/default.jpg';
   $kondisi = date('Y-m-d H:i:s');
   $penulis = $_POST['penulis'];
   date_default_timezone_set('Asia/Jakarta');  
   $tanggal_upload =  date('Y-m-d H:i:s');
   $usia = $_POST['usia']; 
   

   require_once('koneksi.php');

        
         $sql = "INSERT INTO daun (id, id_user,nama_penyakit,value_warna,solusi,gambar,kondisi,penulis,tanggal_upload, usia) VALUES(0,'$user_id', '$nama_penyakit', '$value_warna', '$solusi', '$gambar', '$kondisi', '$penulis', '$tanggal_upload', '$usia')";
         if(mysqli_query($con,$sql)) {
           $response["value"] = 1;
           $response["message"] = "Sukses, cek data daun anda di Menu beranda";
           echo json_encode($response);
         } else {
           $response["value"] = 0;
           $response["message"] = "oops! Coba lagi!";
           echo json_encode($response);
         }

   // tutup database
   mysqli_close($con);
} else {
  $response["value"] = 0;
  $response["message"] = "oops! Coba lagi!";
  echo json_encode($response);
}
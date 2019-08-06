<?php

//database constants
 define('DB_HOST', 'localhost');
 define('DB_USER', 'root');
 define('DB_PASS', '');
 define('DB_NAME', 'ci_pantaupadi');
 
 //connecting to database and getting the connection object
 $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
 
 //Checking if any error occured while connecting
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }
 
 //creating a query
 $stmt = $conn->prepare("SELECT * FROM daun;");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($id, $id_user, $nama_penyakit, $solusi, $gambar, $kondisi, $penulis, $tanggal_upload, $value_warna, $usia);
 
 $daun = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['id'] = $id; 
 $temp['id_user'] = $id_user; 
 $temp['nama_penyakit'] = $nama_penyakit; 
 $temp['solusi'] = $solusi;
 $temp['gambar'] = $gambar; 
 $temp['kondisi'] = $kondisi; 
 $temp['penulis'] = $penulis; 
 $temp['tanggal_upload'] = $tanggal_upload; 
 $temp['value_warna'] = $value_warna; 
 $temp['usia'] = $usia; 
 array_push($daun, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($daun);
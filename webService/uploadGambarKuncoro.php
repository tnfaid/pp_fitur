<?php
	include_once "koneksi.php";
	
	class emp{}
	
	$gambar = $_POST['gambar'];
	$nama_penyakit = $_POST['nama_penyakit'];
	$user_id = $_POST['user_id'];
	$kondisi = $_POST['kondisi'];
	$solusi = $_POST['solusi'];
	$penulis = $_POST['penulis'];
	$usia = $_POST['usia'];
	$value_warna = 0;
	date_default_timezone_set('Asia/Jakarta'); 
	$tanggal_upload =date('Y-m-d H:i:s');

	if (empty($nama_penyakit)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Nama Penyakit Harap diisi"; 
		die(json_encode($response));
	}elseif (empty($kondisi)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kondisi harap diisikan."; 
		die(json_encode($response));
	}elseif (empty($solusi)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Solusi harap diisikan."; 
		die(json_encode($response));
	}elseif (empty($usia)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Usia harap diisikan."; 
		die(json_encode($response));
	} else {
		$random = random_word(20);
		
		// $path = "uploads/".$nama_penyakit.".png";
		$path = "uploads/".$nama_penyakit.".png";
		$nama_upload_gambar = $nama_penyakit.".png";
		// sesuiakan ip address laptop/pc atau URL server
		$actualpath = "http://localhost:8080/CI_PantauPadi/webService/$path";
		
		$query = mysqli_query($con, "INSERT INTO daun (gambar,nama_penyakit, user_id, kondisi, solusi, penulis, usia, value_warna, tanggal_upload) VALUES ('$nama_upload_gambar','$nama_penyakit','$user_id', '$kondisi', '$solusi', '$penulis', '$usia', '$value_warna', '$tanggal_upload')");
		
		if ($query){
			file_put_contents($path,base64_decode($gambar));
			
			$response = new emp();
			$response->success = 1;
			$response->message = "Successfully Uploaded";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error Upload image";
			die(json_encode($response)); 
		}
	}	
	
	// fungsi random string pada gambar untuk menghindari nama file yang sama
	function random_word($id = 20){
		$pool = '1234567890abcdefghijkmnpqrstuvwxyz';
		
		$word = '';
		for ($i = 0; $i < $id; $i++){
			$word .= substr($pool, mt_rand(0, strlen($pool) -1), 1);
		}
		return $word; 
	}

	mysqli_close($con);
	
?>
<?php
include_once "koneksi.php";

$nama_penyakit = $_POST['keyword'];

	$query = mysqli_query($con, "SELECT * FROM daun WHERE nama_penyakit LIKE '%".$nama_penyakit."%'");

	$num_rows = mysqli_num_rows($query);

	if ($num_rows > 0){
		$json = '{"value":1, "results": [';

		while ($row = mysqli_fetch_array($query)){
			$char ='"';

			$json .= '{
				"id": "'.str_replace($char,'`',strip_tags($row['id'])).'",
				"nama_penyakit": "'.str_replace($char,'`',strip_tags($row['nama_penyakit'])).'"
				"usia": "'.str_replace($char,'`',strip_tags($row['usia'])).'"
				"value_warna": "'.str_replace($char,'`',strip_tags($row['value_warna'])).'"
				"solusi": "'.str_replace($char,'`',strip_tags($row['solusi'])).'"
				"gambar": "'.str_replace($char,'`',strip_tags($row['gambar'])).'"
				"kondisi": "'.str_replace($char,'`',strip_tags($row['kondisi'])).'"
				"penulis": "'.str_replace($char,'`',strip_tags($row['penulis'])).'"
				"tanggal_upload": "'.str_replace($char,'`',strip_tags($row['tanggal_upload'])).'"
			},';
		}

		$json = substr($json,0,strlen($json)-1);

		$json .= ']}';

	} else {
		$json = '{"value":0, "message": "Data tidak ditemukan."}';
	}

	echo $json;

	mysqli_close($con);


?>
<?php 
	
	//Constants for database connection
	define('DB_HOST','localhost');
	define('DB_USER','root');
	define('DB_PASS','');
	define('DB_NAME','ci_pantaupadi');

	//We will upload files to this folder
	//So one thing don't forget, also create a folder named uploads inside your project folder i.e. MyApi folder
	define('UPLOAD_PATH', '../gambar_unggah/daun/');
	
	//connecting to database 
	$conn = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME) or die('Unable to connect');
	

	//An array to display the response
	$response = array();

					if(
					// isset($_POST['user_id']) && 
					// isset($_POST['solusi']) && 
					// isset($_POST['kondisi']) && 
					// isset($_POST['penulis']) && 
					// isset($_POST['usia']) &&  
					isset($_POST['nama_penyakit']) && 
					isset($_FILES['pic']['name'])){
					
					//uploading file and storing it to database as well 
					try{
						move_uploaded_file($_FILES['pic']['tmp_name'], 
							UPLOAD_PATH . $_FILES['pic']['name']);
						$stmt = $conn->prepare("INSERT INTO daun (
							-- user_id, 
							-- solusi, 
							-- kondisi, 
							-- penulis, 
							-- tanggal_upload, 
							-- value_warna, 
							-- usia, 
							nama_penyakit, 
							gambar) VALUES (?,?)");
						date_default_timezone_set('Asia/Jakarta'); 
						$tanggal_upload = date('Y-m-d H:i:s');
						$value_warna = 0;
						$stmt->bind_param("ss", 
							// $_POST['user_id'], 
							// $_POST['solusi'], 
							// $_POST['kondisi'], 
							// $_POST['penulis'], 
							// $tanggal_upload, 
							// $value_warna, 
							// $_POST['usia'],
							$_POST['nama_penyakit'], 
							$_FILES['pic']['name']);
						if($stmt->execute()){
							$response['error'] = false;
							$response['message'] = 'File Sukses Diunggah, Silahkan cek di Beranda';
						}else{
							throw new Exception("Kagak Bisa Upload File");
						}
					}catch(Exception $e){
						$response['error'] = true;
						$response['message'] = 'Could not upload file';
					}
					
				}else{
					$response['error'] = true;
					$response['message'] = "Required params not available";
				}

				header('Content-Type: application/json');
	echo json_encode($response);
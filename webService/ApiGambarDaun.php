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

	//if the call is an api call 
	if(isset($_GET['apicall'])){
		
		//switching the api call 
		switch($_GET['apicall']){
			
			//if it is an upload call we will upload the image
			case 'uploadpic':
				
				//first confirming that we have the image and tags in the request parameter
				if(isset($_POST['user_id']) && 
					isset($_POST['nama_penyakit']) && 
					isset($_POST['solusi']) && 
					isset($_POST['kondisi']) && 
					isset($_POST['penulis']) && 
					isset($_POST['usia']) &&  
					isset($_FILES['pic']['name'])){
					
					//uploading file and storing it to database as well 
					try{
						move_uploaded_file($_FILES['pic']['tmp_name'], 
							UPLOAD_PATH . $_FILES['pic']['name']);
						$stmt = $conn->prepare("INSERT INTO daun (
							user_id, 
							nama_penyakit, 
							solusi, 
							kondisi, 
							penulis, 
							tanggal_upload, 
							value_warna, 
							usia, 
							gambar) VALUES (?,?,?,?,?,?,?,?,?)");
						date_default_timezone_set('Asia/Jakarta'); 
						$tanggal_upload = date('Y-m-d H:i:s');
						$value_warna = 0;
						$stmt->bind_param("sssssssss", 
							$_POST['user_id'], 
							$_POST['nama_penyakit'], 
							$_POST['solusi'], 
							$_POST['kondisi'], 
							$_POST['penulis'], 
							$tanggal_upload, 
							$value_warna, 
							$_POST['usia'],
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
			
			break;
			
			//in this call we will fetch all the images 
			case 'getpics':
		
				//getting server ip for building image url 
				$server_ip = gethostbyname(gethostname());
				
				//query to get images from database
				$stmt = $conn->prepare("SELECT id, image, tags FROM images");
				$stmt->execute();
				$stmt->bind_result($id, $image, $tags);
				
				$images = array();

				//fetching all the images from database
				//and pushing it to array 
				while($stmt->fetch()){
					$temp = array();
					$temp['id'] = $id; 
					$temp['image'] = 'http://' . $server_ip . '/MyApi/'. UPLOAD_PATH . $image; 
					$temp['tags'] = $tags; 
					
					array_push($images, $temp);
				}
				
				//pushing the array in response 
				$response['error'] = false;
				$response['images'] = $images; 
			break; 
			
			default: 
				$response['error'] = true;
				$response['message'] = 'Invalid api call';
		}
		
	}else{
		header("HTTP/1.0 404 Not Found");
		echo "<h1>404 Not Found</h1>";
		echo "The page that you have requested could not be found.";
		exit();
	}
	
	//displaying the response in json 
	header('Content-Type: application/json');
	echo json_encode($response);

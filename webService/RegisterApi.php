<?php


/* ========= KALAU PAKAI MYSQLI YANG ATAS SEMUA DI REMARK, TERUS YANG INI DI UNREMARK ======== */
 include_once "koneksi.php";
 class usr{}


$first_name = $_POST['first_name'];
$last_name = $_POST['last_name'];
$email = $_POST['email'];
// $mobile = $_POST['mobile'];
// $country = $_POST['country'];
$password = md5($_POST['password']);
$confirm_password = md5($_POST['confirm_password']);

if ((empty($first_name))) {
    $response = new usr();
    $response->success = 0;
    $response->message = "Kolom first name tidak boleh kosong";
    die(json_encode($response));
}else if ((empty($last_name))) {
    $response = new usr();
    $response->success = 0;
    $response->message = "Kolom last name tidak boleh kosong";
    die(json_encode($response));
}else if ((empty($email))) {
    $response = new usr();
    $response->success = 0;
    $response->message = "Kolom email tidak boleh kosong";
    die(json_encode($response));
} else if ((empty($password))) {
    $response = new usr();
    $response->success = 0;
    $response->message = "Kolom password tidak boleh kosong";
    die(json_encode($response));
} else if ((empty($confirm_password)) || $password != $confirm_password) {
    $response = new usr();
    $response->success = 0;
    $response->message = "Konfirmasi password tidak sama";
    die(json_encode($response));

 } else {
 if (!empty($email) && $password == $confirm_password){
 	$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM user WHERE email='".$email."'"));

 	if ($num_rows == 0){
        $query = mysqli_query($con, "INSERT INTO user (id,first_name, last_name, email, password) VALUES(0,'".$first_name."','".$last_name."','".$email."','".$password."'')");


 		if ($query){
 			$response = new usr();
 			$response->success = 1;
 			$response->message = "Register berhasil, silahkan login.";
 			die(json_encode($response));

 		} else {
 			$response = new usr();
 			$response->success = 0;
 			$response->message = "Email sudah ada";
 			die(json_encode($response));
 		}
 	} else {
 		$response = new usr();
 		$response->success = 0;
 		$response->message = "Email sudah ada";
 		die(json_encode($response));
 	}
 }
 }

 mysqli_close($con);

?>
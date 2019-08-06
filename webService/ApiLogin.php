<?php
include 'koneksi.php';

//class usr{}
//
//$email = $_POST["email"];
//$password = $_POST["password"];
//
//
//if ((empty($email)) || (empty($password))) {
//    $response = new usr();
//    $response->success = 0;
//    $response->message = "Kolom tidak boleh kosong";
//    die(json_encode($response));
//}
//
//$query = mysql_query("SELECT * FROM user WHERE email='$email' AND password='$password'");
//
//$row = mysql_fetch_array($query);
//
//if (!empty($row)){
//    $response = new usr();
//    $response->success = 1;
//    $response->message = "Selamat datang ".$row['username'];
//    $response->id = $row['id'];
//    $response->username = $row['username'];
//    die(json_encode($response));
//
//} else {
//    $response = new usr();
//    $response->success = 0;
//    $response->message = "Username atau password salah";
//    die(json_encode($response));
//}

//mysql_close();

//== mysqli
 //include_once "koneksi.php";

 class usr{}

 $email = $_POST["email"];
 $password =md5($_POST["password"]) ;

 if ((empty($email)) || (empty($password))) {
 	$response = new usr();
 	$response->success = 0;
 	$response->message = "Kolom tidak boleh kosong";
 	die(json_encode($response));
 }

 $query = mysqli_query($con, "SELECT * FROM user WHERE email='$email' AND password='$password'");

 $row = mysqli_fetch_array($query);

 if (!empty($row)){
 	$response = new usr();
 	$response->success = 1;
 	$response->message = "Selamat datang ".$row['email'];
 	$response->id = $row['id'];
 	$response->first_name = $row['first_name'];
 	$response->last_name = $row['last_name'];
 	$response->email = $row['email'];
 	$response->mobile = $row['mobile'];
 	$response->country = $row['country'];
 	$response->created_at = $row['created_at'];
 	$response->status = $row['status'];
 	$response->role = $row['role'];
 	die(json_encode($response));

 } else {
 	$response = new usr();
 	$response->success = 0;
 	$response->message = "Email atau password salah";
 	die(json_encode($response));
 }

 mysqli_close($con);
?>
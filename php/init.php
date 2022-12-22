<?php
    $host = "localhost";
    $user = "id19994602_admin";
    $pass = "rp!j~)x<d02/kLgY";
    $db = "id19994602_findmissingpet";
    
    $con = mysqli_connect($host,$user,$pass,$db);
    
    if($con){
        // echo "Connected to Database";
    }else{
        // echo "Failed to connect ".mysqli_connect_error();
    }
    
?>
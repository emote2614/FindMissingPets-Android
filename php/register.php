<?php
    require 'init.php';
    $name = $_POST["name"];
    $id = $_POST["id"];
    $pw = $_POST["pw"];
    $city = $_POST["city"];
    $phone = $_POST["phone"]; 
    
    $sql = "INSERT INTO User (name, id, pw, city, phone) VALUES ('$name','$id','$pw','$city','$phone')";
    
    $result = mysqli_query($con, $sql);
    if($result){
        echo "회원가입 성공";
    }else{
        echo "ERROR : ".mysqli_error($con);
    }
    mysqli_close($con);
?>
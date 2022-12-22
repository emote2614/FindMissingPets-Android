<?php
    require "init.php";
    
    $id = $_POST["id"];
    $pw = $_POST["pw"];
    
    $sql = "SELECT * FROM User WHERE id = '$id' and pw = '$pw'";
    
    $result = mysqli_query($con, $sql);
    
    if(mysqli_num_rows($result)>0){
        echo "로그인 성공";
    }else{
        echo "로그인 실패";
    }
?>
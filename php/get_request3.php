<?php
    require "init.php";
    $sql = "SELECT * FROM Books";
    $result = mysqli_query($con,$sql);
    $response = array();
    while($row = mysqli_fetch_assoc($result)){
        array_push($response, $row);
    }
    echo json_encode($response);

?>
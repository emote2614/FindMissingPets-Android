<?php
require "init.php";
$target_dir = "uploads/";
$target_file_name = $target_dir . basename($_FILES["file"]["name"]);
$response = array();

if (isset($_FILES["file"]))
{
    if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name))
    {
        $url = "https://findmissingpets2.000webhostapp.com/".$target_file_name;
        
        $m0 = $_GET["message0"];
        $m1 = $_GET["message1"];
        $m2 = $_GET["message2"];
        $id = $_GET["id"];
        $sql = "INSERT INTO Posts (`num`,`message0`,`message1`,`message2`,`url`,`id`)
                VALUES (NULL, '$m0', '$m1', '$m2', '$url', '$id');";
        mysqli_query($con, $sql);
        $success = true;
        $message = "Uploaded!!!";
    }
    else
    {
        $success = false;
        $message = "NOT Uploaded!!! _ Error While Uploading";
    }
}
else
{
    $success = false;
    $message = "missing field";
}
$response["success"] = $success;
$response["message"] = $message;
echo json_encode($response);
?>
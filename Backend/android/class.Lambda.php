<?php
class Lambda
{

  function Create($name,$title,$data)
  {
    $db= mysqli_connect("192.184.85.36","root","MyBass58","CSCE4444") or die("Error " . mysqli_error());

//	echo"<br>".$name."<br>";
//	echo $title."<br>";
//	echo $data."<br>";
	echo "INSERT INTO " .$name." (".$title. ") VALUES (". $data .")";
    mysqli_query($db,"INSERT INTO " .$name." (".$title. ") VALUES (". $data .")");//needs to be in format value1,value2,value3

    mysqli_close($db);

 }

 function Read($name,$value,$table)// locates info.
  {
     $db= mysqli_connect("192.184.85.36","root","MyBass58","CSCE4444") or die("Error " . mysqli_error());

    if($name=="Menu")
    {
		$data=array();
        $result=mysqli_query($db,"SELECT * FROM Menu GROUP BY ID");
		$count=0;
		while($row = mysqli_fetch_assoc($result))
		{
			$data[]=$row;
			++$count;
		}

    }

   elseif($name=="Order")//will look for the order
   {
    	$result=mysqli_query($db,"SELECT * FROM Orders WHERE TableId = ".$value);//returns json for order
 
		$data=array();
		$count=0;
        while($row = mysqli_fetch_assoc($result))
        {
         	$data[]=$row;
         	++$count;
        }

    	$table="null";
    	$count="null";
   }

 else
  {}
    mysqli_close($db);
    self::Send($name,$data,$table,$count);//send data to be encoded into json

  }

 function Update($name,$row,$colum,$data,$value)//update an existing row in db.
  {
     $db= mysqli_connect("192.184.85.36","root","MyBass58","CSCE4444") or die("Error " . mysqli_error());

     mysqli_query($db,"UPDATE '$name' SET '$row' = '$data' WHERE '$value' = '$colum");

     mysqli_close($db);

  }

 function Delete($name, $value, $data)//remove a row in the db.
  {
     $db=mysqli_connect("192.184.85.36","root","MyBass58","CSCE4444") or die("Error " . mysqli_error());
     echo "DELETE FROM".$name." WHERE".$value." = ".$data;
     echo("complete");

     mysqli_query($db,"DELETE FROM".$name." WHERE".$value." = ".$data);
     mysqli_close($db);
  }

 function Send($name,$data,$table,$count)//function to send json data.
  {
	if($name=="Menu")
	{
		$num=1;
		echo "Name: ".json_encode($name);
		echo "count: ".json_encode($count);
		echo "Resutlt: [";

	foreach($data as &$row)
	{
		echo"{";
		echo'"ID": ' .json_encode($row["ID"]);
		echo'", Name": '.json_encode($row["Name"]);
		echo'", Category": ' .json_encode($row["Category"]);
		echo'", Price": '.json_encode($row["Price"]);
		echo'", Desctiption": ' .json_encode($row["Description"]);
		echo'", Modifiables": ' .json_encode($row["Modifiables"]);
		echo'", PicLocation": ' .json_encode($row["Modifiables"]);
		echo'", Visibility": ' .json_encode($row["Visibility"]);
		echo"}";

		if($num<$count)
		{
			echo ",";
			$num+=1;
		}

	}
	
	echo "]";
  	
  	}

	elseif($name=="Order")
	{
		$num=1;
		echo "Name: ".json_encode($name);
        echo "count: ".json_encode($count);
        echo "Resutlt: [";

	foreach($data as &$row)
        {
			echo"{";
        	echo'"TableId": '.json_encode($row["TableId"]);
        	echo'", Title": '.json_encode($row["Title"]);
			echo'", Modifiables": '.json_encode($row["Modifiables"]);
			echo'", Price": '.json_encode($row["Price"]);
			echo"}";

	 	if($num<$count)
        {
        	echo ",";
        	$num+=1;
        }

	}
	 echo "]";
	}
  }
}
?>

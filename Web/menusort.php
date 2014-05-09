<!DOCTYPE html>
<html>
<body>

<?php
// Create connection
$con = mysqli_connect("192.184.85.36","root","MyBass58","CSCE4444");

// Check connection
if (mysqli_connect_errno($con))
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  };

print( '<a href="openorders.php">View Open Orders</a>' );
print( '<a href="closedorders.php">View Closed Orders</a>' );

$result = mysqli_query($con,"SELECT * FROM Menu");
$row=array();
echo "<table border='1'>
<tr>
<th>ID</th>
<th>Name</th>
<th>Category</th>
<th>Description</th>
<th>Modifiables</th>
<th>PicLocation</th>
<th>Visibility</th>
</tr>";
		
while($row = mysqli_fetch_assoc($result))
	{
	echo "<tr>";
	echo "<td>" . $row['ID'] . "</td>";
	echo "<td>" . $row['Name'] . "</td>";
	echo "<td>" . $row['Category'] . "</td>";
	echo "<td>" . $row['Description'] . "</td>";
	echo "<td>" . $row['Modifiables'] . "</td>";
	echo "<td>" . $row['PicLocation'] . "</td>";
	echo "<td>" . $row['Visibility'] . "</td>";
	echo "</tr>";
	};
echo "</table>";
	
mysqli_close($con);
  
?>

</body>
</html>

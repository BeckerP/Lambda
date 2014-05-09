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

print( '<a href="menusort.php">View Menu</a>' );
print( '<a href="closedorders.php">View Closed Orders</a>' );

$result = mysqli_query($con,"SELECT * FROM Orders ORDER BY TableId ASC");
$row=array();
echo "<table border='1'>
<tr>
<th>TableId</th>
<th>Wait Staff</th>
<th>Title</th>
<th>Modifiables</th>
<th>Price</th>
</tr>";
		
while($row = mysqli_fetch_assoc($result))
	{
	echo "<tr>";
	echo "<td>" . $row['TableId'] . "</td>";
	if ($row['TableId'] < 8){
		echo "<td>WaitStaff1</td>";
	} elseif ($row['TableId'] > 7 && $row['TableId'] < 15){
		echo "<td>WaitStaff2</td>";
	} else {
		echo "<td>WaitStaff3</td>";
	}
	echo "<td>" . $row['Title'] . "</td>";
	echo "<td>" . $row['Modifiables'] . "</td>";
	echo "<td>" . $row['Price'] . "</td>";
	echo "</tr>";
	};
	
echo "</table>";
	
mysqli_close($con);
  
?>

</body>
</html>

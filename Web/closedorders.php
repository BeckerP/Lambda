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
print( '<a href="openorders.php">View Open Orders</a>' );

$result = mysqli_query($con,"SELECT * FROM Admin ORDER BY Date, ID ASC");
$row=array();
echo "<table border='1'>
<tr>
<th>Table ID</th>
<th>Wait Staff</th>
<th>Date</th>
<th>Total</th>
<th>Tax</th>
<th>Net</th>
<th>Category</th>
<th>Tips</th>
<th>Comps</th>
</tr>";
		
while($row = mysqli_fetch_assoc($result))
	{
	echo "<tr>";
	echo "<td>" . $row['ID'] . "</td>";
	if ($row['ID'] < 8){
		echo "<td>WaitStaff1</td>";
	} elseif ($row['ID'] > 7 && $row['ID'] < 15){
		echo "<td>WaitStaff2</td>";
	} else {
		echo "<td>WaitStaff3</td>";
	}
	echo "<td>" . $row['Date'] . "</td>";
	echo "<td>" . $row['Total'] . "</td>";
	echo "<td>" . $row['Tax'] . "</td>";
	echo "<td>" . $row['Net'] . "</td>";
	echo "<td>" . $row['Category'] . "</td>";
	echo "<td>" . $row['Tips'] . "</td>";
	echo "<td>" . $row['Comps'] . "</td>";
	echo "</tr>";
	};
	
echo "</table>";
	
mysqli_close($con);
  
?>

</body>
</html>

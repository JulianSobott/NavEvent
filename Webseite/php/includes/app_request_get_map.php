<?php
session_start();
error_reporting(E_ALL);
//Verbindung mit Datenbank herstellen
//include 'DatenbankConnect.inc.php';

//Read from database
if(isset($_POST['mapName']))
{
	$con = mysqli_connect("localhost", "root", "password", "navevent01");
	
	//Select map data
	$mapName = $_POST['mapName'];
	$sql = "SELECT * FROM maps WHERE name='" . $mapName . "'";
	$res = mysqli_query($con, $sql);
	
	if( $result = mysqli_fetch_assoc($res) )//Select only first result, if more than one
	{
		
		echo $result['name'] . "
"			. $result['id'] . "
"			. $result['major_id'] . "
"			. $result['description'] . "
"			. $result['img_file'] . "
";

		//Select beacon data
		$mapID = $result['id'];
		$o_places = array();
		$s_places = array();
		
		$sql = "SELECT * FROM beacons WHERE fk_map_id='" . $mapID . "'";
		$res = mysqli_query($con, $sql);
		
		echo mysqli_num_rows($res) . "
";
		while( $result = mysqli_fetch_assoc($res) )//Select all beacons
		{
			$beacon_id = $result['id'];
			echo $result['name'] . "
"				. $beacon_id . "
"				. $result['minor_id'] . "
"				. $result['pos_x'] . "
"				. $result['pos_y'] . "
"				. $result['description'] . "
";
			if( isset($result['fk_ordinary']) )
			{
				$place_id = $result['fk_ordinary'];
				if( !isset($o_places[$place_id]) ) $o_places[$place_id] = array();
				$o_places[$place_id][count($o_places[$place_id])] = $beacon_id;
			}
			if( isset($result['fk_special']) )
			{
				$place_id = $result['fk_special'];
				if( !isset($s_places[$place_id]) ) $s_places[$place_id] = array();
				$s_places[$place_id][count($s_places[$place_id])] = $beacon_id;
			}
		}
		
		//Select ordinary places
		echo count($o_places) . "
";
		foreach( $o_places as $place_id=>$arrayList )
		{
			$res = mysqli_query($con, "SELECT * FROM ordinary_places WHERE id='".$place_id."'");
			if( $result = mysqli_fetch_assoc($res) )
			{
				echo $result['name'] . "
"					. count($arrayList) . "
";
				for( $j = 0 ; $j < count($arrayList) ; $j++ )
				{
					echo $arrayList[$j] . "
";
				}
			}
		}
		
		//Select special places
		echo count($s_places) . "
";
		foreach( $s_places as $place_id=>$arrayList )
		{
			$res = mysqli_query($con, "SELECT * FROM special_places WHERE id='".$place_id."'");
			if( $result = mysqli_fetch_assoc($res) )
			{
				echo $result['name'] . "
"					. count($arrayList) . "
";
				for( $j = 0 ; $j < count($arrayList) ; $j++ )
				{
					echo $arrayList[$j] . "
";
				}
			}
		}
		
	}
	
}
else echo "Wrong argument";

?>

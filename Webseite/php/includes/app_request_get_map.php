<?php
session_start();
error_reporting(E_ALL);
//Verbindung mit Datenbank herstellen
include 'DatenbankConnect.inc.php';

//Read from database
if(isset($_POST['mapID']))
{	
	//Select map data
	$mapID = $_POST['mapID'];
	$sql = "SELECT * FROM maps WHERE id='" . $mapID . "'";
	$res = mysqli_query($con, $sql);

	if( $result = mysqli_fetch_assoc($res) )//Select only first result, if more than one
	{
		echo "found map" . chr(0x0A);

		echo $result['name'] . chr(0x0A)
			. $result['id'] . chr(0x0A)
			. $result['major_id'] . chr(0x0A)
			. $result['description'] . chr(0x0A)
			. $result['img_file'] . '.' . $result['mime'] . chr(0x0A);

		//Select beacon data
		$o_places = array();
		$s_places = array();

		$sql = "SELECT * FROM beacons WHERE fk_map_id='" . $mapID . "'";
		$res = mysqli_query($con, $sql);

		echo mysqli_num_rows($res) . chr(0x0A);
		while( $result = mysqli_fetch_assoc($res) )//Select all beacons
		{
			$beacon_id = $result['id'];
			echo $result['name'] . chr(0x0A)
				. $beacon_id . chr(0x0A)
				. $result['minor_id'] . chr(0x0A)
				. $result['pos_x'] . chr(0x0A)
				. $result['pos_y'] . chr(0x0A)
				. $result['description'] . chr(0x0A);

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
		echo count($o_places) . chr(0x0A);
		foreach( $o_places as $place_id=>$arrayList )
		{
			$res = mysqli_query($con, "SELECT * FROM ordinary_places WHERE id='".$place_id."'");
			if( $result = mysqli_fetch_assoc($res) )
			{
				echo $result['name'] . chr(0x0A)
					. count($arrayList) . chr(0x0A);
				for( $j = 0 ; $j < count($arrayList) ; $j++ )
				{
					echo $arrayList[$j] . chr(0x0A);
				}
			}
		}

		//Select special places
		echo count($s_places) . chr(0x0A);
		foreach( $s_places as $place_id=>$arrayList )
		{
			$res = mysqli_query($con, "SELECT * FROM special_places WHERE id='".$place_id."'");
			if( $result = mysqli_fetch_assoc($res) )
			{
				echo $result['name'] . chr(0x0A)
					. count($arrayList) . chr(0x0A);
				for( $j = 0 ; $j < count($arrayList) ; $j++ )
				{
					echo $arrayList[$j] . chr(0x0A);
				}
			}
		}

	}
	else echo "No such map found" . chr(0x0A);
}
else echo "Wrong argument" . chr(0x0A);

?>

<?php

include 'DatabaseMan.php';

$DatabaseMan = new DatabaseMan();

$statusTable = $DatabaseMan->createUsersTable();
$statusLocationPeopleTable = $DatabaseMan->createLocationPeopleTable();

$statusPictureTable = $DatabaseMan->createExtrasTablePictures();
$statusOpenTable = $DatabaseMan->createExtrasTableOpen();
$statusTagTable = $DatabaseMan->createExtrasTableTags();
$statusPersonnelTable = $DatabaseMan->createTablePersonnel();

echo $statusTable;
echo "<br>";
echo  $statusLocationPeopleTable;
echo "<br>";
echo $statusPictureTable;
echo "<br>";
echo $statusOpenTable;
echo "<br>";
echo $statusTagTable;
echo "<br>";
echo $statusPersonnelTable;
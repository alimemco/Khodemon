<?php
include 'config.php';

class DatabaseMan

{
    const DB_NAME = "khodemon_db";
    const DB_USER = "khodemon_user";
    const DB_PASSWORD = "Ali0631Papital";
    const DB_HOST = "localhost";

    const LOCATION_CODE = 1;
    const PEOPLE_CODE = 2;

    const GROUP_NAME_LOCATION = "LOCATION";
    const GROUP_NAME_PEOPLE = "PEOPLE";

    const GROUP_REC_VIEW_LOCATION = "مکان های جدید";
    const GROUP_REC_VIEW_PEOPLE = "افراد جدید";


    function createUsersTable()
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }


        $mySql = "CREATE TABLE users (
      position INT AUTO_INCREMENT  PRIMARY KEY,
      user_name VARCHAR(20) NOT NULL,
      user_pass VARCHAR(40) NOT NULL,
      user_email VARCHAR(20) DEFAULT '',
      user_phone VARCHAR(20) DEFAULT '',
      first_name VARCHAR(20) DEFAULT '',
      last_name VARCHAR(20) DEFAULT '',
      user_image VARCHAR(100) DEFAULT '',
      user_status INT DEFAULT 0,
      user_level INT DEFAULT 0,
      user_score INT DEFAULT 0,
      reg_date TIMESTAMP
)COLLATE='utf8mb4_unicode_520_ci';";

        if ($conn->query($mySql) == TRUE) {
            return "created [ usersT ] successfully";
        } else {
            return "created [ usersT ] ERROR";
        }
    }

    function createLocationPeopleTable()
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }


        $mySql = "CREATE TABLE locationPeople (
      position INT AUTO_INCREMENT  PRIMARY KEY,
      group_name TEXT NOT NULL,
      confirmed INT DEFAULT 0,
      nameLocPeo TEXT,
      tagLocPeo TEXT,
      city TEXT,
      province TEXT,
      since INT DEFAULT 0,
      work_experience INT DEFAULT 0,
      dimensions INT DEFAULT 0,
      master_id INT DEFAULT 0,
      master_username TEXT,
      owner_seller TEXT,
      phone TEXT,
      address TEXT,
      experts TEXT ,
      degreeOfEducation TEXT,
      study TEXT,
      description TEXT,
      instagram TEXT,
      telegram TEXT,
      site TEXT,
      map TEXT,
      is_ad TEXT,
      is_pinned TEXT,
      is_verified TEXT,
      has_certificate TEXT,
      original_pic TEXT,
      thumb_pic TEXT,
      visit INT DEFAULT 0,
      score INT DEFAULT 0,
      reg_date TIMESTAMP
)COLLATE='utf8mb4_unicode_520_ci';";

        //master id select from users table

        if ($conn->query($mySql) == TRUE) {
            return "created [ LocationPeopleT ] successfully";
        } else {
            return "created [ locationPeopleT ] ERROR";
        }
    }


    function createExtrasTablePictures()
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }


        $mySql = "CREATE TABLE pictures (
      position INT AUTO_INCREMENT  PRIMARY KEY,
      Post_ID INT NOT NULL,
      is_original  TEXT,
      pic_id INT NOT NULL ,
      pic_address TEXT,
      width TEXT,
      height TEXT,
      thumb_150 TEXT,
      thumb_1000 TEXT
)COLLATE='utf8mb4_unicode_520_ci';";


        if ($conn->query($mySql) == TRUE) {
            return "created [ picturesT ] successfully";
        } else {
            return "created [ picturesT ] ERROR";
        }
    }

    function createExtrasTableOpen()
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }


        $mySql = "CREATE TABLE open (
      position INT AUTO_INCREMENT  PRIMARY KEY,
      location_ID INT NOT NULL,
      days  TEXT,
      is_24  INT DEFAULT 0,
      is_two_shift  INT DEFAULT 1,
      start_hour_one  INT DEFAULT 8,
      end_hour_one  INT DEFAULT 13,
      start_hour_two  INT DEFAULT 0,
      end_hour_two  INT DEFAULT 0
)COLLATE='utf8mb4_unicode_520_ci';";


        if ($conn->query($mySql) == TRUE) {
            return "created [ openT ] successfully";
        } else {
            return "created [ openT ] ERROR";
        }
    }

    function createExtrasTableTags()
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }


        $mySql = "CREATE TABLE tags (
      position INT AUTO_INCREMENT  PRIMARY KEY,
      Post_ID INT NOT NULL,
      tags_key  INT NOT NULL,
      tags_value  VARCHAR(30) DEFAULT ''
)COLLATE='utf8mb4_unicode_520_ci';";


        if ($conn->query($mySql) == TRUE) {
            return "created [ tagT ] successfully";
        } else {
            return "created [ tagT ] ERROR";
        }
    }


    function createTablePersonnel()
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }


        $mySql = "CREATE TABLE personnel (
      position INT AUTO_INCREMENT  PRIMARY KEY,
      LOCATION_ID INT NOT NULL,
      PEOPLE_ID INT NOT NULL,
      TagPeople  TEXT,
      personnelName  TEXT,
      withEvidence  TEXT,
      evidence  TEXT,
      image TEXT,
      width TEXT,
      height TEXT,
      thumb_150 TEXT,
      thumb_1000 TEXT
)COLLATE='utf8mb4_unicode_520_ci';";


        if ($conn->query($mySql)) {
            return "created [ personnelT ] successfully";
        } else {
            return "created [ picturesT ] ERROR";
        }
    }


    function createUser($user_name,
                        $user_pass,
                        $first_name,
                        $last_name
    )
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        mysqli_query($conn, "SET NAMES utf8");
        $mysqlUserCheck = "SELECT * FROM `users` WHERE `user_name`='$user_name'";

        $res = mysqli_query($conn, $mysqlUserCheck);
        if ($res->num_rows > 0) {
            return 0;
            //user_exists
        } else {
            $hashedPass = md5($user_pass);
            $mySqli = "INSERT INTO `users`( `user_name`, `user_pass`, `first_name`, `last_name`) VALUES 
                ('$user_name','$hashedPass','$first_name','$last_name') ";

            if ($conn->query($mySqli) == TRUE) {
                return 29;
                //success
            } else {
                return 1;
                //error
            }
        }

    }

    function loginUser($user_name, $user_pass)
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        mysqli_query($conn, "SET NAMES utf8");
        $mysqlLogin = "SELECT * FROM `users` WHERE `user_name`='$user_name' OR 
         `user_email`='$user_name' OR 
         `user_phone`='$user_name'";

        $res = mysqli_query($conn, $mysqlLogin);

        if ($res->num_rows > 0) {

            $row = $res->fetch_array();

            $hashPassword = md5($user_pass);
            $dbPassword = $row['user_pass'];


            // $result = array();

            if ($hashPassword == $dbPassword) {

                $result[] = array(
                    "success" => "true",
                    "message" => "login successfully",
                    "code" => "29",
                    "username" => $user_name
                );
                //return 29;
                //success
            } else {
                $result[] = array(
                    "success" => "false",
                    "message" => "wrong pass",
                    "code" => "2",
                    "username" => $user_name
                );
                //return 2;
                //wrong pass
            }

        } else {
            $result[] = array(
                "success" => "false",
                "message" => "user Not Found",
                "code" => "1",
                "username" => $user_name
            );
            //return 1;
            //user Not Found.
        }

        return $result;
    }

    function AddLocationOrPeople(
        $group_name,
        $nameLocPeo,
        $master_username,
        $owner_seller,
        $tagLoc,
        $city,
        $province,
        $since,
        $work_experience,
        $dimensions,
        $phone,
        $address,
        $experts,
        $degreeOfEducation,
        $study,
        $description,
        $instagram,
        $telegram,
        $site,
        $map,
        $evidence,
        $original_pic,
        $thumb_pic,
        $time
    )
    {

        date_default_timezone_set('Asia/Tehran');

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        mysqli_query($conn, "SET NAMES utf8");
        $mysqlUserCheck = "SELECT * FROM `users` WHERE `user_name`='$master_username' OR `user_email`='$master_username' OR `user_phone`='$master_username'";

        $res = mysqli_query($conn, $mysqlUserCheck);
        if ($res->num_rows > 0) {

            $row = mysqli_fetch_array($res, MYSQLI_ASSOC);
            $masterID = $row['position'];
            $masterUserName = $row['user_name'];


            $mysqlInsertLocPeo = "INSERT INTO `locationPeople` 
( `group_name`, `confirmed`, `nameLocPeo`, `tagLocPeo`, `city`,
 `province`, `since`, `work_experience`, `dimensions`, `master_id`,
  `master_username`, `owner_seller`, `phone`, `address`, `experts`,
   `degreeOfEducation`, `study`, `description`, `instagram`, `telegram`,
    `site`, `map`, `is_ad`, `is_pinned`, `is_verified`,
     `has_certificate`, `original_pic`, `thumb_pic`, `visit`, `score`, `reg_date`) 
     VALUES 
            ( '$group_name', '1', '$nameLocPeo', '$tagLoc', '$city',
             '$province', '$since', '$work_experience', '$dimensions', '0',
              '$masterUserName', '$owner_seller', '$phone', '$address', '',
               '', '', '$description', '$instagram', '$telegram',
                '$site', '$map', '0', '0', 'false',
                 '', '$original_pic','$thumb_pic', '0', '0', $time);";

            $response = array();
            if ($resLocPeo = mysqli_query($conn, $mysqlInsertLocPeo)) {
                $last_id = $conn->insert_id;
                $response['success'] = 1;
                $response['last_id'] = $last_id;
                $response['message'] = "Successfully";
                // return $last_id;
            } else {
                $response['success'] = 0;
                $response['last_id'] = -1;
                $response['message'] = " error insert ==> " . mysqli_error($conn);
                // return "(( error insert Loc )) ==> " . mysqli_error($conn);
            }

            return $response;


            //user_exists
        } else {
            return 'user not found';
        }

    }

    function getHomeItems()
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");

        $mySql = "SELECT * FROM `locationPeople` ORDER BY `reg_date` DESC";

        $result = $conn->query($mySql);

        $rows = array();

        while ($res = $result->fetch_assoc()) {
            $rows[] = array(
                "position" => $res['position'],
                "group_name" => $res['group_name'],
                "name" => $res['nameLocPeo'],
                "tagLoc" => $res['tagLoc'],
                "original_pic" => $res['original_pic']
            );
        }

        if ($conn->query($mySql) == TRUE) {
            return $rows;
        } else {
            return "[ ERROR ] " . mysqli_error($conn);
        }
    }

    function getGroupItems($group)
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");

        $LocationGroupName = DatabaseMan::GROUP_NAME_LOCATION;
        $PeopleGroupName = DatabaseMan::GROUP_NAME_PEOPLE;


        if ($group == DatabaseMan::GROUP_NAME_LOCATION) {
            $mySql = "SELECT * FROM `locationPeople` WHERE `group_name`='$LocationGroupName' ORDER BY `visit` DESC , `reg_date` DESC ";

        } else if ($group == DatabaseMan::GROUP_NAME_PEOPLE) {
            $mySql = "SELECT * FROM `locationPeople` WHERE `group_name`='$PeopleGroupName' ORDER BY `visit` DESC , `reg_date` DESC ";

        } else if (empty($code)) {
            return "empty";

        }

        $result = $conn->query($mySql);

        $rows = array();

        if ($group == DatabaseMan::GROUP_NAME_LOCATION) {
            while ($res = $result->fetch_assoc()) {
                $rows[] = array(
                    "position" => $res['position'],
                    "group_name" => $res['group_name'],
                    "name" => $res['nameLocPeo'],
                    "tagLocPeo" => $res['tagLocPeo'],
                    "city" => $res['city'],
                    "province" => $res['province'],
                    "address" => $res['address'],
                    "owner_seller" => $res['owner_seller'],
                    "original_pic" => $res['original_pic']
                );
            }

        } else if ($group == DatabaseMan::GROUP_NAME_PEOPLE) {
            while ($res = $result->fetch_assoc()) {
                $rows[] = array(
                    "position" => $res['position'],
                    "group_name" => $res['group_name'],
                    "name" => $res['nameLocPeo'],
                    "tagLocPeo" => $res['tagLocPeo'],
                    "city" => $res['city'],
                    "work_experience" => $res['work_experience'],
                    "experts" => $res['experts'],
                    "original_pic" => $res['original_pic']
                );
            }

        } else if (empty($code)) {
            return "empty";

        }


        if ($conn->query($mySql) == TRUE) {
            return $rows;

        } else {
            return "[ ERROR ] " . mysqli_error($conn);
        }
    }

    function getHomeItemsList()
    {

        $LIMIT_ITEM_REC = 15;

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");


        $mySql = "SELECT * FROM `locationPeople` WHERE `group_name`='LOCATION' ORDER BY `reg_date` DESC LIMIT $LIMIT_ITEM_REC";
        $result = $conn->query($mySql);
        $rows = array();

        // $rows[] = array("title"=>"Location");

        while ($res = $result->fetch_assoc()) {
            $rows[] = array(
                "position" => $res['position'],
                "group_name" => $res['group_name'],
                "name" => $res['nameLocPeo'],
                "tag" => $res['tagLocPeo'],
                "original_pic" => $res['original_pic']
            );
        }

        $mySqlPeople = "SELECT * FROM `locationPeople` WHERE `group_name`='PEOPLE' ORDER BY `reg_date` DESC LIMIT $LIMIT_ITEM_REC";
        $resultPeople = $conn->query($mySqlPeople);
        $rowsPeople = array();

        while ($resPeople = $resultPeople->fetch_assoc()) {
            $rowsPeople[] = array(
                "position" => $resPeople['position'],
                "group_name" => $resPeople['group_name'],
                "name" => $resPeople['nameLocPeo'],
                "tag" => $resPeople['tagLocPeo'],
                "original_pic" => $resPeople['original_pic']
            );
        }


        $listItems = array();
        $listItems[0] = array(
            "title" => DatabaseMan::GROUP_REC_VIEW_LOCATION,
            "group" => DatabaseMan::GROUP_NAME_LOCATION,
            "data" => $rows
        );
        $listItems[1] = array(
            "title" => DatabaseMan::GROUP_REC_VIEW_PEOPLE,
            "group" => DatabaseMan::GROUP_NAME_PEOPLE,
            "data" => $rowsPeople
        );


        if ($conn->query($mySql) == TRUE) {
            return $listItems;

        } else {
            return "[ ERROR ] " . mysqli_error($conn);
        }
    }


    function addPictures($Post_id,
                         $is_original,
                         $pic_id,
                         $pic_address,
                         $width,
                         $height,
                         $thumb_150,
                         $thumb_1000)
    {


        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        mysqli_query($conn, "SET NAMES utf8");


        $mysqlInsertPictures = "INSERT INTO `pictures` 
            ( `Post_ID`,`is_original`, `pic_id`, `pic_address` ,`width`,`height`, `thumb_150`, `thumb_1000`) VALUES 
            (  '$Post_id','$is_original', '$pic_id','$pic_address','$width','$height', '$thumb_150','$thumb_1000');";

        if ($resPictures = mysqli_query($conn, $mysqlInsertPictures)) {
            return "pictures inserted :)";
        } else {
            return "(( error insert Loc )) ==> " . mysqli_error($conn);
        }


    }

    function getPictures($Post_ID)
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");

        $mySql = "SELECT * FROM `pictures` WHERE `Post_ID`='$Post_ID' ORDER BY `pic_id` ";


        $result = $conn->query($mySql);

        $rows = array();

        while ($res = $result->fetch_assoc()) {
            $rows[] = array(
                "is_original" => $res['is_original'],
                "pic_id" => $res['pic_id'],
                "pic_address" => $res['pic_address'],
                "width" => $res['width'],
                "height" => $res['height'],
                "thumb_1000" => $res['thumb_1000'],
                "thumb_150" => $res['thumb_150']
            );
        }

        if ($conn->query($mySql) == TRUE) {
            return $rows;

        } else {
            return "[ ERROR ] " . mysqli_error($conn);
        }
    }

    function getDetail($Post_ID)
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");

        $mySql = "SELECT * FROM `locationPeople` WHERE `position`='$Post_ID' ";


        //$result = array();
        $rows = array();
        $info = array();

        if ($data = $conn->query($mySql)) {

            if ($data->num_rows > 0) {

                while ($res = $data->fetch_assoc()) {
                    $rows[] = array(
                        "phone" => $res['phone'],

                    );
                    /*
                    $rows[] = array(
                        "group_name" => $res['group_name'],
                        "confirmed" => $res['confirmed'],
                        "nameLocPeo" => $res['nameLocPeo'],
                        "tagLocPeo" => $res['tagLocPeo'],
                        "city" => $res['city'],
                        "province" => $res['province'],
                        "since" => $res['since'],
                        "work_experience" => $res['work_experience'],
                        "dimensions" => $res['dimensions'],
                        "master_username" => $res['master_username'],
                        "owner_seller" => $res['owner_seller'],
                        "phone" => $res['phone'],
                        "address" => $res['address'],
                        "experts" => $res['experts'],
                        "degreeOfEducation" => $res['degreeOfEducation'],
                        "study" => $res['study'],
                        "description" => $res['description'],
                        "instagram" => $res['instagram'],
                        "telegram" => $res['telegram'],
                        "site" => $res['site'],
                        "map" => $res['map'],
                        "is_ad" => $res['is_ad'],
                        "is_pinned" => $res['is_pinned'],
                        "is_verified" => $res['is_verified'],
                        "has_certificate" => $res['has_certificate'],
                        "visit" => $res['visit'],
                        "score" => $res['score'],
                        "reg_date" => $res['reg_date']

                    );
                    */
                }


                $result = array(
                    "success" => "true",
                    "message" => "Successfully :)",
                    "items" => $rows
                );


            } else {
                $result = array(
                    "success" => "false",
                    "message" => "error get data"
                );
            }


        } else {
            $result = array(
                "success" => "false",
                "message" => mysqli_error($conn)
            );
        }


        return $result;

    }

    function getInfo($GROUP_NAME, $Post_ID)
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");

        $mySql = "SELECT * FROM `locationPeople` WHERE `position`='$Post_ID' ";


        $merge = array();

        if ($data = $conn->query($mySql)) {

            if ($data->num_rows > 0) {

                if ($GROUP_NAME == DatabaseMan::GROUP_NAME_LOCATION) {
                    while ($res = $data->fetch_assoc()) {
                        $dimensions = array('0' => array(
                            "key" => "dimensions",
                            "value" => $res['dimensions'],
                            "isBoolean" => "false"
                        ));
                        $since = array('0' => array(
                            "key" => "since",
                            "value" => $res['since'],
                            "isBoolean" => "false"
                        ));
                        $phone = array('0' => array(
                            "key" => "phone",
                            "value" => $res['phone'],
                            "isBoolean" => "false"
                        ));

                        $merge = array_merge($dimensions, $since, $phone);

                    }
                } else if ($GROUP_NAME == DatabaseMan::GROUP_NAME_PEOPLE) {
                    while ($res = $data->fetch_assoc()) {
                        $is_verified = array('0' => array(
                            "key" => "is_verified",
                            "value" => $res['is_verified'],
                            "isBoolean" => "true"
                        ));
                        $has_certificate = array('0' => array(
                            "key" => "has_certificate",
                            "value" => $res['has_certificate'],
                            "isBoolean" => "true"
                        ));
                        $degreeOfEducation = array('0' => array(
                            "key" => "degreeOfEducation",
                            "value" => $res['degreeOfEducation'],
                            "isBoolean" => "false"
                        ));

                        $study = array('0' => array(
                            "key" => "study",
                            "value" => $res['study'],
                            "isBoolean" => "false"
                        ));
                        $since = array('0' => array(
                            "key" => "since",
                            "value" => $res['since'],
                            "isBoolean" => "false"
                        ));
                        $work_experience = array('0' => array(
                            "key" => "work_experience",
                            "value" => $res['work_experience'],
                            "isBoolean" => "false"
                        ));

                        $experts = array('0' => array(
                            "key" => "experts",
                            "value" => $res['experts'],
                            "isBoolean" => "false"
                        ));


                        $merge = array_merge(
                            $is_verified,
                            $has_certificate,
                            $degreeOfEducation,
                            $study, $since,
                            $work_experience,
                            $experts);
                        /*
 group_name TEXT NOT NULL,
 confirmed INT DEFAULT 0,
 nameLocPeo TEXT,
 tagLocPeo TEXT,
 city TEXT,
 province TEXT,
 since INT DEFAULT 0,
 work_experience INT DEFAULT 0,
 dimensions INT DEFAULT 0,
 master_id INT DEFAULT 0,
 master_username TEXT,
 owner_seller TEXT,
 phone TEXT,
 address TEXT,
 experts TEXT,
 degreeOfEducation TEXT,
 study TEXT,
 description TEXT,
 instagram TEXT,
 telegram TEXT,
 site TEXT,
 map TEXT,
 is_ad INT DEFAULT 0,
 is_pinned INT DEFAULT 0,
 is_verified INT DEFAULT 0,
 has_certificate INT DEFAULT 0,
 original_pic TEXT,
 thumb_pic TEXT,
 visit INT DEFAULT 0,
 score INT DEFAULT 0,
 reg_date TIMESTAMP*/

                    }
                }


                $result = array(
                    "success" => "true",
                    "message" => "Successfully :)",
                    "items" => $merge
                );


            } else {
                $result = array(
                    "success" => "false",
                    "message" => "error get data"
                );
            }


        } else {
            $result = array(
                "success" => "false",
                "message" => mysqli_error($conn)
            );
        }


        return $result;

    }

    function getCategory($category)
    {


        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");


        $mySql = "SELECT * FROM `locationPeople` WHERE `group_name`='$category' ORDER BY `visit` DESC , `reg_date` DESC ";

        $rows = array();
        $resRow = array();

        if ($result = $conn->query($mySql)) {

            if (mysqli_num_rows($result) > 0) {

                while ($res = $result->fetch_assoc()) {
                    $rows[] = array(

                        "position" => $res['position'],
                        "nameLocPeo" => $res['nameLocPeo'],
                        "tagLocPeo" => $res['tagLocPeo'],
                        "original_pic" => $res['original_pic'],
                        "thumb_pic" => $res['thumb_pic']
                    );

                }

                $resRow[] = array(
                    "success" => "true",
                    "message" => "get successfully " . $category,
                    "items" => $rows
                );

            } else {
                $resRow[] = array(
                    "success" => "false",
                    "message" => "in empty " . $category
                );
            };


        } else {
            $resRow[] = array(
                "success" => "false",
                "message" => mysqli_error($conn) . " " . $category,
                "items" => $rows
            );
        }


        return $resRow;

    }


    function getSimilar($category)
    {


        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");


        $mySql = "SELECT * FROM `locationPeople` WHERE `group_name`='$category' ORDER BY `visit` DESC LIMIT 7";

        $rows = array();
        $resRow = array();

        if ($result = $conn->query($mySql)) {

            if (mysqli_num_rows($result) > 0) {

                while ($res = $result->fetch_assoc()) {
                    $rows[] = array(

                        "position" => $res['position'],
                        "nameLocPeo" => $res['nameLocPeo'],

                        "tagLocPeo" => $res['tagLocPeo'],
                        "original_pic" => $res['original_pic'],
                        "thumb_pic" => $res['thumb_pic']
                    );

                }

                $resRow[] = array(
                    "success" => "true",
                    "message" => "get successfully " . $category,
                    "items" => $rows
                );

            } else {
                $resRow[] = array(
                    "success" => "false",
                    "message" => "in empty " . $category
                );
            };


        } else {
            $resRow[] = array(
                "success" => "false",
                "message" => mysqli_error($conn) . " " . $category,
                "items" => $rows
            );
        }


        return $resRow;

    }

    function addPersonnel($LOCATION_ID,
                          $PEOPLE_ID,
                          $withEvidence,
                          $evidence)
    {


        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        mysqli_query($conn, "SET NAMES utf8");

        $mysqlFetch = "SELECT * FROM `locationPeople` WHERE `position`='$PEOPLE_ID'";


        if ($resQueryFetch = $conn->query($mysqlFetch)) {

            $detail = array();
            $result = array();

            while ($resFetch = $resQueryFetch->fetch_assoc()) {
                //$personnelName = $res['nameLocPeo'];
                $detail[] = array(
                    "nameLocPeo" => $resFetch['nameLocPeo'],
                    "tagLocPeo" => $resFetch['tagLocPeo'],
                    "original_pic" => $resFetch['original_pic'],
                    "thumb_pic" => $resFetch['thumb_pic'],
                );


            }

            $personnelName = $detail[0]['nameLocPeo'];
            $image = $detail[0]['original_pic'];
            $TagPeople = $detail[0]['tagLocPeo'];

            $mysqlFetchImage = "SELECT * FROM `pictures` WHERE `Post_ID`='$PEOPLE_ID' AND `is_original`='1'";

            if ($resQueryImage = $conn->query($mysqlFetchImage)) {

                while ($resImage = $resQueryImage->fetch_assoc()) {


                    $detail[] = array(
                        "width" => $resImage['width'],
                        "height" => $resImage['height'],
                        "thumb_150" => $resImage['thumb_150'],
                        "thumb_1000" => $resImage['thumb_1000']
                    );


                }

                $width = $detail[1]['width'];
                $height = $detail[1]['height'];
                $thumb_150 = $detail[1]['thumb_150'];
                $thumb_1000 = $detail[1]['thumb_1000'];


                $mysqlInsert = "INSERT INTO `personnel` 
            ( `LOCATION_ID`,`PEOPLE_ID`,`TagPeople`,`personnelName`, `withEvidence`, `evidence`, `image` ,`width`,`height`, `thumb_150`, `thumb_1000`) VALUES 
            (  '$LOCATION_ID','$PEOPLE_ID','$TagPeople','$personnelName','$withEvidence','$evidence','$image','$width','$height', '$thumb_150','$thumb_1000');";

                if (mysqli_query($conn, $mysqlInsert)) {
                    $result [] = array(
                        "success" => "true",
                        "message" => "personnel ad successfully"
                    );

                } else {
                    $result [] = array(
                        "success" => "false",
                        "message" => mysqli_error($conn)
                    );

                }


            } else {
                $result [] = array(
                    "success" => "false",
                    "message" => "mysqlFetch | " . mysqli_error($conn)
                );
            }


            // return $rows;
        } else {
            $result [] = array(
                "success" => "false",
                "message" => "mysqlFetch | " . mysqli_error($conn)
            );
            //return ;
        }

        return $result;

    }

    function getPersonnel($LOCATION_ID)
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");


        $mySqlQuery = "SELECT * FROM `personnel` WHERE `LOCATION_ID`=$LOCATION_ID";


        $rows = array();
        $resRow = array();

        if ($result = $conn->query($mySqlQuery)) {

            if (mysqli_num_rows($result) > 0) {

                while ($res = $result->fetch_assoc()) {
                    $rows[] = array(

                        "position" => $res['position'],
                        "PEOPLE_ID" => $res['PEOPLE_ID'],
                        "personnelName" => $res['personnelName'],
                        "TagPeople" => $res['TagPeople'],
                        "withEvidence" => $res['withEvidence'],
                        "image" => $res['image'],
                        "width" => $res['width'],
                        "height" => $res['height'],
                        "thumb_150" => $res['thumb_150'],
                        "thumb_1000" => $res['thumb_1000']
                    );
                }

                $resRow[] = array(
                    "success" => "true",
                    "message" => "personnel get successfully",
                    "items" => $rows
                );

            } else {
                $resRow[] = array(
                    "success" => "false",
                    "message" => "personnel in empty",
                    "items" => $rows
                );
            };


        } else {
            $resRow[] = array(
                "success" => "false",
                "message" => mysqli_error($conn),
                "items" => $rows
            );
        }


        return $resRow;

    }


    function getPersonList()
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");

        $PeopleGroupName = DatabaseMan::GROUP_NAME_PEOPLE;


        $mySql = "SELECT * FROM `locationPeople` WHERE `group_name`='$PeopleGroupName' ORDER BY `visit` DESC , `reg_date` DESC ";


        $rows = array();
        $resRow = array();

        if ($result = $conn->query($mySql)) {

            if (mysqli_num_rows($result) > 0) {

                while ($res = $result->fetch_assoc()) {
                    $rows[] = array(

                        "position" => $res['position'],
                        "nameLocPeo" => $res['nameLocPeo'],
                        "tagLocPeo" => $res['tagLocPeo'],
                        "original_pic" => $res['original_pic'],
                        "thumb_pic" => $res['thumb_pic']
                    );

                }

                $resRow[] = array(
                    "success" => "true",
                    "message" => "person get successfully",
                    "items" => $rows
                );

            } else {
                $resRow[] = array(
                    "success" => "false",
                    "message" => "person in empty",
                    "items" => $rows
                );
            };


        } else {
            $resRow[] = array(
                "success" => "false",
                "message" => mysqli_error($conn),
                "items" => $rows
            );
        }


        return $resRow;
    }


    function search($category, $keyword, $city, $province)
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");


        $query = "";

        if (isset($category)) {
            $query = "SELECT * FROM locationPeople WHERE tagLocPeo LIKE '%$category%' ";

            if (isset($city)) {
                $query .= "AND city LIKE '$city' ";
            }

            if (isset($province)) {
                $query .= "AND province LIKE '$province' ";
            }

            if (isset($keyword)) {
                // $query .= "AND ( nameLocPeo LIKE '%$keyword%' OR experts LIKE '%$keyword%') ";
                $aKeyword = explode(" ", $keyword);
                $query = "AND ( nameLocPeo like '%" . $aKeyword[0] . "%') ";

            }


        } else if (isset($keyword)) {

            $aKeyword = explode(" ", $keyword);
            $query = "SELECT * FROM locationPeople WHERE nameLocPeo like '%" . $aKeyword[0] . "%'";

            for ($i = 1; $i < count($aKeyword); $i++) {
                if (!empty($aKeyword[$i])) {
                    $query .= " AND nameLocPeo like '%" . $aKeyword[$i] . "%'";
                }
            }


            // $query = "SELECT * FROM locationPeople WHERE nameLocPeo LIKE '%$keyword%' OR experts LIKE '%$keyword%' ";
            if (isset($city)) {
                $query .= "AND city LIKE '$city' ";
            }
            if (isset($province)) {
                $query .= "AND province LIKE '$province' ";
            }
        } else if (isset($city)) {
            $query = "SELECT * FROM locationPeople WHERE city LIKE '$city' ";
        } else if (isset($city)) {
            $query = "SELECT * FROM locationPeople WHERE province LIKE '$province' ";
        }


        $rows = array();
        $resRow = array();

        if ($result = $conn->query($query)) {

            if (mysqli_num_rows($result) > 0) {

                while ($res = $result->fetch_assoc()) {
                    $rows[] = array(

                        "position" => $res['position'],
                        "nameLocPeo" => $res['nameLocPeo'],
                        "tagLocPeo" => $res['tagLocPeo'],
                        "city" => $res['city'],
                        "province" => $res['province'],
                        "original_pic" => $res['original_pic'],
                        "thumb_pic" => $res['thumb_pic']

                    );

                }

                $resRow[] = array(
                    "success" => "true",
                    "message" => "successfully ! ITEMS -> " . mysqli_num_rows($result),
                    "items" => $rows
                );

            } else {
                $resRow[] = array(
                    "success" => "false",
                    "message" => "in empty"
                );
            };


        } else {
            $resRow[] = array(
                "success" => "false",
                "message" => mysqli_error($conn)
            );
        }

        return $resRow;
    }

    function getSearchCategory($category, $keyword, $city, $province)
    {

        $conn = new mysqli(DatabaseMan::DB_HOST, DatabaseMan::DB_USER, DatabaseMan::DB_PASSWORD, DatabaseMan::DB_NAME);
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysqli_query($conn, "SET NAMES utf8");


        $query = "";

        if (isset($category)) {
            $query = "SELECT * FROM locationPeople WHERE tagLocPeo LIKE '%$category%' ";

            if (isset($city)) {
                $query .= "AND city LIKE '$city' ";
            }

            if (isset($province)) {
                $query .= "AND province LIKE '$province' ";
            }

            if (isset($keyword)) {
                // $query .= "AND ( nameLocPeo LIKE '%$keyword%' OR experts LIKE '%$keyword%') ";
                $aKeyword = explode(" ", $keyword);
                $query = "AND ( nameLocPeo like '%" . $aKeyword[0] . "%') ";

            }


        } else if (isset($keyword)) {

            $aKeyword = explode(" ", $keyword);
            $query = "SELECT * FROM locationPeople WHERE nameLocPeo like '%" . $aKeyword[0] . "%'";

            for ($i = 1; $i < count($aKeyword); $i++) {
                if (!empty($aKeyword[$i])) {
                    $query .= " AND nameLocPeo like '%" . $aKeyword[$i] . "%'";
                }
            }


            // $query = "SELECT * FROM locationPeople WHERE nameLocPeo LIKE '%$keyword%' OR experts LIKE '%$keyword%' ";
            if (isset($city)) {
                $query .= "AND city LIKE '$city' ";
            }
            if (isset($province)) {
                $query .= "AND province LIKE '$province' ";
            }
        } else if (isset($city)) {
            $query = "SELECT * FROM locationPeople WHERE city LIKE '$city' ";
        } else if (isset($city)) {
            $query = "SELECT * FROM locationPeople WHERE province LIKE '$province' ";
        }


        $searchRes = array();
        $rows = array();


        if ($result = $conn->query($query)) {

            if (mysqli_num_rows($result) > 0) {

                while ($res = $result->fetch_assoc()) {
                    $rows[] = array(

                        "position" => $res['position'],
                        "nameLocPeo" => $res['nameLocPeo'],
                        "tagLocPeo" => $res['tagLocPeo'],
                        "city" => $res['city'],
                        "province" => $res['province'],
                        "original_pic" => $res['original_pic'],
                        "thumb_pic" => $res['thumb_pic']

                    );

                }

                $searchRes[] = array(
                    "success" => "true",
                    "message" => "successfully ! ITEMS -> " . mysqli_num_rows($result),
                    "items" => $rows
                );

            } else {
                $searchRes[] = array(
                    "success" => "false",
                    "message" => "in empty"
                );
            };


        } else {

            $searchRes[] = array(
                "success" => "false",
                "message" => mysqli_error($conn)
            );
        }


        $items = array();

        $items[0] = array(
            "title" => "search",
            "data" => $searchRes);

        $items[1] = array(
            "title" => "suggestion",
            "data" => $this->getSuggestion(3, $category, $city, $province));

        $items[2] = array(
            "title" => "ad",
            "data" => $this->getAd(1, $category, $city, $province));

        return $items;
    }

    function getAd($count, $category, $city, $province)
    {

        $conn = OpenConnection();
        mysqli_query($conn, "SET NAMES utf8");

        $query = "SELECT * FROM `locationPeople` WHERE `is_ad`='true'";

        if (isset($city)) {
            $query .= " AND `city`='$city'";
        }

        if (isset($category)) {
            $query .= " AND `tagLocPeo`='$category'";
        }

        if (isset($province)) {
            $query .= " AND `province`='$province'";
        }


        $query .= " LIMIT " . $count;

        $ads = array();
        $rows = array();




        if ($result = $conn->query($query)) {

            if (mysqli_num_rows($result) > 0) {

                while ($res = $result->fetch_assoc()) {
                    $rows[] = array(

                        "position" => $res['position'],
                        "nameLocPeo" => $res['nameLocPeo'],
                        "tagLocPeo" => $res['tagLocPeo'],
                        "city" => $res['city'],
                        "province" => $res['province'],
                        "original_pic" => $res['original_pic'],
                        "thumb_pic" => $res['thumb_pic']


                    );
                }
                $success = "true";
                $message = "successfully ! ITEMS -> " . mysqli_num_rows($result);
            } else {
                //not get data with arguments

                $query = "SELECT * FROM `locationPeople` WHERE `is_ad`='true' LIMIT $count";

                $result = $conn->query($query);

                if (mysqli_num_rows($result) > 0){
                    while ($res = $result->fetch_assoc()) {
                        $rows[] = array(

                            "position" => $res['position'],
                            "nameLocPeo" => $res['nameLocPeo'],
                            "tagLocPeo" => $res['tagLocPeo'],
                            "city" => $res['city'],
                            "province" => $res['province'],
                            "original_pic" => $res['original_pic'],
                            "thumb_pic" => $res['thumb_pic']


                        );
                    }
                    $success = "true";
                    $message = "successfully ELSE ! ITEMS -> " . mysqli_num_rows($result);

                }else{
                    $success = "false";
                    $message = "empty";
                }

            }

        } else {
            $success = "false";
            $message = mysqli_error($conn);
        }


        $ads[] = array(
            "success" => $success,
            "message" => $message,
            "query" => $query,
            "items" => $rows
        );
        return $ads;
    }

    function getSuggestion($count, $category, $city, $province)
    {

        $conn = OpenConnection();
        mysqli_query($conn, "SET NAMES utf8");

        $querySuggestion = "SELECT * FROM `locationPeople`";

        if (isset($city)) {
            $querySuggestion .= " WHERE `city`='$city'";

            if (isset($category)) {
                $querySuggestion .= " OR `tagLocPeo`='$category'";
            }

            if (isset($province)) {
                $querySuggestion .= " OR `province`='$province'";
            }


        } else if (isset($category)) {
            $querySuggestion .= " WHERE `tagLocPeo`='$category'";

            if (isset($province)) {
                $querySuggestion .= " OR `province`='$province'";
            }

        } else if (isset($province)) {
            $querySuggestion .= " WHERE `province`='$province'";

        }


        $querySuggestion .= " ORDER BY `score` DESC LIMIT " . $count;

        $suggestions = array();
        $rows = array();


        if ($result = $conn->query($querySuggestion)) {

            if (mysqli_num_rows($result) > 0) {

                while ($res = $result->fetch_assoc()) {
                    $rows[] = array(

                        "position" => $res['position'],
                        "nameLocPeo" => $res['nameLocPeo'],
                        "tagLocPeo" => $res['tagLocPeo'],
                        "city" => $res['city'],
                        "province" => $res['province'],
                        "original_pic" => $res['original_pic'],
                        "thumb_pic" => $res['thumb_pic']


                    );
                }
                $success = "true";
                $message = "successfully ! ITEMS -> " . mysqli_num_rows($result);
            } else {
                $success = "false";
                $message = "empty";
            }

        } else {
            $success = "false";
            $message = mysqli_error($conn);
        }


        $suggestions[] = array(
            "success" => $success,
            "message" => $message,
            "query" => $querySuggestion,
            "items" => $rows
        );
        return $suggestions;
    }

}
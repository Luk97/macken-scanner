<?php

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    http_response_code(405); // Method Not Allowed
    header('Allow: GET');
    echo json_encode(["error" => "Method Not Allowed. Only GET is allowed."]);
    exit;
}

// Database connection details
$host = "localhost";
$dbname = "d04219f7";
$username = "d04219f7";
$password = "RGGtV9xQxfhGEVC4HMtM";


header('Content-Type: application/json');

try {
    // Create a PDO connection
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Retrieve GET parameters
    $date = isset($_GET['date']) ? $_GET['date'] : null;
    $datetime1 = isset($_GET['datetime1']) ? $_GET['datetime1'] : null;
    $datetime2 = isset($_GET['datetime2']) ? $_GET['datetime2'] : null;

    // Validate input
    if (empty($date) && (empty($datetime1) || empty($datetime2))) {
        http_response_code(400);
        echo json_encode(["error" => "You must provide either 'date' or both 'datetime1' and 'datetime2' as parameters."]);
        exit;
    }

    // Prepare query variables
    $query = "";
    $params = [];

    // Query for a specific date
    if (!empty($date)) {
        $query = "
            SELECT 
                COUNT(*) AS total_entries,
                SUM(value) AS total_value,
                AVG(value) AS average_value,
                MIN(value) AS min_value,
                MAX(value) AS max_value
            FROM (
                SELECT value
                FROM Wasteside 
                WHERE DATE(scan_time) = :specific_date
                AND scan_time = (
                    SELECT MIN(scan_time) 
                    FROM Wasteside w2 
                    WHERE w2.qr_code = Wasteside.qr_code
                )
            ) AS first_scans
        ";
        $params[':specific_date'] = $date;
    }
    
    // Query for a time range
    if (!empty($datetime1) && !empty($datetime2)) {
        $query = "
            SELECT 
                COUNT(*) AS total_entries,
                SUM(value) AS total_value,
                AVG(value) AS average_value,
                MIN(value) AS min_value,
                MAX(value) AS max_value
            FROM (
                SELECT value
                FROM Wasteside 
                WHERE scan_time BETWEEN :datetime1 AND :datetime2
                AND scan_time = (
                    SELECT MIN(scan_time) 
                    FROM Wasteside w2 
                    WHERE w2.qr_code = Wasteside.qr_code
                )
            ) AS first_scans
        ";
        $params[':datetime1'] = $datetime1;
        $params[':datetime2'] = $datetime2;
    }

    // Execute query
    $stmt = $pdo->prepare($query);
    $stmt->execute($params);
    $result = $stmt->fetch(PDO::FETCH_ASSOC);

    // Return result
    echo json_encode($result);

} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(["error" => "Database error: " . $e->getMessage()]);
}
?>
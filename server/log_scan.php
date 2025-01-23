<?php
// Database connection parameters
$host = 'localhost';
$dbname = 'd04219f7';
$username = 'd04219f7';
$password = 'RGGtV9xQxfhGEVC4HMtM';

// Set headers to handle JSON input and output
header('Content-Type: application/json');

// Connect to the database
try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(["error" => "Database connection failed: "]);
    exit;
}

// Parse JSON input
$input = file_get_contents('php://input');
$data = json_decode($input, true);

// Validate input
if (!isset($data['scan_time'], $data['qr_code'], $data['value'])) {
    http_response_code(400);
    echo json_encode(["error" => "Invalid input. Required fields: scan_time, qr_code, value"]);
    exit;
}

$scan_time = $data['scan_time'];
$qr_code = $data['qr_code'];
$value = $data['value'];

// Validate value types
if (!is_string($qr_code) || !is_numeric($value) || !strtotime($scan_time)) {
    http_response_code(400);
    echo json_encode(["error" => "Invalid data format"]);
    exit;
}

if (!empty($date)) {
    $query = "
        SELECT 
            COUNT(*) AS total_entries,
            SUM(value) AS total_value,
            AVG(value) AS average_value,
            MIN(value) AS min_value,
            MAX(value) AS max_value
        FROM Wasteside
        WHERE DATE(scan_time) = :specific_date
    ";
    $params[':specific_date'] = $date;
}

// Insert data into the database
try {
    $stmt = $pdo->prepare("INSERT INTO Wasteside (scan_time, qr_code, value) VALUES (:scan_time, :qr_code, :value)");
    $stmt->bindParam(':scan_time', $scan_time);
    $stmt->bindParam(':qr_code', $qr_code);
    $stmt->bindParam(':value', $value);
    $stmt->execute();

    http_response_code(201);
    echo json_encode(["success" => "Data inserted successfully"]);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(["error" => "Failed to insert data: " . $e->getMessage()]);
}
?>

<?php

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405); // Method Not Allowed
    header('Allow: POST');
    echo json_encode(["error" => "Method Not Allowed. Only POST is allowed."]);
    exit;
}
// Database connection parameters
$host = 'localhost';
$dbname = 'd04219f7';
$username = 'd04219f7';
$password = 'RGGtV9xQxfhGEVC4HMtM';

// Set headers to handle JSON input and output
header('Content-Type: application/json');

try {
    // Connect to the database
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(["error" => "Database connection failed"]);
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

try {
    // Check if qr_code exists in wasteside_codes
    $stmt = $pdo->prepare("SELECT code FROM wasteside_codes WHERE code = :qr_code");
    $stmt->bindParam(':qr_code', $qr_code);
    $stmt->execute();
    $codeData = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$codeData) {
        http_response_code(200);
        echo json_encode(["error" => "invalid_code"]);
        exit;
    }

    // Check if qr_code has been scanned before
    $stmt = $pdo->prepare("SELECT MIN(scan_time) AS first_scan FROM Wasteside WHERE qr_code = :qr_code");
    $stmt->bindParam(':qr_code', $qr_code);
    $stmt->execute();
    $scanData = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($scanData['first_scan']) {
        http_response_code(200);
        echo json_encode([
            "error" => "already_used",
            "first_scan_time" => $scanData['first_scan']
        ]);
        exit;
    }

    if(isset($data['check_only']) && $data['check_only'] == true) {
        http_response_code(200);
        echo json_encode(["success" => "code_valid"]);
        exit;
    }

    // Insert data into the Wasteside table
    $stmt = $pdo->prepare("INSERT INTO Wasteside (scan_time, qr_code, value) VALUES (:scan_time, :qr_code, :value)");
    $stmt->bindParam(':scan_time', $scan_time);
    $stmt->bindParam(':qr_code', $qr_code);
    $stmt->bindParam(':value', $value);
    $stmt->execute();

    http_response_code(201);
    echo json_encode(["success" => "code_logged"]);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(["error" => "Database error"]);
}
?>
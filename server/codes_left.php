<?php

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    http_response_code(405); // Method Not Allowed
    header('Allow: GET');
    echo json_encode(["error" => "Method Not Allowed. Only GET is allowed."]);
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

try {
    // Query to count unscanned codes
    $stmt = $pdo->query("
        SELECT COUNT(*) AS unscanned_count
        FROM wasteside_codes wc
        LEFT JOIN Wasteside w ON wc.code = w.qr_code
        WHERE w.qr_code IS NULL
    ");
    
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    
    // Return the count of unscanned codes
    echo json_encode([
        "unscanned_codes" => $result['unscanned_count']
    ]);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(["error" => "Database query failed" ]);
}
?>
# REST API Documentation: codes_left.php

## Overview
The `codes_left.php` endpoint provides the count of unscanned QR codes from the database. It only supports `GET` requests and returns the count in JSON format.

## Base URL
```
http://yourdomain.com/codes_left.php
```

## HTTP Method
- `GET` (Only `GET` requests are allowed. Any other HTTP method will return a `405 Method Not Allowed` response.)

## Headers
- `Content-Type: application/json`

## Request Parameters
No parameters are required for this endpoint.

## Response
### Successful Response
- **Status Code:** `200 OK`
- **Content-Type:** `application/json`
- **Response Body:**
```json
{
    "unscanned_codes": 42
}
```
Where `unscanned_codes` is the number of QR codes that have not yet been scanned.

### Error Responses
#### Method Not Allowed
- **Status Code:** `405 Method Not Allowed`
- **Response Body:**
```json
{
    "error": "Method Not Allowed. Only GET is allowed."
}
```

#### Database Connection Failure
- **Status Code:** `500 Internal Server Error`
- **Response Body:**
```json
{
    "error": "Database connection failed"
}
```

#### Database Query Failure
- **Status Code:** `500 Internal Server Error`
- **Response Body:**
```json
{
    "error": "Database query failed"
}
```


# REST API Documentation: load_stats.php

## Overview
The `load_stats.php` endpoint retrieves statistical data based on a specified date or a time range. It allows users to get aggregated information about scanned QR codes.

## Base URL
```
http://yourdomain.com/load_stats.php
```

## HTTP Method
- `GET` (Only `GET` requests are allowed. Any other HTTP method will return a `405 Method Not Allowed` response.)

## Headers
- `Content-Type: application/json`

## Request Parameters
The endpoint requires either a `date` parameter or both `datetime1` and `datetime2` parameters.

### Query Parameters
| Parameter   | Type   | Required | Description |
|------------|--------|----------|-------------|
| `date`      | `string` | Conditional | The specific date (YYYY-MM-DD) to retrieve statistics for. |
| `datetime1` | `string` | Conditional | The start datetime (YYYY-MM-DD HH:MM:SS) for a range query. |
| `datetime2` | `string` | Conditional | The end datetime (YYYY-MM-DD HH:MM:SS) for a range query. |

- Either `date` or both `datetime1` and `datetime2` must be provided.
- If none of the required parameters are provided, a `400 Bad Request` error is returned.

## Response
### Successful Response
- **Status Code:** `200 OK`
- **Content-Type:** `application/json`
- **Response Body:**
```json
{
    "total_entries": 100,
    "total_value": 5000,
    "average_value": 50,
    "min_value": 10,
    "max_value": 90
}
```
Where:
- `total_entries`: Total number of unique first scans.
- `total_value`: Sum of the values associated with the scans.
- `average_value`: Average value of scanned QR codes.
- `min_value`: Minimum recorded value.
- `max_value`: Maximum recorded value.

### Error Responses
#### Method Not Allowed
- **Status Code:** `405 Method Not Allowed`
- **Response Body:**
```json
{
    "error": "Method Not Allowed. Only GET is allowed."
}
```

#### Missing Parameters
- **Status Code:** `400 Bad Request`
- **Response Body:**
```json
{
    "error": "You must provide either 'date' or both 'datetime1' and 'datetime2' as parameters."
}
```

#### Database Error
- **Status Code:** `500 Internal Server Error`
- **Response Body:**
```json
{
    "error": "Database error"
}
```

# REST API Documentation: log_scan.php

## Overview
The `log_scan.php` endpoint allows logging a QR code scan with a timestamp and value. It validates input, checks for duplicate scans, and records the data in the database.

## Base URL
```
http://yourdomain.com/log_scan.php
```

## HTTP Method
- `POST` (Only `POST` requests are allowed. Any other HTTP method will return a `405 Method Not Allowed` response.)

## Headers
- `Content-Type: application/json`

## Request Body
The request must be a JSON object containing the following fields:

| Parameter   | Type    | Required | Description |
|------------|---------|----------|-------------|
| `scan_time` | `string` | Yes | The timestamp (YYYY-MM-DD HH:MM:SS) when the scan occurred. |
| `qr_code`   | `string` | Yes | The QR code identifier. |
| `value`     | `integer`  | Yes | The recorded value of the scanned item (in Cents). |
| `check_only` | `boolean` | No | If `true`, only validates the code without inserting it. |

### Example Request
```json
{
    "scan_time": "2024-01-23 12:30:00",
    "qr_code": "ABC123",
    "value": 450, //-> 4,50€
    "check_only": false
}
```

## Response
### Successful Responses
#### Code Successfully Logged
- **Status Code:** `201 Created`
- **Response Body:**
```json
{
    "success": "code_logged"
}
```

#### Code Valid (Check Only)
- **Status Code:** `200 OK`
- **Response Body:**
```json
{
    "success": "code_valid"
}
```

### Error Responses
#### Method Not Allowed
- **Status Code:** `405 Method Not Allowed`
- **Response Body:**
```json
{
    "error": "Method Not Allowed. Only POST is allowed."
}
```

#### Invalid Input
- **Status Code:** `400 Bad Request`
- **Response Body:**
```json
{
    "error": "Invalid input. Required fields: scan_time, qr_code, value"
}
```

#### Invalid Data Format
- **Status Code:** `400 Bad Request`
- **Response Body:**
```json
{
    "error": "Invalid data format"
}
```

#### Invalid QR Code
- **Status Code:** `200 OK`
- **Response Body:**
```json
{
    "error": "invalid_code"
}
```

#### QR Code Already Used
- **Status Code:** `200 OK`
- **Response Body:**
```json
{
    "error": "already_used",
    "first_scan_time": "2024-01-22 14:00:00"
}
```

#### Database Error
- **Status Code:** `500 Internal Server Error`
- **Response Body:**
```json
{
    "error": "Database error"
}
```
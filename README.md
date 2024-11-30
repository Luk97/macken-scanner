# Schnittstelle Statistik

Here are the descriptions for the two backend routes:

---

### **Route 1: `log_scan.php`**

#### **Description**
This endpoint is used to log a scan entry into the database. It accepts a JSON payload containing the scan time, QR code, and price (in cents). The data is stored in the database.

#### **Request Details**
- **Method**: `POST`
- **URL**: `http://statistik.hanomacke.de/log_scan.php`
- **Headers**:
  - `Content-Type: application/json`
- **Request Body** (JSON):
  ```json
  {
      "scan_time": "2024-11-30 14:29:00",
      "qr_code": "example-qr-code",
      "value": 70
  }
  ```

#### **Response Examples**
- **Success**:
  ```json
  {
      "success": true,
      "message": "Scan logged successfully."
  }
  ```
- **Error**:
  ```json
  {
      "success": false,
      "message": "Invalid input data."
  }
  ```

---

### **Route 2: `load_stats.php`**

#### **Description**
This endpoint retrieves statistical data based on a single date or a date range. It provides the total number of entries and the total value (in cents) for the specified period.

#### **Request Details**
- **Method**: `GET`
- **URL**: `http://statistik.hanomacke.de/load_stats.php`
- **Query Parameters**:
  1. **For Single Date**:
     - `date` (required): A specific date in the format `YYYY-MM-DD`.
     - Example: `http://statistik.hanomacke.de/load_stats.php?date=2024-11-30`

  2. **For Date Range**:
     - `datetime1` (required): The start date/time in the format `YYYY-MM-DD HH:MM:SS`.
     - `datetime2` (required): The end date/time in the format `YYYY-MM-DD HH:MM:SS`.
     - Example: `http://statistik.hanomacke.de/load_stats.php?datetime1=2024-11-01 00:00:00&datetime2=2024-11-30 23:59:59`

#### **Response Examples**
- **Success**:
  ```json
  {
      "total_entries": 15,
      "total_value": 1050
  }
  ```
  - **`total_entries`**: The count of entries for the specified period.
  - **`total_value`**: The total value in cents.

- **Error**:
  ```json
  {
      "error": "Invalid input: Either 'date' or both 'datetime1' and 'datetime2' must be provided."
  }
  ```

---

### Summary of Backend Routes

| Route Name      | Method | Purpose                                     | Example Usage                                                                                  |
|------------------|--------|---------------------------------------------|------------------------------------------------------------------------------------------------|
| `log_scan.php`  | POST   | Logs a scan with time, QR code, and price   | POST a JSON object containing `scan_time`, `qr_code`, and `value`.                             |
| `load_stats.php` | GET    | Fetches stats for a single date or range   | Provide `date` for a single day or `datetime1` and `datetime2` for a range as query parameters. |

These descriptions can help document the backend API for easier integration or future reference.

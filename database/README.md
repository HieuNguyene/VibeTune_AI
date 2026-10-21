# Cơ Sở Dữ Liệu VibeTune AI (PostgreSQL)

Thư mục này chứa toàn bộ tài nguyên định nghĩa cấu trúc cơ sở dữ liệu và dữ liệu mẫu phục vụ kiểm thử cho dự án **VibeTune AI**.

## 1. Danh sách tệp tin
* `V1__init_schema.sql`: Script khởi tạo 9 bảng dữ liệu quan hệ, ràng buộc toàn vẹn, chỉ mục Index phục vụ tìm kiếm và thuật toán gợi ý âm nhạc.
* `V2__seed_data.sql`: Dữ liệu mẫu khởi tạo gồm 7 thể loại, 6 ca sĩ nổi tiếng, 12 bài hát hoàn chỉnh (nguồn âm thanh hợp lệ), tài khoản người dùng mẫu, danh sách yêu thích, playlist và lịch sử nghe nhạc.
* `init_database.sql`: Tệp gộp toàn bộ (All-in-one) để chạy trực tiếp trên **pgAdmin**, **DBeaver** hoặc dòng lệnh **psql**.
* `docker-compose.yml` (ở thư mục gốc dự án): Khởi chạy PostgreSQL 16 tự động nạp toàn bộ cấu trúc và dữ liệu chỉ với một dòng lệnh.

## 2. Thông số kết nối mặc định
* **Host**: `localhost` (hoặc `10.0.2.2` từ Android Emulator)
* **Port**: `5432`
* **Database Name**: `vibetune_db`
* **Username**: `vibetune_admin`
* **Password**: `vibetune_secret_password`

## 3. Cách khởi chạy cơ sở dữ liệu

### Cách 1: Khởi chạy bằng Docker Desktop (Khuyên dùng - 1 Click)
1. Mở ứng dụng **Docker Desktop** trên máy tính.
2. Mở terminal tại thư mục gốc dự án và chạy:
   ```bash
   docker compose up -d
   ```
3. Docker sẽ tự động tải PostgreSQL 16, tạo cơ sở dữ liệu `vibetune_db` và thực thi toàn bộ script nạp dữ liệu mẫu.

### Cách 2: Khởi chạy trên pgAdmin hoặc DBeaver
1. Mở **pgAdmin** hoặc **DBeaver** đã kết nối tới máy chủ PostgreSQL cục bộ của bạn.
2. Tạo mới một cơ sở dữ liệu tên: `vibetune_db`.
3. Mở công cụ **Query Tool** (hoặc SQL Editor).
4. Mở tệp `database/init_database.sql`, dán toàn bộ nội dung và bấm nút **Execute (F5)**.

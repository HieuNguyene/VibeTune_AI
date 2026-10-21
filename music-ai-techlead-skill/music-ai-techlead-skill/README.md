# Music AI Tech Lead Skill

Skill hướng dẫn xây dựng ứng dụng nghe nhạc cá nhân tích hợp gợi ý bài hát và playlist cá nhân hóa.

## Quyết định cố định
- Android Native: Java + XML + AndroidX Media3
- Backend: Java + Spring Boot modular monolith
- Database: PostgreSQL
- Recommendation MVP: thuật toán Java, không cần API AI trả phí
- Mục tiêu hạ tầng: 0 đồng trong giai đoạn phát triển
- Audio: chỉ dùng nguồn được phép sử dụng

## Cách dùng
1. Đặt thư mục này trong thư mục skills mà AI agent của bạn hỗ trợ, hoặc mở `SKILL.md` và dùng nội dung làm chỉ dẫn dự án.
2. Đọc `SKILL.md` trước; đọc các file trong `knowledge/`, `workflows/`, `templates/` khi tác vụ liên quan.
3. Khi triển khai code, yêu cầu AI thực hiện từng bước nhỏ, nêu file cần sửa, lệnh chạy, test và tiêu chí nghiệm thu.
4. Không yêu cầu AI sinh toàn bộ dự án trong một lần; duy trì vòng lặp implement → run → test → review.

## Cấu trúc
- `SKILL.md`: vai trò, phạm vi, quy tắc kỹ thuật và cách làm việc.
- `knowledge/architecture.md`: stack và kiến trúc.
- `knowledge/database-and-api.md`: schema và API contract.
- `knowledge/recommendation-engine.md`: thuật toán gợi ý.
- `workflows/roadmap-and-sprints.md`: roadmap 14 tuần.
- `workflows/implementation-loop.md`: quy trình hướng dẫn và review.
- `templates/acceptance-checklist.md`: checklist nghiệm thu.

Lưu ý: kiểm tra phiên bản thư viện, tình trạng hỗ trợ, quota dịch vụ miễn phí và điều khoản cấp phép audio trước khi phát hành. Tài liệu này là kế hoạch kỹ thuật; các phiên bản và điều khoản có thể thay đổi theo thời gian.

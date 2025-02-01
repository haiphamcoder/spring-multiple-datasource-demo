# Spring Multiple Datasource Demo

Một dự án mẫu sử dụng **Spring Boot** (phiên bản 3.4.2) với đa datasource, được xây dựng theo các nguyên tắc của **Clean Architecture**. Dự án tích hợp các module chính như Spring Data JPA, Spring Security, Validation, và Web, cùng với các tiện ích hỗ trợ như Lombok và DevTools.  
Dự án sử dụng MySQL làm cơ sở dữ liệu và được cấu hình với các dependency tiêu chuẩn.

---

## Table of Contents

- [Giới thiệu](#giới-thiệu)
- [Kiến trúc (Clean Architecture)](#kiến-trúc-clean-architecture)
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Các tính năng chính](#các-tính-năng-chính)
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Cách cấu hình](#cách-cấu-hình)
- [Cách chạy ứng dụng](#cách-chạy-ứng-dụng)
- [Build & Test](#build--test)
- [Ghi chú](#ghi-chú)
- [License](#license)

---

## Giới thiệu

Dự án **Spring Multiple Datasource Demo** được xây dựng nhằm mục đích:
- **Quản lý đa datasource:** Cung cấp kết nối cho các cơ sở dữ liệu primary và secondary.
- **Clean Architecture:** Tách biệt rõ ràng các tầng Domain, Application, Interface và Infrastructure, đảm bảo rằng các module nghiệp vụ không phụ thuộc vào các chi tiết triển khai bên ngoài.
- **Tích hợp REST API:** Xây dựng các endpoint với cấu trúc phản hồi chuẩn, bao gồm các trường như `code`, `message`, `data`, `meta` và `errors`.
- **Bảo mật & Validation:** Sử dụng Spring Security và Validation để đảm bảo an toàn và kiểm tra dữ liệu, dù bảo mật có thể được cấu hình tùy chọn theo môi trường phát triển/production.
- **Hỗ trợ phát triển nhanh:** Sử dụng Lombok để giảm thiểu boilerplate code và Spring Boot DevTools cho việc phát triển nhanh.

---

## Kiến trúc (Clean Architecture)

Dự án được xây dựng theo **Clean Architecture** với các tầng sau:

- **Domain Layer:**  
  - **Mục đích:** Chứa các entity và logic nghiệp vụ cốt lõi (không phụ thuộc vào framework).  
  - **Vị trí:** `com.haiphamcoder.demo.domain.entity`  
  - **Ví dụ:** Các entity cho datasource primary (vd: `User.java`) và secondary.

- **Application Layer:**  
  - **Mục đích:** Xử lý các use case, logic nghiệp vụ, và định nghĩa các interface repository.  
  - **Vị trí:** `com.haiphamcoder.demo.application.service` và `com.haiphamcoder.demo.application.repository`

- **Interface (Presentation) Layer:**  
  - **Mục đích:** Xây dựng các REST API, xử lý request/response và chuyển đổi dữ liệu thông qua DTO.  
  - **Vị trí:** `com.haiphamcoder.demo.infrastructure.controller` và các mapper/DTO trong `com.haiphamcoder.demo.infrastructure.mapper`

- **Infrastructure Layer:**  
  - **Mục đích:** Bao gồm tất cả các thành phần tương tác với thế giới bên ngoài như:  
    - **Config:** Cấu hình datasource, JPA, v.v.  
    - **Persistence:** Triển khai repository cụ thể cho các datasource.  
    - **Security:** Các cấu hình bảo mật (nếu cần).  
    - **Controller:** Định nghĩa giao diện REST API.  
  - **Vị trí:** `com.haiphamcoder.demo.infrastructure` (bao gồm các gói `config`, `persistence`, `controller`, `security`, …)  
  - **Chú ý:** Infrastructure Layer đại diện cho tất cả các thành phần của hệ thống tương tác với thế giới bên ngoài.

Các tầng này tuân theo nguyên tắc Dependency Inversion: Domain và Application không phụ thuộc vào Infrastructure hay Presentation.

---

## Cấu trúc dự án

```plaintext
.
├── bin
│   └── run.sh                     # Script để chạy ứng dụng
├── config
│   └── application.yml            # Cấu hình chính của ứng dụng
├── pom.xml                        # File cấu hình Maven (xem dependency bên dưới)
├── README.md                      # Tài liệu hướng dẫn
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── haiphamcoder
    │   │           └── demo
    │   │               ├── application
    │   │               │   ├── repository
    │   │               │   │   └── UserRepository.java
    │   │               │   └── service
    │   │               │       └── UserService.java
    │   │               ├── Application.java         # Lớp main của Spring Boot
    │   │               ├── domain
    │   │               │   └── entity
    │   │               │       ├── primary
    │   │               │       │   └── User.java      # Entity cho datasource primary
    │   │               │       └── secondary         # Entity cho datasource secondary
    │   │               ├── infrastructure
    │   │               │   ├── config
    │   │               │   │   └── PrimaryDataSourceConfiguration.java
    │   │               │   ├── controller
    │   │               │   │   └── UserController.java
    │   │               │   ├── mapper
    │   │               │   │   ├── dto
    │   │               │   │   │   └── UserDto.java     # DTO cho User
    │   │               │   │   └── UserMapper.java      # Mapper chuyển đổi giữa User và UserDto
    │   │               │   ├── persistence
    │   │               │   │   ├── primary
    │   │               │   │   │   └── UserJpaRepository.java
    │   │               │   │   └── secondary
    │   │               │   └── security                    # Cấu hình bảo mật (nếu cần)
    │   │               └── shared
    │   │                   ├── ApiResponse.java         # Wrapper cho response API
    │   │                   ├── BaseEntity.java            # Entity cơ sở với các field audit
    │   │                   └── DataSourceUtils.java       # Tiện ích liên quan đến datasource
    │   └── resources
    │       ├── application.properties  # Cấu hình bổ sung (nếu cần)
    │       ├── static                  # Các file tĩnh (JS, CSS,…)
    │       └── templates               # Các file template (Thymeleaf, JSP,…)
    └── test
        └── java
            └── com
                └── haiphamcoder
                    └── demo
                        └── ApplicationTests.java
```

---

## Các tính năng chính

- **Đa datasource:**  
  Hỗ trợ kết nối đến nhiều cơ sở dữ liệu (primary và secondary).

- **Spring Data JPA & Repository:**  
  Quản lý truy xuất dữ liệu với các repository được triển khai riêng cho từng datasource.

- **REST API với cấu trúc phản hồi chuẩn:**  
  Mỗi API trả về một response có cấu trúc gồm `code`, `message`, `data`, `meta` và `errors` (nếu có).

- **DTO & Mapper:**  
  Sử dụng MapStruct (nếu cần) để chuyển đổi giữa entity và DTO, đảm bảo tính nhất quán dữ liệu.

- **Bảo mật:**  
  Dù bảo mật chưa được cấu hình chi tiết trong môi trường phát triển, dự án đã tích hợp Spring Security sẵn sàng cho việc mở rộng bảo mật sau này.

- **Validation:**  
  Sử dụng Spring Boot Starter Validation để đảm bảo dữ liệu được kiểm tra và xử lý lỗi hợp lý.

- **Lombok:**  
  Giảm thiểu boilerplate code bằng cách tự động sinh getter, setter và các phương thức hữu ích khác.

- **DevTools:**  
  Hỗ trợ phát triển nhanh với Spring Boot DevTools.

---

## Yêu cầu hệ thống

- **Java:** JDK 21
- **Maven:** Phiên bản Maven mới nhất
- **MySQL:** Được sử dụng làm cơ sở dữ liệu (đảm bảo cấu hình đúng trong `config/application.yml`)

---

## Cách cấu hình

- **File cấu hình chính:**  
  - `config/application.yml` chứa các thiết lập cho datasource, JPA, logging và các cấu hình khác.
  - Các cấu hình bổ sung có thể được định nghĩa trong `src/main/resources/application.properties`.

- **Bảo mật:**  
  Trong môi trường phát triển, nếu không cần bảo mật, bạn có thể loại trừ cấu hình bảo mật mặc định bằng cách chỉnh sửa lớp main (`Application.java`) hoặc trong file cấu hình.

---

## Cách chạy ứng dụng

### Chạy qua Maven

```bash
mvn clean install -DskipTests
mvn spring-boot:run
```

### Chạy qua IntelliJ IDEA

1. **Bật Annotation Processing:**  
   Vào **File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors** và bật **Enable annotation processing**.
2. Chạy lớp **Application.java** (lớp main của Spring Boot).

### Sử dụng Script Shell

```bash
./bin/run.sh
```

*(Đảm bảo file `run.sh` có quyền thực thi và được cấu hình đúng.)*

---

## Build & Test

- **Build:**  
  Sử dụng lệnh `mvn clean install` để build dự án.

- **Test:**  
  Chạy các unit test có trong `src/test/java` bằng lệnh:
  
  ```bash
  mvn test
  ```

---

## Ghi chú

- **Dependency:**  
  Dự án chỉ sử dụng các dependency sau trong file **pom.xml**:
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Security
  - Spring Boot Starter Validation
  - Spring Boot Starter Web
  - Spring Boot DevTools
  - MySQL Connector/J
  - Lombok
  - Spring Boot Starter Test
  - Spring Security Test

- **Lombok:**  
  Đảm bảo bạn đã cài đặt plugin Lombok trong IDE và bật Annotation Processing.

- **Clean Architecture:**  
  Dự án được xây dựng theo Clean Architecture, với sự tách biệt rõ ràng giữa các tầng (Domain, Application, Interface, Infrastructure).  
  **Infrastructure Layer** bao gồm tất cả các thành phần tương tác với thế giới bên ngoài như Controller, Config, Persistence, và Security.

---

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.

---

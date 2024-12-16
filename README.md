# P2P Lending Platform

## Deskripsi
Platform P2P Lending adalah sebuah layanan yang dikembangkan menggunakan Spring Boot, dirancang untuk memfasilitasi pinjaman *peer-to-peer* dengan memungkinkan pengguna untuk berperan sebagai peminjam maupun pemberi pinjaman. Pengguna dapat membuat permintaan pinjaman dengan jumlah, jangka waktu, dan tingkat bunga tertentu. Peminjam dapat mengajukan permintaan pendanaan, sementara pemberi pinjaman dapat menjelajahi, memfilter, dan memilih pinjaman untuk diinvestasikan. Setiap perjanjian pinjaman diproses sebagai transaksi, sistem secara otomatis menghitung jadwal pembayaran, serta memberi notifikasi kepada peminjam tentang pembayaran yang akan datang. Platform ini menyediakan laporan kepada pemberi pinjaman dan peminjam tentang status pinjaman dan penghasilan mereka, menjadikan proses pinjaman transparan dan aman.

## Teknologi
- **Spring Boot**: Framework untuk membuat dan mengelola RESTful API.
- **Spring Security & JWT**: Untuk autentikasi dan otorisasi pengguna secara aman, mendukung kontrol akses berbasis peran.
- **Hibernate/JPA**: Untuk pemetaan objek-relasional dan interaksi dengan database relasional.
- **Lombok**: Library untuk mengurangi kode boilerplate.
- **MongoDB**: Digunakan untuk pencatatan log asinkron, menyimpan log aplikasi secara rinci dalam database NoSQL.

## Fitur
1. **Autentikasi dan Otorisasi Pengguna**: Fungsi registrasi dan login yang aman dengan kontrol akses berbasis peran untuk peminjam dan pemberi pinjaman.
2. **Permintaan Pinjaman**: Peminjam dapat membuat permintaan pinjaman dengan jumlah, tingkat bunga, dan jangka waktu tertentu.
3. **Penawaran Pemberi Pinjaman**: Pemberi pinjaman dapat membuat penawaran untuk mendanai pinjaman dengan menentukan jumlah, tingkat bunga, dan jangka waktu.
4. **Penerimaan Pinjaman**: Peminjam dapat menerima penawaran pemberi pinjaman untuk memulai proses perjanjian pinjaman.
5. **Manajemen Transaksi**: Penanganan otomatis transaksi saat pinjaman diterbitkan atau pembayaran dilakukan.
6. **Penjadwalan Pembayaran**: Perhitungan dan pembuatan jadwal pembayaran untuk pinjaman secara otomatis.
7. **Manajemen Saldo**: Pengguna dapat menambah saldo dan melihat saldo saat ini.
8. **Pencatatan Log Asinkron**: Pencatatan log peristiwa aplikasi dan kesalahan secara rinci yang disimpan di MongoDB untuk audit dan debugging.
9. **Laporan**: Penyediaan laporan kepada pemberi pinjaman dan peminjam tentang status pinjaman dan penghasilan.

## Dokumentasi Endpoint API

### 1. **Authentication API**
| Method | HTTP Request              | Deskripsi                              |
|--------|---------------------------|----------------------------------------|
| POST   | `/api/auth/register`      | Mendaftarkan pengguna baru             |
| POST   | `/api/auth/login`         | Mengautentikasi pengguna dan mengembalikan JWT |

### 2. **User Management API**
| Method | HTTP Request              | Deskripsi                              |
|--------|---------------------------|----------------------------------------|
| POST   | `/api/user/balance/top-up`| Menambah saldo pengguna                |
| GET    | `/api/user/balance`       | Mengambil saldo pengguna               |

### 3. **Lender Offers API**
| Method | HTTP Request              | Deskripsi                              |
|--------|---------------------------|----------------------------------------|
| POST   | `/api/lender/offers`      | Membuat penawaran pemberi pinjaman (khusus pemberi pinjaman) |
| GET    | `/api/lender/offers/active` | Mengambil semua penawaran aktif pemberi pinjaman |

### 4. **Loan Management API**
| Method | HTTP Request                  | Deskripsi                              |
|--------|-------------------------------|----------------------------------------|
| POST   | `/api/loans/accept-offer/{offerId}` | Peminjam menerima penawaran pemberi pinjaman untuk membuat pinjaman |
| GET    | `/api/loans/{loanId}`         | Mengambil detail pinjaman tertentu     |
| GET    | `/api/loans/borrower`         | Mengambil semua pinjaman untuk peminjam yang sedang login |
| GET    | `/api/loans/lender`           | Mengambil semua pinjaman untuk pemberi pinjaman yang sedang login |

### 5. **Payment API**
| Method | HTTP Request                  | Deskripsi                              |
|--------|-------------------------------|----------------------------------------|
| POST   | `/api/payments/pay/{paymentId}` | Memproses pembayaran untuk cicilan yang dijadwalkan |
| GET    | `/api/payments/loan/{loanId}` | Mengambil jadwal pembayaran untuk pinjaman tertentu |

### 6. **Transaction API**
| Method | HTTP Request              | Deskripsi                              |
|--------|---------------------------|----------------------------------------|
| GET    | `/api/transactions`       | Mengambil semua transaksi pengguna     |

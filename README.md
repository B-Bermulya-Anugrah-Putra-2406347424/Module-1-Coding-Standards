# Reflection 1

### Coding Standards & Clean Code Principles
Dalam mengerjakan fitur *Edit* dan *Delete* Product, saya berusaha untuk menerapkan beberapa prinsip *Clean Code* agar kode dapat mudah dibaca dan dikembangkan nantinya, diantaranya:

* **Meaningful Names**: Saya menggunakan penamaan yang deskriptif. Contohnya, menggunakan `productData` untuk menyimpan list product.
* **Single Responsibility Principle (SRP)**: Saya membagi responsibility code secara modular. Contohnya, saya membuat `ProductController` yang menangani navigasi, `ProductService` yang mengelola logika bisnis, dan `ProductRepository` yang berfokus pada manage data product.
* **Fail-Fast Principle**: Pada bagian `ProductRepository`, saya menerapkan fungsi `findById` menggunakan `.orElseThrow()`. Jika ID yang dicari tidak ada, sistem akan langsung melempar `IllegalArgumentException` agar error dapat terdeteksi dengan baik.
* **DRY (Don't Repeat Yourself)**: Saya memastikan tidak ada logic yang berulang dengan memanfaatkan method yang sudah ada untuk mencari dan memvalidasi data product.

### Secure Coding Practices
Beberapa praktik *Secure Coding* yang saya implementasikan antara lain:

* **Non-Predictable Object Identifiers**: Untuk `productId`, saya menggunakan `UUID.randomUUID().toString()` di dalam *constructor* model. Hal ini nantinya dapat mencegah potensi serangan di mana pihak luar mencoba mengakses atau memanipulasi data hanya dengan mengganti urutan angka di URL.
* **Method Restriction for Destructive Actions**: Saya memastikan operasi yang bersifat menghapus data tidak bisa dilakukan melalui metode `GET`. Proses penghapusan data dilakukan melalui metode `POST` setelah melalui halaman konfirmasi, supaya menghindari penghapusan data yang tidak disengaja melalui URL.

### Self-Correction & Future Improvements
* **User-Friendly Error Handling**: Saat ini, jika terjadi kesalahan seperti ID tidak ditemukan, sistem melempar `IllegalArgumentException` yang memunculkan halaman error bawaan Java. Kedepannya, saya perlu mengalihkan user ke halaman error yang lebih *User-Friendly*.
* **Input Validation**: Kedepannya, Saya perlu menambahkan validasi input pada model (seperti jumlah product tidak boleh negatif).
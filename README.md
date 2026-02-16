# Reflection 1 [module-1]

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
* **Input Validation**: Kedepannya, Saya perlu menambahkan validasi input pada model (seperti jumlah product tidak boleh negatif). [update : saya sudah menambahkan validasi input di bagian model. Namun, untuk bagian UI, handling error yang akan ditampilkan ke user belum diimplementasikan.]

# Reflection 2 [module-1]

Membuat unit test membuat saya merasa aman karena setiap komponen kode saya dapat divalidasi secara independen dan cepat. Terkait kuantitas, sebuah class idealnya memiliki test yang seharusnya mencakup seluruh kemungkinan jalur logic bisnis, termasuk *edge cases*. Untuk memastikan hal tersebut, saya kedepannya dapat menggunakan *code coverage* agar dapat mengidentifikasi bagian *source code* mana yang belum dieksekusi. Namun, *coverage* 100% bukan jaminan kode bebas dari bug. *Coverage* hanya mengukur aspek kuantitas eksekusi baris kode, namun tidak menjamin kebenaran logic bisnis untuk segala variasi input di lingkungan *real-time*.

Mengenai pembuatan *functional test suite* baru untuk memverifikasi jumlah item, melakukan *copy-paste* prosedur *setup* dari test sebelumnya merupakan praktik yang buruk bagi kualitas kode. Masalah *clean code* utama yang muncul adalah pengulangan kode atau pelanggaran prinsip **DRY (Don't Repeat Yourself)**. Hal ini menyebabkan kode menjadi *rigid* dan sulit di-develop kedepannya karena setiap perubahan infrastruktur, seperti konfigurasi URL atau logic sinkronisasi halaman, harus diubah secara manual di setiap class test.

Untuk memperbaiki masalah tersebut, saya menerapkan prinsip **Inheritance** dengan mengekstraksi logika umum ke dalam sebuah `BaseProductFunctionalTest`. Dengan memindahkan *instance variable* serta *helper methods* ke dalam class induk, class test spesifik hanya perlu melakukan *extends*. Pendekatan ini juga memastikan setiap test suite tetap fokus pada tanggung jawab fungsionalnya saja (*Single Responsibility Principle*), meningkatkan keterbacaan kode, dan memudahkan skalabilitas testing di masa mendatang tanpa menimbulkan *redundance*.

# Reflection [module-2]

## Project Links
- **Deployed Application (Koyeb):** [link](https://handicapped-crissy-mug-playground-bae751cb.koyeb.app/)
- **SonarCloud Project:** [Code Quality Analysis](https://sonarcloud.io/project/overview?id=B-Bermulya-Anugrah-Putra-2406347424_Module-1-Coding-Standards)

### Code Quality Issue(s) & Fixing Strategy
Dalam proses mengintegrasikan *Quality code analysis tools* (SonarCloud) ke dalam *pipeline*, terdapat beberapa isu kualitas kode (*Code Smell*) yang berhasil dideteksi dan saya perbaiki:

* **Empty Methods (Maintainability)**: SonarCloud mendeteksi adanya *method* kosong (seperti `setUp()` dan `contextLoads()`) pada `ProductRepositoryTest` dan `EshopApplicationTests`. Strategi perbaikan yang saya lakukan adalah menghapus *method* tersebut karena memang tidak ada logika *setup* yang dibutuhkan.
* **Duplicated Literals (Maintainability)**: Pada `ProductController`, *string* literal seperti `"product"` dan `"redirect:list"` digunakan secara berulang. Berdasarkan prinsip **DRY (Don't Repeat Yourself)**, saya mengubah *string* tersebut menjadi sebuah *constant variables* (`private static final String`). Strategi ini mempermudah proses *maintenance*, karena jika ada perubahan nama *redirect* atau model, saya hanya perlu mengubahnya di satu tempat.

### CI/CD Implementation Evaluation
Menurut saya, implementasi *workflows* (GitHub Actions) dan *pipelines* yang telah saya buat saat ini sudah memenuhi definisi dari *Continuous Integration* (CI), namun **belum sepenuhnya memenuhi** definisi *Continuous Deployment* (CD).

Untuk **Continuous Integration**, saya telah mengonfigurasi *workflow* yang berjalan otomatis setiap kali ada proses `push` atau `pull request`. *Pipeline* ini secara otomatis melakukan proses *build*, menjalankan seluruh *unit test* dan menghasilkan laporan *code coverage* menggunakan Jacoco (yang saat ini sudah mencapai 100%), serta memindai kode dari potensi *bug* atau *code smell* menggunakan SonarCloud. Hal ini memastikan bahwa setiap iterasi kode baru telah divalidasi kebenarannya.

Namun, untuk proses perilisan ke tahap *production* (PaaS Koyeb), implementasi saya saat ini belum memenuhi prinsip **Continuous Deployment** dengan sepenuhnya. Hal ini dikarenakan meskipun *codebase* sudah terintegrasi dan siap untuk dirilis (*deployable*), proses *deployment*-nya belum sepenuhnya otomatis. Saya masih perlu melakukan intervensi manual (menekan tombol *redeploy* di *dashboard* Koyeb) untuk memperbarui aplikasi yang *live*. Ke depannya, hal ini dapat ditingkatkan dengan memastikan konfigurasi *webhook/auto-deploy* dari GitHub ke Koyeb berjalan secara otomatis tanpa campur tangan developer.
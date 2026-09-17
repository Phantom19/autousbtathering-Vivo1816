# Build APK langsung di GitHub

## 1. Buat repository
Di GitHub buat repository baru, misalnya:

`AutoUsbTether-Vivo1816`

Public atau Private boleh.

## 2. Upload isi folder ini
Upload semua isi folder proyek ke repository.
Pastikan `.github/workflows/build-apk.yml` ikut ter-upload.

## 3. Jalankan build
Buka:

Actions → Build APK → Run workflow

Atau cukup push ke branch `main`/`master`; workflow otomatis berjalan.

## 4. Ambil APK
Setelah job selesai:

Actions → Build APK → pilih run terbaru → Artifacts

Download:

`AutoUsbTether-Vivo1816-debug`

Extract ZIP dan ambil:

`app-debug.apk`

## Catatan
Workflow membuat APK debug. APK ini cocok untuk pengujian di Vivo 1816.
Fungsi non-root tidak dijamin dapat melewati pembatasan tethering Funtouch OS.
Mode root hanya bekerja jika perangkat benar-benar memiliki `su` dan pengguna memberi izin root.

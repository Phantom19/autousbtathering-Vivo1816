# Auto USB Tether — Vivo 1816

Target:
- Vivo 1816
- Android 8.1.0
- Funtouch OS 4.5
- OpenWrt recipient with RNDIS/usb0

## Important limitation

Android 8.1 exposes USB tethering internally through ConnectivityManager/Tethering,
but the permission `android.permission.TETHER_PRIVILEGED` is `signature|privileged`.
A normal third-party APK therefore cannot guarantee silent USB tethering on stock firmware.

This project deliberately implements two paths:

1. Non-root:
   - tries the hidden `ConnectivityManager.setUsbTethering(true)` API;
   - if Vivo blocks it, the app reports the failure instead of pretending it succeeded.

2. Root:
   - runs `su -c "svc usb setFunctions rndis"`;
   - also tries two fallback USB config commands;
   - on success the phone should expose RNDIS and OpenWrt should create `usb0`.

## Build

Open this folder in Android Studio and let Gradle sync.

Then:
Build > Build APK(s)

Expected APK:
app/build/outputs/apk/debug/app-debug.apk

## Test on Vivo

1. Install APK.
2. Connect Vivo to ZTE B860H.
3. Open app.
4. Tap AKTIFKAN USB TETHERING.
5. If rooted, grant root when Magisk/Superuser asks.
6. On OpenWrt:
   ip link show usb0
   ip addr show usb0
   ping -c 3 1.1.1.1

## Automatic boot

The receiver listens for BOOT_COMPLETED and USB_STATE. After boot it waits 12 seconds,
then tries the non-root API and root RNDIS command.

On Funtouch OS, battery/background restrictions may need to be disabled for this app.

## Why RNDIS?

Your OpenWrt logs already showed:

rndis_host ... usb0: register 'rndis_host' ... RNDIS device

So the OpenWrt side is already capable of receiving the Vivo USB tether as usb0.

## Next step if stock non-root fails

If the non-root attempt is rejected, the next useful step is to root the Vivo 1816
or use a privileged/system-app installation. Do not modify boot/system partitions
until the exact Vivo firmware/build number is confirmed.


## GitHub Actions

This repository includes `.github/workflows/build-apk.yml`.
It builds the debug APK automatically on push or from
Actions → Build APK → Run workflow.

The workflow can generate the Gradle wrapper when it is not present.

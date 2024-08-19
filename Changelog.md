# Changelog

## [4.0.2] - 2024-08-15

### Changed

- Changed type of `activity` argument in `TrustlyWebView` class from `AppCompatActivity` to `Activity`.

## [4.0.1] - 2024-02-26

### Changed

- **Android Gradle Plugin**: Updated to version 7.4.2.
- **Gradle**: Updated to version 7.6.4.
- **Compile SDK API version**: Updated to 33.
- **Java compile version**: Updated to v17.
- **Appcompat**: Updated to version 1.6.1.
- **Android browser** updated to version 1.5.0

## [4.0.0] - 2023-03-21

### Removed

- Delegate events.
- Trustly `.onRedirect` event is no longer publicly exposed as tampering with the redirect URL could cause isses with bank redirects

### Added

- Possibility for custom handlers for `.onSuccess`, `.onError` and `.onAbort` by providing lambdas. It's now possible to pick and choose for which events you want to provide custom handlers.

### Changed

- MinSdk version is now 21

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.3.6] - 2020-08-21

### Added

- Use callable module on Android to receive onUnifiedNativeAdLoaded event so ad renders in its correct view using a unique ID.

### Fixed

- Use a partial implementation for ImageProps. @Nox04
- Fix rendering of multiple ads in a FlatList. #51
- Try to reload ad when it fails to load after the specified refreshInterval
- Fix CallToActionView assigned on AdvertiserView on Android. #57
- Refactor and cleanup the code.

### Changed
- Rewrite the example to demonstrate Banner Ad, Video/Image Ad & Ads in a List properly.
- Rewrite the Root NativeAdView component as a Class Component
- Wrap CallToActionView in GestureHandlerRootView @mrousavy
- Hide the Ad when it is loading by default so it does not show empty components.
- Fix NPM dependencies by using peerDependencies, now you have to manually add all the dependancies of the library @mrousavy Fixed #55
- You need to add react-native-star-rating as a direct dependency of the main project from now onwards.

require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))
version = package['version']

Pod::Spec.new do |s|
  s.name                   = 'react-native-admob-native-ads'
  s.version                = version
  s.summary                = 'A simple and robust library for creating & displaying Admob Native Ads in your React Native App using Native Views!'
  s.homepage               = 'https://github.com/ammarahm-ed/react-native-admob-native-ads'
  s.license                = package['license']
  s.author                 = 'Ammar Ahmed <ammarahmed6506@gmail.com>'
  s.platforms              = { :ios => '9.0', :tvos => '9.2' }
  s.source                 = { :git => 'https://github.com/ammarahm-ed/react-native-admob-native-ads.git', :tag => "v#{version}" }
  s.source_files           = 'ios/*.{h,m}'
  
  # We can't add the Google-Mobile-Ads-SDK as a dependency, as it would prevent
  # this library to be used with `use_frameworks!`.
  # So instead we add the default location of the framework to the framework
  # search paths, and we rely on consumers of this library to add
  # Google-Mobile-Ads-SDK as a direct dependency.
  s.weak_frameworks        = 'GoogleMobileAds'
  s.pod_target_xcconfig    = {
    'FRAMEWORK_SEARCH_PATHS' => '"$(PODS_ROOT)/Google-Mobile-Ads-SDK/Frameworks/**"',
  }

  s.dependency 'React-Core'
  s.dependency 'GoogleMobileAdsMediationFacebook'
end

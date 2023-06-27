import React, {useEffect, useState} from 'react';
import {
  Image,
  Platform,
  SafeAreaView,
  StatusBar,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import {AdManager} from 'react-native-admob-native-ads';
import {requestTrackingPermission} from 'react-native-tracking-transparency';
import {AdView} from './src/AdView';
import List from './src/List';
import {routes} from './src/utils';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';
const App = () => {
  const [currentRoute, setCurrentRoute] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const init = async () => {
      const trackingStatus = await requestTrackingPermission();

      let trackingAuthorized = false;
      if (trackingStatus === 'authorized' || trackingStatus === 'unavailable') {
        trackingAuthorized = true;
      }

      await AdManager.setRequestConfiguration({
        trackingAuthorized,
      });

      setLoading(false);
    };

    init();
  }, []);

  return (
    <SafeAreaView
      style={{
        height: '100%',
        width: '100%',
        paddingTop: Platform.OS === 'android' ? StatusBar.currentHeight : 0,
        backgroundColor: 'white',
      }}>
      <StatusBar
        translucent
        backgroundColor="transparent"
        barStyle="dark-content"
      />
      <View
        style={{
          flexDirection: 'row',
          alignItems: 'center',
          height: 50,
          paddingHorizontal: 6,
          marginBottom: 10,
          width: '100%',
        }}>
        {currentRoute && (
          <TouchableOpacity
            onPress={() => setCurrentRoute(null)}
            activeOpacity={0.8}
            style={{
              width: 50,
              alignItems: 'center',
              height: 50,
              justifyContent: 'center',
              borderRadius: 100,
            }}>
            <Icon name="arrow-left" color="black" size={28} />
          </TouchableOpacity>
        )}
      </View>

      {!loading && !currentRoute && (
        <View
          style={{
            alignItems: 'center',
          }}>
          <View
            style={{
              alignItems: 'center',
              marginBottom: 50,
            }}>
            <Image
              source={require('./images.jpg')}
              style={{
                width: 120,
                height: 120,
                marginBottom: 30,
                borderRadius: 100,
                backgroundColor: '#f0f0f0',
              }}
            />

            <Text
              style={{
                fontSize: 18,
                letterSpacing: 1,
                textAlign: 'center',
              }}>
              Admob Native Advanced Ads {'\n'} for React Native
            </Text>
          </View>

          <TouchableOpacity
            onPress={() => setCurrentRoute(routes[0])}
            activeOpacity={0.8}
            style={{
              backgroundColor: 'orange',
              width: '90%',
              alignItems: 'center',
              height: 50,
              justifyContent: 'center',
              borderRadius: 5,
              marginBottom: 5,
            }}>
            <Text
              style={{
                color: 'white',
              }}>
              Simple Banner Ad
            </Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={() => setCurrentRoute(routes[1])}
            activeOpacity={0.8}
            style={{
              backgroundColor: 'orange',
              width: '90%',
              alignItems: 'center',
              height: 50,
              justifyContent: 'center',
              borderRadius: 5,
              marginBottom: 5,
            }}>
            <Text
              style={{
                color: 'white',
              }}>
              Ad with Image
            </Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={() => setCurrentRoute(routes[2])}
            activeOpacity={0.8}
            style={{
              backgroundColor: 'orange',
              width: '90%',
              alignItems: 'center',
              height: 50,
              justifyContent: 'center',
              borderRadius: 5,
              marginBottom: 5,
            }}>
            <Text
              style={{
                color: 'white',
              }}>
              Ad with Video
            </Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={() => setCurrentRoute(routes[3])}
            activeOpacity={0.8}
            style={{
              backgroundColor: 'orange',
              width: '90%',
              alignItems: 'center',
              height: 50,
              justifyContent: 'center',
              borderRadius: 5,
              marginBottom: 5,
            }}>
            <Text
              style={{
                color: 'white',
              }}>
              Multiple Ads in a List
            </Text>
          </TouchableOpacity>
        </View>
      )}

      {currentRoute?.type === 'banner' && (
        <>
          <AdView type="image" media={false} />
        </>
      )}

      {currentRoute?.type === 'image' && (
        <View
          style={{
            height: 400,
          }}>
          <AdView type="image" media={true} />
        </View>
      )}

      {currentRoute?.type === 'video' && (
        <View
          style={{
            height: 400,
          }}>
          <AdView type="video" media={true} />
        </View>
      )}

      {currentRoute?.type === 'list' && <List />}
    </SafeAreaView>
  );
};

export default App;

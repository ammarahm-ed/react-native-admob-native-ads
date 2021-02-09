import React, {useState} from 'react';
import {
  Image,
  SafeAreaView,
  ScrollView,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import {AdView} from './src/AdView';
import List from './src/List';
import {routes} from './src/utils';

const App = () => {
  const [currentRoute, setCurrentRoute] = useState(null);

  return (
    <SafeAreaView
      style={{
        height: '100%',
        width: '100%',
      }}>
      <View
        style={{
          flexDirection: 'row',
          alignItems: 'center',
          height: 50,
          paddingHorizontal: 12,
          marginBottom: 10,
        }}>
        {currentRoute && (
          <TouchableOpacity
            onPress={() => setCurrentRoute(null)}
            activeOpacity={0.8}
            style={{
              backgroundColor: 'green',
              width: 50,
              alignItems: 'center',
              height: 50,
              justifyContent: 'center',
              borderRadius: 100,
            }}>
            <Text
              style={{
                color: 'white',
              }}>
              Back
            </Text>
          </TouchableOpacity>
        )}
      </View>

      {!currentRoute && (
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

      {currentRoute?.type === 'banner' && <AdView type="image" media={false} />}

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

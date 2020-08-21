import React, {useState} from 'react';
import {
  FlatList,
  Image,
  Modal,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import {AdView} from './AdView';

const App = () => {
  const [modalData, setModalData] = useState({
    visible: false,
    title: '',
    info: '',
    type: null,
  });

  return (
    <View
      style={{
        height: '100%',
        width: '100%',
        justifyContent: 'center',
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
        onPress={() =>
          setModalData({
            visible: true,
            title: 'Simple Banner Ad',
            info: 'A banner ad without full size image.',
            type: 'banner',
          })
        }
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
        onPress={() =>
          setModalData({
            visible: true,
            title: 'A Video/Image Ad',
            info: 'An ad with a full size image or video',
            type: 'media',
          })
        }
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
          Ad with Video/Image
        </Text>
      </TouchableOpacity>

      <TouchableOpacity
        onPress={() =>
          setModalData({
            visible: true,
            title: 'Ads in a List',
            info:
              'You can see the ads are displayed in a list after an interval of 3 items',
            type: 'list',
          })
        }
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

      <Modal visible={modalData.visible} animationType="slide">
        <View
          style={{
            backgroundColor: 'white',
            height: '100%',
            width: '100%',
            justifyContent: 'center',
            alignItems: 'center',
          }}>
          <Text
            style={{
              color: 'black',
              fontSize: 25,
              letterSpacing: 1,
            }}>
            {modalData.title}
          </Text>
          <Text
            style={{
              width: '90%',
              alignSelf: 'center',
              color: 'gray',
              textAlign: 'center',
              marginBottom: 30,
            }}>
            {modalData.info}
          </Text>

          {modalData.type === 'banner' || modalData.type === 'media' ? (
            <AdView media={modalData.type === 'media'} />
          ) : null}

          {modalData.type === 'list' ? (
            <View
              style={{
                height: 300,
              }}>
              <FlatList
                style={{
                  height: 300,
                  width: '100%',
                }}
                data={[
                  'Apple',
                  'Jam',
                  'Banana',
                  'ad',
                  'Orange',
                  'Pineapple',
                  'Apple',
                  'ad',
                  'Jam',
                  'Banana',
                  'Orange',
                  'ad',
                  'Pineapple',
                ]}
                renderItem={({item, index}) =>
                  item === 'ad' ? (
                    <AdView media={false} />
                  ) : (
                    <View
                      style={{
                        borderBottomWidth: 1,
                        borderBottomColor: 'orange',
                      }}>
                      <Text
                        style={{
                          paddingHorizontal: 12,
                          height: 60,
                          textAlignVertical: 'center',
                        }}>
                        {item}
                      </Text>
                    </View>
                  )
                }
              />
            </View>
          ) : null}

          <TouchableOpacity
            activeOpacity={0.8}
            onPress={() =>
              setModalData({
                ...modalData,
                visible: false,
              })
            }
            style={{
              width: '90%',
              alignItems: 'center',
              height: 100,
              width: 100,
              borderWidth: 1,
              borderColor: 'orange',
              justifyContent: 'center',
              borderRadius: 100,
              marginBottom: 5,
              marginTop: 100,
              backgroundColor: 'white',
            }}>
            <Text
              style={{
                color: 'black',
                fontSize: 16,
                letterSpacing: 1,
              }}>
              Go Back
            </Text>
          </TouchableOpacity>
        </View>
      </Modal>
    </View>
  );
};

export default App;

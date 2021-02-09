import React from 'react';
import {
  DeviceEventEmitter,
  FlatList,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import {AdView} from './AdView';
import {Events, listItemsGenerator} from './utils';

let viewableItemsChanged = null;
const dummyData = listItemsGenerator(10);

const List = () => {
  const renderItem = React.useCallback(
    ({item, index}) =>
      item.includes('ad') ? (
        <AdView loadOnMount={false} index={index} type="image" media={false} />
      ) : (
        <View
          style={{
            borderBottomWidth: 1,
            borderBottomColor: 'orange',
            width: '100%',
            paddingHorizontal: 12,
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
      ),
    [],
  );

  const onScrollEnd = React.useCallback(() => {
    DeviceEventEmitter.emit(
      Events.onViewableItemsChanged,
      viewableItemsChanged,
    );
  }, []);

  const onViewableItemsChanged = React.useCallback((e) => {
    viewableItemsChanged = e;
  }, []);

  const keyExtractor = React.useCallback((item) => item, []);

  return (
    <View style={styles.container}>
      <FlatList
        style={styles.list}
        keyExtractor={keyExtractor}
        data={dummyData}
        onScrollAnimationEnd={onScrollEnd}
        onMomentumScrollEnd={onScrollEnd}
        onScrollEndDrag={onScrollEnd}
        onViewableItemsChanged={onViewableItemsChanged}
        renderItem={renderItem}
      />
    </View>
  );
};

export default List;

const styles = StyleSheet.create({
  list: {
    flex: 1,
  },
  container: {
    height: '100%',
    width: '100%',
    justifyContent: 'center',
    alignItems: 'center',
  },
});

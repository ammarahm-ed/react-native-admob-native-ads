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
        /**
         * loadOnMount -> We are telling the AdView to not load the ad when
         * it is mounted.
         */
        <AdView loadOnMount={false} index={index} type="image" media={false} />
      ) : (
        <View
          style={{
            borderBottomWidth: 1,
            borderBottomColor: 'orange',
            width: '100%',
            paddingHorizontal: 12,
          }}
        >
          <Text
            style={{
              paddingHorizontal: 12,
              height: 60,
              textAlignVertical: 'center',
            }}
          >
            {item}
          </Text>
        </View>
      ),
    [],
  );

  /**
   * [STEP II] We only want to know about the viewable
   * items when user stops scrolling. So as soon as the
   * scrolling ends we will send an event to all Ad Items
   * in the list to check which item is visible currently.
   *
   * Go to AdView.js for next steps.
   */
  const onScrollEnd = React.useCallback(() => {
    DeviceEventEmitter.emit(
      Events.onViewableItemsChanged,
      viewableItemsChanged,
    );
  }, []);

  /**
   * [STEP I] When viewable items change in the list
   * we want to know what items are visible and store them
   * in a variable for later us.
   */
  const onViewableItemsChanged = React.useCallback(e => {
    viewableItemsChanged = e;
  }, []);

  const keyExtractor = React.useCallback(item => item, []);

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

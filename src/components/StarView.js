import React, { useCallback, useState, useEffect, useMemo } from 'react';
import { View, StyleSheet} from 'react-native';

/*
export interface StarViewProps {
  style?: StyleProp<ViewStyle>;
  stars: number;
  size?: number;
  fullIcon?: string;
  halfIcon?: string;
  emptyIcon?: string;
}
*/

function requireIconSet(iconSet) {
  return require(`react-native-vector-icons/${iconSet}`).default;
}

export default function StarView({
  style = undefined,
  stars,
  size = 15,
  iconSet = 'MaterialCommunityIcons',
  fullIcon = 'star',
  halfIcon = 'shield-half-full',
  emptyIcon = 'shield-outline',
  ...passThroughProps
}) {
  const [Icon, setIcon] = useState();

  const viewStyle = useMemo(() => [styles.row, style], [style]);

  const renderIcons = useCallback(
    (_stars, _size, icons = [], emptyStars = 5) => {
      if (typeof stars !== 'number') return null;
      if (typeof _size !== 'number') return null;

      if (_stars > 5) _stars = 5;
      if (_stars >= 1) {
        // 1 - 5
        icons.push(<Icon name={fullIcon} size={_size} key={`star-full${_stars}`} color="#ffd27d" />);
        return renderIcons(_stars - 1, _size, icons, emptyStars - 1);
      } else if (_stars >= 0.5) {
        // 0 - 1
        icons.push(<Icon name={halfIcon} size={_size} key={`star-half${_stars}`} color="#ffd27d" />);
        return renderIcons(_stars - 1, _size, icons, emptyStars - 1);
      }
      if (emptyStars > 0) {
        icons.push(<Icon name={emptyIcon} size={_size} key={`star-empty${emptyStars}`} color="#f0f0f0" />);
        return renderIcons(_stars, _size, icons, emptyStars - 1);
      }
      // 0
      return icons;
    },
    [emptyIcon, fullIcon, halfIcon, Icon],
  );

  useEffect(() => {
    setIcon(requireIconSet(iconSet));
  }, [iconSet]);

  if (stars == null || typeof stars !== 'number') return null;

  const icons = renderIcons(stars, size);
  return <View style={viewStyle} {...passThroughProps}>{icons}</View>;
}

const styles = StyleSheet.create({
  row: {
    flexDirection: 'row',
    marginTop: 1,
  },
});

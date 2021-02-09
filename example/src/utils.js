/**
 *
 * @param {string} tag
 * @param {string} type
 * @param {string} value
 */
export function Logger(tag = 'AD', type, value) {
  console.log(`[${tag}][${type}]:`, value);
}

export const adUnitIDs = {
  image:
    Platform.OS === 'ios'
      ? 'ca-app-pub-3940256099942544/3986624511'
      : 'ca-app-pub-3940256099942544/2247696110',
  video:
    Platform.OS === 'ios'
      ? 'ca-app-pub-3940256099942544/2521693316'
      : 'ca-app-pub-3940256099942544/1044960115',
};

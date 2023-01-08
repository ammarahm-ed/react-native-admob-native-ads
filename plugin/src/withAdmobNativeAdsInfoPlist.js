const { withInfoPlist } = require('@expo/config-plugins');

function withAdmobNativeAdsInfoPlist(config, props) {
    return withInfoPlist(config, config => {
        config.modResults.GADApplicationIdentifier = props.iosAppId;
        const identifiers = [
            {
                SKAdNetworkIdentifier: 'cstr6suwn9.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: '4fzdc2evr5.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: '4pfyvq9l8r.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: '2fnua5tdw4.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: 'ydx93a7ass.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: '5a6flpkh64.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: 'p78axxw29g.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: 'v72qych5uu.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: 'ludvb6z3bs.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: 'cp8zw746q7.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: 'c6k4g5qg8m.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: 's39g8k73mm.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: '3qy4746246.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: '3sh42y64q3.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: 'f38h382jlk.skadnetwork',
            },
            {
                SKAdNetworkIdentifier: 'hs6bdukanm.skadnetwork',
            },
        ];
        config.modResults.SKAdNetworkItems = identifiers;
        return config;
    });
}
function withAdmobNativeAds(config, props) {
    return withAdmobNativeAdsInfoPlist(config, props);
}

module.exports = withAdmobNativeAds;
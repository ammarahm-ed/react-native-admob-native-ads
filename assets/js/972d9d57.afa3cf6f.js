"use strict";(self.webpackChunkdocs=self.webpackChunkdocs||[]).push([[937],{3905:function(e,t,n){n.d(t,{Zo:function(){return u},kt:function(){return m}});var a=n(7294);function r(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,a)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){r(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function s(e,t){if(null==e)return{};var n,a,r=function(e,t){if(null==e)return{};var n,a,r={},o=Object.keys(e);for(a=0;a<o.length;a++)n=o[a],t.indexOf(n)>=0||(r[n]=e[n]);return r}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(a=0;a<o.length;a++)n=o[a],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(r[n]=e[n])}return r}var l=a.createContext({}),d=function(e){var t=a.useContext(l),n=t;return e&&(n="function"==typeof e?e(t):i(i({},t),e)),n},u=function(e){var t=d(e.components);return a.createElement(l.Provider,{value:t},e.children)},p={inlineCode:"code",wrapper:function(e){var t=e.children;return a.createElement(a.Fragment,{},t)}},c=a.forwardRef((function(e,t){var n=e.components,r=e.mdxType,o=e.originalType,l=e.parentName,u=s(e,["components","mdxType","originalType","parentName"]),c=d(n),m=r,h=c["".concat(l,".").concat(m)]||c[m]||p[m]||o;return n?a.createElement(h,i(i({ref:t},u),{},{components:n})):a.createElement(h,i({ref:t},u))}));function m(e,t){var n=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var o=n.length,i=new Array(o);i[0]=c;var s={};for(var l in t)hasOwnProperty.call(t,l)&&(s[l]=t[l]);s.originalType=e,s.mdxType="string"==typeof e?e:r,i[1]=s;for(var d=2;d<o;d++)i[d]=n[d];return a.createElement.apply(null,i)}return a.createElement.apply(null,n)}c.displayName="MDXCreateElement"},9880:function(e,t,n){n.r(t),n.d(t,{frontMatter:function(){return s},contentTitle:function(){return l},metadata:function(){return d},toc:function(){return u},default:function(){return c}});var a=n(3117),r=n(102),o=(n(7294),n(3905)),i=["components"],s={id:"introduction",title:"Introduction",sidebar_label:"Introduction"},l=void 0,d={unversionedId:"introduction",id:"introduction",title:"Introduction",description:"If you are working on a React Native Application, you might feel limited when it comes to displaying ads that look beautiful and match your App's look and feel. Usually when you show ads in your app, they are loaded inside a WebView and that is why you cannot modify them except changing the width and height in some cases.",source:"@site/docs/introduction.mdx",sourceDirName:".",slug:"/introduction",permalink:"/react-native-admob-native-ads/docs/introduction",editUrl:"https://github.com/ammarahm-ed/react-native-admob-native-ads/edit/master/docs/docs/introduction.mdx",tags:[],version:"current",frontMatter:{id:"introduction",title:"Introduction",sidebar_label:"Introduction"},sidebar:"someSidebar",next:{title:"Usage examples",permalink:"/react-native-admob-native-ads/docs/examples-2"}},u=[{value:"How is this library different?",id:"how-is-this-library-different",children:[],level:2},{value:"Can I design the ads myself?",id:"can-i-design-the-ads-myself",children:[],level:2},{value:"Features",id:"features",children:[],level:2},{value:"Benefits",id:"benefits",children:[],level:2},{value:"Sponsor this project",id:"sponsor-this-project",children:[],level:2},{value:"Contact &amp; support",id:"contact--support",children:[],level:2},{value:"Contribute",id:"contribute",children:[],level:2},{value:"License",id:"license",children:[],level:2}],p={toc:u};function c(e){var t=e.components,n=(0,r.Z)(e,i);return(0,o.kt)("wrapper",(0,a.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("p",null,"If you are working on a React Native Application, you might feel limited when it comes to displaying ads that look beautiful and match your App's look and feel. Usually when you show ads in your app, they are loaded inside a ",(0,o.kt)("inlineCode",{parentName:"p"},"WebView")," and that is why you cannot modify them except changing the width and height in some cases."),(0,o.kt)("h2",{id:"how-is-this-library-different"},"How is this library different?"),(0,o.kt)("p",null,"This library on the other hand do not load ads in a ",(0,o.kt)("inlineCode",{parentName:"p"},"WebView"),". It uses ",(0,o.kt)("a",{parentName:"p",href:"https://developers.google.com/admob/android/native/start"},"Native Advanced")," ad format to display ads. We request Admob servers to send us the ad information such as images, headlines, videos etc. Then we send this information over the bridge and render it in ",(0,o.kt)("inlineCode",{parentName:"p"},"View")," and ",(0,o.kt)("inlineCode",{parentName:"p"},"Text")," components execpt a few exceptions where we use our own custom components."),(0,o.kt)("h2",{id:"can-i-design-the-ads-myself"},"Can I design the ads myself?"),(0,o.kt)("p",null,"Yes absolutely you can! There are no limiations. You can design ads that look just like your App's design and colors."),(0,o.kt)("h2",{id:"features"},"Features"),(0,o.kt)("ol",null,(0,o.kt)("li",{parentName:"ol"},(0,o.kt)("a",{parentName:"li",href:"https://developers.google.com/admob/android/native/start"},"Admob Native Advanced Ads")," format"),(0,o.kt)("li",{parentName:"ol"},"Cross Platform (iOS and Android)"),(0,o.kt)("li",{parentName:"ol"},"Identical Working on both platforms"),(0,o.kt)("li",{parentName:"ol"},"You can create your ads from ground up as you desire, ",(0,o.kt)("strong",{parentName:"li"},"no limits.")),(0,o.kt)("li",{parentName:"ol"},"No need to manage any .xml or .xib layout files!"),(0,o.kt)("li",{parentName:"ol"},"AutoRefresh ad at specific intervals"),(0,o.kt)("li",{parentName:"ol"},"Ad preloading"),(0,o.kt)("li",{parentName:"ol"},(0,o.kt)("strong",{parentName:"li"},"Support for Video Ads")),(0,o.kt)("li",{parentName:"ol"},"Ad Mediation (Especially Facebook Ads)"),(0,o.kt)("li",{parentName:"ol"},"Ad Targeting"),(0,o.kt)("li",{parentName:"ol"},"Typescript definations")),(0,o.kt)("h2",{id:"benefits"},"Benefits"),(0,o.kt)("p",null,"Ads that look just like your App's design will result in better conversions and revenue for you as compared to other ad formats which look out of place and interrupt the user experience."),(0,o.kt)("h2",{id:"sponsor-this-project"},"Sponsor this project"),(0,o.kt)("p",null,"It costs me alot of time to keep the library updated and address all the bugs & issues. If this library has helped you ",(0,o.kt)("a",{parentName:"p",href:"https://www.patreon.com/streetwriters"},"support me on patreon"),"."),(0,o.kt)("h2",{id:"contact--support"},"Contact & support"),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},"Add a \u2b50\ufe0f ",(0,o.kt)("a",{parentName:"li",href:"https://github.com/ammarahm-ed/react-native-admob-native-ads"},"star on GitHub")," to support the project!"),(0,o.kt)("li",{parentName:"ul"},"Create a GitHub issue for bug reports, feature requests, or questions"),(0,o.kt)("li",{parentName:"ul"},"Follow ",(0,o.kt)("a",{parentName:"li",href:"https://github.com/ammarahm-ed"},"@ammarahm-ed")," for announcements")),(0,o.kt)("h2",{id:"contribute"},"Contribute"),(0,o.kt)("p",null,"That is awesome news! There is alot happening at a very fast pace in this library right now. Every little help is precious. You can contribute in many ways:"),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},"Suggest code improvements on native iOS and Android"),(0,o.kt)("li",{parentName:"ul"},"If you have suggestion or idea you want to discuss, open an issue."),(0,o.kt)("li",{parentName:"ul"},(0,o.kt)("a",{parentName:"li",href:"https://github.com/ammarahm-ed/react-native-admob-native-ads/issues/"},"Open an issue")," if you want to make a pull request, and tell me what you want to improve or add so we can discuss"),(0,o.kt)("li",{parentName:"ul"},"I am always open to new ideas")),(0,o.kt)("h2",{id:"license"},"License"),(0,o.kt)("p",null,"This library is licensed under the ",(0,o.kt)("a",{parentName:"p",href:"https://github.com/ammarahm-ed/react-native-admob-native-ads/blob/master/LICENSE"},"MIT license"),"."),(0,o.kt)("p",null,"Copyright \xa9 Ammar Ahmed (",(0,o.kt)("a",{parentName:"p",href:"https://github.com/ammarahm-ed"},"@ammarahm-ed"),")"))}c.isMDXComponent=!0}}]);
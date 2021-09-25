module.exports = {
  title: "React Native Admob Native Ads",
  tagline: "Admob Native Advanced Ads for React Native",
  url: "https://ammarahm-ed.github.io/index.html",
  baseUrl: "/react-native-admob-native-ads/",
  onBrokenLinks: "throw",
  onBrokenMarkdownLinks: "warn",
  favicon: "img/favicon.ico",
  organizationName: "ammarahm-ed",
  projectName: "react-native-admob-native-ads",
  themeConfig: {
    navbar: {
      title: "React Native Admob Native Ads",
      items: [
        {
          to: "docs/introduction",
          activeBasePath: "docs",
          label: "Docs",
          position: "left",
        },
        {
          href: "https://github.com/ammarahm-ed/react-native-admob-native-ads",
          label: "GitHub",
          position: "right",
        },
      ],
    },
    footer: {
      style: "dark",
      links: [
        {
          title: "Links",
          items: [
            {
              label: "GitHub",
              href:
                "https://github.com/ammarahm-ed/react-native-admob-native-ads",
            },
          ],
        },
      ],
      copyright: `Copyright © ${new Date().getFullYear()} Ammar Ahmed, Built with Docusaurus.`,
    },
  },
  presets: [
    [
      "@docusaurus/preset-classic",
      {
        docs: {
          sidebarPath: require.resolve("./sidebars.js"),
          // Please change this to your repo.
          editUrl:
            "https://github.com/ammarahm-ed/react-native-admob-native-ads/edit/master/docs/",
        },
        theme: {
          customCss: require.resolve("./src/css/custom.css"),
        },
      },
    ],
  ],
};

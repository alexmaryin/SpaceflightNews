Custom Tabs are a feature in Android browsers that gives app developers
a way to add a customized browser experience directly within their app.

Loading web content has been a part of mobile apps since the early days of
smartphones, but older options can present challenges for developers. Launching
the actual browser is a heavy context switch for users that isn't customizable,
while WebViews [don't support](https://research.google/pubs/pub46739/) all features of the web platform, don't share
state with the browser and add maintenance overhead.

Custom Tabs lets users remain within the app while browsing, increasing
engagement and reducing the risk of users abandoning the app. Custom Tabs are
powered directly by the user's preferred browser and automatically share the
state and features offered by it. You don't need to write custom code to manage
requests, permission grants, or cookie stores.

## What can Custom Tabs do?

By using a Custom Tab, your web content loads in whatever rendering engine
powers your user's preferred browser. Any API or web platform feature is
available there, and is available in your Custom Tab. Their browsing session,
saved passwords, payment methods, and addresses all show up just like they
are accustomed to already.

## What can I customize in a Custom Tab?

Quite a bit! Custom Tabs give you fine grained control over a lot of the browser
chrome and user experience. Within your app, you launch a Custom Tab using an
[Intent](https://developer.android.com/guide/components/intents-filters). When this Intent is called, you can add a number of attributes to
the [CustomTabIntent](https://developer.android.com/reference/androidx/browser/customtabs/CustomTabsIntent) to get the exact experience you want. Some
customizations that you can add are listed here:

- Custom entrance and exit animations to match the rest of your app
- Modifing the toolbar color to match your app's branding
- Color consistency that can stay with your app, even if they switch between light and dark themes
- Custom actions and entries to the browser's toolbar, and menus
- Control the launch height of the Custom Tab, enabling things like streaming your videos while interacting with your web store

In addition, users can minimize a Custom Tab to interact with the underlying
app, and restore it at any time without losing any progress to resume their
journey. This gives users an alternative to closing the Custom Tab so they can
seamlessly multitask between the web and the native app. The feature is
enabled by default for Custom Tabs.

That is far from everything. Custom Tabs are very powerful, and under active
development. Each browser needs to add support for these features as they become
available. While nearly all have some level of support, it is important to know
what may or may not be available in your user's browsers. Refer to the
[feature comparison table](https://developer.chrome.com/docs/android/custom-tabs/browser-support) to quickly check the availability of the
different features across popular
Android browsers.

You can test this now with our [sample](https://github.com/GoogleChrome/android-browser-helper/tree/master/demos/custom-tabs-example-app) on GitHub.

## When should I use Custom Tabs?

There is no single "correct" way to load web content. In certain situations,
WebView is going to be the right technology to use. For example, if you are
exclusively hosting your own content inside your app, or if you need to inject
javascript directly from your app. If your app directs people to URLs outside
domains, the built-in shared state in Custom Tabs means they are likely a
better choice. Other strengths of Custom Tabs include:

1. Security: Custom Tabs use Google's Safe Browsing to protect the user and the device from dangerous sites.
2. Performance optimization:
   1. Pre-warming of the Browser in the background, while avoiding stealing resources from the application.
   2. Speed up the page load time by speculatively loading URLs in advance.
3. Lifecycle management: Apps launching a Custom Tab won't be evicted by the system during the Tab's use. The importance of the Custom Tab is raised to the *foreground* level.
4. Shared cookie jar and permissions model so users don't have to sign in to sites they are already connected to, or re-grant permissions they have already granted.
5. Browser features like autofill for better form completion are available out-of-the-box.
6. Users can return to app with an integrated back button.

## Custom Tabs versus Trusted Web Activity

[Trusted Web Activities](https://developer.android.com/develop/ui/views/layout/webapps/trusted-web-activities) extend the Custom Tabs protocol and shares most of
its benefits. But, instead of providing a customized UI, it allows developers to
open a browser tab without any UI at all. It is recommended for developers who
want to open their own [Progressive Web App](https://web.dev/progressive-web-apps/), in full screen, inside their
own Android app.

## Where are Custom Tabs available?

Custom Tabs is a feature supported by browsers on the Android platform. It was
originally introduced by [Chrome](https://play.google.com/store/apps/details?id=com.android.chrome), on version 45. The protocol is supported
by most Android browsers.

We're looking for feedback, questions and suggestions on this project, so we
encourage you to file issues on [crbug.com](https://crbug.com) and ask questions on Twitter
[@ChromiumDev](https://twitter.com/ChromiumDev).

## Learn more

For questions, check the [chrome-custom-tabs](https://stackoverflow.com/questions/tagged/chrome-custom-tabs) tag on StackOverflow.
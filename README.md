<h2 align="center">
  <img src="/docs/wifi-hotspot-app.jpg" /><br>
  React Native Hotspot [Android]
</h2>

[![NPM Version](https://img.shields.io/badge/npm-1.0.0-orange.svg)](https://www.npmjs.com/package/react-native-hotspotmanager)
[![NPM Version](https://img.shields.io/badge/yarn-1.0.0-red.svg)](https://yarnpkg.com/en/package/react-native-hotspotmanager)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](#)

# Introduce
Since there's not a strict API from React Native to allow us to handle operations on `Hotspot`. So, today I would like to share my small kit with you guys to help most of rn developers to be able to use wifi-hotspot and enjoy its powerful.

The kit is designed to be helpful and to provide an easy API that can suit your needs so let me tell you how this kit can help you a long away with:
  * Talk to your ROBOT!
  * Communicating without an Internet
  * Build yourown SHAREit (Share any types of files locally)
  * Build a tiny network with your friends

## Setup
Fetch it using npm or yarn
```
npm i --save react-native-hotspotmanager
OR
yarn add react-native-hotspotmanager
```
Then run this command to link it
```
react-native link react-native-hotspotmanager
```
Almost done just put this lines into `Settings.gradle`
```
include ':hotspotmanager'
project(':hotspotmanager').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-hotspotmanager/android/hotspotmanager')
```
💥 Just run it!

## Example
🔥 [Full Example](https://github.com/amitrai98/react-native-hotspotmanager)

## Features
  * Enable Hotspot
  * Disable Hotspot
  * Create Hotspot's Settings (SSID, Password, ..)
  * Fetch Hotspot's Settings
  * Get List of Connected Peers

## Demo
![example app](/docs/image.gif)

## Get Started

* Check WIFI is on/off then turn hotspot on and automatically check if hotspot already opened
~~~
Hotspotmanager.enable(() => {
      ToastAndroid.show("Hotspot Enabled",ToastAndroid.SHORT);
    }, (err) => {
      ToastAndroid.show(err.toString(),ToastAndroid.SHORT);
    })
~~~

* Check hotspot is on then turn it off and automatically check if hotspot already closed
~~~
Hotspotmanager.disable(() => {
      ToastAndroid.show("Hotspot Disabled",ToastAndroid.SHORT);
    }, (err) => {
      ToastAndroid.show(err.toString(),ToastAndroid.SHORT);
    })
~~~

* You can set your own configuration to your hotspot using this function. supported configuration:
  * SSID
  * Password
  * Protocols
  * authAlgorithms
  * Security type
  

| Parameters | Required | Types | Default
| --- | --- | --- | --- |
| `SSID` | * | none | none
| `password` | * | none | password should be provided if you will use our settings |
| `protocols` | - | `Hotspotmanager.protocols.RSN` <br> `Hotspotmanager.protocols.WPA` <br>`Hotspotmanager.protocols.BOTH`| `Hotspotmanager.protocols.BOTH` |
| `securityType` | - | `Hotspotmanager.security.WPA_PSK` <br> `Hotspotmanager.security.WPA_EAP` <br> `Hotspotmanager.security.IEEE8021X` <br> `Hotspotmanager.security.WPA2_PSK` | `Hotspotmanager.security.WPA2_PSK` |
| `authAlgorithms` | - | `Hotspotmanager.auth.OPEN` <br> `Hotspotmanager.auth.SHARED` <br> `Hotspotmanager.auth.LEAP` | `Hotspotmanager.auth.SHARED` |

~~~
const hotspot = {SSID: 'ASSEM', password: 'helloworld', authAlgorithms: Hotspotmanager.auth.OPEN, protocols: Hotspotmanager.protocols.WPA }
    Hotspotmanager.create(hotspot, () => {
      ToastAndroid.show("Hotspot enstablished", ToastAndroid.SHORT);
    }, (err) => {
      ToastAndroid.show(err.toString(), ToastAndroid.SHORT);
    })
~~~
> You can also use your own old settings without the need of creating new one

* You can fetch your hotsot's configuration. Supported returning config:
  * ssid
  * password
  * status ( network is enabled or disabled )
  * networkId
~~~
config: {
  ssid: string,
  password: string,
  status: boolean ( true means enable, false means disable )
  networkId: Int
}
Hotspotmanager.getConfig((config) => {
      ToastAndroid.show("Hotspot SSID: " + config.ssid, ToastAndroid.SHORT);
    }, (err) => {
      ToastAndroid.show(err.toString(), ToastAndroid.SHORT);
    })
~~~

* Fethcing connected devices to your wifi network. Returned an object for every device contains:
  * ip
  * mac
  * device number
~~~
data: [
  results: {
    ip: 192.168.x.x,
    mac: A3:76:E1:33:79:F3,
    device: number
  }
]
Hotspotmanager.peersList((data) => {
      const peers = JSON.parse(data);
    }, (err) => {
      ToastAndroid.show(err.toString(), ToastAndroid.SHORT);
    })
~~~

package com.reactlibrary.core;

import android.content.Context;
import android.net.wifi.WifiConfiguration;

import com.facebook.react.bridge.ReadableMap;
import com.reactlibrary.Hotspotmanager;

import java.util.ArrayList;


public class HotspotManager {
    private WifiApManager wifi;
    private peersList callback;

    public interface peersList {
        void onPeersScanned(ArrayList<ClientScanResult> peers);
    }

    public HotspotManager(Context context) {
        wifi = new WifiApManager(context);
    }

    public void setPermission() {
        wifi.showWritePermissionSettings(false);
    }

    public boolean isEnabled() {
        if(!wifi.isWifiApEnabled()) {
            wifi.setWifiApEnabled(null, true);
            return true;
        } else {
            return false;
        }
    }
    public boolean isDisabled() {
        if(wifi.isWifiApEnabled()) {
            wifi.setWifiApEnabled(null, false);
            return true;
        } else {
            return false;
        }
    }
    public boolean isCreated(ReadableMap info) {
        if(wifi.isWifiApEnabled()) {
            WifiConfiguration config = new WifiConfiguration();
            if( info.hasKey("SSID") && info.hasKey("password")) {
                config.SSID = info.getString("SSID");
                config.preSharedKey = info.getString("password");
                config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                config.allowedKeyManagement.set(4);
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                if(info.hasKey("protocols")) {
                    switch(info.getInt("protocols")) {
                        case Hotspotmanager.protocols.WPA:
                            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                            break;
                        case Hotspotmanager.protocols.RSN:
                            config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                            break;
                        default:
                        {
                            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                            config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                        }
                        break;
                    }
                } if(info.hasKey("securityType")) {
                    switch(info.getInt("securityType")) {
                        case Hotspotmanager.security.IEEE8021X:
                            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
                            break;
                        case Hotspotmanager.security.WPA_EAP:
                            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
                            break;
                        case Hotspotmanager.security.WPA_PSK:
                            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                            break;
                        default:
                            config.allowedKeyManagement.set(4);
                            break;
                    }
                } if(info.hasKey("authAlgorithm")) {
                    switch(info.getInt("authAlgorithm")) {
                        case Hotspotmanager.authAlgorithm.LEAP:
                            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.LEAP);
                            break;
                        case Hotspotmanager.authAlgorithm.OPEN:
                            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                            break;
                        default:
                            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                            break;
                    }
                }
                if(wifi.setWifiApConfiguration(config))
                    return true;
                else
                    return false;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }
    public WifiConfiguration getConfig() {
        if(wifi.isWifiApEnabled()) {
            return wifi.getWifiApConfiguration();
        } else {
            return null;
        }
    }
    private void peersList() {
        if(wifi.isWifiApEnabled()) {
            wifi.getClientList(true, new FinishScanListener() {
                @Override
                public void onFinishScan(ArrayList<ClientScanResult> clients) {
                    callback.onPeersScanned(clients);
                }
            });
        }
    }
    public void setPeersCallback(peersList callback) {
        this.callback = callback;
        peersList();
    }
}

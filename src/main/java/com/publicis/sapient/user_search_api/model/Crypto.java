package com.publicis.sapient.user_search_api.model;

import jakarta.persistence.*;


@Entity
@Table(name = "crypto")
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String coin;
    private String wallet;
    private String network;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Crypto(String coin, String wallet, String network) {
        this.coin = coin;
        this.wallet = wallet;
        this.network = network;
    }

    public Crypto() {
    }
}


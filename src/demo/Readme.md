# Readme

## How to drop packet from Azure Cache for Redis

```sh
sudo iptables -A INPUT -p tcp --sport 6380 -j DROP
```

```sh
sudo iptables -D INPUT -p tcp --sport 6380 -j DROP
```

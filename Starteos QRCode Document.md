# Starteos QRCode 文档

## 协议
* Starteos QRCode 使用 URL Scheme，方便其他应用拉起。
* 传值方式使用URL键值对后跟上JSON数据。
* 协议头为starteos，二维码生成内容为：starteos://starteos.io?param={JSON}

## 转账
```json
{
    "type":"transfer",//转账操作
    "address":"developer.x",//收款地址
    "amount":"1",//金额
    "symbol":"EOS",//单位
    "contract":"eosio.token",//代币合约地址
    "precision":"4",//精度
    "blockchain":"EOS",//底层链标识，EOS、ETH、BTC等
    "chainName":"EOS",//侧链名称，例如BOS、MEETONE
    "chainid":"aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906"//chainid
}
```

`* 支持simpleWallet协议`
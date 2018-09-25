# STARTEOS DAPP SDK API文档

- [STARTEOS DAPP SDK API文档](#starteos-dapp-sdk-api文档)
    - [数据交互](#数据交互)
        - [_input_](#_input_)
        - [_output_](#_output_)
        - [_code_](#_code_)
    - [API](#api)
        - [_SYSTEM_](#_system_)
            - [获取Native SDK信息](#获取native-sdk信息)
            - [获取当前语言设置](#获取当前语言设置)
            - [获取当前显示币种](#获取当前显示币种)
        - [_GUI_](#_gui_)
            - [全屏切换](#全屏切换)
            - [更改状态栏显示](#更改状态栏显示)
            - [Toast](#toast)
            - [Alert](#alert)
            - [Dialog](#dialog)
            - [复制到剪切板](#复制到剪切板)
            - [扫描二维码](#扫描二维码)
        - [_CUSTOMER_](#_customer_)
            - [获取当前钱包类型](#获取当前钱包类型)
        - [_EOS_](#_eos_)
            - [获取当前账户](#获取当前账户)
            - [获取当前账户余额](#获取当前账户余额)
            - [获取当前账户信息](#获取当前账户信息)
            - [获取账户列表](#获取账户列表)
            - [获取余额](#获取余额)
            - [获取账户信息](#获取账户信息)
            - [转账](#转账)
            - [获取交易记录](#获取交易记录)
        - [_ETH_](#_eth_)
            - [转账](#转账-1)


---

## 数据交互
约定JavaScript与原生APP交互方式，数据使用json传输
### _input_
```json
{
    "namespace":"customer", //模块
    "function":"getWalletAddress", //原生API方法
    "callback":"getWalletAddressCallback", //自定义JavaScript接受原生回调的方法名（包括路径）
    "params":{ //参数，根据不同的API说明进行传参
        "key":"value",
        ……
        }
}
```
### _output_
```json
{
    "code":10000,
    "message":"success",
    "data":{//返回数据，根据不同的API说明进行返回
        "key":"value",
        ……
    }
}
```
### _code_
>10000 -> 成功
-10001 -> namespace错误
-10002 -> function错误
-10003 -> params错误
-10004 -> 用户取消了操作
-10005以后 -> 其他错误，根据api不同，文档中给于说明
---

## API
### _SYSTEM_
namespace: `system`
#### 获取Native SDK信息
**function:** `getSdkInfo`
**params:** `null`
**output:**
key | value | remark
--- | --- | ---
version | String | 版本号
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"version": "1.0.5"
	}
}
```
#### 获取当前语言设置
**function:** `getLanguageSetting`
**params:** `null`
**output:**
key | value | remark
--- | --- | ---
languageSetting | String | 语言设置(zh,en)
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"languageSetting": "zh"
	}
}
```
#### 获取当前显示币种
**function:** `getSymbol`
**params:** `null`
**output:**
key | value | remark
--- | --- | ---
symbol | String | 币种(USD,CNY)
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"symbol": "CNY"
	}
}
```
### _GUI_
namespace: `gui`
#### 全屏切换
**function:** `changeFullScreen`
**params:**
key | value | remark
--- | --- | ---
fullScreen | Boolean | 是否全屏
**output:** `null`
```json
{
	"code": 10000,
	"message": "success",
	"data": {
	}
}
```
#### 更改状态栏显示
**function:** `changeStatusBar`
**params:**
key | value | remark
--- | --- | ---
title | String | 标题
color | String | 十六进制颜色(#FFFFFF)
theme | String | 使用暗色或者白色的图标以及文字(dark,light)
canGoBack | Boolean | 是否能够返回上一级（并且显示返回按钮）
**output:** `null`
```json
{
	"code": 10000,
	"message": "success",
	"data": {
	}
}
```
#### Toast
**function:** `showToast`
**params:**
key | value | remark
--- | --- | ---
message | String | 内容
delay | Integer | 显示时长（毫秒）
**output:** `null`
```json
{
	"code": 10000,
	"message": "success",
	"data": {
	}
}
```
#### Alert
**function:** `showAlert`
**params:**
key | value | remark
--- | --- | ---
title | String | 标题
message | String | 内容
btnString | String | 按钮文字
**output:**
key | value | remark
--- | --- | ---
clicked | Integer | 接收到回调时，代表用户按下了按钮
```json
{
	"code": 10000,
	"message": "success",
	"data": {
        "clicked":0
	}
}
```
#### Dialog
**function:** `showDialog`
**params:**
key | value | remark
--- | --- | ---
title | String | 标题
message | String | 内容
leftBtnString | String | 左边按钮文字
rightBtnString | String | 右边按钮文字
**output:**
key | value | remark
--- | --- | ---
clicked | Integer | 接收到回调时，代表用户按下了按钮，0代表左边，1代表右边
```json
{
	"code": 10000,
	"message": "success",
	"data": {
        "clicked":0
	}
}
```
#### 复制到剪切板
**function:** `setClipboard`
**params:**
key | value | remark
--- | --- | ---
data | String | 复制的内容
**output:** `null`
```json
{
	"code": 10000,
	"message": "success",
	"data": {
	}
}
```
#### 扫描二维码
**function:** `scanQRCode`
**params:** `null`
**output:**
key | value | remark
--- | --- | ---
result | String | 扫描到的内容
```json
{
	"code": 10000,
	"message": "success",
	"data": {
        "result":"url"
	}
}
```
### _CUSTOMER_
namespace: `customer`
#### 获取当前钱包类型
**function:** `getCurrentWalletType`
**params:** `null`
**output:**
key | value | remark
--- | --- | ---
walletType | String | 钱包类型(EOS,ETH)
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"walletType": "EOS"
	}
}

```
**error:**
 code | remark
 --- | ---
 -10005 | 当前没有钱包
### _EOS_
namespace: `eos`
#### 获取当前账户
**function:** `getCurrentWalletAccount`
**params:** `null`
**output:**
key | value | remark
--- | --- | ---
account | String | 账户名
address | String | 公钥
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"account": "visualvisual",
		"address": "EOS6HQsKWtyirURq7Nr63M4n1Fo4zyeesATgcq3XvbXCfeCMu1tPA"
	}
}
```
**error:**
 code | remark
 --- | ---
 -10005 | 当前没有钱包
#### 获取当前账户余额
**function:** `getCurrentBalance`
**params:**
key | value | remark
--- | --- | ---
tokenName | String | 代币名称
contract | String | 合约地址
**output:**
key | value | remark
--- | --- | ---
account | String | 账户名
contract | String | 合约地址
balance | Double | 余额
symbol | String | 单位
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"account": "visualvisual",
		"contract": "aircoin.eos",
		"balance": 699.9301,
		"symbol": "AC"
	}
}
```
**error:**
 code | remark
 --- | ---
 -10005 | 当前没有钱包
#### 获取当前账户信息
**function:** `getCurrentAccountInfo`
**params:** `null`
**output:** `链上返回的原始数据`
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"account_name": "visualvisual",
		"head_block_num": 15953308,
		"head_block_time": "2018-09-12T02:07:00.000",
		"privileged": false,
		"last_code_update": "1970-01-01T00:00:00.000",
		"created": "2018-07-26T07:53:58.000",
		"ram_quota": 8266,
		"net_weight": 1000,
		"cpu_weight": 14800,
		"net_limit": {
			"used": 137,
			"available": 68242,
			"max": 68379
		},
		"cpu_limit": {
			"used": 454,
			"available": 186742,
			"max": 187196
		},
		"ram_usage": 4902,
		"permissions": [{
			"perm_name": "active",
			"parent": "owner",
			"required_auth": {
				"threshold": 1,
				"keys": [{
					"key": "EOS6HQsKWtyirURq7Nr63M4n1Fo4zyeesATgcq3XvbXCfeCMu1tPA",
					"weight": 1
				}],
				"accounts": [],
				"waits": []
			}
		}, {
			"perm_name": "owner",
			"parent": "",
			"required_auth": {
				"threshold": 1,
				"keys": [{
					"key": "EOS5o6n7yrSfyKq9jaE226FR2xvggMhRJdvzfU2ja3efrwCBcynBb",
					"weight": 1
				}],
				"accounts": [],
				"waits": []
			}
		}],
		"total_resources": {
			"owner": "visualvisual",
			"net_weight": "0.1000 EOS",
			"cpu_weight": "1.4800 EOS",
			"ram_bytes": 8266
		},
		"self_delegated_bandwidth": {
			"from": "visualvisual",
			"to": "visualvisual",
			"net_weight": "0.0000 EOS",
			"cpu_weight": "1.0800 EOS"
		},
		"refund_request": null,
		"voter_info": {
			"owner": "visualvisual",
			"proxy": "",
			"producers": [],
			"staked": 10800,
			"last_vote_weight": "0.00000000000000000",
			"proxied_vote_weight": "0.00000000000000000",
			"is_proxy": 0
		}
	}
}
```
**error:**
 code | remark
 --- | ---
 -10005 | 当前没有钱包
#### 获取账户列表
**function:** `getWalletAccounts`
**params:** `null`
**output:**
key | value | remark
--- | --- | ---
accounts | Array | 账户名列表
**item:**
key | value | remark
--- | --- | ---
account | String | 账户名
address | String | 公钥
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"accounts": [{
			"address": "EOS6HQsKWtyirURq7Nr63M4n1Fo4zyeesATgcq3XvbXCfeCMu1tPA",
			"account": "visualvisual"
		}, {
			"address": "EOS4zajtGudUSrrH89tzbL8Jz8Wi1DezYeUyHmM9HPxZxnMjz97mx",
			"account": "emememememem"
		}]
	}
}
```
**error:**
 code | remark
 --- | ---
 -10005 | 当前没有钱包
#### 获取余额
**function:** `getBalance`
**params:**
key | value | remark
--- | --- | ---
account | String | 账户名
tokenName | String | 代币名称
contract | String | 合约地址
**output:**
key | value | remark
--- | --- | ---
account | String | 账户名
contract | String | 合约地址
balance | Double | 余额
symbol | String | 单位
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"account": "visualvisual",
		"contract": "aircoin.eos",
		"balance": 699.9301,
		"symbol": "AC"
	}
}
```
**error:**
 code | remark
 --- | ---
 -10006 | 查询失败（网络错误）
#### 获取账户信息
**function:** `getAccountInfo`
**params:**
key | value | remark
--- | --- | ---
account | String | 账户名
**error:**
 code | remark
 --- | ---
 -10006 | 网络错误
**output:** `链上返回的原始数据`
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"account_name": "visualvisual",
		"head_block_num": 15953308,
		"head_block_time": "2018-09-12T02:07:00.000",
		"privileged": false,
		"last_code_update": "1970-01-01T00:00:00.000",
		"created": "2018-07-26T07:53:58.000",
		"ram_quota": 8266,
		"net_weight": 1000,
		"cpu_weight": 14800,
		"net_limit": {
			"used": 137,
			"available": 68242,
			"max": 68379
		},
		"cpu_limit": {
			"used": 454,
			"available": 186742,
			"max": 187196
		},
		"ram_usage": 4902,
		"permissions": [{
			"perm_name": "active",
			"parent": "owner",
			"required_auth": {
				"threshold": 1,
				"keys": [{
					"key": "EOS6HQsKWtyirURq7Nr63M4n1Fo4zyeesATgcq3XvbXCfeCMu1tPA",
					"weight": 1
				}],
				"accounts": [],
				"waits": []
			}
		}, {
			"perm_name": "owner",
			"parent": "",
			"required_auth": {
				"threshold": 1,
				"keys": [{
					"key": "EOS5o6n7yrSfyKq9jaE226FR2xvggMhRJdvzfU2ja3efrwCBcynBb",
					"weight": 1
				}],
				"accounts": [],
				"waits": []
			}
		}],
		"total_resources": {
			"owner": "visualvisual",
			"net_weight": "0.1000 EOS",
			"cpu_weight": "1.4800 EOS",
			"ram_bytes": 8266
		},
		"self_delegated_bandwidth": {
			"from": "visualvisual",
			"to": "visualvisual",
			"net_weight": "0.0000 EOS",
			"cpu_weight": "1.0800 EOS"
		},
		"refund_request": null,
		"voter_info": {
			"owner": "visualvisual",
			"proxy": "",
			"producers": [],
			"staked": 10800,
			"last_vote_weight": "0.00000000000000000",
			"proxied_vote_weight": "0.00000000000000000",
			"is_proxy": 0
		}
	}
}
```
#### 转账
**function:** `transfer`
**params:**
key | value | remark
--- | --- | ---
from | String | 转出账户名
fromAddress | String | 转出账户公钥
to | String | 转账接受账户名
amount | Double | 金额
symbol | String | 代币单位
contract | String | 合约地址
memo | String | 备注
hint | String | 提示，仅用于展示给用户
**output:**
key | value | remark
--- | --- | ---
transactionId | String | 交易ID
```json
{
	"code": 10000,
	"message": "success",
	"data": {
		"transactionId": "740298b6a4430ad902b6791fe1d1ee15a2f236e36d16e9b99cf120c7a0fd15d2"
	}
}
```
**error:**
 code | remark
 --- | ---
 -10006 | 网络错误 |
 -10007 | 交易失败 |
 -10008 | 没有找到转出的钱包 |
#### 获取交易记录
**function:** `getTransactionRecord`
**params:**
key | value | remark
--- | --- | ---
account | String | 账户名
tokenName | String | 代币名称
contract | String | 合约地址
 \ | \ | 翻页方式待定
**output:** `链上返回的原始数据`
### _ETH_
namespace: `eth`
#### 转账
`待定`

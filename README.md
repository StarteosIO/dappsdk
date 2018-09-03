# DAPP SDK
## 引用
```gradle
compile 'io.starteos:dappsdk:1.0.1'
compile 'io.starteos:dappsdk-compiler:1.0.1'
compile 'io.starteos:dappsdk-annotation:1.0.1'
```
## 使用
实例化一个DAppBridge对象，将当前context和webView传入即可
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DAppBridge(this, webview)
        webview.loadUrl("http://www.abcd.dog/starteos/dapp.html")
    }

}
```
新建一个类继承DAppApi，并在类上注解@Namespace()，此时如果JavaScript进行相应操作，会自动调用对应方法
```kotlin
@Namespace("gui")
public class DAppApiGUI extends DAppApi {
    public DAppApiGUI(DAppBridge bridge) {
        super(bridge);
    }

    public void alert(final Request request) {
        JSONObject params = request.getParams();
        new AlertDialog.Builder(bridge.getContext())
                .setTitle(params.optString("title"))
                .setMessage(params.optString("content"))
                .setNegativeButton(params.optString("btnString"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bridge.callback(request, new Response(Response.CODE_SUCCESS, "success"));
                    }
                }).create().show();
    }

    public void toast(Request request) {
        Toast.makeText(bridge.getContext(), request.getParams().optString("message"), Toast.LENGTH_SHORT).show();
    }

}
```
要实现文档中标准的API，需要在继承DAppApi的同时实现io.starteos.dappsdk.standard中的接口
```kotlin
@Namespace("customer")
public class DAppApiCustomer extends DAppApi implements IDAppApiCustomer{
    public DAppApiCustomer(DAppBridge bridge) {
        super(bridge);
    }

    @Override
    public void getCurrentWalletType(Request request) {

    }

    @Override
    public void getCurrentWalletAccount(Request request) {

    }
}
```
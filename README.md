# DAPP SDK
## 引用
```gradle
implementation 'io.starteos:dappsdk-lib:1.0.5.fix.2'
implementation 'io.starteos:dappsdk-annotation:1.0.5.fix.2'
annotationProcessor 'io.starteos:dappsdk-compiler:1.0.5.fix.2'
// kapt 'io.starteos:dappsdk-compiler:1.0.5.fix.2'
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
```java
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
```java
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
回调数据到JavaScript，需要先实例化一个Response对象，必传参数为code和message，data可以在实例化对象的时候传入，但是无法再进行更改，也不能再调用putData方法。如果实例化对象的时候未传入data，则可以调用putData方法在返回值中添加键值对。最后再将request和response同时传入DAppBridge的callback方法即可。
```java
    //回调data为空的数据
    bridge.callback(request, new Response(Response.CODE_SUCCESS, "success"));
    //回调自定义类型的data数据
    bridge.callback(request, new Response(Response.CODE_SUCCESS, "success","this is a result"));
    //回调键值对data数据
    Response response = new Response(Response.CODE_SUCCESS, "success");
    response.putData("name", "Michael Lee");
    response.putData("age", 23);
    bridge.callback(request, response);
    //注意：Response中传入的内容在Response的toString时，
    //返回的内容中不能存在[\"]，只能是["]，否则JavaScript可能会无法收到回调
```
## 数据
按照starteos dapp sdk api document中格式返回
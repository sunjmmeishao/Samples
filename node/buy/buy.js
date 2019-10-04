var router = require('express').Router();

var moment =  require   ('moment');
var crypto =  require ('crypto');
var fs =  require   ('fs');
var path = require('path');
var accountSettings =  {
    // 参考下方 SDK 配置
    APP_ID: '2016101200669544',
    APP_GATEWAY_URL: "https://openapi.alipaydev.com/gateway.do",
    APP_PRIVATE_KEY_PATH:process.cwd() +"/buy/key"
} ;
// 查询 Todo 列表
router.get('/', function(req, res, next) {
    let params = new Map();
    params.set('app_id',  accountSettings.APP_ID);
    params.set('method', 'alipay.trade.app.pay');
    params.set('charset', 'utf-8');
    params.set('sign_type', 'RSA2');
    params.set('timestamp', moment().format('YYYY-MM-DD HH:mm:ss'));
    params.set('version', '1.0');
    params.set('notify_url',  accountSettings.APP_GATEWAY_URL);
    params.set('biz_content',  _buildBizContent('商品名称xxxx', '商户订单号xxxxx', '商品金额8.88'));




    params.set('sign', _buildSign(params));

    res.send( [...params].map(([k, v]) => `${k}=${encodeURIComponent(v)}`).join('&'));
    }
);

/**
 * 生成业务请求参数的集合
 * @param subject       商品的标题/交易标题/订单标题/订单关键字等。
 * @param outTradeNo    商户网站唯一订单号
 * @param totalAmount   订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
 * @returns {string}    json字符串
 * @private
 */
function _buildBizContent(subject, outTradeNo, totalAmount){
    let bizContent = {
        subject: subject,
        out_trade_no: outTradeNo,
        total_amount: totalAmount,
        product_code: 'QUICK_MSECURITY_PAY',
    };
 
    return JSON.stringify(bizContent);

 }

 /**
 * 根据参数构建签名
 * @param paramsMap    Map对象
 * @returns {number|PromiseLike<ArrayBuffer>}
 * @private
 */
 function _buildSign(paramsMap) {
    //1.获取所有请求参数，不包括字节类型参数，如文件、字节流，剔除sign字段，剔除值为空的参数
    let paramsList = [...paramsMap].filter(([k1, v1]) => k1 !== 'sign' && v1);
    //2.按照字符的键值ASCII码递增排序
    paramsList.sort();
    //3.组合成“参数=参数值”的格式，并且把这些参数用&字符连接起来
    let paramsString = paramsList.map(([k, v]) => `${k}=${v}`).join('&');
 
    let privateKey = fs.readFileSync(accountSettings.APP_PRIVATE_KEY_PATH, 'utf8');
    console.log(privateKey);
    let signType = paramsMap.get('sign_type');
    console.log(paramsString);
    return _signWithPrivateKey(signType, paramsString, privateKey);
}
 
/**
 * 通过私钥给字符串签名
 * @param signType      返回参数的签名类型：RSA2或RSA
 * @param content       需要加密的字符串
 * @param privateKey    私钥
 * @returns {number | PromiseLike<ArrayBuffer>}
 * @private
 */
function _signWithPrivateKey(signType, content, privateKey) {
    let sign;
    if (signType.toUpperCase() === 'RSA2') {
        sign = crypto.createSign("RSA-SHA256");
    } else if (signType.toUpperCase() === 'RSA') {
        sign = crypto.createSign("RSA-SHA1");
    } else {
        throw new Error('请传入正确的签名方式，signType：' + signType);
    }
    sign.update(content);
    return sign.sign(privateKey, 'base64');
 }

module.exports = router;
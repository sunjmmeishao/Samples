var AV = require('leanengine');
var AV = require('leancloud-storage');
var router = require('express').Router();
var { Query, User } = AV;
var url = require('url');
const Todo = AV.Object.extend('Todo')

AV.init({
  appId: process.env.LEANCLOUD_APP_ID || 'SAD7NRjB3sjxhxmSqmPivDGd-MdYXbMMI',
  appKey: process.env.LEANCLOUD_APP_KEY || '5Xot9pTHQR4ua1mVFLphUI13',
  masterKey: process.env.LEANCLOUD_APP_MASTER_KEY || 'v3V0KgGMerFarp5NGCXU3al3'
});

router.get("/del",(req,res)=>{
   res.json({code:1,msg:"user_del"})
});
 
router.get("/update",(req,res)=>{
   res.json({code:1,msg:"user_update"})
});

router.get('/', function(req, request, next) {
	var params = url.parse(req.url, true).query;
	
	  const todo = new Todo()
console.log(params)
  todo.set('content', params.message)
  todo.set('status', 0)

  // if (request.currentUser) {
  //   // 如果客户端已登录（发送了 sessionToken），将 Todo 的作者设置为登录用户
  //   todo.set('author', request.currentUser)
  //   // 设置 ACL，可以使该 todo 只允许创建者修改，其他人只读
  //   const acl = new AV.ACL(request.currentUser)
  //   acl.setPublicWriteAccess(false)
  //   todo.setACL(acl)
  // }

   todo.save(null, {sessionToken: request.sessionToken})
   request.writeHead(200,{
"Content-Type":"text/plain;charset=utf-8"
});
   request.write('document.write("提交成功，感谢您的建议")')
request.end();

// 	new AV.Query('Todo').find().then(function (todos) {
//   console.log(todos);
// }).catch(function (err) {
//   console.log(err);
// });
// 	console.log(params);
//     res.end();
// 	
}
);
module.exports = router;
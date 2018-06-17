print(module.file);
var common = require('js/common.js');
print(common.greeting('Liqingfeng'));

function sayhello(name) {
    print("hello "+name);
}

$Context.printType(sayhello);

//$Context.calltest(sayhello);
//$Context.setTimeOut(5000, sayhello);
var timierid = $Context.setTimeOut(5000, sayhello, "ifan");
print(timierid);
print('Line 13');
$Context.clearTimeOut(timierid);
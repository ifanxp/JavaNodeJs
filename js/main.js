print(module.file);
var common = require('js/common.js');
print(common.greeting('Liqingfeng'));

function sayhello(name) {
    print("hello "+name);
}

$Context.printType(sayhello);

//$Context.calltest(sayhello);
//$Context.setTimeOut(5000, sayhello);
$Context.setTimeOut(0, sayhello, "ifan");
print('Line 13');
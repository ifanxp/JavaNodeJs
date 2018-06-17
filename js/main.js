print(module.file);
var common = require('js/common.js');
print(common.greeting('Liqingfeng'));

function sayhello(name) {
    print("hello "+name);
}

global.printType(sayhello);

//global.calltest(sayhello);
//global.setTimeOut(5000, sayhello);
var timierid = global.setTimeOut(5000, sayhello, "ifan");
print(timierid);
print('Line 13');
global.clearTimeOut(timierid);


var intvid = global.setInterval(1000, sayhello, "interval");
global.setTimeOut(10000, function(id){
    global.clearTimeOut(id);
}, intvid);

1、手写注解，里面定义url的值
2、手写servlet, 实现两个方法doGet和doPost加上自定义注解，赋予其url地址
3、整个项目启动的时候，扫描含有注解的文件，并放到map中，key为url，value为全类名
4、发送请求，使用socket实现，发送url和参数
5、拿到socket返回的字符串，处理拿到url和参数
6、通过url找到map中对应的key并利用反射拿到selvet中的方法
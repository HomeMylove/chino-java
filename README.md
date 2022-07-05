# chino-java
> Chino bot based on Java

## 编写一个插件
1.继承 CommonPlugin 类以编写一个普通插件

```java
import com.homemylove.core.load.CommonPlugin;
import com.homemylove.core.load.Help;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;

import java.util.List;
import java.util.Map;

public class ExampleCommonPlugin extends CommonPlugin {

    {
        List<String> banList = getBanList();
        List<String> perList = getPerList();
    }

    @Override
    public boolean run(RequestBody requestBody, Robot robot) {
        // ... code here
        return false;
    }

    @Override
    public Help getHelp(RequestBody requestBody, Robot robot) {
        Help help = new Help("PluginName");
        Map<String, String> helpMap = help.getHelpMap();
        helpMap.put("operation1", "order1");
        helpMap.put("operation2", "order2");
        // ...
        // ... code here

        help.setPass(isPass(requestBody.getGroupId()));
        return help;
    }
}
```
> 1.一个CommonPlugin插件应该实现 run 方法。该方法的返回值为 boolean 类型。  
> true 代表消息的传递在此结束  
> false 代表消息继续向下一个插件传递
> 
> 2.CommonPlugin 插件应该实现 getHelp 方法。该方法返回一个 Help 对象。  
> <span style="background-color:yellow">注意,Help对象应该至少具有一个名字</span>
> 确保你的插件都拥有不同的名字
> 
> 3.可以在代码块中对 banList 和 perList 进行初始化。它们分别代表禁用群组和仅允许通过的群组。

2.继承 EnhancePlugin 类以编写一个辅助插件

```java
import com.homemylove.core.load.EnhancePlugin;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;

public class ExampleEnhancePlugin extends EnhancePlugin {

    @Override
    public boolean run(RequestBody requestBody, Robot robot){
        
        return false;
    }
}
```
> 1.一个 EnhancePlugin 只需要实现 run 方法
> 
> 2.不允许设置禁用群组和仅允许通过群组

## 让插件生效
有两种方式让插件生效  
1.为插件添加 @BotPlugin 注解，注意name属性应该是唯一的。  
注意插件应该统一存放在 chino.config.xml 配置文件中 <plugin-position>所设定的包内

2.在applicationContext.xml 文件中添加 JavaBean  

> 使用applicationContext.xml配置Bean时，Bean的书写顺序也会影响插件生效的顺序，因此推荐这种写法  
> 如果未配置Bean，而使用了@BotPlugin注解，这些插件都将按照全类名排序并在最后生效。
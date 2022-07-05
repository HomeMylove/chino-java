package com.homemylove.core.load;

import com.homemylove.Main;
import com.homemylove.core.handlers.RequestHandler;
import com.homemylove.core.ioc.BeanFactory;
import com.homemylove.core.ioc.impl.BeanFactoryImpl;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.utils.ClassScanner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class RobotFactory {
    private static final Robot robot = new Robot();

    public static Robot getRobot(){
        return robot;
    }

    public static Robot create() {
        return create("chino.config.xml");
    }
    public static Robot create(String path) {
        if (path == null) {
            throw new RuntimeException("no file");
        }
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder documentBuilder = null;
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            NodeList beanNodeList = document.getElementsByTagName("config");
            Node configList = beanNodeList.item(0);
            NodeList childNodes = configList.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item != null && item.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeName = item.getNodeName();
                    switch (nodeName) {
                        case "robot-name":
                            robot.setName(item.getTextContent());
                            break;
                        case "self-id":
                            robot.setSelfId(item.getTextContent());
                            break;
                        case "nicknames":
                            List<String> nicknames = robot.getNicknames();
                            NodeList nicknameChildNodes = item.getChildNodes();
                            for (int j = 0; j < nicknameChildNodes.getLength(); j++) {
                                Node item1 = nicknameChildNodes.item(j);
                                if (item1 != null && item1.getNodeType() == Node.ELEMENT_NODE && item1.getNodeName().equals("nickname")) {
                                    nicknames.add(item1.getTextContent());
                                }
                            }
                            break;
                        case "superusers":
                            List<String> superusers = robot.getSuperusers();
                            NodeList superuserChildNodes = item.getChildNodes();
                            for (int j = 0; j < superuserChildNodes.getLength(); j++) {
                                Node item1 = superuserChildNodes.item(j);
                                if (item1 != null && item1.getNodeType() == Node.ELEMENT_NODE && item1.getNodeName().equals("superuser")) {
                                    superusers.add(item1.getTextContent());
                                }
                            }
                            break;
                        case "listen-host":
                            robot.setListenHost(item.getTextContent());
                            break;
                        case "listen-port":
                            robot.setListenPort(Integer.parseInt(item.getTextContent()));
                            break;
                        case "target-host":
                            robot.setTargetHost(item.getTextContent());
                            break;
                        case "target-port":
                            robot.setTargetPort(item.getTextContent());
                            break;
                        case "plugin-position":
                            robot.setPluginPosition(item.getTextContent());
                            break;
                        default:
                            break;
                    }
                }
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        robot.init();
        loadPlugin();
        loadHandler();
        return robot;
    }

    private static void loadPlugin(){
        Map<String, BasePlugin> robotPlugins = robot.getPlugins();

        // 从 bean 加载
        // 有顺序
        BeanFactoryImpl beanFactory = new BeanFactoryImpl();
        Map<String, Object> beanMap = beanFactory.getBeanMap();
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Object value = entry.getValue();
            if(value instanceof BasePlugin){
                BasePlugin basePlugin = (BasePlugin) value;
                String pluginName = entry.getKey();
                robotPlugins.put(pluginName,basePlugin);
            }
        }

        // 扫描加载
        // 排在后面
        String path = "com.homemylove.chino.plugins";
        String position = robot.getPluginPosition();
        path = position != null ? position : path;
        List<String> plugins = ClassScanner.scanClasses(path);
        for(int i = 0; i < plugins.size();i++){
            String className = plugins.get(i);
            try {
                Class<?> clazz = Class.forName(className);
                boolean flag = clazz.isAnnotationPresent(BotPlugin.class);
                if(flag){
                    BotPlugin annotation = clazz.getAnnotation(BotPlugin.class);
                    String pluginName = annotation.name();
                    BasePlugin o =(BasePlugin) clazz.newInstance();
                    robotPlugins.put(pluginName,o);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void loadHandler(){
        robot.bind("/",new RequestHandler());
    }

}
